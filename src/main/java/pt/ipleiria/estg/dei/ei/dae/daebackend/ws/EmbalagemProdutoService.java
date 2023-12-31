package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EmbalagemProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.ProdutoFisicoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.EmbalagemProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.ObservacoesBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.ProdutoFisico;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/embalagensProduto")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Fabricante"})
public class EmbalagemProdutoService {
    @EJB
    private EmbalagemProdutoBean embalagemProdutoBean;
    @EJB
    private ObservacoesBean observacoesBean;

    private EmbalagemProdutoDTO toDTONoEmbalagens(EmbalagemProduto embalagem) {
        var dto = new EmbalagemProdutoDTO(
                embalagem.getId(),
                embalagem.getNome(),
                embalagem.getAltura(),
                embalagem.getLargura()
        );
        dto.sensores = embalagem.getSensores().stream().map(this::toDTONoSensores).collect(Collectors.toList());
        dto.produtoFisicos = embalagem.getProdutoFisicos().stream().map(this::toDTONoProdutoFisico).collect(Collectors.toList());
        return dto;
    }

    private SensorDTO toDTONoSensores(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getNome(),
                sensor.getDescricao()
        );
    }

    private ProdutoFisicoDTO toDTONoProdutoFisico(ProdutoFisico produtoFisico) {
        var dto = new ProdutoFisicoDTO(
                produtoFisico.getReferencia()
        );
        return dto;
    }

    private List<EmbalagemProdutoDTO> toDTOsNoEmbalagens(List<EmbalagemProduto> embalagens) {
        return embalagens.stream().map(this::toDTONoEmbalagens).collect(Collectors.toList());
    }

    private List<SensorDTO> sensorToDTOs(List<Sensor> subjects) {
        return subjects.stream().map(this::toDTONoSensores).collect(java.util.stream.Collectors.toList());
    }

    @GET
    @Path("/")
    public List<EmbalagemProdutoDTO> getAllEmbalagens() {
        return toDTOsNoEmbalagens(embalagemProdutoBean.getAll());
    }



    @POST
    @Path("/")
    public Response createNewEmbalagem(EmbalagemProdutoDTO embalagemProdutoDTO){
        embalagemProdutoBean.create(
                embalagemProdutoDTO.getNome(),
                embalagemProdutoDTO.getAltura(),
                embalagemProdutoDTO.getLargura()
        );
        return Response.status(Response.Status.CREATED).build();
        // EmbalagemProduto newEmbalagemProduto = embalagemProdutoBean.find(embalagemProdutoDTO.getNome());
        //return Response.status(Response.Status.CREATED).entity(toDTONoEmbalagens(newEmbalagemProduto)).build();
    }

    @GET
    @Path("{id}")
    public Response getSensorById(@PathParam("id") int id) throws MyEntityNotFoundException {
        EmbalagemProdutoDTO embalagemProdutoDTO = toDTONoEmbalagens(embalagemProdutoBean.find(id));
        return Response.status(Response.Status.OK).entity(embalagemProdutoDTO).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteSensor(@PathParam("id") int id) throws MyEntityNotFoundException {
        embalagemProdutoBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("{idEmbalagem}/sensor/{idSensor}")
    public Response associarSensorAEmbalagem(@PathParam("idEmbalagem") int idEmbalagem, @PathParam("idSensor") int idSensor) throws MyEntityNotFoundException {
        embalagemProdutoBean.associarEmbalagemAoSensor(idEmbalagem, idSensor);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{idEmbalagem}/sensor/{idSensor}")
    public Response desassociarSensorAEmbalagem(@PathParam("idEmbalagem") int idEmbalagem, @PathParam("idSensor") int idSensor) throws MyEntityNotFoundException {
        embalagemProdutoBean.desassociarEmbalagemAoSensor(idEmbalagem, idSensor);
        observacoesBean.deleteWhereSensorId(idSensor);
        return Response.status(Response.Status.OK).build();
    }
}
