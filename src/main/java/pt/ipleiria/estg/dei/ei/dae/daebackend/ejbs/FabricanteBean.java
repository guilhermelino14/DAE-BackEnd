package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Fabricante;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Hasher;

@Stateless
public class FabricanteBean {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    public void create(String username, String password, String name, String email) {
        entityManager.persist(new Fabricante(username, hasher.hash(password), name, email));
    }
}
