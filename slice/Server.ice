
module Demo {
	interface PaketSender {
	    void sendPakete();
	};
	
	class Producto{
		int productoId;
		string nombre;
		string fechaCaducidad;
		int estanteriaId;
		int categoriaId;
	};
	
	["java:type:java.util.ArrayList<Producto>"]
	sequence<Producto> ListaProductos;
	
	class Pakete{
		int id;
		string estado;
		ListaProductos listaProductos;
	};
};

