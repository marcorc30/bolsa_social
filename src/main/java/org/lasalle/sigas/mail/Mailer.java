package org.lasalle.sigas.mail;

import org.lasalle.sigas.model.AnaliseDocumental;
import org.lasalle.sigas.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class Mailer {
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	WebApplicationContext context;
	
	@Async
	public void enviar(AnaliseDocumental analiseDocumental, String email) {
//		dadosIniciais.getUsuario().getEmail();
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("sistema.lsa@lasalle.org.br");
		mensagem.setTo(email);
		mensagem.setSubject(analiseDocumental.getTitulo());
		mensagem.setText(analiseDocumental.getDescricao());
		
		
		mailSender.send(mensagem);
		
	}
	
	@Async
	public void enviarConfirmacao(Usuario usuario) {
		

		String urlAplicacao =  ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
		
		
		String texto = "Prezado(a) Sr.(a) " + usuario.getNome() +  "\n" + 
				"\n" + 
				"Esta é a confirmação do seu cadastro na Plataforma de Bolsa Assistencial da Rede La Salle. Seguem os dados para seu acesso através do link: " + urlAplicacao  + "/login\n" + 
				"Usuário = " + usuario.getEmail() +  "\n" + 
				"\n" + 
				"Lembramos que não guardamos a sua senha. Em caso de esquecimento, na tela de acesso a Plataforma, clique em Esqueci a senha?\n" + 
				"\n" + 
				"Seja bem-vindo(a)!\n" + 
				"Atenciosamente,\n" + 
				"Equipe de Bolsa Assistencial da Rede La Salle\n" + 
				"";
		
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("naoresponda.lsa@lasalle.org.br");
		mensagem.setTo(usuario.getEmail());
		mensagem.setSubject("Confirmação de cadastro na plataforma");
		mensagem.setText(texto);
		
		mailSender.send(mensagem);
		
	}

//	@Async
	public void enviarSolicitacaoConfirmacaoEmail(Usuario usuario) {
		
		String urlAplicacao =  ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
		
		String texto = "*** NÃO RESPONDA: Esta é uma mensagem automática *** \n\n"
				+ "Prezado(a) Sr.(a) " + usuario.getNome() +  "\n" + 
				"\n" + 
				"Nós recebemos uma solicitação para redefinição de senha do seu usuário na Plataforma de Bolsa Assistencial da Rede La Salle.\n" + 
				"\n" + 
				"Acesse o link abaixo para definir uma nova senha de no mínimo 6 (seis)  dígitos:\n" +
				urlAplicacao + "/usuarios/altera-senha-externa/"+usuario.getHash() +"\n\n" +
				"Se desconhece esta solicitação, então não se preocupe, apenas ignore esta mensagem.\n" + 
				"\n" + 
				"Obrigado,\n" + 
				"\n" + 
				"Equipe de Bolsa da Rede La Salle";
		
		SimpleMailMessage mensagem1 = new SimpleMailMessage();
		
		mensagem1.setFrom("naoresponda.lsa@lasalle.org.br");
		mensagem1.setTo(usuario.getEmail());
		mensagem1.setSubject("Solicitacao de alteração de senha");
		mensagem1.setText(texto);
		
		
		mailSender.send(mensagem1);

		
	}	

}
