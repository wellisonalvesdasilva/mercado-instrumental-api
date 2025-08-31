package br.com.mercadoinstrumental.manager.admin.indicacao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.mercadoinstrumental.controller.admin.indicacao.schema.ComissaoIndicadorResponse;
import br.com.mercadoinstrumental.controller.admin.indicacao.schema.DashboardIndicadorResponse;
import br.com.mercadoinstrumental.controller.admin.indicacao.schema.IndicacaoFilter;
import br.com.mercadoinstrumental.controller.admin.indicacao.schema.IndicacaoResponse;
import br.com.mercadoinstrumental.controller.commom.schema.ResponsePagedCommom;
import br.com.mercadoinstrumental.controller.schema.geral.EnumResponseMapper;
import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.domain.model.anuncio.StatusAnuncioEnum;
import br.com.mercadoinstrumental.domain.model.anuncio.TipoPlanoEnum;
import br.com.mercadoinstrumental.manager.SegurancaManager;
import br.com.mercadoinstrumental.model.usuario.TipoChavePixEnum;
import br.com.mercadoinstrumental.model.usuario.Usuario;
import br.com.mercadoinstrumental.repository.anuncio.AnuncioRepository;
import br.com.mercadoinstrumental.usuario.repository.UsuarioRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Validated
public class IndicacaoManager {

	@Autowired
	private SegurancaManager segurancaManager;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AnuncioRepository anuncioRepository;
	
	@Value("${aplicacao-web}")
	public String urlBaseFront;
	
	public ResponsePagedCommom<IndicacaoResponse> findAllUsuariosIndicadosPaged(@Valid IndicacaoFilter filtros) {

		List<IndicacaoResponse> listResponse = new ArrayList<IndicacaoResponse>();

		Specification<Usuario> filtrosCustomizados = (root, query, cb) -> {
			List<Predicate> condicoes = new ArrayList<>();
			
			if (filtros.getId() != null) {
				condicoes.add(cb.equal(root.get("id"), filtros.getId()));
			}
			
			if (filtros.getNome() != null && !filtros.getNome().isBlank()) {
				condicoes.add(cb.like(root.get("nome"), "%" + filtros.getNome() + "%"));
			}
			
			if (filtros.getDataCadastro() != null) {
				condicoes.add(cb.equal(root.get("dataCadastro"), filtros.getDataCadastro()));
			}
			
			if (Boolean.FALSE.equals(filtros.getVisaoAdmin())) {
				condicoes.add(cb.equal(root.get("hashDeQuemIndicou"), segurancaManager.obterUsuarioLogado().getHashPropria()));
			} 
			
			if (filtros.getTiposChavePix() != null && !filtros.getTiposChavePix().isBlank()) {
				condicoes.add(cb.equal(root.get("tipoChavePix"), TipoChavePixEnum.fromLabel(filtros.getTiposChavePix())));
			} 
			
			if (filtros.getChavePix() != null && !filtros.getChavePix().isBlank()) {
				condicoes.add(cb.equal(root.get("chavePix"), filtros.getChavePix()));
			} 
			
			if (filtros.getWhats() != null && !filtros.getWhats().isBlank()) {
				condicoes.add(cb.equal(root.get("whats"), filtros.getWhats()));
			} 

		    if (filtros.getQuantidadeAnunciosPublicados() != null) {
		        Subquery<Long> subqueryAnuncios = query.subquery(Long.class);
		        Root<Anuncio> anuncio = subqueryAnuncios.from(Anuncio.class);
		        subqueryAnuncios.select(cb.count(anuncio));
		        subqueryAnuncios.where(cb.equal(anuncio.get("usuario"), root));
		        condicoes.add(cb.equal(subqueryAnuncios, filtros.getQuantidadeAnunciosPublicados()));
		    }

		    if (filtros.getQuantidadeIndicacoes() != null) {
		        Subquery<Long> subqueryIndicados = query.subquery(Long.class);
		        Root<Usuario> u = subqueryIndicados.from(Usuario.class);
		        subqueryIndicados.select(cb.count(u));
		        subqueryIndicados.where(cb.equal(u.get("hashDeQuemIndicou"), root.get("hashPropria")));
		        condicoes.add(cb.equal(subqueryIndicados, filtros.getQuantidadeIndicacoes()));
		    }

		    if (Boolean.TRUE.equals(filtros.getHasComissaoPendente())) {
		        Subquery<Long> subqueryPag = query.subquery(Long.class);
		        Root<Usuario> u = subqueryPag.from(Usuario.class);
		        Join<Usuario, Anuncio> anuncios = u.join("anuncios", JoinType.LEFT);
		        subqueryPag.select(cb.count(anuncios));
		        subqueryPag.where(
		            cb.equal(u.get("hashDeQuemIndicou"), root.get("hashPropria")),
		            cb.isNull(anuncios.get("numeroTransacaoPagamento"))
		        );
		        condicoes.add(cb.greaterThan(subqueryPag, 0L));
		    }
			
			return cb.and(condicoes.toArray(Predicate[]::new));
		};

		Pageable pageable = PageRequest.of(filtros.getPage(), filtros.getSize(), Sort.by(filtros.getDirection(), filtros.getOrdenarPor()));
		Page<Usuario> listaBd = usuarioRepository.findAll(filtrosCustomizados, pageable);

		listaBd.forEach(item -> { 
			long qtdAnuncios = anuncioRepository.countByUsuarioId(item.getId());
		    long qtdUsuariosIndicados = usuarioRepository.countByHashDeQuemIndicou(item.getHashPropria());
		    boolean pagamentoPendente = usuarioRepository.existsByHashDeQuemIndicouAndAnunciosNumeroTransacaoPagamentoIsNull(item.getHashPropria());
		    listResponse.add(new IndicacaoResponse(
		        item.getId(), 
		        item.getNome(), 
		        item.getDataCadastro(),
		        qtdUsuariosIndicados,
		        qtdAnuncios,
		        pagamentoPendente,
		        item.getChavePix(),
		        EnumResponseMapper.INSTANCE.toEnumResponse(item.getTipoChavePix()),
		        item.getWhats()
		    ));
		});


		return new ResponsePagedCommom<IndicacaoResponse>(listResponse, listaBd.getTotalElements(),
				listaBd.getTotalPages(), filtros.getSize(), filtros.getPage());

	}
	
	
	public DashboardIndicadorResponse findDashboardIndicador(Boolean isGeral) {

		Usuario usuarioLogado = segurancaManager.obterUsuarioLogado();
	    String hashDeQuemIndicou = isGeral ? null : usuarioLogado.getHashDeQuemIndicou();
	    String hashUsuarioLogado = isGeral ? null : usuarioLogado.getHashPropria();

	    return new DashboardIndicadorResponse(
	        usuarioLogado.getHashPropria(),
	        anuncioRepository.somatorioPlanosPagosSemTransacao(hashDeQuemIndicou),
	        anuncioRepository.somatorioPlanosPagosComTransacao(hashDeQuemIndicou),
	        usuarioRepository.countUsuariosComHashIndicador(hashUsuarioLogado),
	        usuarioRepository.countUsuariosComAnuncioProPorIndicador(hashUsuarioLogado),
	        usuarioLogado.getChavePix(),
	        EnumResponseMapper.INSTANCE.toEnumResponse(usuarioLogado.getTipoChavePix())
	    );
	}



