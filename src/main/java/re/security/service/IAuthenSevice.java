package re.security.service;

import re.security.dto.request.FormLogin;
import re.security.dto.request.FormRegister;
import re.security.dto.response.JwtResponse;

public interface IAuthenSevice {
    // Đăng nhập
    JwtResponse login(FormLogin request);
    // đăng ký
    void register(FormRegister request);
}
