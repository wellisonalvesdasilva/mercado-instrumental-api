package br.com.mercadoinstrumental.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadoinstrumental.controller.schema.geral.EnumResponse;
import br.com.mercadoinstrumental.controller.schema.geral.EnumResponseMapper;
import br.com.mercadoinstrumental.domain.model.anuncio.MarcaInstrumentoMusicalEnum;
import br.com.mercadoinstrumental.domain.model.anuncio.TipoInstrumentoMusicalEnum;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Geral::Enum")
@RequestMapping("enum")
public class TypeController {

	
	@GetMapping(path = "tipos-instrumentos")
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public List<EnumResponse> listTiposInstrumentos() {
		return EnumResponseMapper.INSTANCE.toEnumResponseList(TipoInstrumentoMusicalEnum.values());
	}
	
	@GetMapping(path = "marcas")
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public List<EnumResponse> listMarcas() {
		return EnumResponseMapper.INSTANCE.toEnumResponseList(MarcaInstrumentoMusicalEnum.values());
	}
	
}
