$(function(){
	

	inputCpf = $('.js-input-cpf');
	inputCpf.mask("000.000.000-00");
	
	
	inputTelefone = $('.js-formatar-telefone');
	
		var maskBehavior = function (val) {
			  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
			}
		var	options = {
			  onKeyPress: function(val, e, field, options) {
			      field.mask(maskBehavior.apply({}, arguments), options);
			    }
			};
		inputTelefone.mask(maskBehavior, options);		
		
	
	inputAno = $('.js-ano');
	inputAno.mask('0000');	
		
	inputCep = $('.js-input-cep');
	inputCep.mask('00000-000');	
	
	inputStatus = $('.js-status');
	inputStatus.bootstrapSwitch();
	
	inputStatus2 = $('.js-status2');
	inputStatus2.bootstrapSwitch();
	
	inputParcela = $('.js-parcela');
	inputParcela.mask('00/0000');
	 

	inputDate = $('.js-date');
	inputDate.mask('00/00/0000');
	
	$('#modalExemplol').modal('show')  
	
	var inteiro = $('.js-inteiro');   
	inteiro.maskMoney({precision : 0, thousands: '.', allowZero: true});
	
	var decimal = $('.js-decimal');
	decimal.maskMoney({decimal: ',', thousands: '.', allowZero: true}); 
	
	
	var alfanumerico = $('.js-bloqueia-numero');
	alfanumerico.on("keypress", function(e) {
	    var keyCode = (e.keyCode ? e.keyCode : e.which);
  
		  if (keyCode > 47 && keyCode < 58) {
		    e.preventDefault();
  }
});
	

	
	
})


/*$(function(){
	
	inputTelefone = $('.js-formatar-telefone');
	
		var maskBehavior = function (val) {
			  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
			}
		var	options = {
			  onKeyPress: function(val, e, field, options) {
			      field.mask(maskBehavior.apply({}, arguments), options);
			    }
			};
		inputTelefone.mask(maskBehavior, options);		
	
})

*//*var Sigas = Sigas || {};

Sigas.FormatarCpf = (function(){
	
	function FormatarCpf(){
		this.inputCpf = $('.js-input-cpf');
	}
	
	FormatarCpf.prototype.iniciar = function(){
		this.inputCpf.mask("000.000.000-00");
	}
	
	return FormatarCpf;
	
})();

Sigas.FormatarTelefone = (function(){

	function FormatarTelefone(){
		this.inputTelefone = $('.js-formatar-telefone');
	}
	
	FormatarTelefone.prototype.iniciar = function(){
		var maskBehavior = function (val) {
			  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
			}
		var	options = {
			  onKeyPress: function(val, e, field, options) {
			      field.mask(maskBehavior.apply({}, arguments), options);
			    }
			};
		this.inputTelefone.mask(maskBehavior, options);		
	}
	
	return FormatarTelefone;
	
})();*/