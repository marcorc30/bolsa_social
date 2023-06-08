$(function(){
	
	var form = $("#analise-tecnica-financeira");
	var botaoSalvar = $("#js-botao-salvar-analise-tecnica-financeira");
						  
	
	form.on('submit', function(event){event.preventDefault()});
	var url = form.attr('action');
	console.log('url', url)
	
	botaoSalvar.on('click', onBotaoSalvar);
	
	/*data: JSON.stringify({'titulo': titulo, 'descricao': descricao, 'data': data}),
	contentType: 'application/json',*/
	
	var mensagemErro = form.find(".js-mensagem-erro");
	var mensagemSucesso = form.find(".js-mensagem-sucesso");
	
	
	function onBotaoSalvar(){
		var rendaBruta = $('#rendaBruta').val();
		var numeroFamiliares = $('#numeroFamiliares').val();
		var rendaPercapita = $('#rendaPercapita').val();
		var observacao = form.find('#observacao').val();
		var dadosIniciais = $("#dadosIniciais").val();
		var percentual = $("#percentual").val();
		var id = $("#id").val();
		
		console.log('url', url);
		console.log('percentual', percentual);
		console.log('dadosIniciais', dadosIniciais);
		console.log('id', id);
		console.log('obervacao', observacao);
		console.log('renda bruta familiar',  rendaBruta.replace(",", "."));
		console.log('renda percapita', rendaPercapita.replace(",", "."));
		console.log('numero de familiares', numeroFamiliares);
		
		
				
		
		var objeto = titulo;
				 
		console.log('url dentro.......', url);		
		swal({
			title: 'Tem certeza?',
			/*text: 'Gravar "' + objeto + '"? Será enviado por email agora. Você não poderá cancelar operação depois.',*/
			text: `Gravar essa Análise Técnica Financeira? Você não poderá cancelar a operação`,
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, grave agora!'
		}, onSalvarConfirmado.bind(this, url, percentual, dadosIniciais, id, observacao, rendaBruta, rendaPercapita, numeroFamiliares));

		
	}  
	

	function onSalvarConfirmado(url, percentual, dadosIniciais, id, observacao, rendaBruta, rendaPercapita, numeroFamiliares){
		console.log('url', url);
		console.log('percentual', percentual);
		console.log('percentual 222', percentual); 
		console.log('dadosIniciais', dadosIniciais);
		console.log('id', id);
		console.log('obervacao', observacao);
		console.log('renda bruta familiar', rendaBruta.replace('.', '').replace(',', '.'));
		console.log('numero de familiares', numeroFamiliares);
		var rendaBrutaMoeda = parseFloat(rendaBruta.replace('.', '').replace(',', '.'));
		console.log('renda percapita', (rendaBrutaMoeda / numeroFamiliares));
		
		var rendaPercapitaCalculada = (rendaBrutaMoeda / numeroFamiliares).toFixed(2);
		var rendaPercapitaGravada = $('#rendaPercapita').val(rendaPercapitaCalculada.toLocaleString('pt-br', {minimumFractionDigits: 2}));
		
		console.log('renda percapita formatada', rendaPercapitaCalculada)
		console.log('renda percapita formatada2', rendaPercapitaCalculada.replace('.',','))
		
		$.ajax({
			url: url,
			method: 'POST',
			data: JSON.stringify({percentualId: percentual, 
									dadosIniciais: dadosIniciais, 
									id:id,
									observacao: observacao,
									rendaBruta: rendaBruta.replace('.','').replace(',','.'),
									rendaPercapita: rendaPercapitaCalculada, /*.replace(',','.'),*/
									numeroFamiliares: numeroFamiliares
									}),
			contentType: 'application/json',
			error: onErroSalvando.bind(this)
			
		}) 
/*		console.log('valor da observacao', data.observacao);
		console.log('valor do percentual', data.percentualId);*/
		
		swal('Pronto', 'Gravado com sucesso!', 'success');
		mensagemErro.addClass('hidden');

		var mensagemSucessoEnvio = 'Análise Técnica Financeira salva com sucesso.';	
		mensagemSucesso.removeClass('hidden');
		mensagemSucesso.html('<span>' + mensagemSucessoEnvio + '</span>');
		
		
	}
	
	function onErroSalvando(e){
		console.log(e);
		var mensagem = 'Por favor, informe todos os campos do formulário';
		
		mensagemSucesso.addClass('hidden');
		mensagemErro.removeClass('hidden');
		mensagemErro.html('<span>' + mensagem + '</span>');
		/*swal("Desculpe!", e.responseText, 'error');*/
	}
	
	
	
	
	
	
	
})