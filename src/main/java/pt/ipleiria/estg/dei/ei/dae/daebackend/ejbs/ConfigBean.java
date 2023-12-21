package pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

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

    @PostConstruct
    public void populateDB() {
        fabricanteBean.create("fabricante1", "fabricante1", "fabricante1", "fabricante1@mail.com");
        operadorBean.create("operador1", "operador1", "operador1", "operador1@mail.com");
        consumidorBean.create("consumidor1", "consumidor1", "consumidor1", "consumidor1@mail.com");
        embalagemProdutoBean.create("embalagem1", 1, 1);
        embalagemProdutoBean.create("embalagem2", 2, 2);
        sensorBean.create("sensor1", "temperatura");
    }
}
