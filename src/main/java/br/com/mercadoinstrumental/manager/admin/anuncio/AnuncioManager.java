package br.com.mercadoinstrumental.manager.admin.anuncio;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

import br.com.mercadoinstrumental.client.webhook.rws.schema.LiquidateInvoiceWebhookReq;
import br.com.mercadoinstrumental.controller.admin.anuncio.mapper.AnuncioMapper;
import br.com.mercadoinstrumental.controller.admin.anuncio.schema.AnuncioFilter;
import br.com.mercadoinstrumental.controller.admin.anuncio.schema.AnuncioReq;
import br.com.mercadoinstrumental.controller.admin.anuncio.schema.AnuncioResponse;
import br.com.mercadoinstrumental.controller.admin.anuncio.schema.AnuncioUpd;
import br.com.mercadoinstrumental.controller.commom.manager.EnvioEmailManager;
import br.com.mercadoinstrumental.controller.commom.schema.ResponsePagedCommom;
import br.com.mercadoinstrumental.domain.model.anuncio.Anuncio;
import br.com.mercadoinstrumental.domain.model.anuncio.ArtefatoAnuncio;
import br.com.mercadoinstrumental.domain.model.anuncio.MarcaInstrumentoMusicalEnum;
import br.com.mercadoinstrumental.domain.model.anuncio.StatusAnuncioEnum;
import br.com.mercadoinstrumental.domain.model.anuncio.TipoInstrumentoMusicalEnum;
import br.com.mercadoinstrumental.domain.model.anuncio.TipoPlanoEnum;
import br.com.mercadoinstrumental.exceptions.BusinessException;
import br.com.mercadoinstrumental.manager.SegurancaManager;
import br.com.mercadoinstrumental.model.usuario.TipoPerfilEnum;
import br.com.mercadoinstrumental.model.usuario.Usuario;
import br.com.mercadoinstrumental.repository.anuncio.AnuncioRepository;
import br.com.mercadoinstrumental.repository.anuncio.ArtefatoAnuncioRepository;
import br.com.mercadoinstrumental.usuario.repository.UsuarioRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;

@Service
@Validated
public class AnuncioManager {

	@Autowired
	private SegurancaManager segurancaManager;

	@Autowired
	private AnuncioRepository anuncioRepository;

	@Autowired
	private ArtefatoAnuncioRepository artefatoAnuncioRepository;

	@Autowired
	private EnvioEmailManager envioEmaiManager;

	@Autowired
	private LytexManager lytexManager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Value("${aplicacao-web}")
	public String urlBaseFront;

	@Value("${clients.rws-lytex.urlBase}")
	public String urlBaseLytex;
	
	@Transactional
	public Anuncio createAnuncio(@Valid AnuncioReq req) {

		Usuario usuarioLogado = segurancaManager.obterUsuarioLogado();

		validacoesCadastro(req, usuarioLogado);
		
		Anuncio anuncio = new Anuncio(req.titulo(), req.descricao(), req.tipo(), req.marca(), req.estado(),
				req.municipio(), req.valor(), req.novo(), Boolean.TRUE, usuarioLogado, req.tipoPlano());

		// Guardar o valor pago pelo plano.
		anuncio.setValorPagoPlano(BigDecimal.valueOf(req.tipoPlano().getPrice(), 2));
				
		anuncio = anuncioRepository.save(anuncio);
		criarArtefatosRascunho(anuncio);

		return anuncio;
	}


