package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.TypeOfSensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager entityManager;


    public List<Sensor> getAll(){
        return entityManager.createNamedQuery("getAllSensores", Sensor.class).getResultList();
    }

    public List<Sensor> getAllAvailable(){
        return entityManager.createNamedQuery("getAllSensores", Sensor.class).getResultList().stream()
                .filter(sensor -> sensor.getEmbalagens().isEmpty())
                .collect(Collectors.toList());

    }

    public Sensor create(TypeOfSensor typeOfSensor) {
        Sensor sensor = new Sensor(typeOfSensor);
        entityManager.persist(sensor);
        return sensor;
    }

    public Sensor find(int id) throws MyEntityNotFoundException {
        Sensor sensor = entityManager.find(Sensor.class, id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor with id " + id + " not found.");
        }
        return sensor;
    }

    public boolean update(int id, String nome, String descricao) throws MyEntityNotFoundException {
        Sensor sensor = find(id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor with id " + id + " not found.");
        }
        entityManager.merge(sensor);
        return true;
    }

    public boolean delete(int id) throws MyEntityNotFoundException {
        Sensor sensor = find(id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor with id " + id + " not found.");
        }
        entityManager.remove(sensor);
        return true;
    }

    public void associarSensorAEmbalagem(int idSensor, int idEmbalagem) throws MyEntityNotFoundException {
        Sensor sensor = entityManager.find(Sensor.class, idSensor);
        EmbalagemProduto embalagemProduto = entityManager.find(EmbalagemProduto.class, idEmbalagem);
        if (sensor != null && embalagemProduto != null) {
            sensor.addEmbalagem(embalagemProduto);
            embalagemProduto.addSensor(sensor);
        }
    }

    public void dissociarSensoresFromEmbalagem(int idEmbalagem) throws MyEntityNotFoundException {
        EmbalagemProduto embalagemProduto = entityManager.find(EmbalagemProduto.class, idEmbalagem);
        if (embalagemProduto != null) {
            embalagemProduto.getSensores().forEach(sensor -> {
                sensor.removeEmbalagem(embalagemProduto);
            });
            embalagemProduto.getSensores().clear();
        }
    }
}
