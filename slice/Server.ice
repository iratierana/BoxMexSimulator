
module Demo {

	class ProductoIce{
		int productoId;
		string nombre;
		string fechaCaducidad;
		int estanteriaId;
		int categoriaId;
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

