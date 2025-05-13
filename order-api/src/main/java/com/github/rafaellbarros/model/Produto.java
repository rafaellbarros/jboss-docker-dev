package com.github.rafaellbarros.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Entity
@Getter
@Setter
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do produto", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", required = true, example = "Smartphone XYZ")
    private String nome;

    @Schema(description = "Preço do produto", required = true, example = "999.99")
    private Double preco;

    @Schema(description = "Descrição do produto")
    private String descricao;
    

}