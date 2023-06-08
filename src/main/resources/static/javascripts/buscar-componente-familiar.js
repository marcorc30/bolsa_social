$(function(){
	
	var nomeInput = $('.js-nome-componente-familiar');

	
	nomeInput.change(function(){
		
		var servico = nomeInput.data('url2');
		/*var servico = "http://localhost:8080/sigas/composicao-familiar";*/
		/*var servico = "http://200.152.49.5:8085/sigas/composicao-familiar";*/
		var nome = nomeInput.val();
		
		
		
		var dados = {nome: nome};
	
		$.ajax({
			url: servico,
			method: 'GET',
			contentType: 'application/json',
			data: dados,
			error: onErroListandoComponente,
			success: onAdicionaComponente
			
		})
		
		console.log(">>>>>>>>>>>>", servico);
	
	function onErroListandoComponente(arguments){ 
		console.log("gerou erro ao salvar a unidade", arguments);
	}
	
	function onAdicionaComponente(componente){
		
			console.log("unidades: >>>>>>>>>>", componente)
			
			var dia = String(componente.dataNascimento.dayOfMonth).padStart(2, '0');
			var mes = String(componente.dataNascimento.monthValue).padStart(2, '0');
			var ano = String(componente.dataNascimento.year);
			
			console.log(dia, mes, ano);
			
			var data = dia + "/" + mes + "/" +  ano;
			console.log(data);
			$('#data-nascimento').val(data);
			$('#input-responsavel-profissao').val(componente.ocupacao);
			
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
	
