import java.io.*;
import java.util.*;

// Classe que simula o comportamento do escalonador
public class Escalonador {
	/*
	/ ATRIBUTOS
	*/

	private int N_COM; // Número de Comandos que cada processo tem direito de executar nas condições iniciais, obtido pelo arquivo quantum.txt
	private static LinkedList<BCP> tabelaProcessos; // Tabela de Processos (Lista de BCPs, contendo todos os processos carregados - sem ordenação específica)
	private static Collection<BCP> listaProntos; // Lista dos processos prontos (Lista de BCPs - ordenação primária por créditos e secundária por ordem alfabética)
	private static LinkedList<BCP> listaBloqueados; // Lista dos processos bloqueados (Lista de BCPs) - ordenada por ordem de chegada
	private LogFile logFile; // Instância de LogFile

	/*
	/ CONSTRUTORES
	*/

	public Escalonador() {
	}


	// Constrói um buffer com o código do programa, para que haja o controle
	// realizado pelo PC
	public static String[] constroiBufferPrograma(BufferedReader arquivoPrograma, int contador) {
		String[] buffer = new String[contador];

		try {
			String linha = arquivoPrograma.readLine();

			for (int i = 0; linha != null; i++) {
				buffer[i] = linha;
				linha = arquivoPrograma.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo: " + arquivoPrograma.toString() + " não encontrado");
			return null;
		} catch (IOException e) {
			System.out.println("Erro " + e.toString() + " na leitura do arquivo: " + arquivoPrograma.toString());
			return null;
		}

		return buffer;
	}

	// Realiza a contagem de linhas do arquivo programa
	public static int contaLinhasPrograma(BufferedReader arquivoPrograma) {
		int contador = 0;

		try {
			String linha = arquivoPrograma.readLine();

			while (linha != null) {
				contador++;
				linha = arquivoPrograma.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo: " + arquivoPrograma.toString() + " não encontrado");
			return -1;
		} catch (IOException e) {
			System.out.println("Erro " + e.toString() + " na leitura do arquivo: " + arquivoPrograma.toString());
			return -1;
		}
		return contador;
	}

	public static BufferedReader[] geraArqProg(BufferedReader[] entrada) throws Exception {
		int auxNomeTxt = 0;
		String aux = "";
		try {
			for (int i = 0; i < 10; i++) {
				auxNomeTxt++;
				if (Integer.toString(i).length() == 1) {
					aux = "0" + Integer.toString(auxNomeTxt) + ".txt";
				} else {
					aux = Integer.toString(auxNomeTxt) + ".txt";
				}
				entrada[i] = new BufferedReader(new FileReader(aux));
			}
			return entrada;
		} catch (Exception e) {
			throw e;
		}

	}

	// Rotina de inicialização do escalonador - leitura dos arquivos e carregamento
	// dos processos
	public static void carregaProcessos() {
		// Declaração das instâncias dos apontadores dos arquivos
		BufferedReader[] arqProg = new BufferedReader[10];

		arqProg = geraArqProg(arqProg);

		BufferedReader arqPrioridade = new BufferedReader(new FileReader("prioridades.txt"));
		BufferedReader arqQuantum = new BufferedReader(new FileReader("quantum.txt"));

		// Marcação da quantidade máxima de caracteres permitida até o Reset
		for (int i = 0; i < 10; i++) {
			arqProg[i].mark(1000);
		}

		// Declaração dos buffers dos códigos de cada programa
		// Declaração dos buffers dos códigos de cada programa
		String[] codProg1;
		String[] codProg2;
		String[] codProg3;
		String[] codProg4;
		String[] codProg5;
		String[] codProg6;
		String[] codProg7;
		String[] codProg8;
		String[] codProg9;
		String[] codProg10;

		// Declaração das variáveis que contabilizam a quantidade de linhas de cada
		// programa

		int qtdLinhas[] = new int[10];
		for (int i = 0; i < 10; i++) {
			qtdLinhas[i] = contaLinhasPrograma(arqProg[i]);
		}

		// Testa se o retorno da contagem foi válida, em caso positivo encaminha para a
		// construção dos buffers dos programas
		if (qtdLinhas[0] >= 0 && qtdLinhas[1] >= 0 && qtdLinhas[2] >= 0 && qtdLinhas[3] >= 0 && qtdLinhas[4] >= 0
				&& qtdLinhas[5] >= 0 && qtdLinhas[6] >= 0 && qtdLinhas[7] >= 0 && qtdLinhas[8] >= 0
				&& qtdLinhas[9] >= 0) {

			// Reposiciona cada ponteiro para a marcação inicial de cada Stream
			for (int i = 0; i < 10; i++) {
				arqProg[i].reset();
			}

			// Chamada ao construtor do buffer
			codProg1 = constroiBufferPrograma(arqProg[0], qtdLinhas[0]);
			codProg2 = constroiBufferPrograma(arqProg[1], qtdLinhas[1]);
			codProg3 = constroiBufferPrograma(arqProg[2], qtdLinhas[2]);
			codProg4 = constroiBufferPrograma(arqProg[3], qtdLinhas[3]);
			codProg5 = constroiBufferPrograma(arqProg[4], qtdLinhas[4]);
			codProg6 = constroiBufferPrograma(arqProg[5], qtdLinhas[5]);
			codProg7 = constroiBufferPrograma(arqProg[6], qtdLinhas[6]);
			codProg8 = constroiBufferPrograma(arqProg[7], qtdLinhas[7]);
			codProg9 = constroiBufferPrograma(arqProg[8], qtdLinhas[8]);
			codProg10 = constroiBufferPrograma(arqProg[9], qtdLinhas[9]);

			// Caso algum deles retorne null, não será adicionado às estruturas - adiante
		} else
			System.exit(1); // Valores negativos indicam exceção tratada - arquivo comprometido - parada do
							// processamento

		// Construção da Tabela de Processos, da Lista de Prontos e da Lista de
		// Bloqueados
		tabelaProcessos = new LinkedList<BCP>();
		listaProntos = new TreeSet<BCP>();
		listaBloqueados = new LinkedList<BCP>();

		// Declaração dos BCPs de cada processo/programa
		BCP bcp1 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg1, codProg1[0]);
		BCP bcp2 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg2, codProg2[0]);
		BCP bcp3 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg3, codProg3[0]);
		BCP bcp4 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg4, codProg4[0]);
		BCP bcp5 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg5, codProg5[0]);
		BCP bcp6 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg6, codProg6[0]);
		BCP bcp7 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg7, codProg7[0]);
		BCP bcp8 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg8, codProg8[0]);
		BCP bcp9 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg9, codProg9[0]);
		BCP bcp10 = new BCP(Integer.parseInt(arqPrioridade.readLine()), codProg10, codProg10[0]);

		// Se possuírem código a ser lido, os BCPs criados são adicionados à Tabela de
		// Processos e à Lista de Prontos
		// (Há a precedência de que todo processo recém chegado vem com status 'P' -
		// pronto) - como consequência, a Lista
		// de Bloqueados é inicializada somente com o nó cabeça (sem processos
		// adicionados)

		if (codProg1 != null) {
			tabelaProcessos.add(bcp1);
			listaProntos.add(bcp1);
		}
		if (codProg2 != null) {
			tabelaProcessos.add(bcp2);
			listaProntos.add(bcp2);
		}
		if (codProg3 != null) {
			tabelaProcessos.add(bcp3);
			listaProntos.add(bcp3);
		}
		if (codProg4 != null) {
			tabelaProcessos.add(bcp4);
			listaProntos.add(bcp4);
		}
		if (codProg5 != null) {
			tabelaProcessos.add(bcp5);
			listaProntos.add(bcp5);
		}
		if (codProg6 != null) {
			tabelaProcessos.add(bcp6);
			listaProntos.add(bcp6);
		}
		if (codProg7 != null) {
			tabelaProcessos.add(bcp7);
			listaProntos.add(bcp7);
		}
		if (codProg8 != null) {
			tabelaProcessos.add(bcp8);
			listaProntos.add(bcp8);
		}
		if (codProg9 != null) {
			tabelaProcessos.add(bcp9);
			listaProntos.add(bcp9);
		}
		if (codProg10 != null) {
			tabelaProcessos.add(bcp10);
			listaProntos.add(bcp10);
		}

		// Leitura do arquivo quantum.txt e declaração do LogFile
		N_COM = arqQuantum.readLine();
		logFile = LogFile.getInstance(N_COM);

		// Fechamento das Streams dos arquivos
		for (int i = 0; i < 10; i++) {
			arqProg[i].close();
		}
		arqPrioridade.close();
		arqQuantum.close();

		// Confecção do log de carregamento dos processos - cria um iterador, um buffer
		// com os nomes dos processos e
		// chama o método do logFile
		Iterator<BCP> it = listaProntos.descendingIterator();
		String[] nomesProcessos = new String[10];

		for (String nomeProcesso : nomesProcessos) {
			nomeProcesso = it.next().getNomePrograma();
		}

		logFile.msgCarregaProcessos(nomesProcessos);
	}

	// Interpreta uma linha de código recebida e executa as instruções nela contidas
	public static boolean interpretaCodigo(BCP bcp, int qtdInstrucoes){
		char[] linhaFatorada = bcp.getInstrucao(bcp.getPC()).toCharArray();
		
		if (linhaFatorada[0] == 'E'){
			logFile.msgESProcesso(bcp.getNomePrograma(), qtdInstrucoes);
		
		else if (linhaFatorada[0] == 'X'){
			if (linhaFatorada.length > 3){
				String valor = "";
			
				for (int i = 2; i < linhaFatorada.length; i++)
					valor += linhaFatorada[i];
				
				bcp.setX(Integer.parseInt(valor));
			}	
			else
				bcp.setX((int)linhaFatorada[2]);
		}
		
		else if (linhaFatorada[0] == 'Y'){
			if (linhaFatorada.length > 3){
				String valor = "";
			
				for (int i = 2; i < linhaFatorada.length; i++)
					valor += linhaFatorada[i];
				
				bcp.setY(Integer.parseInt(valor));
			}	
			else
				bcp.setY((int)linhaFatorada[2]);
		}	
		
		else if (linhaFatorada[0] == 'S'){
			logFile.msgFimProcesso(bcp.getNomePrograma(), bcp.getX(), bcp.getY());
			
			if (tabelaProcessos.remove(bcp) == false || listaProntos.remove(bcp) == false)
				System.err.println("BCP não encontrado na tabela ou na lista de prontos para que seja removido");
			else 
				return true;

		}

		return false;
	}

	public static void main(String[] args) {
		// Declara um BCP genérico para realizar as chamadas, um contador de instruções
		// para determinar quantas instruções
		// cada processo realizou e chama a rotina de carregamento dos processos
		carregaProcessos();
		BCP bcp;
		int contaInstrucoes;

		// O escalonador executará os processos até que todos tenham terminado. Para tal
		// o contador de instruções
		// inicia cada quantum com zero, obtém-se o BCP do processo de maior prioridade
		// (dada a implementação via TreeSet,
		// esse processo estará por último) e o status do mesmo é alterado para E -
		// Executando
		while (tabelaProcessos.size() > 0) {
			contaInstrucoes = 0;
			boolean finalizado = false;
			bcp = listaProntos.last();
			bcp.setStatusProcesso('E');
			logFile.msgExecutaProcesso(bcp.getNomePrograma());

			// Executa instruções enquanto estiver com status E - Executando ou enquanto a
			// contagem de instruções não superar
			// o número de comandos por quantum multiplicado pela quantidade de quantum que
			// o processo detém
			while ((contaInstrucoes < bcp.getQuantum() * N_COM) && (bcp.getStatusProcesso() == 'E') && finalizado == false) {
				contaInstrucoes++;
				boolean finalizou = interpretaCodigo(bcp, contaInstrucoes);
				bcp.setPC(bcp.getPC() + 1);
			}

			logFile.msgInterrompeProcesso(bcp.getNomePrograma(), contaInstrucoes);
			// TODO:
			bcp.setQuantum(bcp.getQuantum()++);
			bcp.setCreditos(bcp.getCreditos() - 2);
			if (bcp.getCreditos() < 0) {
				bcp.setCreditos(0);
			}
		}
	}
	}
}