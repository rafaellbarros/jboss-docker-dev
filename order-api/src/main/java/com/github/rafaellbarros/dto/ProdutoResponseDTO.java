package com.github.rafaellbarros.dto;

import lombok.Data;

@Data
public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private String precoFormatado;
    private String descricao;

}