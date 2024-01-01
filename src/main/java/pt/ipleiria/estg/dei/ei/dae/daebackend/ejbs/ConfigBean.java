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
        embalagemProdutoBean.create("embalagem1", 1, 1);
        embalagemProdutoBean.create("embalagem2", 2, 2);
        embalagemTransporteBean.create("embalagemTransporte1", 1, 1);
        embalagemTransporteBean.create("embalagemTransporte2", 2, 2);
        sensorBean.create("sensor1", "temperatura", SensorType.FABRICANTE);
        sensorBean.create("sensor2", "temperatura", SensorType.OPERADOR);
        observacoesBean.create(sensorBean.find(1), "observacao1", new Date());
//        produtoBean.create("produto1", "categoria1", "descricao1");
//        produtoBean.associateFabricante(1, "fabricante1");
        createAListOfProducts(10);
        produtoFisicoBean.create(produtoBean.find(1));
        produtoFisicoBean.create(produtoBean.find(2));
        produtoFisicoBean.create(produtoBean.find(1));
        encomendaBean.create(operadorBean.find("operador1"), consumidorBean.find("consumidor1"));
        encomendaBean.addProduct(1, 1);
        sensorBean.associarSensorAEmbalagem(1, 1);
        produtoFisicoBean.addEmbalagemProduto(1, 1);
    }

    public void createAListOfProducts(int numberOfProducts) throws MyEntityNotFoundException{
        for(int i = 0; i < numberOfProducts; i++){
            produtoBean.create("produto" + i, "categoria" + i, "descricao" + i);
            produtoBean.associateFabricante(i, "fabricante1");
        }
    }
}
