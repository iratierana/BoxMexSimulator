package validadorDePaketes;

import Demo.PaketeIce;
import Demo.ProductoIce;
import entitys.system.Pakete;
import entitys.system.Producto;

public class ConversorAPakete {
	
	public ConversorAPakete (){
		
	}
	
	Pakete convertirPaketeIce(PaketeIce paketeIce){
		Pakete pakete = null;
		pakete.setEstado(paketeIce.estado);
		pakete.setId(paketeIce.id);
		for(int i = 0; i < paketeIce.listaProductos.size(); i++){
			Producto producto = convertirProductoIce(paketeIce.listaProductos.get(i));
		}
		return pakete;
	}
	
	Producto convertirProductoIce(ProductoIce productoIce){
		Producto producto = null;
		producto.setCategoriaId(productoIce.categoriaId);
		producto.setEstanteriaId( productoIce.estanteriaId);
		producto.setFechaCaducidad(productoIce.fechaCaducidad);
		producto.setNombre(productoIce.nombre);
		producto.setProductoId(productoIce.productoId);
		return producto;
	}

}
