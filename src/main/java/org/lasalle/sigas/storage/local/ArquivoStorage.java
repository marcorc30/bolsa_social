package org.lasalle.sigas.storage.local;

import org.springframework.web.multipart.MultipartFile;

public interface ArquivoStorage {
	
	public String salvarTemporariamente(MultipartFile[] files);

	public byte[] recuperarArquivo(String nome);

}
