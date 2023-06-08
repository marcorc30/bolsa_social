$(function(){
	
	var form = $("#analise-documental");
	var botaoSalvar = $(".js-salvar-enviar-email");
	
	form.on('submit', function(event){event.preventDefault()});
	var url = form.attr('action');
	console.log('url', url)
	
	botaoSalvar.on('click', onBotaoSalvar);
	
	/*data: JSON.stringify({'titulo': titulo, 'descricao': descricao, 'data': data}),
	contentType: 'application/json',*/
	
	var mensagemErro = form.find(".js-mensagem-erro");
	var mensagemSucesso = form.find(".js-mensagem-sucesso");
	
	
	function onBotaoSalvar(){
		var titulo = $('#titulo').val();
		var descricao = $('#descricao').val();
		var data = $('#data-limite').val();
		var dadosIniciais = $("#dadosIniciais").val();
		var nome = $('#nome').val();
		var email = $('#email').val();
		
		console.log('data', data);
		
		
		
		var objeto = titulo;
				 
		console.log('url', url);		
		swal({
			title: 'Tem certeza?',
			/*text: 'Gravar "' + objeto + '"? Será enviado por email agora. Você não poderá cancelar operação depois.',*/
			text: `Gravar ${objeto}? Será enviado um email para ${nome}, no endereço: ${email} nesse momento. Você não poderá cancelar a operação`,
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, envie agora!'
		}, onSalvarConfirmado.bind(this, url, titulo, descricao, data,  dadosIniciais));

		
	}
	

	function onSalvarConfirmado(url, titulo, descricao, data, dadosIniciais){
		console.log('titulo', titulo);
		$.ajax({
			url: url,
			method: 'POST',
			data: JSON.stringify({titulo: titulo, descricao: descricao, data: data, dadosIniciais: dadosIniciais}),
			contentType: 'application/json',
			error: onErroSalvando.bind(this)
			
		})
		swal('Pronto', 'Gravado com sucesso!', 'success');
		mensagemErro.addClass('hidden');

		var mensagemSucessoEnvio = 'Email enviado com sucesso';	
		mensagemSucesso.removeClass('hidden');
		mensagemSucesso.html('<span>' + mensagemSucessoEnvio + '</span>');
		
		
	}
	
	function onErroSalvando(e){
		var mensagem = 'Por favor, informe todos os campos do formulário';
		
		mensagemErro.removeClass('hidden');
		mensagemSucesso.addClass('hidden');
		mensagemErro.html('<span>' + mensagem + '</span>');
		/*swal("Desculpe!", e.responseText, 'error');*/
	}
	
	
	
	
	
	
	
})