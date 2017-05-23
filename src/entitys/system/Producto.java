package entitys.system;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class Producto.
 */
@XmlRootElement(name = "pakete")
@XmlType(propOrder = { "productoId", "nombre", "fechaCaducidad", "estanteriaId", "categoriaId" })
public class Producto {

	/** The producto id. */
	int productoId;

	/** The nombre. */
	String nombre;

	/** The fecha caducidad. */
	String fechaCaducidad;

	/** The estanteria id. */
	int estanteriaId;

	/** The categoria id. */
	int categoriaId;

	/**
	 * Instantiates a new producto.
	 */
	public Producto() {

	}

	/**
	 * Instantiates a new producto.
	 *
	 * @param productoId the producto id
	 * @param nombre the nombre
	 * @param fechaCaducidad the fecha caducidad
	 * @param estanteriaId the estanteria id
	 * @param categoriaId the categoria id
	 */
	public Producto(final int productoId, final String nombre, final String fechaCaducidad, final int estanteriaId, final int categoriaId) {
		this.productoId = productoId;
		this.nombre = nombre;
		this.fechaCaducidad = fechaCaducidad;
		this.estanteriaId = estanteriaId;
		this.categoriaId = categoriaId;
	}

	/**
	 * Gets the producto id.
	 *
	 * @return the producto id
	 */
	public int getProductoId() {
		return productoId;
	}

	/**
	 * Sets the producto id.
	 *
	 * @param productoId the new producto id
	 */
	public void setProductoId(final int productoId) {
		this.productoId = productoId;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the fecha caducidad.
	 *
	 * @return the fecha caducidad
	 */
	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	/**
	 * Sets the fecha caducidad.
	 *
	 * @param fechaCaducidad the new fecha caducidad
	 */
	public void setFechaCaducidad(final String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	/**
	 * Gets the estanteria id.
	 *
	 * @return the estanteria id
	 */
	public int getEstanteriaId() {
		return estanteriaId;
	}

	/**
	 * Sets the estanteria id.
	 *
	 * @param estanteriaId the new estanteria id
	 */
	public void setEstanteriaId(final int estanteriaId) {
		this.estanteriaId = estanteriaId;
	}

	/**
	 * Gets the categoria id.
	 *
	 * @return the categoria id
	 */
	public int getCategoriaId() {
		return categoriaId;
	}

	/**
	 * Sets the categoria id.
	 *
	 * @param categoriaId the new categoria id
	 */
	public void setCategoriaId(final int categoriaId) {
		this.categoriaId = categoriaId;
	}

}
