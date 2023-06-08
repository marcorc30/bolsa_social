$(function(){
	
	var cpfInput = $('.js-input-cpf');

	 
	cpfInput.blur(function(){
		console.log('saiu do campo');
		
		var servico = "http://localhost:8080/sigas/responsaveis/pesquisar";
		/*var servico = "http://200.152.49.5:8085/sigas/responsaveis/pesquisar";*/
		var cpf = cpfInput.val();
		
		
		
		var dados = {cpf: cpf};
	
		$.ajax({
			url: servico,
			method: 'GET',
			contentType: 'application/json',
			data: dados,
			error: onErroListandoResponsavel,
			success: onAdicionaResponsavel
			
		})
		
		console.log(">>>>>>>>>>>>", servico);
	
	function onErroListandoResponsavel(arguments){ 
		console.log("gerou erro ao salvar a unidade", arguments);
	}
	
	function onAdicionaResponsavel(componente){
		
			console.log("unidades: >>>>>>>>>>", componente)
			
			var dia = String(componente.dataNascimento.dayOfMonth).padStart(2, '0');
			var mes = String(componente.dataNascimento.monthValue).padStart(2, '0');
			var ano = String(componente.dataNascimento.year);
			
			console.log(dia, mes, ano);
			
			var data = dia + "/" + mes + "/" +  ano;
			console.log(data);
			$('#data-nascimento').val(data);
			$('#input-responsavel-nome').val(componente.nome);
			
		}
		
function formatarData(data) {
	var d = new Date(data), mes = '' + (d.getMonth() + 1), dia = ''
			+ d.getDate(), ano = d.getFullYear();

	if (mes.length < 2)
		mes = '0' + mes;
	if (dia.length < 2)
		dia = '0' + dia;

	return [ dia, mes, ano ].join('/');
}
			
	
	}) 
})
	
