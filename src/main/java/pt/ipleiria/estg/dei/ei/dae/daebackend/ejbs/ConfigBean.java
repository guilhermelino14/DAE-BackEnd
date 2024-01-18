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
        fabricanteBean.create("fabricante1", "fabricante1", "fabricante1", "fabricante1@mail.com");
        operadorBean.create("operador1", "operador1", "operador1", "operador1@mail.com");
        consumidorBean.create("consumidor1", "consumidor1", "consumidor1", "consumidor1@mail.com", "Morada1");
        embalagemProdutoBean.create("embalagemProduto1", 1, 1);
        embalagemProdutoBean.create("embalagemProduto2", 2, 2);
        embalagemTransporteBean.create("embalagemTransporte1", 1, 1);
        embalagemTransporteBean.create("embalagemTransporte2", 2, 2);
        sensorBean.create(TypeOfSensor.TEMPERATURA);
        sensorBean.create(TypeOfSensor.HUMIDADE);
        observacoesBean.create(sensorBean.find(1), 50, "ºC", "Temperatura atingiu 50ºC", new Date());
        observacoesBean.create(sensorBean.find(2), 150, "km/h","Velocidade atingiu 150km/h", new Date());
//        produtoBean.create("produto1", "categoria1", "descricao1");
//        produtoBean.associateFabricante(1, "fabricante1");
        createAListOfProducts();
        produtoFisicoBean.create(produtoBean.find(1));
        produtoFisicoBean.create(produtoBean.find(2));
        produtoFisicoBean.create(produtoBean.find(1));
        encomendaBean.create(consumidorBean.find("consumidor1"));
        encomendaBean.addProduct(1, 1);
        sensorBean.associarSensorAEmbalagem(1, 1);
        produtoFisicoBean.addEmbalagemProduto(1, 1);
        sensorRulesBean.create(produtoBean.find(1), TypeOfSensor.TEMPERATURA, 50, 25);
        sensorRulesBean.create(produtoBean.find(1), TypeOfSensor.HUMIDADE, 50, 25);
    }

    public void createAListOfProducts() throws MyEntityNotFoundException{
        produtoBean.create("Cholocate 1 unidade", "Chocolate", "Uma barra de chocolate", 1 , null);
        produtoBean.create("Cholocate 4 unidade", "Chocolate", "Quatro barra de chocolate", 4, null);
        produtoBean.create("Cholocate 12 unidade", "Chocolate", "Doze barra de chocolate", 12, TypeOfSensor.HUMIDADE);
        produtoBean.associateFabricante(1, "fabricante1");
        produtoBean.associateFabricante(2, "fabricante1");
        produtoBean.associateFabricante(3, "fabricante1");
    }
}
