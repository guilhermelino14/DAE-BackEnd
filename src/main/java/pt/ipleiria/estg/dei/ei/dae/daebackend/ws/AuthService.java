package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.User;

@Path("/auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @EJB
    private UserBean userBean;

    @GET
    @Path("/user")
    public Response getUser() {
        User user = userBean.find("fabricante1");
        return Response.ok(UserDTO.from(user)).build();
    }
}
