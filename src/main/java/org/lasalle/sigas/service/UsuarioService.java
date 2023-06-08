package org.lasalle.sigas.service;

import java.util.Optional;

import org.lasalle.sigas.model.Usuario;
import org.lasalle.sigas.repository.UsuarioRepository;
import org.lasalle.sigas.service.exception.CpfJaCadastradoException;
import org.lasalle.sigas.service.exception.EmailJaCadastradoException;
import org.lasalle.sigas.service.exception.SenhaObrigatoriaUsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Transactional
	public void salvar(Usuario usuario) {
		

		
		Optional<Usuario> usuarioCpf = usuarioRepository.findByCpf(usuario.getCpf());
		Optional<Usuario> usuarioEmail = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioCpf.isPresent() && !usuarioCpf.get().equals(usuario)) {
			throw new CpfJaCadastradoException("Já existe um usuário com esse CPF cadastrado.");
		}
		
		if (usuarioEmail.isPresent() && !usuarioEmail.get().equals(usuario)) {
			throw new EmailJaCadastradoException("Já existe um usuário com esse Email cadastrado.");
		}
		
		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		}
				
		
		if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		} else if (StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(usuarioEmail.get().getSenha());
		}
		usuario.setConfirmacaoSenha(usuario.getSenha());
		
		if (!usuario.isNovo() && usuario.getAtivo() == null) {
			usuario.setAtivo(usuarioEmail.get().getAtivo());
		}
		

		
		usuarioRepository.save(usuario);
	}

//	@Transactional
//	public void salvar(Usuario usuario) {
//		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
//		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
//			throw new EmailJaCadastradoException("E-mail já cadastrado");
//		}
//		
//		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
//			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
//		}
//		
//		
//		if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
//			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
//		} else if (StringUtils.isEmpty(usuario.getSenha())) {
//			usuario.setSenha(usuarioExistente.get().getSenha());
//		}
//		usuario.setConfirmacaoSenha(usuarioExistente.get().getSenha());
//		
//		if (!usuario.isNovo() && usuario.getAtivo() == null) {
//			usuario.setAtivo(usuarioExistente.get().getAtivo());
//		}
//		
//		usuarioRepository.save(usuario);
//	}
	
}
