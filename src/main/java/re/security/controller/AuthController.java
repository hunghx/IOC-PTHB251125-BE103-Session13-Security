package re.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import re.security.dto.request.FormLogin;
import re.security.dto.request.FormRegister;
import re.security.dto.response.JwtResponse;
import re.security.service.IAuthenSevice;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final IAuthenSevice authenSevice;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody FormRegister  request){
        authenSevice.register(request);
        return new ResponseEntity<>("Register Successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody FormLogin request){
        return ResponseEntity.ok(authenSevice.login(request));
    }
}
