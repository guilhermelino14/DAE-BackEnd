package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
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
    private SensorBean sensorBean;
    @EJB
    private ObservacoesBean observacoesBean;
    @EJB
    private ProdutoBean produtoBean;

    @EJB
    private ProdutoFisicoBean produtoFisicoBean;

    @PostConstruct
    public void populateDB() throws MyEntityNotFoundException {
        fabricanteBean.create("fabricante1", "fabricante1", "fabricante1", "fabricante1@mail.com");
        operadorBean.create("operador1", "operador1", "operador1", "operador1@mail.com");
        consumidorBean.create("consumidor1", "consumidor1", "consumidor1", "consumidor1@mail.com");
        embalagemProdutoBean.create("embalagem1", 1, 1);
        embalagemProdutoBean.create("embalagem2", 2, 2);
        sensorBean.create("sensor1", "temperatura", SensorType.FABRICANTE);
        observacoesBean.create(sensorBean.find(1), "observacao1", new Date());
        produtoBean.create("produto1", "categoria1", "descricao1");
        produtoFisicoBean.create(produtoBean.find(1));
    }
}
