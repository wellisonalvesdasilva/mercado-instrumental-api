package br.com.mercadoinstrumental.manager.admin.anuncio;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mercadoinstrumental.controller.admin.usuario.schema.UsuarioAlteracaoSenhaResponse;
import br.com.mercadoinstrumental.controller.admin.usuario.schema.UsuarioReq;
import br.com.mercadoinstrumental.controller.commom.manager.EnvioEmailManager;
import br.com.mercadoinstrumental.exceptions.BusinessException;
import br.com.mercadoinstrumental.manager.SegurancaManager;
import br.com.mercadoinstrumental.model.usuario.Usuario;
import br.com.mercadoinstrumental.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;

@Service
public class UsuarioManager {

    @Autowired
    private EnvioEmailManager envioEmailManager;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SegurancaManager segurancaManager;
    
    @Value("${aplicacao-web}")
    public String urlBase;

    private static final String DATA_FORMAT = "dd/MM/yyyy HH:mm:ss";

    @Transactional
    public Usuario createUsuario(@Valid UsuarioReq req) {
        validateEmail(req.email());
        Usuario usuario = new Usuario(req.nome(), req.email(), req.senha(), req.whats());
        if (req.hashAfiliado() != null) {
        	Usuario afiliado = usuarioRepository.findByHashPropria(req.hashAfiliado()).orElseThrow();
        	usuario.setHashDeQuemIndicou(afiliado.getHashPropria());
        }
        usuario.setHashPropria(gerarCodigoAfiliado(usuario));

        usuario = usuarioRepository.save(usuario);
        sendActivationEmail(usuario);
        return usuario;
    }

    @Transactional
    public void activeUser(String palavraChave) {
        Usuario usuario = findByPalavraChaveTemp(palavraChave);
        if (usuario != null) {
            usuario.setAtivo(true);
            usuario.setPalavraChaveTemp(null);
            usuarioRepository.save(usuario);
        } else {
            throw new BusinessException("A palavra-chave informada é inválida.");
        }
    }

    @Transactional
    public void requestPasswordRecovery(String email) {
        Usuario usuario = findUsuarioByEmail(email);
        usuario.gerarNovaSenhaTemporaria();
        usuarioRepository.save(usuario);
        sendPasswordRecoveryEmail(usuario);
    }

    @Transactional
    public void confirmPasswordRecovery(String newPassword, String palavraPasse) {
        Usuario usuario = findByPalavraChaveTemp(palavraPasse);
        if (usuario != null) {
            usuario.setSenha(newPassword);
            usuario.setPalavraChaveTemp(null);
            usuarioRepository.save(usuario);
        } else throw new BusinessException("A palavra-chave informada é inválida.");
    }

    private void validateEmail(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new BusinessException("Já existe um usuário cadastrado com o e-mail informado. Use a opção 'Esqueci minha senha' para redefinir a senha.");
        }
    }

    private Usuario findUsuarioByEmail(String email) {
        return usuarioRepository.findByEmailAndAtivo(email, true)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado para o e-mail informado!"));
    }
    
    private Usuario findByPalavraChaveTemp(String palavraPasse) {
        return usuarioRepository.findByPalavraChaveTemp(palavraPasse)
                .orElseThrow(() -> new BusinessException("Este link para redefinir a senha é inválido ou já foi utilizado!"));
    }

    private void sendActivationEmail(Usuario usuario) {
        String corpo = String.format("""
                <html>
                <body>
                    <p>Olá, %s,</p>
                    <p>Obrigado por se registrar no <strong>%s</strong>! Estamos felizes em tê-lo conosco.</p>
                    <p>Para concluir o seu cadastro, ative sua conta clicando no link abaixo:</p>
                    <p><a href="%s/account/login/%s" target="_blank">%s/account/login/%s</a></p>
                    <p><small>Data da solicitação: %s</small></p>
                </body>
                </html>
                """, 
                usuario.getNome().toUpperCase(),
                envioEmailManager.nomeEmpresa,
                urlBase,
                usuario.getPalavraChaveTemp(),
                urlBase,
                usuario.getPalavraChaveTemp(),
                new SimpleDateFormat(DATA_FORMAT).format(new Date())
        );
        envioEmailManager.enviarEmailHtml(List.of(usuario.getEmail()), "Ativação de Cadastro", corpo);
    }

    private void sendPasswordRecoveryEmail(Usuario usuario) {
        String corpo = String.format("""
                <html>
                <body>
                    <p>Olá, %s,</p>
                    <p>Para redefinir a senha do e-mail <strong>%s</strong>, acesse o link abaixo:</p>
                    <p><strong>Palavra-passe:</strong> %s</p>
                    <p><strong>Link:</strong> <a href="%s/account/recuperacao-senha/%s" target="_blank">%s/account/recuperacao-senha/%s</a></p>
                    <p>Caso não tenha solicitado, ignore este e-mail.</p>
                    <p><small>%s</small></p>
                </body>
                </html>
                """, 
                usuario.getNome().toUpperCase(),
                usuario.getEmail(),
                usuario.getPalavraChaveTemp(),
                urlBase,
                usuario.getPalavraChaveTemp(),
                urlBase,
                usuario.getPalavraChaveTemp(),
                new SimpleDateFormat(DATA_FORMAT).format(new Date())
        );
        envioEmailManager.enviarEmailHtml(List.of(usuario.getEmail()), "Recuperação de Senha", corpo);
    }

    
    @Transactional
	public void confirmPasswordChange(Integer idUsuario, String senha) {
    	
    	Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(BusinessException.from("Usuario.1000", "Usuario não encontrado para o id informado."));
    	
    	usuario.setSenha(senha);
    	usuarioRepository.save(usuario);
	}

    
    public UsuarioAlteracaoSenhaResponse buscarDadosUsuarioLogado() {
        Usuario usuarioLogado = segurancaManager.obterUsuarioLogado();
        return new UsuarioAlteracaoSenhaResponse(
            usuarioLogado.getId(),
            usuarioLogado.getNome(),
            usuarioLogado.getEmail(),
            usuarioLogado.getWhats()
        );
    }

    
   
    @Transactional
    public void alterarSenha(String senhaAtual, String novaSenha) {
       
    	Usuario usuarioLogado = segurancaManager.obterUsuarioLogado();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(senhaAtual, usuarioLogado.getSenha())) {
            throw new BusinessException("A senha atual informada não confere!");
        }

        usuarioLogado.setSenha(novaSenha);
        usuarioRepository.save(usuarioLogado);
    }
    

    @Transactional
    public String gerarCodigoAfiliado(Usuario usuarioLogado) {
		String prefixo = "MI-";
        int tamanhoCodigo = 12;
        String codigoAleatorio = gerarStringAleatoria(tamanhoCodigo);

        return prefixo + codigoAleatorio;

	}

	private String gerarStringAleatoria(int length) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }

        return sb.toString();
    }

    
}
