package br.com.shopdosmusicos.controller.schema.geral;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.shopdosmusicos.enums.ItemValorDescricao;

@Mapper
public interface EnumResponseMapper {

    EnumResponseMapper INSTANCE = Mappers.getMapper(EnumResponseMapper.class);

    EnumResponse toEnumResponse(ItemValorDescricao item);

    List<EnumResponse> toEnumResponseList(Collection<? extends ItemValorDescricao> itens);

    List<EnumResponse> toEnumResponseList(ItemValorDescricao... itens);

    List<EnumValorDescricaoResponse> toEnumResponseDescricaoList(Collection<? extends ItemValorNomeDescricao> itens);

    List<EnumValorSituacaoResponse> toEnumResponseSituacaoList(Collection<? extends ItemValorNomeSituacao> itens);

}
