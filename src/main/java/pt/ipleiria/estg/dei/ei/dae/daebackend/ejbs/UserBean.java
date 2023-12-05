package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.User;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager entityManager;

    public User find(String username) {
        return entityManager.find(User.class, username);
    }

    public User findOrFail(String username) {
        var user = entityManager.getReference(User.class, username);
        Hibernate.initialize(user);
        return user;
    }

    public boolean canLogin(String username, String password) {
        var user = find(username);
        return user != null && user.getPassword().equals(password);
    }
}
