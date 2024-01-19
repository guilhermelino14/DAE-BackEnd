package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class EncomendaBean {

    @PersistenceContext
    private EntityManager entityManager;


    public Encomenda create(Consumidor consumidor) {
        Encomenda encomenda = new Encomenda(consumidor, EncomendaStatus.PENDENTE, new Date(), "Fabrica");
        entityManager.persist(encomenda);
        return encomenda;
    }

    public Encomenda find(int id) throws MyEntityNotFoundException {
        Encomenda encomenda = entityManager.find(Encomenda.class, id);
        if (encomenda == null) {
            throw new MyEntityNotFoundException("Encomenda with id '" + id + "' not found.");
        }
        return encomenda;
    }

    public List<Encomenda> getAll() {
        return entityManager.createNamedQuery("getAllEncomendas", Encomenda.class).getResultList();
    }

    public ProdutoFisico addProduct(int id, int productReference) throws MyEntityNotFoundException {
        Encomenda encomenda = find(id);
        ProdutoFisico produtoFisico = entityManager.find(ProdutoFisico.class, productReference);
        if (produtoFisico == null) {
            throw new MyEntityNotFoundException("ProdutoFisico with id '" + id + "' not found.");
        }
        encomenda.addProdutoFisico(produtoFisico);
        produtoFisico.addEncomenda(encomenda);
        return produtoFisico;
    }

    public List<Encomenda> getEncomendasFromHimAndNotPendentes(String operadorUsername) {
        return entityManager.createNamedQuery("getAllEncomendasFromHimAndNotPendentes", Encomenda.class).setParameter("operadorUsername", operadorUsername).setParameter("status", EncomendaStatus.PENDENTE).getResultList();
    }

    public void updateStatus(int id, EncomendaStatus status) throws MyEntityNotFoundException {
        Encomenda encomenda = find(id);
        EmbalagemTransporte embalagemTransporte = encomenda.getEmbalagensTransporte().get(0);
        Sensor sensorGPS = null;
        // get sensor typeOfSensor GPS from embalagemTransporte
        for (Sensor sensor : embalagemTransporte.getSensores()) {
            if (sensor.getTypeOfSensor() == TypeOfSensor.GPS) {
                sensorGPS = sensor;
            }
        }
        if (status == EncomendaStatus.RECOLHIDA){
            encomenda.setLocalizacao("Armazem");
            entityManager.persist(new Observacoes(sensorGPS,0,"","Encomenda foi recolhida, está no armazem", new Date()));
        }
        if (status == EncomendaStatus.EM_TRANSITO){
            encomenda.setLocalizacao("Rua do Quim");
            entityManager.persist(new Observacoes(sensorGPS,0,"","Encomenda esta na Rua do Quim", new Date()));
        }
        if (status == EncomendaStatus.ENTREGUE){
            encomenda.setLocalizacao(encomenda.getConsumidor().getMorada());
        }
        encomenda.setStatus(status);
        entityManager.merge(encomenda);
    }

    public void updateOperador(int id, Operador operador) throws MyEntityNotFoundException {
        Encomenda encomenda = find(id);
        encomenda.setOperador(operador);
        entityManager.merge(encomenda);
    }
}
