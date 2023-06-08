$(function(){
	
	var form = $("#analise-documental");
	var botaoSalvar = $(".js-salvar-enviar-email");
	
	form.on('submit', function(event){event.preventDefault()});
	var url = form.attr('action');
	console.log('url', url)
	
	botaoSalvar.on('click', onBotaoSalvar);
	
	/*data: JSON.stringify({'titulo': titulo, 'descricao': descricao, 'data': data}),
	contentType: 'application/json',*/
	
	function onBotaoSalvar(){
		var titulo = $('#titulo').val();
		var descricao = $('#descricao').val();
		var data = $('#data').val();
				
		
		/*const dataFormatada = Intl.DateFDateTimeFormat('pt-BR').format(data);*/
		
		console.log(titulo, typeof(titulo))
		console.log(data, typeof(data))
		
		$.ajax({
			url: url,
			method: 'POST',
			data: JSON.stringify({titulo: titulo, descricao: descricao}),
			contentType: 'application/json',
			error: onErroSalvando
			
		})
	}
	
	function onErroSalvando(){
		console.log(arguments)
	}
	
	
	
	
	
	
	
})