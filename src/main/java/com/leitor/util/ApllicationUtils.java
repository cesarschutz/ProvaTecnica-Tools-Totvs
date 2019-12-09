package com.leitor.util;

import java.net.URISyntaxException;

public class ApllicationUtils {
	/**
	 * Retorna a pasta onde o .jar está localizado.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public String buscarCaminhoRaiz() throws URISyntaxException {
		String caminhoJar = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		caminhoJar = caminhoJar.substring(1, caminhoJar.lastIndexOf('/') + 1);
		return caminhoJar;
	}
}
