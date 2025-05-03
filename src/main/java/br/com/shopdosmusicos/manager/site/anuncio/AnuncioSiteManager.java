package br.com.shopdosmusicos.manager.site.anuncio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.com.shopdosmusicos.controller.commom.schema.ResponsePagedCommom;
import br.com.shopdosmusicos.controller.schema.geral.EnumResponseMapper;
import br.com.shopdosmusicos.controller.site.anuncio.schema.AnuncioListSiteResponse;
import br.com.shopdosmusicos.controller.site.anuncio.schema.AnuncioSiteFilter;
import br.com.shopdosmusicos.controller.site.anuncio.schema.AnuncioSiteResponse;
import br.com.shopdosmusicos.controller.site.anuncio.schema.AnuncioVendedorSiteResponse;
import br.com.shopdosmusicos.domain.model.anuncio.Anuncio;
import br.com.shopdosmusicos.domain.model.anuncio.ArtefatoAnuncio;
import br.com.shopdosmusicos.repository.anuncio.AnuncioRepository;
import br.com.shopdosmusicos.repository.anuncio.ArtefatoAnuncioRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;

@Service
@Validated
public class AnuncioSiteManager {

	@Autowired
	private AnuncioRepository anuncioRepository;

	@Autowired
	private ArtefatoAnuncioRepository artefatoAnuncioRepository;
	
	
	public AnuncioSiteResponse findDetailAnuncio(Anuncio anuncio) {
	    List<ArtefatoAnuncio> artefatos = artefatoAnuncioRepository.findAllByAnuncio(anuncio);

	    List<String> artefatoSrcDirs = artefatos.stream()
	        .map(ArtefatoAnuncio::getSrcDocumento)
	        .toList();

	    AnuncioVendedorSiteResponse vendedor = new AnuncioVendedorSiteResponse(
	        anuncio.getUsuario().getNome(),
	        anuncio.getUsuario().getEmail(),
	        anuncio.getUsuario().getWhats()
	    );

	    return new AnuncioSiteResponse(
	        anuncio.getId(),
	        anuncio.getTitulo(),
	        anuncio.getDataHoraPublicacao().toLocalDate(),
	        anuncio.getNovo(),
	        anuncio.getValor(),
	        vendedor,
	        anuncio.getQuantidadeAcesso(),
	        anuncio.getEstado(),
	        anuncio.getMunicipio(),
	        anuncio.getDescricao(),
	        artefatoSrcDirs
	    );
	}



	public ResponsePagedCommom<AnuncioListSiteResponse> findAllAnuncioPaged(@Valid AnuncioSiteFilter filtros) {

		List<AnuncioListSiteResponse> listResponse = new ArrayList<AnuncioListSiteResponse>();

		Specification<Anuncio> filtrosCustomizados = (root, query, cb) -> {
			List<Predicate> condicoes = new ArrayList<>();

			condicoes.add(cb.equal(root.get("ativo"), true));
			
			if (filtros.getTitulo() != null && !filtros.getTitulo().isBlank()) {
				condicoes.add(cb.equal(root.get("titulo"), filtros.getTitulo()));
			}
			
			if (filtros.getTipo() != null) {
				condicoes.add(cb.equal(root.get("tipo"), filtros.getTipo()));
			}

			if (filtros.getMarca() != null) {
				condicoes.add(cb.equal(root.get("marca"), filtros.getMarca()));
			}
			
			if (filtros.getCondicao() != null) {
				condicoes.add(cb.equal(root.get("novo"), filtros.getCondicao()));
			}
			

			if (filtros.getEstado() != null) {
				condicoes.add(cb.like(root.get("estado"), "%" + filtros.getEstado() + "%"));
			}
			
			if (filtros.getMunicipio() != null) {
				condicoes.add(cb.like(root.get("municipio"), "%" + filtros.getMunicipio() + "%"));
			}
			
			if (filtros.getPrecoMin() != null && filtros.getPrecoMax() != null) {
			    condicoes.add(cb.between(root.get("valor"), filtros.getPrecoMin(), filtros.getPrecoMax()));
			} else if (filtros.getPrecoMin() != null) {
			    condicoes.add(cb.greaterThanOrEqualTo(root.get("valor"), filtros.getPrecoMin()));
			} else if (filtros.getPrecoMax() != null) {
			    condicoes.add(cb.lessThanOrEqualTo(root.get("valor"), filtros.getPrecoMax()));
			}

			
			return cb.and(condicoes.toArray(Predicate[]::new));
		};

		Page<Anuncio> listaBd = anuncioRepository.findAll(
						filtrosCustomizados, PageRequest.of(filtros.getPage(),
						filtros.getSize(),
						Sort.by(filtros.getDirection(),
						filtros.getOrdenarPor())));
		
		listaBd.forEach(item -> {
			ArtefatoAnuncio miniatura = artefatoAnuncioRepository.findByAnuncioAndMiniatura(item, true);
			listResponse.add(
					new AnuncioListSiteResponse(
							item.getId(), 
							miniatura.getSrcDocumento(),
							item.getNovo(),
							EnumResponseMapper.INSTANCE.toEnumResponse(item.getMarca()),
							item.getTitulo(),
							item.getValor(),
							item.getEstado(),
							item.getMunicipio()));
		});

		return new ResponsePagedCommom<AnuncioListSiteResponse>(
				listResponse, 
				listaBd.getTotalElements(),
				listaBd.getTotalPages(),
				filtros.getSize(),
				filtros.getPage());

	}
	
	
	@Transactional
	public void atualizarQtdeAcesso(Anuncio anuncio) {
	    anuncio.incrementarQtdeAcesso();
	    anuncioRepository.save(anuncio);
	}


}
