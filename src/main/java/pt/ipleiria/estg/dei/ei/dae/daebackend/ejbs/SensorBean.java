package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Sensor> getAll(){
        return entityManager.createNamedQuery("getAllSensores", Sensor.class).getResultList();
    }

    public void create(String nome, String descricao) {
        entityManager.persist(new Sensor(nome, descricao));
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
        sensor.setNome(nome);
        sensor.setDescricao(descricao);
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
        Sensor sensor = find(idSensor);
        EmbalagemProduto embalagemProduto = entityManager.find(EmbalagemProduto.class, idEmbalagem);
        if (sensor != null && embalagemProduto != null) {
            sensor.addEmbalagem(embalagemProduto);
            embalagemProduto.addSensor(sensor);
        }
    }
}
