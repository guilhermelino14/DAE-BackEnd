package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class EmbalagemTransporteBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String nome, double altura, double largura){
        entityManager.persist(new EmbalagemTransporte(nome, altura, largura));
    }

    public EmbalagemTransporte find(int id) {
        return entityManager.find(EmbalagemTransporte.class, id);
    }

    public void remove(int id) {
        EmbalagemTransporte embalagemTransporte = find(id);
        if (embalagemTransporte != null) {
            entityManager.remove(embalagemTransporte);
        }
    }

    public List<EmbalagemTransporte> getAll()  {
        return entityManager.createNamedQuery("getAllEmbalagensTransporte", EmbalagemTransporte.class).getResultList();
    }

    public void addEncomenda(int idEmbalagemTransporte, int idEncomenda) throws Exception {
        EmbalagemTransporte embalagemTransporte = find(idEmbalagemTransporte);
        Encomenda encomenda = entityManager.find(Encomenda.class, idEncomenda);
        if (embalagemTransporte != null && encomenda != null) {
            if (!embalagemTransporte.getEncomendas().isEmpty()) {
                throw new Exception("Embalagem de Transporte ja esta associada a uma encomenda");
            }
            embalagemTransporte.addEncomenda(encomenda);
            encomenda.addEmbalagemTransporte(embalagemTransporte);
        }
    }

    public void removeEncomenda(int idEmbalagemTransporte, int idEncomenda) {
        EmbalagemTransporte embalagemTransporte = find(idEmbalagemTransporte);
        Encomenda encomenda = entityManager.find(Encomenda.class, idEncomenda);
        if (embalagemTransporte != null && encomenda != null) {
            embalagemTransporte.removeEncomenda(encomenda);
            encomenda.removeEmbalagemTransporte(embalagemTransporte);
        }
    }

    public void associarEmbalagemAoSensor(int idEmbalagem, int idSensor) throws MyEntityNotFoundException {
        EmbalagemTransporte embalagemTransporte = find(idEmbalagem);
        Sensor sensor = entityManager.find(Sensor.class, idSensor);
        if (embalagemTransporte != null && sensor != null) {
            if (embalagemTransporte.getSensores().contains(sensor)) {
                throw new MyEntityNotFoundException("Sensor with id " + idSensor + " already associated with Embalagem with id " + idEmbalagem);
            }
            embalagemTransporte.addSensor(sensor);
            sensor.addEmbalagem(embalagemTransporte);
        }
    }

    public void desassociarEmbalagemAoSensor(int idEmbalagem, int idSensor) throws MyEntityNotFoundException {
        EmbalagemTransporte embalagemTransporte = find(idEmbalagem);
        Sensor sensor = entityManager.find(Sensor.class, idSensor);
        if (embalagemTransporte != null && sensor != null) {
            if (!embalagemTransporte.getSensores().contains(sensor)) {
                throw new MyEntityNotFoundException("Sensor with id " + idSensor + " not associated with Embalagem with id " + idEmbalagem);
            }
            embalagemTransporte.removeSensor(sensor);
            sensor.removeEmbalagem(embalagemTransporte);
        }
    }
}
