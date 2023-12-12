package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EmbalagemProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.EmbalagemProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/fabricante")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class FabricanteService {

    @EJB
    private EmbalagemProdutoBean embalagemProdutoBean;

    @Context
    private SecurityContext securityContext;

    private EmbalagemProdutoDTO toDTONoEmbalagens(EmbalagemProduto embalagem) {
        return new EmbalagemProdutoDTO(
                embalagem.getNome(),
                embalagem.getAltura(),
                embalagem.getLargura()
        );
    }

    private List<EmbalagemProdutoDTO> toDTOsNoEmbalagens(List<EmbalagemProduto> embalagens) {
        return embalagens.stream().map(this::toDTONoEmbalagens).collect(Collectors.toList());
    }

    @GET
    @Authenticated
    @Path("/embalagens")
    public List<EmbalagemProdutoDTO> getAllEmbalagens() {
        return toDTOsNoEmbalagens(embalagemProdutoBean.getAll());
    }
}
