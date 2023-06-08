package org.lasalle.sigas.storage;

import org.lasalle.sigas.dto.ArquivoDTO;
import org.lasalle.sigas.storage.local.ArquivoStorage;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

public class ArquivoStorageRunnable implements Runnable {

	MultipartFile[] files;
	DeferredResult<ArquivoDTO> resultado;
	ArquivoStorage arquivoStorage;
	
	public ArquivoStorageRunnable(MultipartFile[] files, DeferredResult<ArquivoDTO> resultado, ArquivoStorage arquivoStorage){
		this.files = files;
		this.resultado = resultado;
		this.arquivoStorage = arquivoStorage;
	}
	
	@Override 
	public void run() {
		
		String nomeArquivo = this.arquivoStorage.salvarTemporariamente(files);
//		String nomeArquivo = novoNome; // files[0].getOriginalFilename();
//		String contentType = files[0].getContentType();
		resultado.setResult(new ArquivoDTO(nomeArquivo));

	}

}
