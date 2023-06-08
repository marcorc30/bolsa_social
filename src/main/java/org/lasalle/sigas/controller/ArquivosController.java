package org.lasalle.sigas.controller;

import org.lasalle.sigas.dto.ArquivoDTO;
import org.lasalle.sigas.storage.ArquivoStorageRunnable;
import org.lasalle.sigas.storage.local.ArquivoStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/arquivo")
public class ArquivosController {

	@Autowired
	private ArquivoStorage arquivoStorage;
	
	@PostMapping
	public DeferredResult<ArquivoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		
		
		DeferredResult<ArquivoDTO> resultado = new DeferredResult<ArquivoDTO>();
		
		Thread thread = new Thread(new ArquivoStorageRunnable(files, resultado, arquivoStorage));
		thread.run();
		
		return resultado;
	}
	
	@GetMapping("/{nome:.*}")
	public byte[] recuperarArquivo(@PathVariable String nome) {
		System.out.println("nome do arquivo " + nome );
		return arquivoStorage.recuperarArquivo(nome);
	}
	
	
}
