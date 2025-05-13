package com.github.rafaellbarros.mapper;

import com.github.rafaellbarros.dto.ProdutoRequestDTO;
import com.github.rafaellbarros.dto.ProdutoResponseDTO;
import com.github.rafaellbarros.model.Produto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProdutoMapper {
    // Mapeamento automático por convenção de nomes
    Produto toEntity(ProdutoRequestDTO dto);
    
    // Customização para campos não padrão
    @Mapping(target = "precoFormatado", source = "preco", numberFormat = "R$#.00")
    ProdutoResponseDTO toDTO(Produto produto);

    List<ProdutoResponseDTO> toDTOList(List<Produto> produtos);

    @InheritInverseConfiguration(name = "toDTO")
    void fromDTO(ProdutoRequestDTO dto, @MappingTarget Produto entity);
}