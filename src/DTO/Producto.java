package DTO;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "pakete")
@XmlType(propOrder = {"productoId","nombre","fechaCaducidad", "estanteriaId", "categoriaId"})
public class Producto {

	int productoId;
	String nombre;
	String fechaCaducidad;
	int estanteriaId;
	int categoriaId;
	
	public Producto(){
		
	}
	
	public Producto(int productoId, String nombre, String fechaCaducidad, int estanteriaId, int categoriaId) {
		this.productoId = productoId;
		this.nombre = nombre;
		this.fechaCaducidad = fechaCaducidad;
		this.estanteriaId = estanteriaId;
		this.categoriaId = categoriaId;
	}

	public int getProductoId() {
		return productoId;
	}

	public void setProductoId(int productoId) {
		this.productoId = productoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public int getEstanteriaId() {
		return estanteriaId;
	}

	public void setEstanteriaId(int estanteriaId) {
		this.estanteriaId = estanteriaId;
	}

	public int getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
	}
	
	
}
