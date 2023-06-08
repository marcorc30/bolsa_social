$(function(){
	
	var id_unidade = $('#input-unidade');
	var processo_seletivo = $('#input-processo-seletivo');
	
/*	console.log("Id da unidade ", id_unidade.val());
	console.log("Processo: ", processo_seletivo);
*/	
	id_unidade.change(function(){
		
		processo_seletivo.each(function(){
			$(this).find("option").remove();
		});

	var servico = id_unidade.data('url');
	/*var servico = "http://localhost:8080/sigas/processos";*/
	/*var servico = "http://200.152.49.5:8085/sigas/processos";*/
	
	var unidade = id_unidade.val();
	
	console.log("Id da unidade " + unidade)
	
	var dados = {unidadeId: unidade};
	
	$.ajax({
		url: servico,
		method: 'GET',
		contentType: "application/json",
		data: dados,
		error: onErrorListandoProcessos,
		success: onAdicionandoProcessos
	})
	
	
	function onErrorListandoProcessos(arguments){
		console.log("Erro ao listar os processos", arguments)
	}
	
	function onAdicionandoProcessos(processos){
		console.log("tamanho dos processos", processos.length)
		console.log("Processos: " + processos)

		var textpInformativo = ``


		if (processos.length == 0){
			
				swal({
					title: 'Atenção!',
					text: `O período de preenchimento do candidato não corresponde ao informado no edital de bolsa assistencial da Unidade Educativa selecionada. Por favor, consulte o edital disponível na página da Unidade Educativa. `,
					confirmButtonColor: '#DD6B55',
	 				confirmButtonText: 'Ok!'
				});
				return;
		}
		
		
		processo_seletivo.append('<option value="0" selected disabled>Selecionar...</option>');
		
		$.each(processos, function(i, valor){
				processo_seletivo.append($('<option>', {
				value: valor.id,
				text: valor.descricao

			}));

			console.log("id", valor.id)
			console.log("valor", valor.descricao)
		
			
		});
		
	}
	
});	
	
	
	
	
	
});