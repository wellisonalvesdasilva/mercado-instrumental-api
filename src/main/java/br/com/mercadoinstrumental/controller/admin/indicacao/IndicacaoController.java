package br.com.mercadoinstrumental.controller.admin.indicacao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadoinstrumental.controller.admin.indicacao.schema.ComissaoIndicadorResponse;
import br.com.mercadoinstrumental.controller.admin.indicacao.schema.DashboardIndicadorResponse;
import br.com.mercadoinstrumental.controller.admin.indicacao.schema.IndicacaoFilter;
import br.com.mercadoinstrumental.controller.admin.indicacao.schema.IndicacaoResponse;
import br.com.mercadoinstrumental.controller.commom.schema.ResponsePagedCommom;
import br.com.mercadoinstrumental.manager.admin.indicacao.IndicacaoManager;
import br.com.mercadoinstrumental.model.usuario.TipoChavePixEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Usuario::Indicacao")
@RestController
@RequestMapping("indicacoes")
public class IndicacaoController {

	@Autowired
	private IndicacaoManager indicacaoManager;


	@GetMapping("dash")
	@PreAuthorize("hasAnyRole('ANUNCIANTE', 'ADMINISTRADOR')")
	public ResponseEntity<DashboardIndicadorResponse> findDashboardIndicador(@RequestParam Boolean isGeral) {
		return ResponseEntity.ok(indicacaoManager.findDashboardIndicador(isGeral));
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ANUNCIANTE', 'ADMINISTRADOR')")
	public ResponseEntity<ResponsePagedCommom<IndicacaoResponse>> findAllUsuariosIndicadosPaged(
			@Valid IndicacaoFilter filtros) {
		return ResponseEntity.ok(indicacaoManager.findAllUsuariosIndicadosPaged(filtros));
	}
	
	
	@GetMapping("/publicacoes/{idUsuario}")
	@PreAuthorize("hasAnyRole('ANUNCIANTE', 'ADMINISTRADOR')")
	public ResponseEntity<List<ComissaoIndicadorResponse>> findAllPublicacoesByUsuario(
			@PathVariable Long idUsuario,
			@RequestParam Boolean isAdmin) {
		return ResponseEntity.ok(indicacaoManager.findAllPublicacoesByUsuario(idUsuario, isAdmin));
	}
	
	@PostMapping("/indicacao-pagamento/{idAnuncio}")
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public ResponseEntity<List<ComissaoIndicadorResponse>> indicacaoPagamento(
			@PathVariable Long idAnuncio,
			@RequestParam LocalDate dataPagamento,
			@RequestParam String numeroTransacao) {
		indicacaoManager.indicacaoPagamento(idAnuncio, dataPagamento, numeroTransacao);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/save-chave-pix/{chavePix}/{tipoChavePix}")
	@PreAuthorize("hasAnyRole('ANUNCIANTE')")
	public ResponseEntity<Void> saveChavePix(
			@PathVariable String chavePix,
			@PathVariable TipoChavePixEnum tipoChavePix) {
		indicacaoManager.saveChavePix(chavePix, tipoChavePix);
		return ResponseEntity.ok().build();
	}
	
}
