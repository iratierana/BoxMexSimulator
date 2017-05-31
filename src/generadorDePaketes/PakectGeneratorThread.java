package generadorDePaketes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.locks.Lock;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import entitys.system.Pakete;

/**
 * The Class PakectGeneratorThread.
 */
public class PakectGeneratorThread implements Runnable {

	/** The Constant ESPERA_EN_MILISGUNDOS. */
	public static final int ESPERA_EN_MILISGUNDOS = 10000;

	/** The Constant ERROR_HTTP. */
	public static final int ERROR_HTTP = 200;

	/** The lis paketes. */
	ArrayList<Pakete> lisPaketes;

	/** The lock. */
	Lock lock;
	
	final String FICHEROPROPIEDADES = "ipConf.properties";
	String host;

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
		Conversor conversor = new Conversor();

		try {
			cargarPropiedades();
			client = Client.create();
			WebResource webResource = client
					.resource("http://"+host+":8080/BoxMexWebApp/BoxMexWebApp/packetGenerator");
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != ERROR_HTTP) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			String paketeStr = response.getEntity(String.class);
			pakete = conversor.stringJsonToObject(paketeStr);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.destroy();
		}
		return pakete;
	}
	
	/**
	 * Cargar ip del archivo de configuracion.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void cargarPropiedades() throws FileNotFoundException, IOException{
		Properties propiedades = new Properties();
		propiedades.load(new FileInputStream(FICHEROPROPIEDADES));
		host = propiedades.getProperty("ipAplication", "127.0.0.1");
	}

	

}
