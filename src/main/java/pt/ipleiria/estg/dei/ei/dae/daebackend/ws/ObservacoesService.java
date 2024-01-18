package pt.ipleiria.estg.dei.ei.dae.daebackend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.ObservacoesDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.daebackend.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.daebackend.security.Authenticated;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/observacoes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Fabricante"})
public class ObservacoesService {
    @EJB
    private ObservacoesBean observacoesBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ProdutoBean produtoBean;

    @EJB
    private EmbalagemProdutoBean embalagemProdutoBean;

    @EJB
    private ProdutoFisicoBean produtoFisicoBean;

    @Context
    private SecurityContext securityContext;

    private ObservacoesDTO toDTONoObservacoes(Observacoes observacao) {
        return new ObservacoesDTO(
                observacao.getId(),
                observacao.getValue(),
                observacao.getMedida(),
                observacao.getObservacao(),
                observacao.getData()
        );
    }

    private List<ObservacoesDTO> toDTOsNoObservacoes(List<Observacoes> observacoes) {
        return observacoes.stream().map(this::toDTONoObservacoes).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ObservacoesDTO> getAllObservacoes() {
        return toDTOsNoObservacoes(observacoesBean.getAll());
    }

    @POST
    @Path("/")
    public Response createNewObservacao(ObservacoesDTO observacaoDTO) throws MyEntityNotFoundException {
        Sensor sensor = sensorBean.find(observacaoDTO.getSensor());
        List<Embalagem> listaDeEmbalagens = sensor.getEmbalagens();

        if (!listaDeEmbalagens.isEmpty()) {
            Embalagem embalagem = listaDeEmbalagens.get(0);
            if (embalagem instanceof EmbalagemProduto) {
                EmbalagemProduto embalagemProduto = (EmbalagemProduto) embalagem;
                List<ProdutoFisico> produtoFisicos = embalagemProduto.getProdutoFisicos();
                if (!produtoFisicos.isEmpty()) {
                    ProdutoFisico produtoFisico = produtoFisicos.get(0);
                    Produto produto = produtoFisico.getProduto();
                    for (SensorRole role :produto.getSensorRoles()) {
                        if (role.getTypeOfSensor().equals(sensor.getTypeOfSensor())){
                            if(observacaoDTO.getValue() > role.getVal_max()){
                                observacaoDTO.setObservacao("Valor elevado de " +sensor.getTypeOfSensor().toString().toLowerCase());
                            }
                            if(observacaoDTO.getValue() < role.getVal_min()){
                                observacaoDTO.setObservacao("Valor baixo de " +sensor.getTypeOfSensor().toString().toLowerCase());
                            }
                        }
                    }
                }
            }
        }

        observacoesBean.create(sensor,observacaoDTO.getValue(), observacaoDTO.getMedida(), observacaoDTO.getObservacao(), new Date());
        return Response.status(Response.Status.CREATED).build();
    }
}
