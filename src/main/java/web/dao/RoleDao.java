package web.dao;

import web.model.Role;
import java.util.List;

public interface RoleDao {
      List<Role> getAllRoles();
      Role getById(Long id);
      Role getByName(String name);
}

