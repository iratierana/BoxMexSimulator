package Main;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import javax.print.attribute.standard.Media;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import DTO.Pakete;

public class PakectGenerator implements Runnable{
	
	ArrayList<Pakete> lisPaketes;
	Lock lock;
	
	public PakectGenerator(ArrayList<Pakete> lisPaketes, Lock lock) {
		this.lisPaketes = lisPaketes;
		this.lock = lock;
	}

	@Override
	public void run() {
		
		
	}
	
	private Pakete getPaketeFromServer(){
		
		Pakete pakete = null;		
		Client client = null;	
		
		try {
			
			client = Client.create();
			WebResource webResource = client.resource("http://localhost:8080/BoxMexWebApp/BoxMexWebApp/robotInfo/"+robotId+"/"+tipo);
			ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
			
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}			
			
			String result = response.getEntity(String.class);

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			client.destroy();
		}				
		return pakete;
	}

}
