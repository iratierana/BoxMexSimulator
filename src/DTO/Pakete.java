package DTO;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "pakete")
@XmlType(propOrder = {"id","estado","listaProductos"})
public class Pakete {
	
	int id;
	String estado;
	ArrayList<Producto> listaProductos = new ArrayList<Producto>();
	
	public Pakete(){
		
	}
	
	public Pakete(int id, ArrayList<Producto> listaProductos, String estado) {
		super();
		this.id = id;
		this.listaProductos = listaProductos;
		this.estado = estado;
	}
	
	public Pakete(int id, String estado) {
		super();
		this.id = id;
		this.estado = estado;
	}
		
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<Producto> getListaProductos() {
		return listaProductos;
	}
	public void setListaProductos(ArrayList<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}
	
	

}
