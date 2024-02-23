import java.util.regex.*;

// Classe que representa o BCP de um processo
public class BCP implements Comparable<BCP> {
	
/* ------------ ATRIBUTOS ------------- */
	
	private int PC = 1;					// Representa a instrução (linha) que está sendo executada - default = 1, pois a primeira linha do processo é o nome do programa
	private char statusProcesso = 'P';	// Recebe 'E', 'P' ou 'B' - Executando, Pronto ou Bloqueado, respectivamente
	private int prioridade;				// Inteiro, iniciado com o valor originado pelo arquivo .txt e que sofrer alterações ao longo das execuções
	private int X = 0;					// 1º registrador de uso geral - inicializado com zero (0)
	private int Y = 0;					// 2º registrador de uso geral - inicializado com zero (0)
	private String[] referenciaMemoria;	// Referência para a região de memória em que está o código do programa executado
	private String nomePrograma;		// Nome do programa a que cada instância de BCP estar atrelada
	private int creditos;				// Quantidade de créditos que o processo dispõe para que seja corretamente ordenado na lista de prontos
	private int quantum = 1;			// Quantidade de quanta que o processo tem disponível para executar
	private int temporizador = 0;		// Conta o tempo de execução para suspensão de programas
	
/* ---------------- CONSTRUTORES ----------------- */
	
	// PC, Status do Processo, X e Y so inicializados de modo padrão. Prioridade é recebida após extração via arquivo, bem como o Nome do Programa.
	//  Referência de Memória  sendo o ponteiro para o buffer com o código do programa
	//	Créditos é inicializado com o mesmo valor de prioridade recebido
	
	public BCP(int prioridade, String[] buffer, String nomePrograma) {
		this.prioridade = prioridade;
		this.creditos = prioridade;
		this.referenciaMemoria = buffer;
		this.nomePrograma = nomePrograma;
	}
	
/* --------------- MTODOS ------------------- */
	
	// Getters de todos os atributos e Setters somente de PC, X, Y, Status do Processo e Prioridade 
	
	public int getPC() {
		return this.PC;
	}
	
	public char getStatusProcesso() {
		return this.statusProcesso;
	}
	
	public int getPrioridade() {
		return this.prioridade;
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getY() {
		return this.Y;
	}
	
	public String getInstrucao(int linha) {
		return this.referenciaMemoria[linha];
	}
	
	public String getNomePrograma() {
		return this.nomePrograma;
	}
	
	public int getCreditos(){
		return this.creditos;
	}
	
	public int getQuantum(){
		return this.quantum;
	}

	public int getTemporizador(int temp) {
		return this.temporizador;
	}
	
	public void setPC(int PC) {
		this.PC = PC;
	}
	
	public void setStatusProcesso(char statusProcesso) {
		this.statusProcesso = statusProcesso;
	}
	
	public void setX(int X) {
		this.X = X;
	}
	
	public void setY(int Y) {
		this.Y = Y;
	}
	
	public void setCreditos(int creditos){
		this.creditos = creditos;
	}
	
	public void setQuantum(int quantum){
		this.quantum = quantum;
	}

	public void setTemporizador(int temp) {
		this.temporizador = temp;
	}
	
	// Estabelece os critérios de comparação segundo a precedência estabelecida (primeiramente verifica os créditos), depois,
	// somente em caso de créditos iguais, desempata por ordem alfabética
	public int compareTo(BCP bcp) throws NullPointerException, ClassCastException{
		if (this.creditos < bcp.getCreditos())
			return -1;
		if (this.creditos > bcp.getCreditos())
			return 1;
		
		Pattern padrao = Pattern.compile("TESTE-");
		String[] comp1 = padrao.split(this.nomePrograma);
		String[] comp2 = padrao.split(bcp.getNomePrograma());
		
		if (Integer.parseInt(comp1[1]) < Integer.parseInt(comp2[1]))
			return -1;
		else
			return 1;		
	}
}