package fun.witt.relation.config;

import fun.witt.common.template.JWTTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {

    @Value("${jwt.signed}")
    private String signed;
    @Value("${jwt.issuer}")
    private String issuer;

    @Bean
    public JWTTemplate jwtTemplate() {
        return new JWTTemplate(signed, issuer);
    }
}
