package com.github.rafaellbarros.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoFiltroDTO {

    private String nome;
    private String descricao;
    private BigDecimal precoMin;
    private BigDecimal precoMax;

}
