package re.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class AdminController {
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String roleForUser(){
        return "You are an user";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String roleForAdmin(){
        return "You are an admin";
    }
    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public String roleForManager(){
        return "You are an manager";
    }
    @GetMapping("/admin-manager")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public String roleForManagerOrAdmin(){
        return "You are an manager or admin";
    }

}
