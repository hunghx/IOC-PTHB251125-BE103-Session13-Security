package re.security.dto.request;

import lombok.Getter;
import lombok.Setter;
import re.security.entity.RoleName;
@Setter
@Getter
public class FormRegister {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String fullName;
    private RoleName roleName;
}
