package re.security.config.principal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import re.security.entity.User;
import re.security.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceCustom implements UserDetailsService {
    private final IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        User user = userRepository.loadUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName().name()));
        UserDetailCustom detailCustom = UserDetailCustom.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(grantedAuthorities)
                .build();
        return detailCustom;
    }
}
