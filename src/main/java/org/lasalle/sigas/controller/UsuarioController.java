package org.lasalle.sigas.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.lasalle.sigas.controller.page.PageWrapper;
import org.lasalle.sigas.mail.Mailer;
import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.model.Usuario;
import org.lasalle.sigas.repository.GrupoRepository;
import org.lasalle.sigas.repository.UnidadeRepository;
import org.lasalle.sigas.repository.UsuarioRepository;
import org.lasalle.sigas.repository.filter.UsuarioFilter;
import org.lasalle.sigas.security.UsuarioSistema;
import org.lasalle.sigas.service.UnidadeService;
import org.lasalle.sigas.service.UsuarioService;
import org.lasalle.sigas.service.exception.CpfJaCadastradoException;
import org.lasalle.sigas.service.exception.EmailJaCadastradoException;
import org.lasalle.sigas.service.exception.SenhaObrigatoriaUsuarioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	Mailer mailer;
	 
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	GrupoRepository grupoRepository;
	
	@Autowired
	UnidadeRepository unidadeRepository;
	
	@Autowired
	UnidadeService unidadeService;
	
	@RequestMapping("/novo")
	public ModelAndView novoUsuario(Usuario usuario) {
		
		ModelAndView mv = new ModelAndView("usuario/NovoUsuario");
		mv.addObject("grupos", grupoRepository.findAll());
		
		return mv;
	}
	
	@RequestMapping("/pesquisarUsuario")
	public String pesquisarUsuario() {
		return "usuario/PesquisaUsuario";
	}
	
	@RequestMapping(value={"/novo", "{\\+d}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		
		
		if (!usuario.isNovo()) {
			Usuario usuarioBanco = usuarioRepository.findById(usuario.getId()).get();
			usuario.setUnidades(usuarioBanco.getUnidades());
		}

		String mensagem = null;
		
		if (result.hasErrors()) {
			return novoUsuario(usuario);
		}
		
		try {
			usuarioService.salvar(usuario);
			
		}catch(CpfJaCadastradoException e) {
			result.rejectValue("cpf", e.getMessage(), e.getMessage());
			return novoUsuario(usuario);
		}catch(EmailJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novoUsuario(usuario);
		}catch(SenhaObrigatoriaUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novoUsuario(usuario);
		}
		
		
		mensagem = "Usuario cadastrado com sucesso";
		attributes.addFlashAttribute("mensagem", mensagem);
		
		return new ModelAndView("redirect:/usuarios/novo");
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView pesquisa(UsuarioFilter usuarioFilter, 
						BindingResult result, @PageableDefault(size = 5) Pageable pageable, HttpServletRequest request ) {
		
		ModelAndView mv = new ModelAndView("usuario/PesquisarUsuario");
		
		mv.addObject("grupos", grupoRepository.findAll());
		

		
		PageWrapper<Usuario> pagina = 
				new PageWrapper<Usuario>(usuarioRepository.filtrar(usuarioFilter, pageable), request);
		
		mv.addObject("pagina", pagina);
		
		return mv;
				
		
	}	
	
	@RequestMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		
//		Usuario usuarioBanco = usuarioRepository.findById(id).get();
		
		
		Usuario usuario = usuarioRepository.buscarUsuarioComGrupos(id);
//		usuario.setUnidades(usuarioBanco.getUnidades());
			
		
		ModelAndView mv = novoUsuario(usuario);

		mv.addObject(usuario);
		return mv;
	}
	
	@RequestMapping("/vincular/{id}")
	public ModelAndView vincularUnidade(Usuario usuario) {
		
		ModelAndView mv = new ModelAndView("usuario/UsuarioUnidade");

		Usuario usuarioBanco = usuarioRepository.findById(usuario.getId()).get();
		
		mv.addObject("usuario", usuarioBanco);
		mv.addObject("unidadesRede", unidadeRepository.findAll());
		

		
		
		return mv;
		

		
	}
	
	@RequestMapping(value = "/solicitar-alteracao-senha")
	public ModelAndView enviarEmailAlterarSenha(String email) {
		ModelAndView mv = new ModelAndView();
		
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
		
		if (usuarioOptional.isPresent()) {
			
//			Usuario usuarioBanco = usuarioRepository.findByEmail(email).get();
			mailer.enviarSolicitacaoConfirmacaoEmail(usuarioOptional.get());
			
			return new ModelAndView("/ConfirmacaoEnvioEmail");
		}else {
			return new ModelAndView("/ErroConfirmacaoEnvioEmail");
		}
		
	}
	
	
	@RequestMapping(value = "/vincular", method = RequestMethod.POST)
	public ModelAndView incluirUnidade(Usuario usuario) {
		
		Usuario usuarioBanco = usuarioRepository.findById(usuario.getId()).get();
		Unidade unidade = unidadeRepository.findById(Long.valueOf(usuario.getIdUnidade()));
		usuarioBanco.getUnidades().add(unidade);
		usuarioBanco.setSenha(usuario.getSenha());
		
		usuarioService.salvar(usuarioBanco);
		
		return new ModelAndView("redirect:/usuarios/vincular/"+usuario.getId());
		
	}	
	
	@RequestMapping(value = "/desvincular/{id}/{unidade}")
	public ModelAndView excluirUnidade(@PathVariable("unidade") String unidade,  
										@PathVariable("id") String id, Usuario usuario) {

		

		String senhaUsuario = usuario.getSenha();
		Usuario usuarioBanco = usuarioRepository.findById(Long.valueOf(id)).get();
		Unidade unidadeBanco = unidadeRepository.findByNome(unidade);

		usuarioBanco.getUnidades().remove(unidadeBanco);
		usuarioBanco.setSenha(senhaUsuario);
		
		usuarioService.salvar(usuarioBanco);
		
		return new ModelAndView("redirect:/usuarios/vincular/"+usuarioBanco.getId());
		
	}	
	
	
	
	@RequestMapping(value = "/altera-senha")
	public ModelAndView alterarSenha(@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String currentPrincipalName = authentication.getName();
		
		ModelAndView mv = new ModelAndView("usuario/AlteraSenha");
		
		Usuario usuario = usuarioRepository.findById(usuarioSistema.getUsuario().getId()).get();
		
		System.out.println("Nome do usuario " + usuario.getNome());
		
//		Usuario usuario = usuarioRepository.buscarUsuarioPeloId(usuarioSistema.getUsuario().getId());
		mv.addObject(usuario);
		
		
		return mv;
	}
	
	
	@RequestMapping(value = "/altera-senha-externa/{hash}")
	public ModelAndView alterarSenhaExterna(@PathVariable("hash") String hash,  @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		ModelAndView mv = new ModelAndView("usuario/AlteraSenhaExterna");

		System.out.println("Passando aqui no altera senha");
		
		Usuario usuario = usuarioRepository.buscarUsuarioPeloHash(hash);
		

		
//		Usuario usuario = usuarioRepository.findById(16l).get();
		mv.addObject(usuario);
		
		
		return mv;
	}


	@RequestMapping(value = "/altera-senha-externa", method = RequestMethod.POST)
	public ModelAndView confirmarSenhaExterna(Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes) {
 		
		Usuario usuarioBanco = usuarioRepository.buscarUsuarioPeloId(usuario.getId()).get(0);
		
		usuarioBanco.setSenha(usuario.getSenha());
		
		if (!usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
				
			result.rejectValue("senha", "Confirmação de Senha Inválida", "Confirmação de Senha Inválida");
			String mensagemErro = "Confirmação de Senha Inválida";
			attributes.addFlashAttribute("usuario", usuarioBanco);
			attributes.addFlashAttribute("mensagemErro", mensagemErro);
			return new ModelAndView("redirect:/usuarios/altera-senha-externa/" + usuarioBanco.getHash());
		}
		
		if (usuario.getSenha().length() < 6) {
			result.rejectValue("senha", "Senha deve ter no mínimo 6 caracteres", "Senha deve ter no mínimo 6 caracteres");
			String mensagemErro = "Senha deve ter no mínimo 6 caracteres";
			attributes.addFlashAttribute("usuario", usuarioBanco);
			attributes.addFlashAttribute("mensagemErro", mensagemErro);
						
			return new ModelAndView("redirect:/usuarios/altera-senha-externa/" + usuarioBanco.getHash());
		}
		
		
		String mensagem = "Senha alterada com sucesso!! Clique na logomarca no La Salle para voltar a tela de login";
		
		usuarioService.salvar(usuarioBanco);
		
		attributes.addFlashAttribute("mensagem", mensagem);
		attributes.addFlashAttribute("hash", usuarioBanco.getHash());
		

		
		return new ModelAndView("redirect:/sucesso");
	}	
	
	
	@RequestMapping(value = "/altera-senha", method = RequestMethod.POST)
	public ModelAndView confirmarSenha(Usuario usuario, BindingResult result, RedirectAttributes attributes) {
 		
		Usuario usuarioBanco = usuarioRepository.buscarUsuarioPeloId(usuario.getId()).get(0);
		
		usuarioBanco.setSenha(usuario.getSenha());
		
	
		
		
		if (!usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
			result.rejectValue("senha", "Confirmação de Senha Inválida", "Confirmação de Senha Inválida");
			return new ModelAndView("usuario/AlteraSenha");
		}
		
		if (usuario.getSenha().length() < 6) {
			result.rejectValue("senha", "Senha deve ter no mínimo 6 caracteres", "Senha deve ter no mínimo 6 caracteres");
			return new ModelAndView("usuario/AlteraSenha");
		}
		
		
		String mensagem = "Senha alterada com sucesso!!";
		
		attributes.addFlashAttribute("mensagem", mensagem);
		

		
		usuarioService.salvar(usuarioBanco);
		
		

		
		return new ModelAndView("redirect:/usuarios/altera-senha");
	}
	
	
	@RequestMapping(value="alterar-email", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> email(@RequestBody String email){
		
		System.out.println("Email recebido..." + email);
		
		return ResponseEntity.ok().body("Email enviado com sucesso");
		
	}

}
