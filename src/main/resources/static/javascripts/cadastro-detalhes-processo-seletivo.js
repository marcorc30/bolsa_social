$(function(){
	$('#input-curso').on("change", function(){
		$(".close").trigger('click');
	})
})

/*$(function(){
	
	var painel = $('.js-panel-series');
	var botao_salvar = $('.js-btn-salvar');
	var nome_processo = $("#js-nome-processo");
	var nome_unidade = $("#js-nome-unidade");


	var bolsa_50 = $("#input-bolsa50")
	var bolsa_100 = $("#input-bolsa100")
	var combo_processo_seletivo = $('#input-processo-seletivo');
	
	//input dos dados da pesquisa
	var input_processo_seletivo = $('#processo');
	var input_nome_unidade = $("#input-unidade-display");	
	var input_nome_processo = $('#input-processo-display');

	/*var nome_processo = $('#js-nome-processo')
	var unidade = $('#js-nome-unidade')*/
	
	
	
	
	
	/*combo_processo_seletivo.change(function(e){
		//painel.css("display","block" );
		input_processo_seletivo.val(combo_processo_seletivo.val());
		input_nome_unidade.val($('#input-unidade').val()) 
		input_nome_processo.val($('#input-processo-seletivo').val())
		
		
		nome_processo.text($('#input-processo-seletivo :selected').text());
		nome_unidade.text($('#input-unidade :selected').text());

		
	})*/


/*$("form").on("submit", function(){
/*	localStorage.setItem("numero_processo", JSON.stringify($('#processo').val()));
	localStorage.setItem("nome_processo", JSON.stringify($('#input-processo-seletivo :selected').text()));
	localStorage.setItem("unidade", JSON.stringify($("#input-unidade-display :selected").text()));
*/	//localStorage.setItem("informacao_processo", JSON.stringify($('#informacao_processo').text()))

	/*
	localStorage.setItem("combo_estado", JSON.stringify($("#input-estado-unidade").val())) 
	localStorage.setItem("combo_unidade",JSON.stringify($("#input-unidade").val()))
	localStorage.setItem("combo_processo",JSON.stringify($('#input-processo-seletivo').val()))
});*/

/*$(window).on("load", function(arguments){

    /* $('#processo').val(JSON.parse(localStorage.getItem("numero_processo")));
     $('#js-nome-processo').text(JSON.parse(localStorage.getItem("nome_processo")));
     $('#js-nome-unidade').text(JSON.parse(localStorage.getItem("unidade")));

      
});
*/
/*$("#etapa2").on("click", function(arguments){
/*
	localStorage.removeItem("numero_processo");
	localStorage.removeItem("nome_processo");
	localStorage.removeItem("unidade");
      
});
*/

/*var inputs = $('input');
inputs.on('keyup', validate);

$('#input-curso').on("change", function(){
	$(".close").trigger('click');
	validate();
})
$('#input-processo-seletivo').on("change", function(){
	$(".close").trigger('click');
/*	localStorage.setItem("numero_processo", JSON.stringify($('#processo').val()));
	localStorage.setItem("nome_processo", JSON.stringify($('#input-processo-seletivo :selected').text()));
	localStorage.setItem("unidade", JSON.stringify($("#input-unidade :selected").text()));

	
})
*/






/*function validate(){
	console.log("entrou")
    if ($('#input-curso').val().length > 0 &&
        $('#input-bolsa100').val().length > 0 &&
        $('#input-bolsa50').val().length > 0) {
        botao_salvar.prop("disabled", false);
	console.log(false)	
    }
    else {
        botao_salvar.prop("disabled", true);
		console.log(true)
    }
}

	
})*/