	@Transactional
	public String updateAnuncio(@Valid Long idAnuncio, AnuncioUpd upd) {

		Anuncio anuncio = anuncioRepository.findById(idAnuncio)
				.orElseThrow(BusinessException.from("Anuncio.1000", "Anuncio não encontrado para o id informado."));

		anuncio.setTitulo(upd.titulo());
		anuncio.setDescricao(upd.descricao());
		anuncio.setTipo(upd.tipo());
		anuncio.setMarca(upd.marca());
		anuncio.setMunicipio(upd.municipio());
		anuncio.setEstado(upd.estado());
		anuncio.setValor(upd.valor());
		anuncio.setNovo(upd.novo());
		anuncio.setStatus(upd.status());
		
		
		String retorno = "";
		if (StatusAnuncioEnum.AGUARDANDO_PUBLICACAO.equals(upd.status())) {
			regrasAoCadastrar(anuncio);
			retorno = anuncio.getHashIdPagamentoLytex() != null ? (this.urlBaseLytex + "/" + anuncio.getHashIdPagamentoLytex()) : "";
		} else if (StatusAnuncioEnum.PUBLICADO.equals(upd.status())) {
			regrasAoAprovar(anuncio);
		}

		anuncioRepository.save(anuncio);
		
		return retorno;
	}


	@Transactional
	public void cancelAnuncio(Long idAnuncio) {

		Anuncio anuncio = anuncioRepository.findById(idAnuncio)
				.orElseThrow(BusinessException.from("Anuncio.1000", "Anuncio não encontrado para o id informado."));

		artefatoAnuncioRepository.findAllByAnuncio(anuncio).forEach(artefato -> {

			if (artefato.getSrcDocumento() != null && !artefato.getSrcDocumento().isEmpty()) {
				Path caminhoArquivo = Paths.get(artefato.getSrcDocumento());
				try {
					Files.deleteIfExists(caminhoArquivo);
				} catch (IOException e) {
					System.err.println("Erro ao deletar arquivo: " + caminhoArquivo + ". " + e.getMessage());
				}
			}

			artefatoAnuncioRepository.delete(artefato);
		});

		anuncio.setStatus(StatusAnuncioEnum.CANCELADO);
		anuncioRepository.save(anuncio);
		

	}

	public AnuncioResponse findAnuncioById(Long idAnuncio) {

		Anuncio anuncio = anuncioRepository.findById(idAnuncio)
				.orElseThrow(BusinessException.from("Anuncio.1000", "Anuncio não encontrado para o id informado."));

		return AnuncioMapper.INSTANCE.toAnuncioResponse(anuncio, anuncio.getDataHoraPublicacao() != null ? anuncio.getDataHoraPublicacao().toLocalDate() : null, urlBaseLytex);
	}

	public ResponsePagedCommom<AnuncioResponse> findAllAnuncioPaged(@Valid AnuncioFilter filtros) {

		List<AnuncioResponse> listResponse = new ArrayList<AnuncioResponse>();

		Specification<Anuncio> filtrosCustomizados = (root, query, cb) -> {
			List<Predicate> condicoes = new ArrayList<>();

			if (filtros.getListarParaRevisao() == null || Boolean.FALSE.equals(filtros.getListarParaRevisao())) {
				condicoes.add(cb.equal(root.get("usuario"), segurancaManager.obterUsuarioLogado()));
			} else {
				condicoes.add(cb.equal(root.get("status"), StatusAnuncioEnum.AGUARDANDO_PUBLICACAO));
			}

			if (filtros.getId() != null) {
				condicoes.add(cb.equal(root.get("id"), filtros.getId()));
			}

			if (filtros.getTitulo() != null && !filtros.getTitulo().isBlank()) {
				condicoes.add(cb.like(root.get("titulo"), "%" + filtros.getTitulo() + "%"));
			}

			if (filtros.getStatus() != null) {
				StatusAnuncioEnum statusEnum = StatusAnuncioEnum.fromLabel(filtros.getStatus());
				condicoes.add(cb.equal(root.get("status"), statusEnum));
			}

			if (filtros.getTipo() != null) {
				TipoInstrumentoMusicalEnum tipoEnum = TipoInstrumentoMusicalEnum.fromLabel(filtros.getTipo());
				condicoes.add(cb.equal(root.get("tipo"), tipoEnum));
			}

			if (filtros.getMarca() != null) {
				MarcaInstrumentoMusicalEnum tipoEnum = MarcaInstrumentoMusicalEnum.fromLabel(filtros.getMarca());
				condicoes.add(cb.equal(root.get("marca"), tipoEnum));
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
				condicoes.add(cb.between(root.get("dataHoraPublicacao"), filtros.getDataPublicacao().atStartOfDay(),
						filtros.getDataPublicacao().atTime(LocalTime.MAX)));
			}

			if (filtros.getQuantidadeAcesso() != null) {
				condicoes.add(cb.equal(root.get("quantidadeAcesso"), filtros.getQuantidadeAcesso()));
			}

			return cb.and(condicoes.toArray(Predicate[]::new));
		};

		Pageable pageable = PageRequest.of(filtros.getPage(), filtros.getSize(),
				Sort.by(filtros.getDirection(), filtros.getOrdenarPor()));

		Page<Anuncio> listaBd = anuncioRepository.findAll(filtrosCustomizados, pageable);

		listaBd.forEach(item -> listResponse
				.add(AnuncioMapper.INSTANCE.toAnuncioResponse(item, item.getDataHoraPublicacao() != null ? item.getDataHoraPublicacao().toLocalDate() : null, urlBaseLytex)));

		return new ResponsePagedCommom<AnuncioResponse>(listResponse, listaBd.getTotalElements(),
				listaBd.getTotalPages(), filtros.getSize(), filtros.getPage());

	}

