package validadorDePaketes;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import Demo.PaketSenderPrx;
import Demo.PaketSenderPrxHelper;
import Demo.PaketeIce;
import Ice.ObjectPrx;
import entitys.system.Pakete;

/**
 * The Class MainCliente.
 */
public class MainCliente {

	/** The Constant ESPERA_EN_MILISGUNDOS. */
	public static final int ESPERA_EN_MILISGUNDOS = 5000;
	
	private MainCliente(){
		
	}		

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws InterruptedException 
	 * @throws JAXBException 
	 * @throws IOException 
	 */
	public static void main(final String[] args) throws JAXBException, InterruptedException, IOException {
		int status = 0;
		java.util.List<String> extraArgs = new java.util.ArrayList<>();

		//
		// try with resource block - communicator is automatically destroyed
		// at the end of this try block
		//
		Ice.Communicator communicator = null;
		try {
			communicator = Ice.Util.initialize(args);
			if (!extraArgs.isEmpty()) {
				status = 1;
			} else {
				status = run(communicator);
			}
		} finally {
			if(communicator != null){
				communicator.destroy();
			}
		}

		System.exit(status);
	}

	/**
	 * Run.
	 *
	 * @param communicator the communicator
	 * @return the int
	 * @throws JAXBException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	private static int run(final Ice.Communicator communicator) throws JAXBException, InterruptedException, IOException {
		PaketeIce paketeIce;
		Pakete pakete;
		Boolean resultadoValidacion;
		PaketValidatorThread validador = new PaketValidatorThread();
		ConversorAPakete conversor = new ConversorAPakete();
		boolean salir = false;
		int paketes = 0;

		ObjectPrx obj = communicator.propertyToProxy("Server.Proxy").ice_twoway().ice_secure(true);
		PaketSenderPrx twoway = (PaketSenderPrx) PaketSenderPrxHelper.checkedCast(obj);

		if (twoway == null) {
			return 1;
		}

		do {
				paketeIce = twoway.getPakete();
				if (paketeIce != null) {
					pakete = conversor.convertirPaketeIce(paketeIce);
					resultadoValidacion = validador.validarPakete(pakete);
					mirarResultadoValidador(resultadoValidacion, pakete, validador);
				}
				Thread.sleep(ESPERA_EN_MILISGUNDOS);
				if(paketes == 1000){
					salir = true;
				}else{
					paketes++;
				}
		} while (!salir);

		return 0;
	}

	private static void mirarResultadoValidador(Boolean resultadoValidacion, Pakete pakete, PaketValidatorThread validador) throws IOException {
		if (resultadoValidacion) {
			validador.meterPaketeEnBaseDeDatos(pakete);
		}
		
	}

}
