package generadorDePaketes;

import DTO.Pakete;
import DTO.Producto;
import Demo.PaketeIce;
import Demo.ProductoIce;

public class Conversor {
	
	public Conversor(){
		
	}
	
	PaketeIce convertirPakete(Pakete pakete){
		PaketeIce paketeIce = null;
		paketeIce.estado = pakete.getEstado();
		paketeIce.id = pakete.getId();
		for(int i = 0; i < pakete.getListaProductos().size(); i++){
			ProductoIce productoIce = convertirProducto(pakete.getListaProductos().get(i));
			paketeIce.listaProductos.add(productoIce);
		}
		
		return paketeIce;
	}
	
	ProductoIce convertirProducto(Producto producto){
		ProductoIce productoIce = null;
		productoIce.categoriaId = producto.getCategoriaId();
		productoIce.estanteriaId = producto.getEstanteriaId();
		productoIce.fechaCaducidad = producto.getFechaCaducidad();
		productoIce.nombre = producto.getNombre();
		productoIce.productoId = producto.getProductoId();
		return productoIce;
	}

}
