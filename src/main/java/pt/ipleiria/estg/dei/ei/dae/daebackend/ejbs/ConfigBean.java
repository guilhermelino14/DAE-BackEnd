package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.SensorType;
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

    @PostConstruct
    public void populateDB() throws MyEntityNotFoundException {
        fabricanteBean.create("fabricante1", "fabricante1", "fabricante1", "fabricante1@mail.com");
        operadorBean.create("operador1", "operador1", "operador1", "operador1@mail.com");
        consumidorBean.create("consumidor1", "consumidor1", "consumidor1", "consumidor1@mail.com", "Morada1");
        embalagemProdutoBean.create("embalagemProduto1", 1, 1);
        embalagemProdutoBean.create("embalagemProduto2", 2, 2);
        embalagemTransporteBean.create("embalagemTransporte1", 1, 1);
        embalagemTransporteBean.create("embalagemTransporte2", 2, 2);
        sensorBean.create("sensor1", "Temperatura", SensorType.FABRICANTE);
        sensorBean.create("sensor2", "Velocidade", SensorType.OPERADOR);
        observacoesBean.create(sensorBean.find(1), "Temperatura atingiu 50ºC", new Date());
        observacoesBean.create(sensorBean.find(2), "Velocidade atingiu 150km/h", new Date());
//        produtoBean.create("produto1", "categoria1", "descricao1");
//        produtoBean.associateFabricante(1, "fabricante1");
        createAListOfProducts();
        produtoFisicoBean.create(produtoBean.find(1));
        produtoFisicoBean.create(produtoBean.find(2));
        produtoFisicoBean.create(produtoBean.find(1));
        encomendaBean.create(operadorBean.find("operador1"), consumidorBean.find("consumidor1"));
        encomendaBean.addProduct(1, 1);
        sensorBean.associarSensorAEmbalagem(1, 1);
        produtoFisicoBean.addEmbalagemProduto(1, 1);
    }

    public void createAListOfProducts() throws MyEntityNotFoundException{
        produtoBean.create("Cholocate 1 unidade", "Chocolate", "Uma barra de chocolate", 1 );
        produtoBean.create("Cholocate 4 unidade", "Chocolate", "Quatro barra de chocolate", 4);
        produtoBean.create("Cholocate 12 unidade", "Chocolate", "Doze barra de chocolate", 12);
        produtoBean.associateFabricante(1, "fabricante1");
        produtoBean.associateFabricante(2, "fabricante1");
        produtoBean.associateFabricante(3, "fabricante1");
    }
}
