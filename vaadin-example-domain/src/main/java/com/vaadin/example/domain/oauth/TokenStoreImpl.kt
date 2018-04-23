package com.vaadin.example.domain.oauth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
@Qualifier("tokenStoreImpl")
class TokenStoreImpl @Autowired constructor(dataSource: DataSource) : JdbcTokenStore(dataSource)