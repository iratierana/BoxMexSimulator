package generadorDePaketes;

import java.util.ArrayList;

import Demo.PaketeIce;
import Demo.ProductoIce;
import entitys.system.Pakete;
import entitys.system.Producto;

public class Conversor {
	
	public Conversor(){
		
	}
	
	PaketeIce convertirPakete(Pakete pakete){
		PaketeIce paketeIce = new PaketeIce();
		paketeIce.listaProductos = new ArrayList<>();
		paketeIce.estado = pakete.getEstado();
		paketeIce.id = pakete.getId();
		for(int i = 0; i < pakete.getListaProductos().size(); i++){
			ProductoIce productoIce = convertirProducto(pakete.getListaProductos().get(i));
			paketeIce.listaProductos.add(productoIce);
		}
		
		return paketeIce;
	}
	
	ProductoIce convertirProducto(Producto producto){
		ProductoIce productoIce = new ProductoIce();
		productoIce.categoriaId = producto.getCategoriaId();
		productoIce.estanteriaId = producto.getEstanteriaId();
		productoIce.fechaCaducidad = producto.getFechaCaducidad();
		productoIce.nombre = producto.getNombre();
		productoIce.productoId = producto.getProductoId();
		return productoIce;
	}

}