	public List<ComissaoIndicadorResponse> findAllPublicacoesByUsuario(Long idUsuario, Boolean isAdmin) {
		
	    List<ComissaoIndicadorResponse> lista = new ArrayList<>();
			
	    List<Anuncio> anuncios = new ArrayList<>();
	    if (isAdmin) {
	    	Usuario usuario = usuarioRepository.findById(idUsuario);
	    	anuncios = anuncioRepository.findAllByUsuario_HashDeQuemIndicou(usuario.getHashPropria());
	    } else {
	    	anuncios = anuncioRepository.findAllByUsuario_Id(idUsuario);
	    }
	    anuncios.forEach(it -> {
	        BigDecimal comissao = it.getTipoPlano().equals(TipoPlanoEnum.GRATIS) 
	        		? BigDecimal.ZERO : it.getValorPagoPlano()
	            .multiply(new BigDecimal("0.20"))
	            .setScale(2, RoundingMode.HALF_UP);

	        String link = String.format("%s/%d", this.urlBaseFront + "/anuncio/", it.getId());

	        String statusComissao = definirStatusComissao(it);
	        lista.add(new ComissaoIndicadorResponse(
	                it.getId(),
	                it.getTitulo(),
	                EnumResponseMapper.INSTANCE.toEnumResponse(it.getTipoPlano()),
	                it.getValorPagoPlano(),
	                comissao,
	                it.getDataPagamentoComissao(),
	                it.getNumeroTransacaoPagamento(),
	                statusComissao,
	                link
	        ));

	    });
			
	    return lista;
	}
	
	private String definirStatusComissao(Anuncio it) {
	    if (TipoPlanoEnum.GRATIS.equals(it.getTipoPlano())) {
	        return "Sem Comissão";
	    }
	    if (it.getDataPagamentoComissao() == null) {
	        return "Comissão a Pagar";
	    }
	    return "Comissão Paga";
	}



	@Transactional
	public void indicacaoPagamento(Long idAnuncio, LocalDate dataPagamentoComissao, String numeroTransacao) {
		Anuncio anuncio = anuncioRepository.findById(idAnuncio).orElseThrow();
		anuncio.setDataPagamentoComissao(dataPagamentoComissao);
		anuncio.setNumeroTransacaoPagamento(numeroTransacao);
		anuncioRepository.save(anuncio);
	}

	@Transactional
	public void saveChavePix(String chavePix, TipoChavePixEnum tipoChavePix) {
		Usuario usuario = segurancaManager.obterUsuarioLogado();
		usuario.setChavePix(chavePix);
		usuario.setTipoChavePix(tipoChavePix);
		usuarioRepository.save(usuario);
	}
	
}
