$(function(){
	
	var uf_naturalidade = $("#uf-naturalidade");
	var naturalidade = $("#naturalidade");
	
	uf_naturalidade.on("change", function(event){
		
		naturalidade.each(function(){
			$(this).find('option').remove();
		});
		
		
		/*var url = 'http://localhost:8080/sigas/municipios';*/
		var url = uf_naturalidade.data('url');
		var uf = event.target.value;
		
		
		
		var dados = {uf: uf}
		
		$.ajax({
			url: url + "/" + uf,
			method: 'GET',
			contentType: 'application/json',
			data: dados,
			error: onErroListandoMunicipios,
			success: onListaMunicipios
			
		})
		
		function onErroListandoMunicipios(arguments){
			console.log("erro ao buscar os municipios", arguments);
		}
		
		function onListaMunicipios(municipios){
			
			
			naturalidade.append('<option value="0" selected disabled>Selecionar...</option>');
			$.each(municipios, function(i, valor){
				naturalidade.append($("<option>", {
					value : valor.municipio,
					text: valor.municipio
				}))
			})
		}
		
	})
	
	
	
	
})