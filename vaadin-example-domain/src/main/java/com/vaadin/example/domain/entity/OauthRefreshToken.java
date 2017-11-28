package com.vaadin.example.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "oauth_refresh_token")
public class OauthRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_id")
    private String tokenId;

    @Lob
    private Byte[] token;

    @Lob
    private Byte[] authentication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Byte[] authentication) {
        this.authentication = authentication;
    }
}
