var Plbolsa = Plbolsa || {};

Plbolsa.GraficoPorEmpresa = (function(){
	
	function GraficoPorEmpresa(){
		this.ctx = $('#graficoPorEmpresa')[0].getContext('2d');
	}
	
	GraficoPorEmpresa.prototype.iniciar = function(){
		$.ajax({
			url: "solicitacao/total-por-unidade",
			method: "GET",
			success: renderizar.bind(this)
		});
	}

	function renderizar(totaisPorUnidade){
		
		var unidades = [];
		var totais = []; 
		
		totaisPorUnidade.forEach(function(obj){
			unidades.unshift(obj.nome);
			totais.unshift(obj.total);
		})

		
		var graficoTotalPorEmpresa = new Chart(this.ctx, {
			type: 'line',
			data: {
				labels: unidades,
				datasets: [{
		    		label: 'Quantidade de Inscritos',
		    		backgroundColor: "rgba(26,179,148,0.5)",
	                pointBorderColor: "rgba(26,179,148,1)",
	                pointBackgroundColor: "#fff",
	                data: totais
		    	}]
			}
		})
		
	}
	
	return GraficoPorEmpresa;
	
}());

$(function(){
	var graficoPorEmpresa = new Plbolsa.GraficoPorEmpresa();
	graficoPorEmpresa.iniciar();
})