// Classe que contém lógicas adicionais, para serem remanejadas em suas respectivas classes futuramente
public class Adicionais {
	
/* ---------- MÉTODOS ------------ */
	
	// Interpreta uma linha de código recebida e - no lugar dos printlns - aplicará ou invocará classes/métodos que executem as devidas funções
	
	public static void interpretaCodigo(String linhaDeCodigo){
		char[] linhaFatorada = linhaDeCodigo.toCharArray();
		
		if (linhaFatorada[0] == 'E')
			System.out.println("Processo fara E/S");
		
		else if (linhaFatorada[0] == 'C')
			System.out.println("Processo executou COMANDO");
		
		else if (linhaFatorada[0] == 'S')	
			System.out.println("Processo finalizado - SAIDA");
		
		else if (linhaFatorada[0] == 'X')
			System.out.println("Atualizar o registrador X para: " + (char)linhaFatorada[2]);
		
		else
			System.out.println("Atualizar o registrador Y para: " + (char)linhaFatorada[2]);
	}
}