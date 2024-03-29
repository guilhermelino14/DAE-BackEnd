package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Consumidor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Hasher;

import java.util.List;

@Stateless
public class ConsumidorBean {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    public void create(String username, String password, String name, String email, String morada) {
        entityManager.persist(new Consumidor(username, hasher.hash(password), name, email, morada));
    }

    public Consumidor find(String username) throws MyEntityNotFoundException {
        Consumidor consumidor = entityManager.find(Consumidor.class, username);
        if(consumidor == null){
            throw new MyEntityNotFoundException("Consumidor with username '" + username + "' not found.");
        }
        return consumidor;
    }

    public List<Encomenda> getAllEncomendasFromConsumidor(String username) {
        return entityManager.createNamedQuery("getAllEncomendasByConsumidorUsername", Encomenda.class).setParameter("username", username).getResultList();
    }

    public List<Consumidor> getAll() {
        return entityManager.createNamedQuery("getAllConsumidores", Consumidor.class).getResultList();
    }
}
