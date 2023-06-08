$(function(){
	
	var email = $('#email');
	var botao = $('.botao-enviar');
	var form = $('#form-senha');
	var url = botao.data('url');
	
	var data = {email: email.val()}
	
	botao.on('click', function(event){
		event.preventDefault();
		console.log('Enviando email....', email.val())
		console.log('url', url)
		
		$.ajax({
			url: url,
			method: 'GET',
			success: enviarEmail,
			data: data
			
		})
		

		function enviarEmail(data){
			console.log('enviado...... dentro do metodo', data.email)
		}

	})
	
	
	
})