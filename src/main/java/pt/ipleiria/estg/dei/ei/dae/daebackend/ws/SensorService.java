package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ObservacoesBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.ForbiddenException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/sensores")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Fabricante"})
public class SensorService {
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ObservacoesBean observacoesBean;

    @Context
    private SecurityContext securityContext;

    private SensorDTO toDTONoSensores(Sensor sensor) {
        var dto = new SensorDTO(
                sensor.getId(),
                sensor.getTypeOfSensor()
        );
        dto.observacoes = sensor.getObservacoes().stream().map(this::toDTONoObservacoes).collect(Collectors.toList());
        dto.embalagens = sensor.getEmbalagens().stream().map(this::toDTOEmbalagens).collect(Collectors.toList());
        return dto;
    }

    private List<SensorDTO> toDTOsNoSensores(List<Sensor> sensors) {
        return sensors.stream().map(this::toDTONoSensores).collect(Collectors.toList());
    }

    private EmbalagemDTO toDTOEmbalagens(Embalagem embalagem) {
        var dto = new EmbalagemDTO(
                embalagem.getId(),
                embalagem.getNome(),
                embalagem.getAltura(),
                embalagem.getLargura()
        );
        if (embalagem instanceof EmbalagemProduto) {
            dto.produtosFisicos = ((EmbalagemProduto) embalagem).getProdutoFisicos().stream().map(this::toDTOEmbalagemProduto).collect(Collectors.toList());
        }
        if (embalagem instanceof EmbalagemTransporte){
            dto.encomendas = ((EmbalagemTransporte) embalagem).getEncomendas().stream().map(this::toDTOEncomenda).collect(Collectors.toList());
        }
        return dto;
    }

    private ProdutoFisicoDTO toDTOEmbalagemProduto(ProdutoFisico produtoFisico) {
        var dto = new ProdutoFisicoDTO(
                produtoFisico.getReferencia()
        );
        dto.produto = toDTOProduto(produtoFisico.getProduto());
        return dto;
    }

    private ObservacoesDTO toDTONoObservacoes(Observacoes observacao) {
        return new ObservacoesDTO(
                observacao.getId(),
                observacao.getValue(),
                observacao.getMedida(),
                observacao.getObservacao(),
                observacao.getData()
        );
    }

    private ProdutoDTO toDTOProduto(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getDescricao(),
                produto.getQuantidade(),
                produto.getTypeOfSensor()
        );
    }

    private EncomendaDTO toDTOEncomenda(Encomenda encomenda) {
        var dto = new EncomendaDTO(
                encomenda.getId(),
                encomenda.getOperador(),
                encomenda.getConsumidor(),
                encomenda.getStatus(),
                encomenda.getData(),
                encomenda.getLocalizacao()
        );
        return dto;
    }

    private List<ObservacoesDTO> toDTOsNoObservacoes(List<Observacoes> observacoes)  {
        return observacoes.stream().map(this::toDTONoObservacoes).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<SensorDTO> getAllSensores() {
        return toDTOsNoSensores(sensorBean.getAll());
    }

    @GET
    @Path("/available")
    public List<SensorDTO> getAllSensoresAvailable() {
        return toDTOsNoSensores(sensorBean.getAllAvailable());
    }
    @GET
    @Path("/inUse")
    public List<SensorDTO> getAllSensoresInUse() {
        return toDTOsNoSensores(sensorBean.getAllInUse());
    }

    @GET
    @Path("{id}")
    public Response getSensorById(@PathParam("id") int id) throws MyEntityNotFoundException {
        SensorDTO sensorDTO = toDTONoSensores(sensorBean.find(id));
        return Response.status(Response.Status.OK).entity(sensorDTO).build();
    }

    @POST
    @Path("/")
    public Response createNewSensor(SensorDTO sensorDTO) {
        sensorBean.create(sensorDTO.getTypeOfSensor());
        return Response.status(Response.Status.CREATED).build();
    }

//    @PUT
//    @Path("{id}")
//    public Response updateSensor(@PathParam("id") int id, SensorDTO sensorDTO) throws MyEntityNotFoundException {
//        boolean response = sensorBean.update(id, sensorDTO.getNome(), sensorDTO.getDescricao());
//        if (!response) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        return Response.status(Response.Status.OK).build();
//    }

    @DELETE
    @Path("{id}")
    public Response deleteSensor(@PathParam("id") int id) throws MyEntityNotFoundException {
        boolean response = sensorBean.delete(id);
        if (!response) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }
}