	private void criarArtefatosRascunho(Anuncio anuncio) {
		for (int i = 0; i < 5; i++) {
			artefatoAnuncioRepository.save(new ArtefatoAnuncio(anuncio, i == 0, i + 1));
		}
	}

	private void validarAntesDePublicar(Anuncio anuncio) {
		boolean possuiImagemNaoEnviada = artefatoAnuncioRepository.existsByAnuncioAndSrcDocumentoIsNullAndNumeroIn(anuncio, List.of(1, 2));

		if (possuiImagemNaoEnviada) {
			throw new BusinessException(
					"Existem imagens que ainda não foram enviadas. Verifique antes de solicitar a publicação do anúncio.");
		}
	}

	private void sendEmailForRevision(Anuncio anuncio) {

		List<Usuario> administradores = usuarioRepository.findByTipoPerfil(TipoPerfilEnum.ADMINISTRADOR.getCod());
		if (administradores.isEmpty()) {
			throw new BusinessException("Nenhum usuário administrador foi encontrado para aprovar o anúncio.");
		}
		
		String corpo = String.format("""
				<html>
				<body>
				    <p><strong>Solicitação de novo anúncio</strong></p>

				    <p><strong>Autor:</strong> %s</p>
				    <p><strong>Título do anúncio:</strong> %s</p>
				    <p><strong>Data da solicitação:</strong> %s</p>

				    <p>Revise e aprove pelo painel administrativo:</p>
				    <p><a href="%s/admin/anuncios/revisao/form/" target="_blank">%s</a></p>
				</body>
				</html>
				""", anuncio.getUsuario().getNome(), anuncio.getTitulo(),
				new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()), // Data
				urlBaseFront, anuncio.getId());

