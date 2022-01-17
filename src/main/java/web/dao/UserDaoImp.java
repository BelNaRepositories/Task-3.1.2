package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    private final RoleService roleService;
    @PersistenceContext
    private EntityManager entityManager;

    public UserDaoImp(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select  user from User user", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        Collection<Role> currentRoles = roleService.getAllRoles();
        Collection<Role> userRoles = user.getRoles();
        Collection<Role> rolesForNewUser = new HashSet<>();
        for (Role currentRole : currentRoles) {
            for (Role userRole : userRoles) {
                if (userRole.getRole().equals(currentRole.getRole())) {
                    rolesForNewUser.add(roleService.getById(currentRole.getId()));
                }
            }
        }
        user.setRoles(rolesForNewUser);
        entityManager.persist(user);
    }

    @Override
    public User readUser(Long id) { return entityManager.find(User.class, id); }

    @Override
    public User delete(Long id) {
        User user = readUser(id);
        entityManager.remove(user);
        return  user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(User user) {
        Collection<Role> currentRoles = roleService.getAllRoles();
        Collection<Role> userRoles = user.getRoles();
        Collection<Role> rolesForNewUser = new HashSet<>();
        for (Role currentRole : currentRoles) {
            for (Role userRole : userRoles) {
                if (userRole.getRole().equals(currentRole.getRole())) {
                    rolesForNewUser.add(roleService.getById(currentRole.getId()));
                }
            }
        }
        user.setRoles(rolesForNewUser);
        entityManager.merge(user);
    }

    @Override
    public User getUserByUsername(String ssoId) {
        return entityManager.createQuery("select user from User user where user.ssoId = :ssoId", User.class)
                .setParameter("ssoId", ssoId)
                .getSingleResult();
    }
}

