package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.User;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager entityManager;

    public User find(String username) {
        return entityManager.find(User.class, username);
    }
}
