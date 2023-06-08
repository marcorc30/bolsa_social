$(function(){

   var serie = $('#input-serie');
   var processo_seletivo = $('#input-processo-seletivo');

	
	processo_seletivo.change(function(){
		
		serie.each(function(){
			$(this).find("option").remove();
		})	
		
		var servico = processo_seletivo.data('url');
		/*var servico = "http://localhost:8080/sigas/detalhesProcessos";*/
		/*var servico = "http://200.152.49.5:8085/sigas/detalhesProcessos";*/
		
		var processoId = processo_seletivo.val();
		
		console.log("valor do id do processo ", processoId);
		
		
		var dados = { processoId: processoId }
		
		$.ajax({
			url: servico,
			method: 'GET',
			contentType: 'application/json',
			data: dados,
			error: onErrorSeries,
			success: onListaSeries
		})
		
		
		function onErrorSeries(arguments){
			alert("entrei no erro")
			console.log("Erro ao processar informações das series", arguments);
			
		}
		
		
		function onListaSeries(series){
			
			console.log("******* series *********", series)
			
			serie.append('<option value="0" selected disabled>Selecionar...</option>');				
			$.each(series, function(i, valor){
				serie.append($("<option>", {
					value: valor.id,
					text: valor.serie.nivel.descricao + ' - '+  valor.serie.descricao + ' - ' + valor.turno.descricao 
				}))
				
				console.log("Id do detalhe dp processo seletivo: ", valor.id);
				console.log("Descricao da serie descricao: ", valor.serie.descricao);
			})
			
		}
	})
});