package validadorDePaketes;

import java.io.StringWriter;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import entitys.system.Pakete;

/**
 * The Class PaketValidatorThread.
 */
public class PaketValidatorThread {

	/** The Constant ERROR_HTTP. */
	public static final int ERROR_HTTP = 200;

	/**
	 * Meter pakete en la base de datos.
	 *
	 * @param pakete the pakete
	 */
	public void meterPaketeEnBaseDeDatos(final Pakete pakete) {
		Client client = null;

		try {
			String paketeInString = objetoPaketeToStringXML(pakete);
			client = Client.create();
			WebResource webResource = client.resource(
					"http://172.17.16.135:8080/BoxMexWebApp/BoxMexWebApp/packetInsertor?paketInXML=" + paketeInString);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);

			if (response.getStatus() != ERROR_HTTP) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.destroy();
		}
	}

	/**
	 * Validar pakete.
	 *
	 * @param pakete
	 *            the pakete
	 * @return true, if successful
	 */
	public boolean validarPakete(final Pakete pakete) {

		String respuesta = null;
		Client client = null;

		try {
			String paketeInString = objetoPaketeToStringXML(pakete);
			client = Client.create();
			WebResource webResource = client.resource(
					"http://localhost:8080/BoxMexWebApp/BoxMexWebApp/packetValidator?paketeXml=" + paketeInString);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);

			if (response.getStatus() != ERROR_HTTP) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			respuesta = response.getEntity(String.class);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.destroy();
		}
		return Boolean.valueOf(respuesta);
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

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(pakete, sw);
			xmlString = sw.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlString;
	}
}
