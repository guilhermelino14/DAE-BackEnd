package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Consumidor;

@Stateless
public class ConsumidorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String password, String name, String email) {
        entityManager.persist(new Consumidor(username, password, name, email));
    }
}
