package com.adjt.core.port;

import com.adjt.core.model.CardapioInfo;

public interface RestauranteExternalPort {
    CardapioInfo obterItemCardapio(Long id);
}
