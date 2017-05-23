package generadorDePaketes;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import entitys.system.Pakete;
import entitys.system.Producto;

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
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			String paketeStr = response.getEntity(String.class);
			pakete = loadPakete(paketeStr);

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			client.destroy();
		}				
		return pakete;
	}

	private Pakete loadPakete(String paketeStr){
		Pakete pakete = null;
		ArrayList<Producto> listProd = new ArrayList<Producto>();
				
        try {
        	
        	StringReader reader = new StringReader(paketeStr);
    		JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			
			JSONArray arrayProductos = (JSONArray) jsonObject.get("listaProductos");
									
			for (int kont = 0; kont < arrayProductos.size(); kont++){
				 JSONObject producto = (JSONObject) arrayProductos.get(kont);
				 Producto p = new Producto(1, (String)producto.get("nombre"), (String)producto.get("fechaCaducidad"), (Long)producto.get("estanteriaId"), (Long)producto.get("categoriaId"));
				 listProd.add(p);
			}
			pakete = new Pakete(1, listProd, "entrada");
			
//			JSONObject id = (JSONObject) jsonObject.get("id");
//			JSONObject estado = (JSONObject) jsonObject.get("estado");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return pakete;		
	}

}
