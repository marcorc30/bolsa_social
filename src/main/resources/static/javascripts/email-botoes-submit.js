

BotaoSubmit = (function() {
	
	function BotaoSubmit() {
		this.submitBtn = $('.js-submit-btn');
		this.formulario = $('.js-formulario-principal');
	}
	
	BotaoSubmit.prototype.iniciar = function() {
		this.submitBtn.on('click', onSubmit.bind(this));
	}
	
	function onSubmit(evento) {
		evento.preventDefault();
		
		var botaoClicado = $(evento.target);
		var acao = botaoClicado.data('acao');
		
		console.log('acao', acao);
		
		var acaoInput = $('<input>');
		acaoInput.attr('name', acao);
		
		console.log('acaoInput', acaoInput)
		
		this.formulario.append(acaoInput);
		this.formulario.submit();
	}
	
	return BotaoSubmit
	
}());

$(function() {
	 
	/*var botaoSubmit = new BotaoSubmit();
	botaoSubmit.iniciar();*/
	
});