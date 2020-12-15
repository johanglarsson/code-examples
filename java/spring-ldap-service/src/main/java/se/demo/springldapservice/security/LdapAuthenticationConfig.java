package se.demo.springldapservice.security;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@Slf4j
@Validated
@Configuration
@EnableWebSecurity
@Setter
@AllArgsConstructor
public class LdapAuthenticationConfig implements InitializingBean {

    private RoleMapping roleMapping;

    private SecurityProperties securityProperties;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            log.info("Configuring  LDAP {}", securityProperties.getLdap());
            auth.authenticationProvider(ldapAuthenticationProvider())
                .eraseCredentials(false);
    }

    @Bean
    LdapContextSource contextSource() {

        final LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(securityProperties.getLdap().getUrl());
        contextSource.setUserDn(securityProperties.getLdap().getUser());
        contextSource.setPassword(securityProperties.getLdap().getPassword());
        return contextSource;
    }

    @Bean
    LdapAuthenticationProvider ldapAuthenticationProvider() {
        final BindAuthenticator bindAuthenticator = new BindAuthenticator(contextSource());
        bindAuthenticator.setUserSearch(userSearch());
        final DefaultLdapAuthoritiesPopulator defaultLdapAuthoritiesPopulator = new DefaultLdapAuthoritiesPopulator(contextSource(), securityProperties
                .getLdap()
                .getSearchBase());
        defaultLdapAuthoritiesPopulator.setSearchSubtree(true);
        defaultLdapAuthoritiesPopulator.setIgnorePartialResultException(true);
        defaultLdapAuthoritiesPopulator.setGroupSearchFilter("(member={0})");
        defaultLdapAuthoritiesPopulator.setAuthorityMapper(this::mapAuthorityRoles);
        return new LdapAuthenticationProvider(bindAuthenticator, defaultLdapAuthoritiesPopulator);
    }

    private GrantedAuthority mapAuthorityRoles(final Map<String, List<String>> roles) {
        final List<String> roleList = roles.keySet().stream()
                                           .map(roles::get)
                                           .flatMap(Collection::stream)
                                           .collect(toList());
        return new SimpleGrantedAuthority(roleMapping.doRoleMap(roleList));

    }

    @Bean
    FilterBasedLdapUserSearch userSearch() {
        final FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch(securityProperties.getLdap()
                                                                                                     .getSearchBase(), "(sAMAccountName={0})", contextSource());
        userSearch.setSearchSubtree(true);
        return userSearch;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        requireNonNull(securityProperties, "securityProperties is null");
        requireNonNull(roleMapping, "roleMapping is null");
    }


}