		envioEmaiManager.enviarEmailHtml(administradores.stream().map(Usuario::getEmail).toList(),
				"Aprovação de Anúncio", corpo);
	}

	private void sendEmailAproved(Anuncio anuncio) {

		String corpo = String.format("""
				<html>
				<body>
				    <p><strong>Anúncio aprovado com sucesso</strong></p>

				    <p>Olá, <strong>%s</strong>,</p>
				    <p>Seu anúncio foi aprovado pela equipe administrativa.</p>

				    <p><strong>Título do anúncio:</strong> %s</p>
				    <p><strong>Data da aprovação:</strong> %s</p>

				    <p>Você pode visualizar o anúncio aprovado no painel administrativo clicando no link abaixo:</p>
				    <p><a href="%s/%s" target="_blank">Ver anúncio</a></p>
				</body>
				</html>
				""", anuncio.getUsuario().getNome(), anuncio.getTitulo(),
				new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()),
				urlBaseFront, anuncio.getId());

		envioEmaiManager.enviarEmailHtml(List.of(anuncio.getUsuario().getEmail()), "Anúncio aprovado", corpo);

	}
	
	private void validacoesCadastro(@Valid AnuncioReq req, Usuario usuario) {
		
	    if (TipoPlanoEnum.GRATIS.equals(req.tipoPlano()) && (anuncioRepository.existsByUsuarioAndTipoPlano(usuario, TipoPlanoEnum.GRATIS) || Boolean.TRUE.equals(usuario.getTeveAnuncioGratis()))) {
	        throw new BusinessException("Usuário tem ou já teve um anúncio grátis cadastrado.");
	    }
	}

	

	private void definirDataHoraExpiracao(Anuncio anuncio) {
	    long diasParaExpirar = switch (anuncio.getTipoPlano()) {
	        case GRATIS   -> 15;
	        case BASICO   -> 20;
	        case PREMIUM  -> 30;
	        case AVANCADO -> 60;
	    };
	    anuncio.setDataHoraExpiracao(anuncio.getDataHoraPublicacao().plusDays(diasParaExpirar));
	}

	
	private void regrasAoCadastrar(Anuncio anuncio) {
		validarAntesDePublicar(anuncio);
		if (TipoPlanoEnum.GRATIS.equals(anuncio.getTipoPlano())) {
			sendEmailForRevision(anuncio);
		} else {
			anuncio.setStatus(StatusAnuncioEnum.AGUARDANDO_CONFIRMACAO_PAGAMENTO);
			lytexManager.createPaymentLytex(anuncio);
		}
	}
	
	
	private void regrasAoAprovar(Anuncio anuncio) {
		
		definirDatasAnuncio(anuncio);
		if (TipoPlanoEnum.GRATIS.equals(anuncio.getTipoPlano())) {
			anuncio.getUsuario().setTeveAnuncioGratis(true);
			usuarioRepository.save(anuncio.getUsuario());
		}
		
		sendEmailAproved(anuncio);
	}
	
	private void definirDatasAnuncio(Anuncio anuncio) {
		if (anuncio.getDataHoraPublicacao() == null) {
			anuncio.setDataHoraPublicacao(LocalDateTime.now());
			definirDataHoraExpiracao(anuncio);
		}
	}
	
	@Transactional
	public void expirarAnuncios() {
	    List<Anuncio> expirados = anuncioRepository.findAllByDataHoraExpiracaoLessThanEqual(LocalDateTime.now());
	    expirados.forEach(item -> {
	    	item.setStatus(StatusAnuncioEnum.EXPIRADO);
	    	anuncioRepository.save(item);
	    });
	}


	public Boolean userHasOrHadAnuncioFree() {
		String email = segurancaManager.obterUsuarioLogado().getEmail();
		Boolean userHasOrHadAnuncioFree = anuncioRepository.existsByTipoPlanoAndUsuario_Email(TipoPlanoEnum.GRATIS, segurancaManager.obterUsuarioLogado().getEmail());
		userHasOrHadAnuncioFree = !userHasOrHadAnuncioFree ? usuarioRepository.findByEmailAndAtivo(email, true).orElseThrow().getTeveAnuncioGratis() : userHasOrHadAnuncioFree;
		return userHasOrHadAnuncioFree;
	}


	@Transactional
	public void finalizeInvoicePayment(@Valid LiquidateInvoiceWebhookReq req) {
		if (Objects.isNull(req) || Objects.isNull(req.getWebhookType())) {
			throw new BusinessException("Favor informar um JSON válido.");
		} else if (req.getWebhookType().equals("liquidateInvoice")) {
			
			Anuncio anuncio = anuncioRepository.findByIdPagamentoLytex(req.getData().get_paymentLinkId());
		    if (anuncio == null || !StatusAnuncioEnum.AGUARDANDO_CONFIRMACAO_PAGAMENTO.equals(anuncio.getStatus())) {
		        throw new BusinessException("Anúncio não apto para liquidação.");
		    }
	
		    anuncio.setStatus(StatusAnuncioEnum.AGUARDANDO_PUBLICACAO);
		    anuncioRepository.save(anuncio);
		    
		    sendEmailForRevision(anuncio);
		}
	}

}
