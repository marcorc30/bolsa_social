$(function(){
	
	var uf_unidade = $('#input-estado-unidade');
	var unidade = $('#input-unidade');
	
	uf_unidade.change(function(){
		
		unidade.each(function(){
			$(this).find('option').remove();
		});
		
		var servico = uf_unidade.data('url');
		/*var servico = "http://localhost:8080/sigas/unidades";*/
		/*var servico = "http://200.152.49.5:8085/sigas/unidades";*/
		var uf = uf_unidade.val();
		
		
		
		var dados = {uf: uf};
	
		$.ajax({
			url: servico,
			method: 'GET',
			contentType: 'application/json',
			data: dados,
			error: onErroListandoUnidades,
			success: onAdicionaUnidades
			
		})
		
		console.log(">>>>>>>>>>>>", servico);
	
	function onErroListandoUnidades(arguments){ 
		console.log("gerou erro ao salvar a unidade", arguments);
	}
	
	function onAdicionaUnidades(unidades){
		
			console.log("unidades: >>>>>>>>>>", unidades)
			
			unidade.append('<option value="0" selected disabled>Selecionar...</option>');
			$.each(unidades, function(i, valor){
				unidade.append($("<option>", {
					value : valor.id,
					text: valor.nome
				}))
			})
			
		}	
	
	}) 
})
	
