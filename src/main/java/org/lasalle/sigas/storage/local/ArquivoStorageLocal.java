package org.lasalle.sigas.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class ArquivoStorageLocal implements ArquivoStorage{

	private Path local;
	private Path localTemporario;

	
	public ArquivoStorageLocal() {
//		this(FileSystems.getDefault().getPath(System.getProperty("HomePath"), "sigasarquivos"));
//		this(FileSystems.getDefault().getPath(System.getProperty("user.home"), "sigasarquivos"));
		this(FileSystems.getDefault().getPath(System.getenv("HOME"), "sigasarquivos"));
		
//		criarPastas();
	}

	public ArquivoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}

	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = FileSystems.getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
			
			System.out.println("Pastas criadas para receber os arquivos");
			System.out.println("Pasta default: " + this.local.toAbsolutePath() );
			System.out.println("Pasta temporária " + this.localTemporario.toAbsolutePath());
			
		}catch(IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar arquivos", e);
		}
		
	}


	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(this.local.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + novoNome));
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando o arquivo na foto temporária");
			}
			
			
		}
		
		return novoNome;
	}

	
	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		
		return novoNome;
	}

	@Override
	public byte[] recuperarArquivo(String nome) {
		// TODO Auto-generated method stub
		try {
			System.out.println("arquivo em arquivo storage " + this.local.resolve(nome));
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Erro lendo o arquivo", e);
		}
	}
	
	
}
