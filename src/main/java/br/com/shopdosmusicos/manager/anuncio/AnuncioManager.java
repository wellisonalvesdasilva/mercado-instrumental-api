package br.com.shopdosmusicos.manager.anuncio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.com.shopdosmusicos.controller.admin.anuncio.schema.AnuncioReq;
import br.com.shopdosmusicos.domain.model.anuncio.Anuncio;
import br.com.shopdosmusicos.manager.SegurancaManager;
import br.com.shopdosmusicos.repository.anuncio.AnuncioRepository;
import jakarta.validation.Valid;

@Service
@Validated
public class AnuncioManager {

	@Autowired
	private SegurancaManager segurancaManager;
	
	@Autowired
	private AnuncioRepository anuncioRepository;

	@Transactional
	public Anuncio createAnuncio(@Valid AnuncioReq req) {
		Anuncio anuncio = new Anuncio(req.nome(), req.descricao(), req.tipo(), req.marca(), req.idMunicipioIbge(),
				req.valor(), req.novo(), segurancaManager.obterUsuarioLogado());
		return anuncioRepository.save(anuncio);
	}
/*
	@Transactional
	public createAnuncio updateAnuncio(@Valid Long idCategoria, CategoriaUpd upd) {

		InstrumentoMusical categoria = categoriaRepository.findById(idCategoria).orElseThrow();

		categoria.setAtivo(upd.ativo());
		categoria.setNome(upd.nome());
		categoria.setRgbColor(upd.rgbColor());

		return categoriaRepository.save(categoria);

	}

	@Transactional
	public void deleteCategoria(Long idCategoria) {
		InstrumentoMusical categoria = categoriaRepository.findById(idCategoria).orElseThrow();
		categoriaRepository.delete(categoria);
	}

	public CategoriaResponse findCategoriaById(Long idCategoria) {
		InstrumentoMusical categoria = categoriaRepository.findById(idCategoria).orElseThrow();
		return CategoriaMapper.INSTANCE.toCategoriaResponse(categoria);
	}

	public ResponsePagedCommom<CategoriaResponse> findAllCategoria(@Valid CategoriaFilter filtros) {

		List<CategoriaResponse> listResponse = new ArrayList<CategoriaResponse>();

		Specification<InstrumentoMusical> filtrosCustomizados = (root, query, cb) -> {
			List<Predicate> condicoes = new ArrayList<>();

			if (filtros.getNome() != null && !filtros.getNome().isBlank()) {
				condicoes.add(cb.equal(root.get("nome"), filtros.getNome()));
			}

			if (filtros.getCor() != null && !filtros.getCor().isBlank()) {
				condicoes.add(cb.like(root.get("categoria").get("rgbColor"), "%" + filtros.getCor() + "%"));
			}

			return cb.and(condicoes.toArray(Predicate[]::new));
		};

		Page<InstrumentoMusical> listaBd = categoriaRepository.findAll(filtrosCustomizados, PageRequest
				.of(filtros.getPage(), filtros.getSize(), Sort.by(filtros.getDirection(), filtros.getOrdenarPor())));
		listaBd.forEach(item -> listResponse.add(CategoriaMapper.INSTANCE.toCategoriaResponse(item)));

		return new ResponsePagedCommom<CategoriaResponse>(listResponse, listaBd.getTotalElements(),
				listaBd.getTotalPages(), filtros.getSize(), filtros.getPage());

	}
*/
}
