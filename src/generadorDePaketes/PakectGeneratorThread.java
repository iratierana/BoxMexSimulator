package generadorDePaketes;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import entitys.system.Pakete;

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
					"http://172.17.16.135:8080/BoxMexWebApp/BoxMexWebApp/packetGenerator"					
					);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
			
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}			
			//System.out.println(response.getEntity(String.class));
//			pakete = response.getEntity(Pakete.class);
			pakete = paketeStringToObject(response.getEntity(String.class));

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			client.destroy();
		}				
		return pakete;
	}
	
	private Pakete paketeStringToObject(String paketXML) throws JAXBException{
		Pakete pakete = null;
		JAXBContext jaxbContext = JAXBContext.newInstance(Pakete.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        

        pakete = (Pakete) jaxbUnmarshaller.unmarshal(new StringReader(paketXML));
		return pakete;
	}

}
