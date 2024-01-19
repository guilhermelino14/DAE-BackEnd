package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class EmbalagemProdutoBean {
    @PersistenceContext
    private EntityManager entityManager;

    public EmbalagemProduto create(String nome, double altura, double largura){
        EmbalagemProduto embalagemProduto = new EmbalagemProduto(nome, altura, largura);
        entityManager.persist(embalagemProduto);
        return embalagemProduto;
    }

    public List<EmbalagemProduto> getAll()  {
        return entityManager.createNamedQuery("getAllEmbalagens", EmbalagemProduto.class).getResultList();
    }

    public EmbalagemProduto find(int id) throws MyEntityNotFoundException {
        EmbalagemProduto embalagemProduto = entityManager.find(EmbalagemProduto.class, id);
        if (embalagemProduto == null) {
            throw new MyEntityNotFoundException("EmbalagemProduto with id '" + id + "' not found.");
        }
        return embalagemProduto;
    }

    public void delete(int id) throws MyEntityNotFoundException {
        EmbalagemProduto embalagemProduto = find(id);
        entityManager.remove(embalagemProduto);
    }

    public void associarEmbalagemAoSensor(int idEmbalagem, int idSensor) throws MyEntityNotFoundException {
        EmbalagemProduto embalagemProduto = find(idEmbalagem);
        Sensor sensor = entityManager.find(Sensor.class, idSensor);
        if(sensor == null){
            throw new MyEntityNotFoundException("Sensor with id '" + idSensor + "' not found.");
        }
        if (embalagemProduto.getSensores().contains(sensor)) {
            throw new MyEntityNotFoundException("Sensor with id '" + idSensor + "' already associated with Embalagem with id '" + idEmbalagem + "'");
        }
        embalagemProduto.addSensor(sensor);
        sensor.addEmbalagem(embalagemProduto);
    }

    public void desassociarEmbalagemAoSensor(int idEmbalagem, int idSensor) throws MyEntityNotFoundException {
        EmbalagemProduto embalagemProduto = find(idEmbalagem);
        Sensor sensor = entityManager.find(Sensor.class, idSensor);
        if(sensor == null){
            throw new MyEntityNotFoundException("Sensor with id '" + idSensor + "' not found.");
        }
        if (!embalagemProduto.getSensores().contains(sensor)) {
            throw new MyEntityNotFoundException("Sensor with id '" + idSensor + "' not associated with Embalagem with id '" + idEmbalagem + "'");
        }
        embalagemProduto.removeSensor(sensor);
        sensor.removeEmbalagem(embalagemProduto);
    }
}
