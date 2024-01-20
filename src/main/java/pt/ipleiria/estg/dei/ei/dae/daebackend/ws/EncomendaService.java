package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.providers.CSVExporter;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/encomendas")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Operador", "Consumidor", "Fabricante"})
public class EncomendaService {
    @Context
    private SecurityContext securityContext;

    @EJB
    private EncomendaBean encomendaBean;
    @EJB
    private ConsumidorBean consumidorBean;
    @EJB
    private OperadorBean operadorBean;
    @EJB
    private ProdutoBean produtoBean;
    @EJB
    private ProdutoFisicoBean produtoFisicoBean;
    @EJB
    private EmbalagemProdutoBean embalagemProdutoBean;
    @EJB
    private EmbalagemTransporteBean embalagemTransporteBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ObservacoesBean observacoesBean;

    private EncomendaDTO toDTO(Encomenda encomenda) {
        var dto = new EncomendaDTO(
                encomenda.getId(),
                encomenda.getOperador(),
                encomenda.getConsumidor(),
                encomenda.getStatus(),
                encomenda.getData(),
                encomenda.getLocalizacao()
        );
        dto.produtosFisicos = encomenda.getProdutosFisicos().stream().map(this::toDTO).collect(Collectors.toList());
        dto.embalagensTransporte = encomenda.getEmbalagensTransporte().stream().map(this::toDTO).collect(Collectors.toList());
        List<EmbalagemProduto> embalagensProduto = new ArrayList<>();
        for (ProdutoFisico produtoFisico : encomenda.getProdutosFisicos()) {
            for (EmbalagemProduto embalagemProduto : produtoFisico.getEmbalagensProduto()) {
                if (!embalagensProduto.contains(embalagemProduto)) {
                    embalagensProduto.add(embalagemProduto);
                }
            }
        }

        dto.embalagensProduto = embalagensProduto.stream().map(this::toDTO).collect(Collectors.toList());
        return dto;
    }

    private ProdutoFisicoDTO toDTO(ProdutoFisico produtoFisico) {
        var dto = new ProdutoFisicoDTO(
                produtoFisico.getReferencia()
        );
        dto.produto = toDTO(produtoFisico.getProduto());
        dto.embalagensProduto = produtoFisico.getEmbalagensProduto().stream().map(this::toDTO).collect(Collectors.toList());
        return dto;
    }

