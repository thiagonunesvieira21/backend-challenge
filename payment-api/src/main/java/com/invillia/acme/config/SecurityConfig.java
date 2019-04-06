package com.invillia.acme.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * Classe de configuração da segurança
 * @author thiago.nvieira
 *
 */
@Configuration
@EnableOAuth2Client
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Value("${security.oauth2.client.clientId}")
    private String clientId;
 
    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;
 
    @Value("${security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;
 
    @Value("${security.oauth2.client.userAuthorizationUri}")
    private String userAuthorizationUri;
 
    @Override
    public void configure( final WebSecurity web ) throws Exception {
        web.ignoring().antMatchers( "/**");
    }
 
 
    @Bean
    public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(accessTokenUri);
        return details;
    }
 
    @Bean
    public OAuth2RestTemplate createRestTemplate(OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails(), clientContext);
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
	    http
        .authorizeRequests()
        .antMatchers(
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/configuration/ui",
            "/swagge‌​r-ui.html",
            "/swagger-resources/configuration/security").permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.antMatchers("/api/v1/*").hasRole("user")
			.anyRequest().permitAll();
	}

}
