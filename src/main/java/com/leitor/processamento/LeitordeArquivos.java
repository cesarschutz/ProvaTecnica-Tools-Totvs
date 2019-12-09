package com.leitor.processamento;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.leitor.util.ApllicationUtils;

public class LeitordeArquivos {

	/**
	 * Processa os arquivos da pasta /dados/in.
	 * 
	 * @throws Exception
	 */
	public void processarArquivos() throws Exception {
		String caminhoPastaArquivos = new ApllicationUtils().buscarCaminhoRaiz() + "dados/in";
		File f = new File(caminhoPastaArquivos);
		validar(caminhoPastaArquivos, f);
		ArrayList<String> arquivos = new ArrayList<String>(Arrays.asList(f.list()));
		arquivos.parallelStream().forEach(nomeArquivo -> new ProcessadordeArquivo().processarArquivo(nomeArquivo));
	}

	private void validar(String caminhoPastaArquivos, File f) throws Exception {
		if (f.list() == null)
			throw new Exception("A pasta não existe: " + caminhoPastaArquivos);
		if (f.list().length < 1)
			throw new Exception("Não foi localizado nenhum arquivo na pasta: " + caminhoPastaArquivos);
	}
}
