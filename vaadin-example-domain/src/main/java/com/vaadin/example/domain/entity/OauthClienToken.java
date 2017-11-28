package com.vaadin.example.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "oauth_client_token")
public class OauthClienToken {

    @Id
    @Column(name = "authentication_id")
    private String authenticationId;

    @Column(name = "token_id")
    private String tokenId;

    @Lob
    private Byte[] token;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "client_id")
    private String clientId;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Byte[] getToken() {
        return token;
    }

    public void setToken(Byte[] token) {
        this.token = token;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
