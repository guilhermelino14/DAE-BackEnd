package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Observacoes;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;

import java.util.Date;
import java.util.List;

@Stateless
public class ObservacoesBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Sensor sensor, double value, String medida, String observacao, Date data) {

        entityManager.persist(new Observacoes(sensor, value, medida, observacao, data));
    }

    public List<Observacoes> getAll() {
        return entityManager.createNamedQuery("getAllObservacoes", Observacoes.class).getResultList();
    }

    public Observacoes find(int id) {
        return entityManager.find(Observacoes.class, id);
    }

    public List<Observacoes> findBySensorId(int id) {
        return entityManager.createNamedQuery("getObservacoesBySensorId", Observacoes.class).setParameter("id", id).getResultList();
    }

    public void deleteWhereSensorId(int id) {
        entityManager.createNamedQuery("deleteObservacoesBySensorId").setParameter("id", id).executeUpdate();
    }
}
