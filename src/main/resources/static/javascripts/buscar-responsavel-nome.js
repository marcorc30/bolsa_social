$(function(){
	
	var nomeInput = $('.js-nome-componente-familiar');

	
	nomeInput.change(function(){
		
		
		/*var servico = "http://localhost:8080/sigas/responsaveis/pesquisar-nome";*/
		var servico = nomeInput.data('url');
		/*var servico = "http://200.152.49.5:8085/sigas/responsaveis/pesquisar-nome";*/
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
		
			console.log("responsavel financeiro: >>>>>>>>>>", componente)
			
			var diaN = String(componente.dataNascimento.dayOfMonth).padStart(2, '0');
			var mesN = String(componente.dataNascimento.monthValue).padStart(2, '0');
			var anoN = String(componente.dataNascimento.year);
			var dataNascimento = diaN + "/" + mesN + "/" +  anoN;


			var diaE = String(componente.rgDataEmissao.dayOfMonth).padStart(2, '0');
			var mesE = String(componente.rgDataEmissao.monthValue).padStart(2, '0');
			var anoE = String(componente.rgDataEmissao.year);
			var dataEmissao = diaE + "/" + mesE + "/" +  anoE;



			$('#data-nascimento').val(dataNascimento);
			$('#input-responsavel_celular').val(componente.celular);
			$('#input-rg').val(componente.rg);
			$('#orgao-emissor').val(componente.rgOrgao);
			$('#data-emissao').val(dataEmissao);
			$('#input-responsavel-cpf').val(componente.cpf);
			
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
	
