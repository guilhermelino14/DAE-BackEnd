package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EmbalagemProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/sensores")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SensorService {
    @EJB
    private SensorBean sensorBean;

    @Context
    private SecurityContext securityContext;

    private SensorDTO toDTONoSensores(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getNome(),
                sensor.getDescricao()
        );
    }

    private List<SensorDTO> toDTOsNoSensores(List<Sensor> sensors) {
        return sensors.stream().map(this::toDTONoSensores).collect(Collectors.toList());
    }

    @GET
    @Authenticated
    @Path("/")
    public List<SensorDTO> getAllSensores() {
        return toDTOsNoSensores(sensorBean.getAll());
    }

    @GET
    @Authenticated
    @Path("{id}")
    public SensorDTO getSensorById(@PathParam("id") int id) throws MyEntityNotFoundException {
        SensorDTO sensorDTO = toDTONoSensores(sensorBean.find(id));
        return sensorDTO;
    }

    @POST
    @Authenticated
    @Path("/")
    public Response createNewSensor(SensorDTO sensorDTO) {
        sensorBean.create(sensorDTO.getNome(), sensorDTO.getDescricao());
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Authenticated
    @Path("{id}")
    public Response updateSensor(@PathParam("id") int id, SensorDTO sensorDTO) throws MyEntityNotFoundException {
        boolean response = sensorBean.update(id, sensorDTO.getNome(), sensorDTO.getDescricao());
        if (!response) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Authenticated
    @Path("{id}")
    public Response deleteSensor(@PathParam("id") int id) throws MyEntityNotFoundException {
        boolean response = sensorBean.delete(id);
        if (!response) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }
}
