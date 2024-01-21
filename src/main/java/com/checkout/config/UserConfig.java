package com.checkout.config;

import com.checkout.model.BearerTokenWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@Order(1)
public class UserConfig {

    /**
     * The No Password encoder has been put for testing purpose.
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * The user has been put for testing purpose.
     * @param passwordEncoder
     * @return
     */
    @Bean
    UserDetailsService userDetails (PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails admin = users
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();
        UserDetails userHavingRole = users
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();
        UserDetails userWithoutRole = users
                .username("test")
                .password(passwordEncoder.encode("test123"))
                .build();
        return new InMemoryUserDetailsManager(admin, userHavingRole, userWithoutRole);
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public BearerTokenWrapper bearerTokenWrapper() {
        return new BearerTokenWrapper();
    }

}
