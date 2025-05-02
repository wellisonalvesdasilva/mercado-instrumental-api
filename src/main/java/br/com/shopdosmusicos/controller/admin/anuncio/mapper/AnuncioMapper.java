package br.com.shopdosmusicos.controller.admin.anuncio.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.shopdosmusicos.controller.admin.anuncio.schema.AnuncioResponse;
import br.com.shopdosmusicos.domain.model.anuncio.Anuncio;

@Mapper
public interface AnuncioMapper {
	AnuncioMapper INSTANCE = Mappers.getMapper(AnuncioMapper.class);

	AnuncioResponse toAnuncioResponse(Anuncio anuncio);
}
