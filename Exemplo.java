import java.io.*;

public class Exemplo{
	public int atributo1;
	public String atributo2;

	public Exemplo(int atributo1, String atributo2){
		this.atributo1 = atributo1;
		this.atributo2 = atributo2;
	}
	
	public int getAtributo1(){
		return this.atributo1;
	}
	
	public String getAtributo2(){
		return this.atributo2;
	}
	
	public void setAtributo1(int atributo1){
		this.atributo1 = atributo1;
	}
	
	public void setAtributo2(String atributo2){
		this.atributo2 = atributo2;
	}
	
	public int comparaCom(Exemplo ex){
		ComparadorExemplo comp = new ComparadorExemplo(this);
		return comp.compareTo(ex);
	}
}