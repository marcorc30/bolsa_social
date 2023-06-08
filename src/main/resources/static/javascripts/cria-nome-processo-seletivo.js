$(function(){
	

	
	$("#ano-vigencia").on("blur", function(event){
		var unidade = $('#input-unidade :selected').text();
		var tipoProcesso = $("#tipo-processo :selected").text();
		var tipoEdital = $("#tipo-edital :selected").text();
		var numeroEdital = $("#numero-edital :selected").val();
		var anoVigencia = $("#ano-vigencia").val();
		var nomeProcesso = $("#nome-processo");
		
		if (numeroEdital == undefined){
			numeroEdital = 0;
		}
		
		nomeProcesso.val(unidade + " - " + tipoProcesso +  " - "  +  tipoEdital +" - "+ numeroEdital +" - "+ anoVigencia);
	})
	
	
	
})