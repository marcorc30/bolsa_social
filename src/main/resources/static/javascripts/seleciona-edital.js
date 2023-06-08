$(function(){
	
	var tipo_edital = $('#tipo-edital');
	var numero_edital = $('#numero-edital');
	var unidade = $('#input-unidade');

	
	
	tipo_edital.on('change', function(arguments){ 
		
		numero_edital.each(function(){
			$(this).find('option').remove();
		});
		
		var valor = tipo_edital.val();
		var idUnidade = unidade.val();

		
		console.log('valor', valor);
		console.log('id da unidade', idUnidade);
		console.log('tipo edital', valor);
		
		if (valor == 1){
			numero_edital.prop("disabled", true);
			numero_edital.val("");
		}else{
			numero_edital.prop("disabled", false);
			var url = tipo_edital.data('url')
			console.log("url: ", url);
			
			var dados = {idUnidade: idUnidade}
			$.ajax({
				url: url,
				method: 'get',
				data: dados,
				contentType: 'application/json',
				success: onListarProcessos,
				error: onErroListarProcessos
			})
			
			function onListarProcessos(processos){
				console.log('Quantidade de processos.....', processos.length);
				
				numero_edital.append('<option value="0" selected disabled>Selecionar...</option>');
				$.each(processos, function(i, valor){
				numero_edital.append($("<option>", {
					value : valor.id,
					text: valor.id + ' - ' + valor.descricao
				}))
			})
				
 				
			}
			
			function onErroListarProcessos(){
				console.log('Erro listando processos.....')
			}
			
		}

		
	})
	
	
})