package com.vaadin.example.api.config.oauth;

import com.vaadin.example.domain.dto.ClientDto;
import com.vaadin.example.domain.exeption.BusinessException;
import com.vaadin.example.domain.oauth.OAuthClientDetails;
import com.vaadin.example.feignclient.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("clientDetailsServiceImpl")
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    private ClientService clientService;

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        try {
            ClientDto client = this.clientService.findByClientId(s);
            return this.convertToClientDetail(client);

        } catch (BusinessException e) {
            throw e;
        }
    }

    private ClientDetails convertToClientDetail(ClientDto client) {
        OAuthClientDetails result = new OAuthClientDetails();

        result.setClients(client.toClient());
        result.setClientId(client.getClientId());
        result.setClientSecret(client.getClientSecret());
        result.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
        result.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
        result.setScope(client.getScopes());
        result.setAuthorizedGrantTypes(Objects.requireNonNull(client.getGrants()));
        result.setRegisteredRedirectUri(new HashSet<>(Objects.requireNonNull(client.getRedirectUris())));
        result.setAuthorities(Objects.requireNonNull(client.getRoles()).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        return result;
    }
}
