/*var Sigas = Sigas || {};


Sigas.MostrarBotaoUpload = (function(){

console.log('lendo o arquivo 1')
	
	function MostrarBotaoUpload(){
		this.checkDeficiente = document.querySelector('.js-status');
		this.botaoUpload = document.querySelector('.doc-deficiente');
	}
	
	MostrarBotaoUpload.prototype.iniciar = function(){
		this.checkDeficiente.addEventListener('change', exibirBotaoUpload.bind(this));
		this.checkDeficiente.addEventListener('click', console.log('cliquei no check'));
		
	}
	
	
	function exibirBotaoUpload(){
		var ehDeficiente = this.checkDeficiente.val();
		console.log('ehDeficiente', ehDeficiente);
		console.log('Chamou o botao aqui');
		
		if(!ehDeficiente){
			this.botaoUpload.classList.add("mostrar-botao-upload");	
		}else{
			this.botaoUpload.classList.remove("mostrar-botao-upload");
		}
		
		
	}
	
	
	return MostrarBotaoUpload;
	
	
})();


$(function(){
	
	console.log('lendo o arquivo 2')
	var mostrarBotaoUpload = new Sigas.MostrarBotaoUpload();
	mostrarBotaoUpload.iniciar();
	
	
	
})*/

var checkDeficiente = document.querySelector('.bootstrap-switch-label');
checkDeficiente.addEventListener('click', console.log('cliquei no check'));
