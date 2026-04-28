package re.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import re.security.config.jwt.JwtService;
import re.security.dto.request.FormLogin;
import re.security.dto.request.FormRegister;
import re.security.dto.response.JwtResponse;
import re.security.entity.User;
import re.security.repository.IRoleRepository;
import re.security.repository.IUserRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenServiceImpl implements IAuthenSevice{
    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public void register(FormRegister request) {
        // tạo mới user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setFullName(request.getFullName());
        user.setRole(roleRepository.findByRoleName(request.getRoleName()).orElseThrow(()->new RuntimeException("Role not found")));
        // lưu
        userRepository.save(user);
    }

    @Override
    public JwtResponse login(FormLogin request) {
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()
            ));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("username or password incorrect");
        }
        User user = userRepository.loadUserByUsername(request.getUsername()).orElseThrow();
        // trả về JWT Response
        JwtResponse res = JwtResponse.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .accessToken(jwtService.generateAccessToken(user.getUsername()))
                .expirationDate(new Date(new Date().getTime() + 15*60*1000))
                .refreshToken(null)
                .build();
        return res;
    }
}