    private ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getDescricao(),
                produto.getQuantidade(),
                produto.getTypeOfSensor(),
                produto.isLiquido()
        );
    }

    private EmbalagemTransporteDTO toDTO(EmbalagemTransporte embalagemTransporte) {
        var dto = new EmbalagemTransporteDTO(
                embalagemTransporte.getId(),
                embalagemTransporte.getNome(),
                embalagemTransporte.getAltura(),
                embalagemTransporte.getLargura()
        );
        return dto;
    }

    private EmbalagemProdutoDTO toDTO(EmbalagemProduto embalagemProduto) {
        var dto = new EmbalagemProdutoDTO(
                embalagemProduto.getId(),
                embalagemProduto.getNome(),
                embalagemProduto.getAltura(),
                embalagemProduto.getLargura()
        );
        return dto;
    }

    private ObservacoesDTO toDTO(Observacoes observacoes) {
        var dto = new ObservacoesDTO(
                observacoes.getId(),
                observacoes.getValue(),
                observacoes.getMedida(),
                observacoes.getObservacao(),
                observacoes.getData()
        );
        return dto;
    }


    private List<EncomendaDTO> toDTOs(List<Encomenda> encomendas) {
        return encomendas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<ObservacoesDTO> toDTOsObservacoes(List<Observacoes> observacoes) {
        return observacoes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<EncomendaDTO> getAll() {
        return toDTOs(encomendaBean.getAll());
    }

    @POST
    @Path("/")
    public Response createEncomenda(CriarEncomendaDTO criarEncomendaDTO) throws Exception {

        System.out.println(criarEncomendaDTO.getTypeOfSensor());
        System.out.println(criarEncomendaDTO.getProdutos());
        System.out.println(criarEncomendaDTO.getConsumidor_username());
        // CLIENTE
        Consumidor consumidor = consumidorBean.find(criarEncomendaDTO.getConsumidor_username());
        // OPERADOR
        //Operador operador = operadorBean.find("operador1");

        // CRIAR ENCOMENDA
        Encomenda encomenda = encomendaBean.create(consumidor);

        // BUSCAR PRODUTOS
        for (ProdutoDTO produto : criarEncomendaDTO.getProdutos()){
            Produto produtoFinded = produtoBean.find(produto.getId());
            // CRUAR EMBALAGEM DE PRODUTO
            EmbalagemProduto embalagemProduto = embalagemProdutoBean.create("Embalagem de "+produtoFinded.getQuantidade()+" Produtos", 10, 10);
            // CRIAR PRODUTO FISICO
            if(produtoFinded.isLiquido()){
                produtoFisicoBean.createManyLiquid(produtoFinded, embalagemProduto.getId(), encomenda);
                Sensor sensor = sensorBean.create(TypeOfSensor.NIVEL_DE_LIQUIDO);
                sensorBean.associarSensorAEmbalagem(sensor.getId(),embalagemProduto.getId());
            }else{
                produtoFisicoBean.createMany(produtoFinded, embalagemProduto.getId(), encomenda);
            }
            if (produtoFinded.getTypeOfSensor() != null){
                Sensor sensor = sensorBean.create(produtoFinded.getTypeOfSensor());
                sensorBean.associarSensorAEmbalagem(sensor.getId(),embalagemProduto.getId());
            }
        }

        // CRIAR EMBALAGEM DE TRANSPORTE
        EmbalagemTransporte embalagemTransporte = embalagemTransporteBean.create("Embalagem de Transporte", 10, 10);
        if (criarEncomendaDTO.isHas_sensor()){
            Sensor sensor = sensorBean.create(criarEncomendaDTO.getTypeOfSensor());
            sensorBean.associarSensorAEmbalagem(sensor.getId(),embalagemTransporte.getId());
        }
        embalagemTransporteBean.addEncomenda(embalagemTransporte.getId(), encomenda.getId());
        Sensor sensorGPS = sensorBean.create(TypeOfSensor.GPS);
        sensorBean.associarSensorAEmbalagem(sensorGPS.getId(),embalagemTransporte.getId());

        return Response.ok(criarEncomendaDTO).build();
    }

    @GET
    @Path("{id}")
    public Response getEncomendaDetails(@PathParam("id") int id) throws MyEntityNotFoundException {
        EncomendaDTO encomendaDTO = toDTO(encomendaBean.find(id));
        if (encomendaDTO == null){
            throw new MyEntityNotFoundException("Encomenda com o id " + id + " não existe");
        }
        return Response.status(Response.Status.OK).entity(encomendaDTO).build();
    }

    @PUT
    @Path("{id}/status/{status}")
    public Response updateEncomendaStatus(@PathParam("id") int id, @PathParam("status") EncomendaStatus status) throws MyEntityNotFoundException {
        Encomenda encomenda = encomendaBean.find(id);
        if (encomenda == null){
            throw new MyEntityNotFoundException("Encomenda com o id " + id + " não existe");
        }
        if (status == EncomendaStatus.RECOLHIDA){
            Operador operador = operadorBean.find(securityContext.getUserPrincipal().getName());
            encomendaBean.updateOperador(id,operador);

        }
        if (status == EncomendaStatus.ENTREGUE){
            EmbalagemTransporte embalagemTransporte = encomenda.getEmbalagensTransporte().get(0);
            List<Sensor> sensores = embalagemTransporte.getSensores();
            for (Sensor sensor : sensores) {
                if (sensor.getTypeOfSensor() == TypeOfSensor.GPS){
                    observacoesBean.create(sensor, 0, "", "Encomenda Entregue com Sucesso", new Date());
                }
            }
        }
        encomendaBean.updateStatus(id, status);
        return Response.ok("Encomenda atualizada com sucesso!").build();
    }

    @POST
    @Path("{id}/status/tentativaEntrega")
    public Response updateEncomendaStatusTentativaEntrega(@PathParam("id") int id) throws MyEntityNotFoundException {
        Encomenda encomenda = encomendaBean.find(id);
        if (encomenda == null){
            throw new MyEntityNotFoundException("Encomenda com o id " + id + " não existe");
        }
        EmbalagemTransporte embalagemTransporte = encomenda.getEmbalagensTransporte().get(0);
        List<Sensor> sensores = embalagemTransporte.getSensores();
        for (Sensor sensor : sensores) {
            if (sensor.getTypeOfSensor() == TypeOfSensor.GPS){
                observacoesBean.create(sensor, 0, "", "Tentativa de Entrega", new Date());
            }
        }
        return Response.ok("Encomenda atualizada com sucesso!").build();
    }

    @GET
    @Path("{id}/observacoes")
    public Response getEncomendaObservacoes(@PathParam("id") int id) throws MyEntityNotFoundException {
        Encomenda encomenda = encomendaBean.find(id);
        List<Observacoes> observacoes = new ArrayList<>();
        List<Sensor> sensoresList = new ArrayList<>();
        List<EmbalagemTransporte> embalagensTransporte = encomenda.getEmbalagensTransporte();
        List<ProdutoFisico> produtoFisicos = encomenda.getProdutosFisicos();
        for (EmbalagemTransporte embalagemTransporte : embalagensTransporte) {
            List<Sensor> sensores = embalagemTransporte.getSensores();
            for (Sensor sensor : sensores) {
                if (!sensoresList.contains(sensor)) {
                    sensoresList.add(sensor);
                }
            }
        }
        for (ProdutoFisico produtoFisico : produtoFisicos) {
            List<EmbalagemProduto> embalagensProduto = produtoFisico.getEmbalagensProduto();
            for (EmbalagemProduto embalagemProduto : embalagensProduto) {
                List<Sensor> sensores = embalagemProduto.getSensores();
                for (Sensor sensor : sensores) {
                    if (!sensoresList.contains(sensor)) {
                        sensoresList.add(sensor);
                    }
                }
            }
        }
        for (Sensor sensor : sensoresList) {
            List<Observacoes> observacoesList = sensor.getObservacoes();
            for (Observacoes observacao : observacoesList) {
                if (!observacoes.contains(observacao)) {
                    observacoes.add(observacao);
                }
            }
        }
        observacoes.sort(Comparator.comparing(Observacoes::getData).reversed());
//        observacoes.sort(Comparator.comparing(Observacoes::getData));
        return Response.ok(toDTOsObservacoes(observacoes)).build();
    }
}
