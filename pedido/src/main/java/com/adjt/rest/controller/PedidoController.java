package com.adjt.rest.controller;

import com.adjt.core.usecase.CancelarPedidoUseCase;
import com.adjt.core.usecase.CriarPedidoUseCase;
import com.adjt.core.usecase.ObterPedidoUseCase;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/pedido")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoController {

   private final CancelarPedidoUseCase cancelarPedidoUseCase;
   private final CriarPedidoUseCase criarPedidoUseCase;
   private final ObterPedidoUseCase obterPedidoUseCase;

   public PedidoController(CancelarPedidoUseCase cancelarPedidoUseCase, CriarPedidoUseCase criarPedidoUseCase, ObterPedidoUseCase obterPedidoUseCase) {
      this.cancelarPedidoUseCase = cancelarPedidoUseCase;
      this.criarPedidoUseCase = criarPedidoUseCase;
      this.obterPedidoUseCase = obterPedidoUseCase;
   }

   @POST
   public String get() {
      return "Pedido";
   }
}
