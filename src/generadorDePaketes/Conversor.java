
package generadorDePaketes;

import java.util.ArrayList;

import Demo.PaketeIce;
import Demo.ProductoIce;
import entitys.system.Pakete;
import entitys.system.Producto;

/**
 * The Class Conversor.
 */
public class Conversor {

	/**
	 * Instantiates a new conversor.
	 */
	public Conversor() {

	}

	/**
	 * Convertir pakete de objeto pakete normal a objeto ice para poder enviar desde el socket.
	 *
	 * @param pakete  el pakete
	 * @return el pakete ice
	 */
	public PaketeIce convertirPakete(final Pakete pakete) {
		PaketeIce paketeIce = new PaketeIce();
		paketeIce.listaProductos = new ArrayList<>();
		paketeIce.estado = pakete.getEstado();
		paketeIce.id = pakete.getId();
		for (int i = 0; i < pakete.getListaProductos().size(); i++) {
			ProductoIce productoIce = convertirProducto(pakete.getListaProductos().get(i));
			paketeIce.listaProductos.add(productoIce);
		}

		return paketeIce;
	}

	/**
	 * Convertir producto.
	 *
	 * @param producto the producto
	 * @return the producto ice
	 */
	public ProductoIce convertirProducto(final Producto producto) {
		ProductoIce productoIce = new ProductoIce();
		productoIce.categoriaId = producto.getCategoriaId();
		productoIce.estanteriaId = producto.getEstanteriaId();
		productoIce.fechaCaducidad = producto.getFechaCaducidad();
		productoIce.nombre = producto.getNombre();
		productoIce.productoId = producto.getProductoId();
		return productoIce;
	}

}
