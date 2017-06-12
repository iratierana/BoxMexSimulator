
package generadorDePaketes;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Demo.PaketeIce;
import Demo.ProductoIce;
import entitys.system.Pakete;
import entitys.system.Producto;

/**
 * The Class Conversor.
 */
public class Conversor {


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
	
	/**
	 * Recibe el pakete en formato json y lo pasa a un objeto de java.
	 *
	 * @param paketeStr pakete en json
	 * @return the pakete
	 * @throws IOException 
	 */
	public Pakete stringJsonToObject(final String paketeStr) throws IOException {
		Pakete pakete = null;
		ArrayList<Producto> listProd = new ArrayList<Producto>();

		try {

			StringReader reader = new StringReader(paketeStr);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

			JSONArray arrayProductos = (JSONArray) jsonObject.get("listaProductos");

			for (int kont = 0; kont < arrayProductos.size(); kont++) {
				JSONObject producto = (JSONObject) arrayProductos.get(kont);
				Producto p = new Producto(1, (String) producto.get("nombre"), (String) producto.get("fechaCaducidad"),
						(Long) producto.get("estanteriaId"), (Long) producto.get("categoriaId"));
				listProd.add(p);
			}
			pakete = new Pakete(1, listProd, "entrada");

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return pakete;
	}
	
	public String objectToJson(final Pakete pakete){
		String json;
		JSONObject paketea = new JSONObject();
		JSONArray productos = new JSONArray();
		JSONObject producto;
		
		paketea.put("id", pakete.getId());
		paketea.put("estado", pakete.getEstado());
		for(int i = 0; i < pakete.getListaProductos().size(); i++){
			producto = new JSONObject();
			producto.put("productoId", pakete.getListaProductos().get(i).getProductoId());
			producto.put("nombre", pakete.getListaProductos().get(i).getNombre());
			producto.put("fechaCaducidad", pakete.getListaProductos().get(i).getFechaCaducidad());
			producto.put("estanteriaId", pakete.getListaProductos().get(i).getEstanteriaId());
			producto.put("categoriaId", pakete.getListaProductos().get(i).getCategoriaId());
			productos.add(producto);
		}
		paketea.put("listaProductos", productos);
		json = paketea.toJSONString();
		return json;
	}

}
