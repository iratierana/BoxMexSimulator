package validadorDePaketes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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
	final static String FICHEROPROPIEDADES = "ipConf.properties";
	String host;
	
	/**
	 * Cargar ip del archivo de configuracion.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void cargarPropiedades() throws IOException{
		Properties propiedades = new Properties();
		propiedades.load(new FileInputStream(FICHEROPROPIEDADES));
		host = propiedades.getProperty("ipAplication");
	}
	
	/**
	 * Meter pakete en la base de datos.
	 *
	 * @param pakete the pakete
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void meterPaketeEnBaseDeDatos(final Pakete pakete) throws  IOException {
		Conversor conversor = new Conversor();
		MultivaluedMap<String,String> formData = new MultivaluedMapImpl();
		formData.add("paketInXML", conversor.objectToJson(pakete));
		ClientResponse response;
		WebResource webResource;
		cargarPropiedades();
		Client client = Client.create();
		webResource = client.resource("http://"+host+":8080/BoxMexWebApp/BoxMexWebApp/packetInsertor");
		response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class,formData);
		if (response.getStatus() == ERROR_HTTP_NO_CONTENT) {
			System.out.println("Pakete en base de datos");
		}
	}
	
	public boolean validarPakete(final Pakete pakete) throws IOException, JAXBException{
		
		MultivaluedMap<String,String> formData = new MultivaluedMapImpl();
		formData.add("paketeXml", objetoPaketeToStringXML(pakete));
		ClientResponse response;
		WebResource webResource;
		cargarPropiedades();
		Client client = Client.create();
		webResource = client.resource("http://"+host+":8080/BoxMexWebApp/BoxMexWebApp/packetValidator");
		response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class,formData);
		return Boolean.valueOf(response.getEntity(String.class));
	}

	/**
	 * Objeto pakete to string XML.
	 *
	 * @param pakete
	 *            the pakete
	 * @return the string
	 * @throws JAXBException 
	 */
	private String objetoPaketeToStringXML(final Pakete pakete) throws JAXBException {

		String xmlString;

		JAXBContext jaxbContext = JAXBContext.newInstance(Pakete.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);

		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(pakete, sw);
		xmlString = sw.toString();
		return xmlString;
	}
}
