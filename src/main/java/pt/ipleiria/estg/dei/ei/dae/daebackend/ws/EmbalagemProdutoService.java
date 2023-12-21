package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.EmbalagemProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.EmbalagemProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.EmbalagemProduto;
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

    private List<EmbalagemProdutoDTO> toDTOsNoEmbalagens(List<EmbalagemProduto> embalagens) {
        return embalagens.stream().map(this::toDTONoEmbalagens).collect(Collectors.toList());
    }

    @GET
    @Path("/embalagens")
    public List<EmbalagemProdutoDTO> getAllEmbalagens() {
        return toDTOsNoEmbalagens(embalagemProdutoBean.getAll());
    }



    @POST
    @Path("/embalagem")
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
}
