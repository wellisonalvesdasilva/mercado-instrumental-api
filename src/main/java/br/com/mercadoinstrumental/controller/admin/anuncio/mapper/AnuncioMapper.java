package br.com.mercadoinstrumental.controller.admin.anuncio.mapper;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.mercadoinstrumental.controller.admin.anuncio.schema.AnuncioResponse;
import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;

@Mapper
public interface AnuncioMapper {
	AnuncioMapper INSTANCE = Mappers.getMapper(AnuncioMapper.class);

	@Mapping(source = "dataPublicacao", target = "dataPublicacao")
	AnuncioResponse toAnuncioResponse(Anuncio anuncio, LocalDate dataPublicacao);
}
