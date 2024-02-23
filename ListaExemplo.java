import java.util.LinkedList;
import java.util.ListIterator;

public class ListaExemplo{
	
	private static LinkedList<Exemplo> listaDeExemplo;
	
	public static void main(String[] args){
		Exemplo ex1  = new Exemplo(1, "01");
		Exemplo ex2  = new Exemplo(3, "02");
		Exemplo ex3  = new Exemplo(4, "03");
		Exemplo ex4  = new Exemplo(3, "04");
		Exemplo ex5  = new Exemplo(4, "05");
		Exemplo ex6  = new Exemplo(2, "06");
		Exemplo ex7  = new Exemplo(4, "07");
		Exemplo ex8  = new Exemplo(3, "08");
		Exemplo ex9  = new Exemplo(3, "09");
		Exemplo ex10 = new Exemplo(2, "10");
		
		listaDeExemplo = new LinkedList<Exemplo>();
		
		listaDeExemplo.add(ex1);
		listaDeExemplo.add(ex2);
		listaDeExemplo.add(ex3);
		listaDeExemplo.add(ex4);
		listaDeExemplo.add(ex5);
		listaDeExemplo.add(ex6);
		listaDeExemplo.add(ex7);
		listaDeExemplo.add(ex8);
		listaDeExemplo.add(ex9);
		listaDeExemplo.add(ex10);
		
		ListIterator<Exemplo> it = listaDeExemplo.listIterator(0);
		
		while (it.hasNext())
			System.out.println(it.next());
		
		
	}
}