$(function(){
	
	var percentual = $('#percentual-analise');
	var parcelaInicial = $('#parcela-inicial');
	var parcelaFinal = $('#parcela-final');
	
	percentual.on('change', function(event){
		console.log(event.target.value);
		if (event.target.value == 3){
			parcelaInicial.val('');
			parcelaFinal.val('');
			parcelaInicial.prop('disabled', true);
			parcelaFinal.prop('disabled', true);
			
		}else{
			parcelaInicial.attr('disabled', false);
			parcelaFinal.attr('disabled', false);
			
		}
	})
	
})