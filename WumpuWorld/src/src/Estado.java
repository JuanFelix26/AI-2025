package src;

import java.util.ArrayList;

public class Estado {
	String nombre;
	ArrayList<String> padres;
	ArrayList<String> hijos;
	
	public Estado(String nombre, ArrayList<String> padres, ArrayList<String> hijos) {
		super();
		this.nombre = nombre;
		this.padres = padres;
		this.hijos = hijos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<String> getPadres() {
		return padres;
	}

	public void setPadres(ArrayList<String> padres) {
		this.padres = padres;
	}

	public ArrayList<String> getHijos() {
		return hijos;
	}

	public void setHijos(ArrayList<String> hijos) {
		this.hijos = hijos;
	}
	
	
}
