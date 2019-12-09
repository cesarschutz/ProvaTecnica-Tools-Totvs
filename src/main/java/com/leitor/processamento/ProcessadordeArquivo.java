package com.leitor.processamento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.leitor.entidades.Cliente;
import com.leitor.entidades.Venda;
import com.leitor.entidades.Vendedor;
import com.leitor.util.ApllicationUtils;

public class ProcessadordeArquivo {

	private Map<Long, Cliente> listaDeClientes = new TreeMap<Long, Cliente>();
	private Map<String, Vendedor> listaDeVendedores = new TreeMap<String, Vendedor>();
	private Map<Long, Venda> listaDeVendas = new TreeMap<Long, Venda>();

	/**
	 * Processa um arquivo, salvando o mesmo na pasta
	 * /dados/out/${nomedoarquivolido}.dat.proc com os resultados obtidos.
	 *
	 * @param nomeDoArquivo
	 */
	public void processarArquivo(String nomeDoArquivo) {
		System.out.println("Processando arquivo: " + nomeDoArquivo);
		try {
			BufferedReader br = new BufferedReader(new FileReader(new ApllicationUtils().buscarCaminhoRaiz() + "dados/in/" + nomeDoArquivo));

			while (br.ready()) {
				String linha = br.readLine();
				String[] linhaSplit = linha.split(";");

				int tipoDeEntidade = Integer.valueOf(linhaSplit[0]);
				criarEntidade(linha, tipoDeEntidade);
			}

			br.close();
			salvarArquivoTotalizador(nomeDoArquivo);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(nomeDoArquivo + " finalizado.");
	}

	/**
	 * Cria uma entidade e adiciona na sua lista de acordo com o tipo de entidade recebida.
	 * 
	 * @param linha          dados da entidade
	 * @param tipoDeEntidade tipo de entidade recebida
	 */
	private void criarEntidade(String linha, int tipoDeEntidade) {
		switch (tipoDeEntidade) {
		case 1:
			Vendedor vendedor = criarVendedor(linha);
			listaDeVendedores.put(vendedor.getNome(), vendedor);
			break;
		case 2:
			Cliente cliente = criarCliente(linha);
			listaDeClientes.put(cliente.getCNPJ(), cliente);
			break;
		case 3:
			Venda venda = criarVenda(linha);
			listaDeVendas.put(venda.getId(), venda);
		}
	}

	/**
	 * Cria um {@link Vendedor}.
	 * 
	 * @param dados (001;CPF;nome;salário)
	 * @return
	 */
	private Vendedor criarVendedor(String dados) {
		Vendedor vendedor = new Vendedor();
		String[] dadosSplit = dados.split(";");

		vendedor.setCPF(Long.valueOf(dadosSplit[1]));
		vendedor.setNome(dadosSplit[2]);
		vendedor.setSalario(Double.valueOf(dadosSplit[3]));

		return vendedor;
	}

	/**
	 * Cria um {@link Cliente}.
	 * 
	 * @param dados (002;CNPJ;nome;ramo de atividade)
	 * @return
	 */
	private Cliente criarCliente(String dados) {
		Cliente cliente = new Cliente();
		String[] dadosSplit = dados.split(";");

		cliente.setCNPJ(Long.valueOf(dadosSplit[1]));
		cliente.setNome(dadosSplit[2]);
		cliente.setRamoDeAtividade(dadosSplit[3]);

		return cliente;
	}

	/**
	 * Cria uma {@link Venda}.
	 * 
	 * @param dados (003;ID da venda;ID do item;qtde do item;preço do item;Nome do Vendedor)
	 * @return
	 */
	private Venda criarVenda(String dados) {
		Venda venda = new Venda();
		String[] dadosSplit = dados.split(";");

		venda.setId(Long.valueOf(dadosSplit[1]));
		venda.setIdItem(Long.valueOf(dadosSplit[2]));
		venda.setQtdItem(Double.valueOf(dadosSplit[3]));
		venda.setPrecoItem(Double.valueOf(dadosSplit[4]));
		venda.setVendedor(listaDeVendedores.get(dadosSplit[5]));

		return venda;
	}

	/**
	 * Salva arquivo com os resultados de um arquivo processado.
	 * 
	 * @param nomeArquivo que foi processado.
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private void salvarArquivoTotalizador(String nomeArquivo) throws IOException, URISyntaxException {
		String caminhoJar = new ApllicationUtils().buscarCaminhoRaiz();

		File diretorio = new File(caminhoJar + "/dados/out");
		diretorio.mkdirs();

		File file = new File(diretorio.getAbsolutePath() + File.separator + nomeArquivo + ".proc");
		FileWriter fw = new FileWriter(file);

		fw.write(criarResultadosDoArquivo(nomeArquivo));
		fw.close();
	}

	/**
	 * Cria uma String com os resultados do arquivo recebido.
	 * 
	 * @param nomeArquivo
	 * @return
	 */
	private String criarResultadosDoArquivo(String nomeArquivo) {
		StringBuilder imprimir = new StringBuilder();
		imprimir.append("Arquivo: " + nomeArquivo);
		imprimir.append("\n1. Quantidade de Clientes: ").append(listaDeClientes.size());
		imprimir.append("\n2. Quantidade de Vendedores: ").append(listaDeVendedores.size());
		imprimir.append("\n3. ID da Venda de valor mais alto: ").append(buscarVendaComValorMaisAlto());
		imprimir.append("\n4. Nome do Vendedor que menos vendeu: ").append(buscarVendedorQueMenosVendeu());
		return imprimir.toString();
	}

	private Long buscarVendaComValorMaisAlto() {
		List<Venda> vendas = new ArrayList<>(listaDeVendas.values());
		// vendas.sort((v1, v2) -> Double.compare(v1.getValorTotalVenda(), v2.getValorTotalVenda()));
		vendas.sort(Comparator.comparing(v -> v.getValorTotalVenda()));
		return vendas.get(vendas.size() - 1).getId();
	}

	private String buscarVendedorQueMenosVendeu() {
		List<Vendedor> vendedores = new ArrayList<>(listaDeVendedores.values());
		List<Venda> vendas = new ArrayList<>(listaDeVendas.values());

		double valorMenordeVendas = 0.0;
		String vendedorComMenosVendas = "";

		for (Vendedor vendedor : vendedores) {
			double valorTotalVendasFuncionario = 0.0;
			for (Venda venda : vendas) {
				if(venda.getVendedor().getNome().equals(vendedor.getNome())) {
					valorTotalVendasFuncionario += venda.getValorTotalVenda();
				}
			}

			if (valorMenordeVendas == 0 || valorMenordeVendas > valorTotalVendasFuncionario) {
				valorMenordeVendas = valorTotalVendasFuncionario;
				vendedorComMenosVendas = vendedor.getNome() + " - " + valorMenordeVendas;
			}
		}

		return vendedorComMenosVendas;
	}
}
