package validadorDePaketes;

import java.io.StringWriter;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import entitys.system.Pakete;
import generadorDePaketes.Conversor;

/**
 * The Class PaketValidatorThread.
 */
public class PaketValidatorThread {

	/** The Constant ERROR_HTTP. */
	public static final int ERROR_HTTP = 200;
	public static final int ERROR_HTTP_NO_CONTENT = 204;

	/**
	 * Meter pakete en la base de datos.
	 *
	 * @param pakete the pakete
	 */
	public void meterPaketeEnBaseDeDatos(final Pakete pakete) {
		
		Conversor conversor = new Conversor();
		MultivaluedMap<String,String> formData = new MultivaluedMapImpl();
		formData.add("paketInXML", conversor.objectToJson(pakete));
		ClientResponse response = null;
		WebResource webResource;
		
		try {
			Client client = Client.create();
			webResource = client.resource("http://172.17.16.222:8080/BoxMexWebApp/BoxMexWebApp/packetInsertor");
			response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class,formData);
			
			if (response.getStatus() == ERROR_HTTP_NO_CONTENT) {
				System.out.println("Pakete en base de datos");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean validarPakete(final Pakete pakete){
		
		MultivaluedMap<String,String> formData = new MultivaluedMapImpl();
		formData.add("paketeXml", objetoPaketeToStringXML(pakete));
		ClientResponse response = null;
		WebResource webResource;
		
		try {
			Client client = Client.create();
			webResource = client.resource("http://172.17.16.222:8080/BoxMexWebApp/BoxMexWebApp/packetValidator");
			response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class,formData);
			
			if (response.getStatus() != ERROR_HTTP) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Boolean.valueOf(response.getEntity(String.class));
	}

	/**
	 * Objeto pakete to string XML.
	 *
	 * @param pakete
	 *            the pakete
	 * @return the string
	 */
	private String objetoPaketeToStringXML(final Pakete pakete) {

		String xmlString = null;

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(Pakete.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);

			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(pakete, sw);
			xmlString = sw.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlString;
	}
}
