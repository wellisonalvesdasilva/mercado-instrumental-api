package br.com.shopdosmusicos.controller.admin.anuncio.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.shopdosmusicos.controller.admin.anuncio.schema.CategoriaResponse;
import br.com.shopdosmusicos.domain.model.instrumentoMusical.InstrumentoMusical;

@Mapper
public interface CategoriaMapper {
	CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

	CategoriaResponse toCategoriaResponse(InstrumentoMusical categoria);
}
