package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.AuthDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.User;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.TokenIssuer;

@Path("/auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @EJB
    private UserBean userBean;
    @Context
    private SecurityContext securityContext;

    @Inject
    private TokenIssuer issuer;

    @GET
    @Authenticated
    @Path("/user")
    public Response getAuthenticatedUser() {
        var username = securityContext.getUserPrincipal().getName();
        var user = userBean.findOrFail(username);
        return Response.ok(UserDTO.from(user)).build();
    }

    @POST
    @Path("/login")
    public Response authenticate(@Valid AuthDTO auth) {
        if (userBean.canLogin(auth.getUsername(), auth.getPassword())) {
            var user = userBean.findOrFail(auth.getUsername());
            UserDTO userDTO = UserDTO.from(user);
            String token = issuer.issue(auth.getUsername());
            JsonObject jsonObject = Json.createObjectBuilder()
                    .add("token", token)
                    .add("user", Json.createObjectBuilder()
                            .add("username", userDTO.getUsername())
                            .add("name", userDTO.getName())
                            .add("email", userDTO.getEmail())
                            .add("password", userDTO.getPassword())
                            .add("role", userDTO.getRole())
                            .build())
                    .build();
            return Response.ok(jsonObject).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
