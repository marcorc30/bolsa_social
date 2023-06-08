$(function(){
	
	var form = $("#analise-tecnica-parecer");
	var botaoSalvar = $("#js-botao-salvar-analise-tecnica-parecer");
	
	form.on('submit', function(event){event.preventDefault()});
	var url = form.attr('action');
	console.log('url', url)
	
	botaoSalvar.on('click', onBotaoSalvar);
	
	/*data: JSON.stringify({'titulo': titulo, 'descricao': descricao, 'data': data}),
	contentType: 'application/json',*/
	
	var mensagemErro = form.find(".js-mensagem-erro-parecer");
	var mensagemSucesso = form.find(".js-mensagem-sucesso-parecer");
	
	
	function onBotaoSalvar(){
		var observacao = form.find('#observacao').val();
		var dadosIniciais = $("#dadosIniciais").val();
		var id = $("#id").val();
		
		console.log('url', url);
		console.log('percentual', percentual);
		console.log('dadosIniciais', dadosIniciais);
		console.log('id', id);
		console.log('obervacao', observacao);
				
		
		var objeto = titulo;
				 
		console.log('url', url);		
		swal({
			title: 'Tem certeza?',
			/*text: 'Gravar "' + objeto + '"? Será enviado por email agora. Você não poderá cancelar operação depois.',*/
			text: `Gravar essa Análise Técnica Parecer? Você não poderá cancelar a operação`,
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, grave agora!'
		}, onSalvarConfirmado.bind(this, url, observacao, dadosIniciais, id));

		
	}
	

	function onSalvarConfirmado(url, observacao, dadosIniciais, id){
		console.log('--url', url);
		console.log('--percentual', percentual);
		console.log('--dadosIniciais', dadosIniciais);
		console.log('--id', id);
		
		$.ajax({
			url: url,
			method: 'POST',
			data: JSON.stringify({observacao: observacao, dadosIniciais: dadosIniciais, id:id}),
			contentType: 'application/json',
			error: onErroSalvando.bind(this)
			
		})
		swal('Pronto', 'Excluído com sucesso!', 'success');
		mensagemErro.addClass('hidden');

		var mensagemSucessoEnvio = 'Análise Técnica Parecer salva com sucesso.';	
		mensagemSucesso.removeClass('hidden');
		mensagemSucesso.html('<span>' + mensagemSucessoEnvio + '</span>');
		
		
	}
	
	function onErroSalvando(e){
		console.log(e);
		var mensagem = 'Erro na gravação da observação. Primeiro preencher a etapa anterior - Análise Técnica Social - Financeira. ';
		
		mensagemSucesso.addClass('hidden');
		mensagemErro.removeClass('hidden');
		mensagemErro.html('<span>' + mensagem + '</span>');
		/*swal("Desculpe!", e.responseText, 'error');*/
	}
	
	
	
	
	
	
	
})