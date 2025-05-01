package br.com.shopdosmusicos.controller.admin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.shopdosmusicos.controller.admin.auth.schema.AuthResponse;
import br.com.shopdosmusicos.controller.admin.auth.schema.LoginReq;
import br.com.shopdosmusicos.controller.admin.auth.schema.ProfileResponse;
import br.com.shopdosmusicos.manager.exception.AuthorizationException;
import br.com.shopdosmusicos.model.usuario.TipoPerfilEnum;
import br.com.shopdosmusicos.security.AuthService;
import br.com.shopdosmusicos.security.EmailDTO;
import br.com.shopdosmusicos.security.JWTUtil;
import br.com.shopdosmusicos.security.UserSS;
import br.com.shopdosmusicos.security.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authManager;
	 
	@Autowired
	private AuthService service;
	
	@PostMapping
	public ResponseEntity<AuthResponse> autenticar(@RequestBody @Valid LoginReq form) {
		UsernamePasswordAuthenticationToken dadosLogin = new UsernamePasswordAuthenticationToken(form.email(), form.senha());
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			UserSS usuario = ((UserSS) authentication.getPrincipal());
			String base64 = "Bearer " + jwtUtil.generateToken(usuario.getUsername());
			return ResponseEntity.ok(new AuthResponse(usuario.getNome(), base64, authentication.getAuthorities()));
		} catch (AuthorizationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
		
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(path = { "/profile" })
	public ResponseEntity<ProfileResponse> getInfoProfile() {
		try {
			UserSS retorno = UserService.authenticated();
			return ResponseEntity.ok(new ProfileResponse(retorno.getNome(), retorno.getUsername(), retorno.getAuthorities()));
		} catch (AuthorizationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
		
	@GetMapping(path = { "/hasRole" })
	public ResponseEntity<Boolean> hasRole(
			@Valid @RequestParam(required = true) TipoPerfilEnum role) {
		try {
			UserSS retorno = UserService.authenticated();
			return ResponseEntity.ok(retorno.hasRole(role));
		} catch (AuthorizationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
