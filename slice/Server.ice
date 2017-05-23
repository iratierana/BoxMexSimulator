
module Demo {

	class ProductoIce{
		int productoId;
		string nombre;
		string fechaCaducidad;
		long estanteriaId;
		long categoriaId;
	};
	
	["java:type:java.util.ArrayList<Producto>"]
	sequence<ProductoIce> ListaProductos;
	
	class PaketeIce{
		int id;
		string estado;
		ListaProductos listaProductos;
	};
	
	interface PaketSender {
	    PaketeIce getPakete();
	};
};

