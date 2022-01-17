package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.User;
import java.util.List;
public interface UserService extends UserDetailsService{
    List<User> getAllUsers();
    void saveUser(User user);
    User readUser(Long id);
    User delete(Long id);
    void update(User user);
    UserDetails loadUserByUsername(String ssoId);
}

