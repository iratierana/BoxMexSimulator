package generadorDePaketes;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import entitys.system.Pakete;
import entitys.system.Producto;

/**
 * The Class PakectGeneratorThread.
 */
public class PakectGeneratorThread implements Runnable {

	/** The Constant ESPERA_EN_MILISGUNDOS. */
	public static final int ESPERA_EN_MILISGUNDOS = 5000;

	/** The Constant ERROR_HTTP. */
	public static final int ERROR_HTTP = 200;

	/** The lis paketes. */
	ArrayList<Pakete> lisPaketes;

	/** The lock. */
	Lock lock;

	/**
	 * Instantiates a new pakect generator thread.
	 *
	 * @param lisPaketes the lis paketes
	 * @param lock the lock
	 */
	public PakectGeneratorThread(final ArrayList<Pakete> lisPaketes, final Lock lock) {
		this.lisPaketes = lisPaketes;
		this.lock = lock;
	}

	/**
	 * Run.
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Pakete pakete = getPaketeFromServer();
				Thread.sleep(ESPERA_EN_MILISGUNDOS);
				lock.lock();
				lisPaketes.add(pakete);
				System.out.println(lisPaketes.size());
				lock.unlock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Se conecta al servidor y llama al servicio web para obtener e pakete.
	 *
	 * @return el pakete que se ha obtenido desde el servidor
	 */
	private Pakete getPaketeFromServer() {

		Pakete pakete = null;
		Client client = null;

		try {

			client = Client.create();
			WebResource webResource = client
					.resource("http://172.17.16.135:8080/BoxMexWebApp/BoxMexWebApp/packetGenerator");
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != ERROR_HTTP) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			String paketeStr = response.getEntity(String.class);
			pakete = loadPakete(paketeStr);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.destroy();
		}
		return pakete;
	}

	/**
	 * Recibe el pakete en formato json y lo pasa a un objeto de java.
	 *
	 * @param paketeStr pakete en json
	 * @return the pakete
	 */
	private Pakete loadPakete(final String paketeStr) {
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

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return pakete;
	}

}
