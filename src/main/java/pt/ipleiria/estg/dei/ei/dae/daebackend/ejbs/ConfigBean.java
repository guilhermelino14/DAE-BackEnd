package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;

import java.util.Date;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    private FabricanteBean fabricanteBean;
    @EJB
    private OperadorBean operadorBean;
    @EJB
    private ConsumidorBean consumidorBean;
    @EJB
    private EmbalagemProdutoBean embalagemProdutoBean;
    @EJB
    private EmbalagemTransporteBean embalagemTransporteBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ObservacoesBean observacoesBean;
    @EJB
    private ProdutoBean produtoBean;

    @EJB
    private ProdutoFisicoBean produtoFisicoBean;
    @EJB
    private EncomendaBean encomendaBean;
    @EJB
    private SensorRulesBean sensorRulesBean;

    @PostConstruct
    public void populateDB() throws MyEntityNotFoundException {
        fabricanteBean.create("fabricante1", "fabricante1", "João Antonio", "fabricante1@mail.com");
        operadorBean.create("operador1", "operador1", "GLS", "operador1@mail.com");
        consumidorBean.create("consumidor1", "consumidor1", "Tomas Andrade", "consumidor1@mail.com", "Morada1");
        sensorBean.create(TypeOfSensor.TEMPERATURA);
        sensorBean.create(TypeOfSensor.HUMIDADE);
        observacoesBean.create(sensorBean.find(1), 50, "ºC", "Temperatura atingiu 50ºC", new Date());
        observacoesBean.create(sensorBean.find(2), 150, "km/h","Velocidade atingiu 150km/h", new Date());
//        produtoBean.create("produto1", "categoria1", "descricao1");
//        produtoBean.associateFabricante(1, "fabricante1");
        createAListOfProducts();
        sensorRulesBean.create(produtoBean.find(1), TypeOfSensor.TEMPERATURA, 50, 25);
        sensorRulesBean.create(produtoBean.find(1), TypeOfSensor.HUMIDADE, 50, 25);
    }

    public void createAListOfProducts() throws MyEntityNotFoundException{
        produtoBean.create("Cholocate 1 unidade", "Chocolate", "Uma barra de chocolate", 1 , null, "fabricante1");
        produtoBean.create("Cholocate 4 unidade", "Chocolate", "Quatro barra de chocolate", 4, null,"fabricante1");
        produtoBean.create("Cholocate 12 unidade", "Chocolate", "Doze barra de chocolate", 12, TypeOfSensor.HUMIDADE,"fabricante1");
        produtoBean.associateFabricante(1, "fabricante1");
        produtoBean.associateFabricante(2, "fabricante1");
        produtoBean.associateFabricante(3, "fabricante1");
    }
}
