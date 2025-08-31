package br.com.mercadoinstrumental.manager.site.anuncio;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.com.mercadoinstrumental.controller.commom.manager.EnvioEmailManager;
import br.com.mercadoinstrumental.controller.commom.schema.ResponsePagedCommom;
import br.com.mercadoinstrumental.controller.schema.geral.EnumResponseMapper;
import br.com.mercadoinstrumental.controller.site.anuncio.schema.AnuncioListSiteResponse;
import br.com.mercadoinstrumental.controller.site.anuncio.schema.AnuncioSiteFilter;
import br.com.mercadoinstrumental.controller.site.anuncio.schema.AnuncioSiteResponse;
import br.com.mercadoinstrumental.controller.site.anuncio.schema.AnuncioVendedorSiteResponse;
import br.com.mercadoinstrumental.controller.site.anuncio.schema.EnvioEmailSiteReq;
import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.domain.model.anuncio.ArtefatoAnuncio;
import br.com.mercadoinstrumental.domain.model.anuncio.StatusAnuncioEnum;
import br.com.mercadoinstrumental.domain.model.anuncio.TipoPlanoEnum;
import br.com.mercadoinstrumental.exceptions.BusinessException;
import br.com.mercadoinstrumental.repository.anuncio.AnuncioRepository;
import br.com.mercadoinstrumental.repository.anuncio.ArtefatoAnuncioRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Service
@Validated
public class AnuncioSiteManager {

	@Autowired
	private AnuncioRepository anuncioRepository;

	@Autowired
	private ArtefatoAnuncioRepository artefatoAnuncioRepository;
	
	@Autowired
	private EnvioEmailManager envioEmailManager;
	
	@Value("${aplicacao-web}")
	private String aplicacaoWebUrl;
	
	@Transactional
	public AnuncioSiteResponse findDetailAnuncio(Anuncio anuncio, HttpServletRequest request) {
		
		
	    List<ArtefatoAnuncio> artefatos = artefatoAnuncioRepository.findAllByAnuncioAndMiniaturaAndSrcDocumentoIsNotNull(anuncio, false);

	    Function<ArtefatoAnuncio, String> extrairSrc = it -> it.getSrcDocumento();
	    List<String> artefatoSrcDirs = artefatos.stream()
	        .map(extrairSrc)
	        .toList();

	  	    AnuncioVendedorSiteResponse vendedor = new AnuncioVendedorSiteResponse(
	        anuncio.getUsuario().getNome(),
	        anuncio.getUsuario().getEmail(),
	        anuncio.getUsuario().getWhats()
	    );

		anuncio.incrementarQtdeAcesso();
		anuncioRepository.save(anuncio);
			
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


	public ResponsePagedCommom<AnuncioListSiteResponse> findAllAnuncioPaged(@Valid AnuncioSiteFilter filtros, HttpServletRequest request) {

		List<AnuncioListSiteResponse> listResponse = new ArrayList<AnuncioListSiteResponse>();

		Specification<Anuncio> filtrosCustomizados = (root, query, cb) -> {
			List<Predicate> condicoes = new ArrayList<>();

			condicoes.add(cb.equal(root.get("status"), StatusAnuncioEnum.PUBLICADO));
			
			if (filtros.getTitulo() != null && !filtros.getTitulo().isBlank()) {
				condicoes.add(cb.like(root.get("titulo"), "%" + filtros.getTitulo() + "%"));
			}
			
			if (filtros.getDescricao() != null && !filtros.getDescricao().isBlank()) {
				condicoes.add(cb.like(root.get("descricao"), "%" + filtros.getDescricao() + "%"));
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
			
			if (filtros.getPrecoMin() != null && filtros.getPrecoMin().compareTo(BigDecimal.ZERO) != 0
				    && filtros.getPrecoMax() != null && filtros.getPrecoMax().compareTo(BigDecimal.ZERO) != 0) {
				    condicoes.add(cb.between(root.get("valor"), filtros.getPrecoMin(), filtros.getPrecoMax()));
				} else if (filtros.getPrecoMin() != null && filtros.getPrecoMin().compareTo(BigDecimal.ZERO) != 0) {
				    condicoes.add(cb.greaterThanOrEqualTo(root.get("valor"), filtros.getPrecoMin()));
				} else if (filtros.getPrecoMax() != null && filtros.getPrecoMax().compareTo(BigDecimal.ZERO) != 0) {
				    condicoes.add(cb.lessThanOrEqualTo(root.get("valor"), filtros.getPrecoMax()));
			}

		    query.orderBy(
		            cb.desc(
		                cb.selectCase()
		                    .when(cb.or(
		                        cb.equal(root.get("tipoPlano"), TipoPlanoEnum.PREMIUM),
		                        cb.equal(root.get("tipoPlano"), TipoPlanoEnum.AVANCADO)
		                    ), 1)
		                    .otherwise(0)
		            ),
		            filtros.getDirection().isAscending() ?
		                cb.asc(root.get(filtros.getOrdenarPor())) :
		                cb.desc(root.get(filtros.getOrdenarPor()))
		        );

		        return cb.and(condicoes.toArray(Predicate[]::new));
		};

		Pageable pageable = PageRequest.of(filtros.getPage(), filtros.getSize(), Sort.by(filtros.getDirection(), filtros.getOrdenarPor()));
		Page<Anuncio> listaBd = anuncioRepository.findAll(
						filtrosCustomizados,
						pageable);
		
		
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
							item.getMunicipio(),
							item.getDescricao(),
							item.getQuantidadeAcesso(),
							TipoPlanoEnum.PREMIUM.equals(item.getTipoPlano()) || TipoPlanoEnum.AVANCADO.equals(item.getTipoPlano())));
		});

		return new ResponsePagedCommom<AnuncioListSiteResponse>(
				listResponse, 
				listaBd.getTotalElements(),
				listaBd.getTotalPages(),
				filtros.getSize(),
				filtros.getPage());

	}
	

	public void envioMensagem(Long idAnuncio, @Valid EnvioEmailSiteReq req) {
	    
	    Anuncio anuncio = anuncioRepository.findById(idAnuncio)
	            .orElseThrow(() -> new BusinessException("Anuncio.1000", "Anúncio não encontrado para o id informado."));

	    String linkAnuncio = aplicacaoWebUrl + "/anuncio/" + idAnuncio;

	    String corpoHtml = String.format("""
	            <html>
	            <body>
	                <p>Olá, %s,</p>
	                <p>Você tem uma nova mensagem enviada pelo site <strong>%s</strong> sobre o anúncio <strong><a href="%s">%s</a></strong>.</p>
	                
	                <p><strong>Informações do remetente:</strong></p>
	                <p><strong>Nome:</strong> %s</p>
	                <p><strong>Email:</strong> %s</p>
	                <p><strong>WhatsApp:</strong> %s</p>
	                
	                <p><strong>Mensagem:</strong></p>
	                <p>%s</p>
	                
	                <p><small>Data de envio: %s</small></p>
	            </body>
	            </html>
	            """, 
	            anuncio.getUsuario().getNome(), 
	            "Mercado Instrumental",  
	            linkAnuncio,
	            anuncio.getTitulo(),
	            req.nome(),
	            req.email(),
	            req.whats(),
	            req.mensagem(),
	            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())  
	    );

	    envioEmailManager.enviarEmailHtml(
	        List.of(anuncio.getUsuario().getEmail()),
	        "Mercado Instrumental: Você recebeu uma nova mensagem!",
	        corpoHtml
	    );
	}
	
   
	
}
