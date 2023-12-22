package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EmbalagemProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.EmbalagemProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
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

    private EmbalagemProdutoDTO toDTONoEmbalagens(EmbalagemProduto embalagem) {
        return new EmbalagemProdutoDTO(
                embalagem.getId(),
                embalagem.getNome(),
                embalagem.getAltura(),
                embalagem.getLargura()
        );
    }

    private SensorDTO toDTONoSensores(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getNome(),
                sensor.getDescricao()
        );
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
    public Response createNewEmbalagem(EmbalagemProdutoDTO embalagemProdutoDTO) throws MyEntityNotFoundException {
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
        List<SensorDTO> sensorDTOs = sensorToDTOs(embalagemProdutoBean.find(id).getSensores());
        JsonObjectBuilder response = Json.createObjectBuilder();
        response.add("id", embalagemProdutoDTO.getId());
        response.add("nome", embalagemProdutoDTO.getNome());
        response.add("altura", embalagemProdutoDTO.getAltura());
        response.add("largura", embalagemProdutoDTO.getLargura());
        JsonArrayBuilder sensorList = Json.createArrayBuilder();
        for (SensorDTO sensorDTO : sensorDTOs) {
            JsonObject sensor = Json.createObjectBuilder()
                    .add("id", sensorDTO.getId())
                    .add("nome", sensorDTO.getNome())
                    .add("descricao", sensorDTO.getDescricao())
                    .build();
            sensorList.add(sensor);
        }
        response.add("Sensores", sensorList);
        return Response.status(Response.Status.OK).entity(response.build()).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteSensor(@PathParam("id") int id) throws MyEntityNotFoundException {
        embalagemProdutoBean.delete(id);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}/sensor")
    public Response getSensorEmbalagem(@PathParam("id") int id) throws MyEntityNotFoundException {
        EmbalagemProduto embalagemProduto = embalagemProdutoBean.find(id);
        if (embalagemProduto != null) {
            return Response.ok(sensorToDTOs(embalagemProduto.getSensores())).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_EMBALAGEM")
                .build();
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
        return Response.status(Response.Status.OK).build();
    }
}
