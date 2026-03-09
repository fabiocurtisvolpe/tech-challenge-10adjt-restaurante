package com.adjt.gprc.mapper;

import com.adjt.core.model.ClienteInfo;
import com.adjt.cliente.Cliente;

public class ClientePedidoMapper {

    public static ClienteInfo toClienteInfo(Cliente proto) {
        ClienteInfo info = new ClienteInfo();
        info.setId(proto.getId());
        info.setNome(proto.getNome());
        info.setCpf(proto.getCpf());
        info.setEmail(proto.getEmail());
        return info;
    }
}
