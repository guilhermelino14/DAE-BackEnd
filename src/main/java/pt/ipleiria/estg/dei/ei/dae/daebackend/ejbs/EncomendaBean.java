package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;

import java.util.Date;
import java.util.List;

@Stateless
public class EncomendaBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Operador operador, Consumidor consumidor, Date date, EncomendaStatus status) {
        entityManager.persist(new Encomenda(operador, consumidor, date, status));
    }

    public Encomenda find(int id) {
        return entityManager.find(Encomenda.class, id);
    }

    public List<Encomenda> getAll() {
        return entityManager.createNamedQuery("getAllEncomendas", Encomenda.class).getResultList();
    }

    public List<Encomenda> getEncomendaByConsumidorUsername(String username){
        return entityManager.createNamedQuery("getAllEncomendasByConsumidorUsername", Encomenda.class).setParameter("username", username).getResultList();
    }
}
