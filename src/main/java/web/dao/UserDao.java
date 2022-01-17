package web.dao;

import web.model.User;
import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    void saveUser(User user);
    User readUser(Long id);
    User delete(Long id);
    void update(User user);
    User getUserByUsername(String ssoId);
}
