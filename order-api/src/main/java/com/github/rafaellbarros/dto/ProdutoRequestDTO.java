package com.github.rafaellbarros.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class ProdutoRequestDTO {

    @NotBlank
    private String nome;
    
    @NotNull
    @Positive
    private BigDecimal preco;
    
    @NotBlank
    private String descricao;

}