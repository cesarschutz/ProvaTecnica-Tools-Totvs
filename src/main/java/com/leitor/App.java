package com.leitor;

import com.leitor.processamento.LeitordeArquivos;

public class App {
	public static void main(String[] argss) {
		try {
			LeitordeArquivos leitorDeArquivo = new LeitordeArquivos();
			leitorDeArquivo.processarArquivos();
		} catch (Exception e) {
			System.out.println("Erro ao processar arquivos:\n" + e.getMessage());
		}
	}
}
