var Sigas = Sigas || {};

Sigas.Cep = (function(){

	function Cep(){
		this.cep = $("#cep");
		this.logradouro = $("#endereco")
		this.bairro = $("#bairro")
		this.uf = $("#estado")
		this.localidade = $("#cidade")
		this.complemento = $('#complemento')
	}

	Cep.prototype.iniciar = function(){	

		this.cep.on("focusout", onBuscarCep.bind(this))

		
	}

	function onBuscarCep(){
		
		$.ajax({
			url: "https://viacep.com.br/ws/" + this.cep.val() + "/json/",
			method: "get",
			dataType: "jsonp",
			success: onCarregarEndereco.bind(this),
			error: onError.bind(this)
		})
	}

	function onCarregarEndereco(endereco){
		this.logradouro.val(endereco.logradouro);
		this.bairro.val(endereco.bairro);
		this.uf.val(endereco.uf);
		this.localidade.val(endereco.localidade);
		this.complemento.val(endereco.complemento);
	}

	function onError(){
		this.logradouro.val("Não localizado");
		this.bairro.val("Não localizado");
		this.uf.val("Não localizado");
		this.localidade.val("Não localizado")
		this.complemento.val("Não localizado")

	}

	return Cep;

})();

$(function(){
	var cep = new Sigas.Cep();
	cep.iniciar();
})