package br.com.shopdosmusicos.manager.admin.anuncio;

import java.time.LocalTime;
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
import org.springframework.web.multipart.MultipartFile;

import br.com.shopdosmusicos.controller.admin.anuncio.mapper.AnuncioMapper;
import br.com.shopdosmusicos.controller.admin.anuncio.schema.AnuncioFilter;
import br.com.shopdosmusicos.controller.admin.anuncio.schema.AnuncioReq;
import br.com.shopdosmusicos.controller.admin.anuncio.schema.AnuncioResponse;
import br.com.shopdosmusicos.controller.admin.anuncio.schema.AnuncioUpd;
import br.com.shopdosmusicos.controller.commom.schema.ResponsePagedCommom;
import br.com.shopdosmusicos.domain.model.anuncio.Anuncio;
import br.com.shopdosmusicos.manager.SegurancaManager;
import br.com.shopdosmusicos.model.usuario.Usuario;
import br.com.shopdosmusicos.repository.anuncio.AnuncioRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
@Validated
public class AnuncioManager {

	@Autowired
	private SegurancaManager segurancaManager;
	
	@Autowired
	private AnuncioRepository anuncioRepository;

	
	@Transactional
	public Anuncio createAnuncio(@Valid AnuncioReq req) {
		
	    Usuario usuarioLogado = segurancaManager.obterUsuarioLogado();

	    Anuncio anuncio = new Anuncio(
	        req.titulo(),
	        req.descricao(),
	        req.tipo(),
	        req.marca(),
	        req.estado(),
	        req.municio(),
	        req.valor(),
	        req.novo(),
	        Boolean.TRUE,
	        usuarioLogado
	    );

	    return anuncioRepository.save(anuncio);
	}

	
	@Transactional
	public Anuncio updateAnuncio(@Valid Long idAnuncio, AnuncioUpd upd) {
	    Anuncio anuncio = anuncioRepository.findById(idAnuncio).orElseThrow();

	    anuncio.setTitulo(upd.titulo());
	    anuncio.setDescricao(upd.descricao());
	    anuncio.setTipo(upd.tipo());
	    anuncio.setMarca(upd.marca());
	    anuncio.setMunicipio(upd.municio());
	    anuncio.setEstado(upd.estado());
	    anuncio.setValor(upd.valor());
	    anuncio.setNovo(upd.novo());

	    return anuncioRepository.save(anuncio);
	}

	

	@Transactional
	public void deleteAnuncio(Long idAnuncio) {
	    Anuncio anuncio = anuncioRepository.findById(idAnuncio).orElseThrow();
	    anuncioRepository.delete(anuncio);
	}

	
	public AnuncioResponse findAnuncioById(Long idAnuncio) {
	    Anuncio anuncio = anuncioRepository.findById(idAnuncio).orElseThrow();
	    return AnuncioMapper.INSTANCE.toAnuncioResponse(anuncio);
	}


	public ResponsePagedCommom<AnuncioResponse> findAllAnuncioPaged(@Valid AnuncioFilter filtros) {

		List<AnuncioResponse> listResponse = new ArrayList<AnuncioResponse>();

		Specification<Anuncio> filtrosCustomizados = (root, query, cb) -> {
			List<Predicate> condicoes = new ArrayList<>();

			if (filtros.getTitulo() != null && !filtros.getTitulo().isBlank()) {
				condicoes.add(cb.equal(root.get("titulo"), filtros.getTitulo()));
			}
			
			if (filtros.getTipo() != null) {
				condicoes.add(cb.equal(root.get("tipo"), filtros.getTipo()));
			}

			if (filtros.getMarca() != null) {
				condicoes.add(cb.equal(root.get("marca"), filtros.getMarca()));
			}

			if (filtros.getEstado() != null) {
				condicoes.add(cb.like(root.get("estado"), "%" + filtros.getEstado() + "%"));
			}
			
			if (filtros.getMunicipio() != null) {
				condicoes.add(cb.like(root.get("municipio"), "%" + filtros.getMunicipio() + "%"));
			}
			
			if (filtros.getValor() != null) {
				condicoes.add(cb.equal(root.get("valor"), filtros.getValor()));
			}
			
			if (filtros.getNovo() != null) {
				condicoes.add(cb.equal(root.get("novo"), filtros.getNovo()));
			}
			
			if (filtros.getAtivo() != null) {
				condicoes.add(cb.equal(root.get("ativo"), filtros.getAtivo()));
			}
			
			if (filtros.getDataPublicacao() != null) {
			    condicoes.add(cb.between(root.get("dataPublicacao"), filtros.getDataPublicacao().atStartOfDay(), filtros.getDataPublicacao().atTime(LocalTime.MAX)));
			}
			
			return cb.and(condicoes.toArray(Predicate[]::new));
		};

		Page<Anuncio> listaBd = anuncioRepository.findAll(
						filtrosCustomizados, PageRequest.of(filtros.getPage(),
						filtros.getSize(),
						Sort.by(filtros.getDirection(),
						filtros.getOrdenarPor())));
		
		listaBd.forEach(item -> listResponse.add(AnuncioMapper.INSTANCE.toAnuncioResponse(item)));

		return new ResponsePagedCommom<AnuncioResponse>(
				listResponse, 
				listaBd.getTotalElements(),
				listaBd.getTotalPages(),
				filtros.getSize(),
				filtros.getPage());

	}


}
