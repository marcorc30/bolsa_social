package org.lasalle.sigas.service.exception;

public class ImpossivelExcluirComissaoInternaException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ImpossivelExcluirComissaoInternaException(String mensagem) {
		super(mensagem);
	}

}
