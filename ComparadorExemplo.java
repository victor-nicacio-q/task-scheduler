import java.io.*;

public class ComparadorExemplo implements Comparable{
	
	Exemplo ex;
	
	public ComparadorExemplo(Exemplo ex){
		this.ex = new Exemplo(ex.atributo1, ex.atributo2);
	}
	
	public int compareTo(Exemplo ex) throws NullPointerException, ClassCastException{
		if (this.atributo1 < ex.atributo1)
			return -1;
		if (this.atributo1 > ex.atributo1)
			return 1;
		char[] comp1 = this.atributo2.toCharArray();
		char[] comp2 = ex.atributo2.toCharArray();
		if (comp1[0] < comp2[0])
			return -1;
		if (comp1[0] > comp2[0])
			return 1;
		if (comp1[1] < comp2[1])
			return -1;
		return 1;
	}
}