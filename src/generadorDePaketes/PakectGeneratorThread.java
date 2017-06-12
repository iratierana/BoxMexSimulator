package generadorDePaketes;

import java.io.FileInputStream;
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
	
	/** The Constant Limite paketes */
	public static final int LIMITEPAKETES = 1000;

	/** The lis paketes. */
	ArrayList<Pakete> lisPaketes;

	/** The lock. */
	Lock lock;
	
	/** Fichero de propiedades*/
	final String FICHEROPROPIEDADES = "ipConf.properties";
	
	/** The host*/
	String host;
	
	private  String localhost = "127.0.0.1";

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
	public void run(){
		Boolean irten = false;
		while (!irten) {
			try {
				Pakete pakete = getPaketeFromServer();
				Thread.sleep(ESPERA_EN_MILISGUNDOS);
				lock.lock();
				lisPaketes.add(pakete);
				lock.unlock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(lisPaketes.size() > LIMITEPAKETES){
					irten = true;
				}
			}
		}
	}

	/**
	 * Se conecta al servidor y llama al servicio web para obtener e pakete.
	 *
	 * @return el pakete que se ha obtenido desde el servidor
	 * @throws IOException 
	 */
	private Pakete getPaketeFromServer() throws IOException {

		Pakete pakete;
		Client client;
		Conversor conversor = new Conversor();

			cargarPropiedades();
			client = Client.create();
			WebResource webResource = client
					.resource("http://"+host+":8080/BoxMexWebApp/BoxMexWebApp/packetGenerator");
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			String paketeStr = response.getEntity(String.class);
			pakete = conversor.stringJsonToObject(paketeStr);
			client.destroy();
		return pakete;
	}
	
	/**
	 * Cargar ip del archivo de configuracion.
	 * @throws IOException
	 */
	public void cargarPropiedades() throws IOException{
		Properties propiedades = new Properties();
		propiedades.load(new FileInputStream(FICHEROPROPIEDADES));
		host = propiedades.getProperty("ipAplication", localhost);
	}

	

}
