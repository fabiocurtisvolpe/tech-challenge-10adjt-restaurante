package com.adjt.rest.controller;

import com.adjt.core.model.Pedido;
import com.adjt.core.usecase.CancelarPedidoUseCase;
import com.adjt.core.usecase.CriarPedidoUseCase;
import com.adjt.core.usecase.ObterPedidoUseCase;
import com.adjt.gprc.ClienteGrpcUseCase;
import com.adjt.rest.dto.request.PedidoRequest;
import com.adjt.rest.dto.response.PedidoResponse;
import com.adjt.rest.interceptor.UserContext;
import com.adjt.rest.mapper.PedidoRestMapper;
import io.smallrye.common.annotation.Blocking;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pedido")
@RolesAllowed("ROLE_CLIENTE")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoController {

   private final CancelarPedidoUseCase cancelarPedidoUseCase;
   private final CriarPedidoUseCase criarPedidoUseCase;
   private final ObterPedidoUseCase obterPedidoUseCase;
   private final PedidoRestMapper pedidoRestMapper;

   private final ClienteGrpcUseCase clienteGrpcUseCase;
   private final UserContext userContext;

   public PedidoController(CancelarPedidoUseCase cancelarPedidoUseCase,
                           CriarPedidoUseCase criarPedidoUseCase,
                           ObterPedidoUseCase obterPedidoUseCase,
                           PedidoRestMapper pedidoRestMapper,
                           ClienteGrpcUseCase clienteGrpcUseCase,
                           UserContext userContext) {
      this.cancelarPedidoUseCase = cancelarPedidoUseCase;
      this.criarPedidoUseCase = criarPedidoUseCase;
      this.obterPedidoUseCase = obterPedidoUseCase;
      this.pedidoRestMapper = pedidoRestMapper;

      this.clienteGrpcUseCase = clienteGrpcUseCase;
      this.userContext = userContext;
   }

   @POST
   @Path("/criar")
   @Blocking
   public Response criar(@Valid PedidoRequest request) {

      Boolean valido = this.clienteGrpcUseCase.validarUsuarioLogado(
              userContext.getKeycloakId(),
              request.idCliente
      ).await().indefinitely();

      if (!Boolean.TRUE.equals(valido)) {
         return Response.status(Response.Status.UNAUTHORIZED).build();
      }

      Pedido pedido = this.pedidoRestMapper.toModel(request);
      Pedido resp = this.criarPedidoUseCase.run(pedido);
      PedidoResponse response = this.pedidoRestMapper.toResponse(resp);

      return Response.status(Response.Status.CREATED).entity(response).build();
   }

   @GET
   @Path("obter/{id}")
   public Response obter(@PathParam("id") Long id) {
      Pedido pedido = this.obterPedidoUseCase.run(id);
      PedidoResponse response = this.pedidoRestMapper.toResponse(pedido);

      return Response.ok(response).build();
   }

   @DELETE
   @Path("cancelar/{id}")
   public Response cancelar(@PathParam("id") Long id) {
      this.cancelarPedidoUseCase.run(id);
      return Response.noContent().build();
   }
}
