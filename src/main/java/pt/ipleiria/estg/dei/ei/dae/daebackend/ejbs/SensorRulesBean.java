package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.SensorRole;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.TypeOfSensor;

@Stateless
public class SensorRulesBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Produto produto,TypeOfSensor typeOfSensor, double val_max, double val_min){
        entityManager.persist(new SensorRole(produto,typeOfSensor,val_max,val_min));
    }
}
