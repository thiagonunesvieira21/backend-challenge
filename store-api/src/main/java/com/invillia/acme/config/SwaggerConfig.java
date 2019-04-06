package com.invillia.acme.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Classe de configuração da documentação (Swagger) das APIs REST
 * @author thiago.nvieira
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	 @Value("${keycloak.auth-server-url}")
	 private String AUTH_SERVER;

	 @Value("${keycloak.credentials.secret}")
	 private String CLIENT_SECRET;

	 @Value("${keycloak.resource}")
	 private String CLIENT_ID;

	 @Value("${keycloak.realm}")
	 private String REALM;

	 private static final String ALLOWED_PATHS = "/.*";
	 private static final String OAUTH_NAME = "spring_oauth";
	 private static final String GROUP_NAME = "invillia-app";
	 
	/**
	 * Define a confiuração do Swaager(Documentação dos serviços REST)
	 * @return Docket
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.invillia.acme.controller"))
				.paths(regex("/api/.*"))
				.build()
				.pathMapping("/")
				.apiInfo(apiInfo())
				.select()
			    .paths(regex(ALLOWED_PATHS))
			    .build()
			    .securitySchemes(Arrays.asList(securityScheme()))
			    .securityContexts(Arrays.asList(securityContext()));
	}
	
	@Bean
	public SecurityConfiguration security() {
	   return SecurityConfigurationBuilder.builder()
	    .realm(REALM)
	    .clientId(CLIENT_ID)
	    .clientSecret(CLIENT_SECRET)
	    .scopeSeparator(" ")
        .useBasicAuthenticationWithAccessCodeGrant(true)
	    .build();
	 }
	
	 private SecurityScheme securityScheme() {
		   GrantType grantType =
		    new AuthorizationCodeGrantBuilder()
		        .tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/realms/" + REALM + "/protocol/openid-connect/token", GROUP_NAME))
		        .tokenRequestEndpoint(
		            new TokenRequestEndpoint(AUTH_SERVER + "/realms/" + REALM + "/protocol/openid-connect/auth", CLIENT_ID, CLIENT_SECRET))
		        .build();

		SecurityScheme oauth =
		    new OAuthBuilder()
		        .name(OAUTH_NAME)
		        .grantTypes(Arrays.asList(grantType))
		        .scopes(Arrays.asList(scopes()))
		        .build();
		return oauth;
	}

	private AuthorizationScope[] scopes() {
		 AuthorizationScope[] scopes = {
		 };
		 return scopes;
	}

	private SecurityContext securityContext() {
		 return SecurityContext.builder()
		     .securityReferences(Arrays.asList(new SecurityReference(OAUTH_NAME, scopes())))
		     .forPaths(PathSelectors.regex(ALLOWED_PATHS))
		     .build();
	}
	 
	/**
	 * Define a confiuração das informações da API, exemplo: title, descrition, version...
	 * @return ApiInfo
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("REST API Store")
				.description("REST services to stores - CRUD")
				.version("0.0.1-SNAPSHOT").build();
	}
}
