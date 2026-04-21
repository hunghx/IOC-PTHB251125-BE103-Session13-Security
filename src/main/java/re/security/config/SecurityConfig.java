package re.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// Phân quyền theo phương thức
@EnableMethodSecurity
public class SecurityConfig {
    // Tạo cấu hình tùy chỉnh
    // Các tài khoản mặc định (username, password, ROLE)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private UserDetailsService userDetailsService;

    // Tài khoản mặc định
//    @Bean
//    public UserDetailsService userDetailsService() {
//        // ADMIN
//        UserDetails admin = User.withUsername("admin123")
//                .password(passwordEncoder().encode("123456$"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withUsername("hunghx")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER")
//                .build();
//        UserDetails man = User.withUsername("manager01")
//                .password(passwordEncoder().encode("123456"))
//                .roles("MANAGER")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user, man);
//    }
    // thực hiện xác thực thông qua userdertail service và password encoder
    @Bean
    public AuthenticationProvider  authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager(); //
    }
    // Tầng xác thực và phân quyền
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        authenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password))
        http.cors(AbstractHttpConfigurer::disable)   // tắt cors
                .csrf(AbstractHttpConfigurer::disable) // tắt csrf
                // phân quyền cho các API theo đường dẫn
                .authorizeHttpRequests(
                        req->
                                req.requestMatchers("/api/v1/admin/**").hasAuthority("ROLE_ADMIN") // admin ms đc truy cập api này
                                        .requestMatchers("/api/v1/user/**").hasRole("USER")
                                        .requestMatchers("/api/v1/manager/**").hasRole("MANAGER")
                                        .anyRequest().authenticated() // các api khác thì phải xác thực thì ms vào đc
                )
                // cơ chế dăng nhâp http basic
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults()); // mặc đinh username pass : /login - POST
        return http.build();
    }
}
