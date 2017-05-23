package validadorDePaketes;

import java.io.StringWriter;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import entitys.system.Pakete;

public class PaketValidatorThread {

	public void meterPaketeEnBaseDeDatos(Pakete pakete){
		Client client = null;	
		
		try {
			String paketeInString = objetoPaketeToStringXML(pakete);
			client = Client.create();
			WebResource webResource = client.resource(
					"http://172.17.16.135:8080/BoxMexWebApp/BoxMexWebApp/packetInsertor?paketInXML="+paketeInString					
					);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
			
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}			

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			client.destroy();
		}	
	}
		

	public boolean validarPakete(Pakete pakete){
			
		String respuesta = null;
		Client client = null;	
		
		try {
			String paketeInString = objetoPaketeToStringXML(pakete);
			client = Client.create();
			WebResource webResource = client.resource(
					"http://localhost:8080/BoxMexWebApp/BoxMexWebApp/packetValidator?paketeXml="+paketeInString					
					);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
			
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}			
			
			respuesta = response.getEntity(String.class);
	

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			client.destroy();
		}	
		return Boolean.valueOf(respuesta);
	}
	
	private String objetoPaketeToStringXML(Pakete pakete){
		
		String xmlString = null;
		
		try{

            JAXBContext jaxbContext = JAXBContext.newInstance(Pakete.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(pakete, sw);            
            xmlString = sw.toString();

        }catch (Exception e){
            e.printStackTrace();
        }
		return xmlString;
	}
}
