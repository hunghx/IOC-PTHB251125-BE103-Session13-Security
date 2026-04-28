package re.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import re.security.config.jwt.JwtService;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
    @Bean
    public CommandLineRunner runner(JwtService jwtService) {
        return args -> {
            String token = jwtService.generateAccessToken("hunghx");
            System.out.println("token 1 : "+ token);
            System.out.println("refresh token 1 : "+ jwtService.generateRefreshToken("hunghx"));
        //kiểm tra token
            System.out.println("is valid : "+jwtService.validateToken(token));

            // giải m
            System.out.println("username : "+jwtService.getUsernameFromToken(token));
        };
    }

}
