
package validadorDePaketes;

import Demo.PaketeIce;
import Demo.ProductoIce;
import entitys.system.Pakete;
import entitys.system.Producto;

/**
 * The Class ConversorAPakete.
 */
public class ConversorAPakete {

	/**
	 * Instantiates a new conversor A pakete.
	 */
	public ConversorAPakete() {

	}

	/**
	 * Convertir pakete ice.
	 *
	 * @param paketeIce the pakete ice
	 * @return the pakete
	 */
	public Pakete convertirPaketeIce(final PaketeIce paketeIce) {
		Pakete pakete = new Pakete();
		pakete.setEstado(paketeIce.estado);
		pakete.setId(paketeIce.id);
		for (int i = 0; i < paketeIce.listaProductos.size(); i++) {
			Producto producto = convertirProductoIce(paketeIce.listaProductos.get(i));
			pakete.getListaProductos().add(producto);
		}
		return pakete;
	}

	/**
	 * Convertir producto ice.
	 *
	 * @param productoIce
	 *            the producto ice
	 * @return the producto
	 */
	public Producto convertirProductoIce(final ProductoIce productoIce) {
		Producto producto = new Producto();
		producto.setCategoriaId(productoIce.categoriaId);
		producto.setEstanteriaId(productoIce.estanteriaId);
		producto.setFechaCaducidad(productoIce.fechaCaducidad);
		producto.setNombre(productoIce.nombre);
		producto.setProductoId(productoIce.productoId);
		return producto;
	}

}
