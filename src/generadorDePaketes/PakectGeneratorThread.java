package generadorDePaketes;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import DTO.Pakete;

public class PakectGeneratorThread implements Runnable{
	
	ArrayList<Pakete> lisPaketes;
	Lock lock;
	
	public PakectGeneratorThread(ArrayList<Pakete> lisPaketes, Lock lock) {
		this.lisPaketes = lisPaketes;
		this.lock = lock;
	}

	@Override
	public void run() {
		while(true){			
			try {
				Pakete pakete = getPaketeFromServer();
				Thread.sleep(5000);
				lock.lock();
				lisPaketes.add(pakete);
				System.out.println(lisPaketes.size());
				lock.unlock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
	
	private Pakete getPaketeFromServer(){
		
		Pakete pakete = null;		
		Client client = null;	
		
		try {
			
			client = Client.create();
			WebResource webResource = client.resource(
					"http://localhost:8080/BoxMexWebApp/BoxMexWebApp/packetGenerator"					
					);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
			
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}			
			
			pakete = response.getEntity(Pakete.class);

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			client.destroy();
		}				
		return pakete;
	}

}
