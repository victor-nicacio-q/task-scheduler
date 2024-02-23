import java.io.*;

/* Classe que estabelece as características e comportamentos do Logfile */

public class LogFile{
	
/* -------- ATRIBUTOS -------- */
	
	private static LogFile logFile = null;		// Instância dessa classe, inicializada com null
	private BufferedWriter arqLogFile;			// Ponteiro para o arquivo de saída 
	private String[] bufferResposta;			// Buffer de Resposta
	private int iterador;						// Controlador global do índice do buffer de resposta

/* -------- CONSTRUTORES -------- */
	
	// Recebe como parâmetro o quantum (N_COM) para que esse seja parte do nome do arquivo gerado. Constrói a stream do
	// arquivo de saída (LogFile), declara um buffer de resposta com 1000 linhas e inicia o iterador global
	private LogFile(int N_COM) throws IOException{
		String nomeDoArquivo = "log";
		
		if (N_COM < 10)
			nomeDoArquivo = nomeDoArquivo + 0;
		nomeDoArquivo = nomeDoArquivo + N_COM + ".txt";
		
		this.arqLogFile = new BufferedWriter(new FileWriter(nomeDoArquivo));
		this.bufferResposta = new String[1000];
		
		for (String linha : bufferResposta)
			linha = null;
		
		this.iterador = 0;
	}

/* -------- MÉTODOS -------- */

	// Inclui no buffer de resposta as mensagens de carregamento dos processos, recebendo como parâmetro uma lista 
	// contendo os nomes dos processos já ordenados pelo escalonador
	public void msgCarregaProcessos(String[] listaProcessos){
		for (String processo : listaProcessos){
			this.bufferResposta[this.iterador] = "Carregando " + processo;
			this.iterador++;
		}
	}
	
	// Inclui no buffer de resposta uma mensagem de processo deixando o status 'P' para 'E' (pronto -> executando), recebendo
	// como parâmetro o nome do processo em questão
	public void msgExecutaProcesso(String nomeProcesso){
		if (this.iterador < 1000){
			this.bufferResposta[this.iterador] = "Executando " + nomeProcesso;
			this.iterador++;
		}
		else
			System.err.println("Buffer do LogFile cheio, parar programa");
	}
	
	// Inclui no buffer de resposta uma mensagem de processo sendo interrompido após o término do seu quantum, recebendo
	// como parâmetros o nome do processo e quantidade de instruções que foi capaz de executar até aquele momento
	public void msgInterrompeProcesso(String nomeProcesso, int qtdInstrucoes){
		if (this.iterador < 1000){
			String temp;
			String temp1 = "Interrompendo " + nomeProcesso + " após " + qtdInstrucoes;
			
			if (qtdInstrucoes == 1)
				temp = temp1 + " instrução";
			else
				temp = temp1 + " instruções";

			this.bufferResposta[this.iterador] = temp;
			this.iterador++;
		}
		else
			System.err.println("Buffer do LogFile cheio, parar programa");
	}
	
	// Inclui no buffer de resposta duas mensagens, sendo a primeira a detecção de início de instrução E/S e a segunda
	// do interrompimento provocado justamente por essa instrução (que possivelmente ocorre antes do término do quantum
	// do processo. Recebe como parâmetros o nome do processo e a quantidade de instruções que foi capaz de executar até
	// aquele momento
	public void msgESProcesso(String nomeProcesso, int qtdInstrucoes){
		if (this.iterador < 999){
			this.bufferResposta[this.iterador] = "E/S iniciada em " + nomeProcesso;
			this.iterador++;
			
			msgInterrompeProcesso(nomeProcesso, qtdInstrucoes);
		}
		else
			System.err.println("Buffer do LogFile cheio, parar programa");
	}
	
	// Inclui no buffer de resposta a mensagem de término de processamento de um dado processo, recebendo como parâmetros o
	// nome do processo e os valores contidos nos registradores X e Y
	public void msgFimProcesso(String nomeProcesso, int x, int y){
		if (this.iterador < 1000){
			this.bufferResposta[this.iterador] = nomeProcesso + " terminado. X=" + x + ". Y=" + y + ".";
			this.iterador++;
		}
		else
			System.err.println("Buffer do LogFile cheio, parar programa");
	}
	
	// Inclui no buffer de resposta as mensagens de estatísticas, e chama o finalizador para gerar o .txt com o log pronto.
	// Recebe como parâmetros a média de trocas de processos, a média de instruções e o quantum utilizado como referência
	public void msgEstatisticas(float mediaTrocas, float mediaInstrucoes, int quantum) throws FileNotFoundException, IOException{
		if (this.iterador < 998){
			this.bufferResposta[this.iterador] = "MEDIA DE TROCAS: " + mediaTrocas;
			iterador++;
			
			this.bufferResposta[this.iterador] = "MEDIA DE INSTRUCOES: " + mediaInstrucoes;
			iterador++;
			
			this.bufferResposta[this.iterador] = "QUANTUM: " + quantum;
			
			gravaBuffer();
		}
		else
			System.err.println("Buffer do LogFile cheio, parar programa");
	}
	
	// Grava o buffer de respostas no arquivo e fecha a Stream, determinando o fim do processo e entrega do LogFile para o
	// o usuário
	private void gravaBuffer() throws IOException, FileNotFoundException{
		if (this.arqLogFile != null){
			for (String linha : this.bufferResposta){
				if (linha != null){
					this.arqLogFile.write(linha, 0, linha.length());
					this.arqLogFile.newLine();
				}
				else 
					break; // Caso encontre alguma linha nula significa que desse momento em diante o buffer está vazio
			}
			arqLogFile.close();
			System.out.println("Logfile gravado com sucesso");
		}
		else
			System.err.println("Erro na gravação do LogFile");
	}
	
	// Método público para retornar a instância única do objeto de LogFile. Recebe como parâmetro o quantum utilizado (N_COM)
	public static LogFile getInstance(int N_COM) throws IOException{
		if (logFile == null)
			logFile = new LogFile(N_COM);
		return logFile;	
	}
}