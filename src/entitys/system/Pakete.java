package entitys.system;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class Pakete.
 */
@XmlRootElement(name = "pakete")
@XmlType(propOrder = { "id", "estado", "listaProductos" })
public class Pakete {

	/** The id. */
	int id;

	/** The estado. */
	String estado;

	/** The lista productos. */
	List<Producto> listaProductos;

	/**
	 * Instantiates a new pakete.
	 */
	public Pakete() {
		this.listaProductos = new ArrayList<>();
	}

	/**
	 * Instantiates a new pakete.
	 *
	 * @param id the id
	 * @param listaProductos the lista productos
	 * @param estado the estado
	 */
	public Pakete(final int id, final List<Producto> listaProductos, final String estado) {
		super();
		this.id = id;
		this.listaProductos = listaProductos;
		this.estado = estado;
	}

	/**
	 * Instantiates a new pakete.
	 *
	 * @param id the id
	 * @param estado the estado
	 */
	public Pakete(final int id, final String estado) {
		super();
		this.id = id;
		this.estado = estado;
	}

	/**
	 * Gets the estado.
	 *
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Sets the estado.
	 *
	 * @param estado the new estado
	 */
	public void setEstado(final String estado) {
		this.estado = estado;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Gets the lista productos.
	 *
	 * @return the lista productos
	 */
	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	/**
	 * Sets the lista productos.
	 *
	 * @param listaProductos the new lista productos
	 */
	public void setListaProductos(final List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

}
