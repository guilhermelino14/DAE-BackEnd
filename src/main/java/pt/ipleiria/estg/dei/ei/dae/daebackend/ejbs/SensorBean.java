package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Embalagem;
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

    public List<Sensor> getAllInUse(){
        return entityManager.createNamedQuery("getAllSensores", Sensor.class).getResultList().stream()
                .filter(sensor -> !sensor.getEmbalagens().isEmpty())
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
            throw new MyEntityNotFoundException("Sensor with id '" + id + "' not found.");
        }
        return sensor;
    }

    public boolean delete(int id) throws MyEntityNotFoundException {
        Sensor sensor = find(id);
        entityManager.remove(sensor);
        return true;
    }

    public void associarSensorAEmbalagem(int idSensor, int idEmbalagem) throws MyEntityNotFoundException {
        Sensor sensor = find(idSensor);
        Embalagem embalagem = entityManager.find(Embalagem.class, idEmbalagem);
        if (embalagem == null) {
            throw  new MyEntityNotFoundException("Embalagem with id '" + idEmbalagem + "' not found");
        }
        sensor.addEmbalagem(embalagem);
        embalagem.addSensor(sensor);
    }

    public void dissociarSensoresFromEmbalagem(int idEmbalagem) throws MyEntityNotFoundException {
        Embalagem embalagem = entityManager.find(Embalagem.class, idEmbalagem);
        if (embalagem == null) {
            throw  new MyEntityNotFoundException("Embalagem with id " + idEmbalagem + " not found");
        }
        embalagem.getSensores().forEach(sensor -> {
            sensor.removeEmbalagem(embalagem);
        });
        embalagem.getSensores().clear();
    }
}
