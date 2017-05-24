package validadorDePaketes;

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

	/**
	 * Instantiates a new main cliente.
	 */
	public MainCliente() {

	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args) {
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
				System.err.println("too many arguments");
				status = 1;
			} else {
				status = run(communicator);
			}
		} finally {
			communicator.destroy();
		}

		System.exit(status);
	}

	/**
	 * Run.
	 *
	 * @param communicator the communicator
	 * @return the int
	 */
	private static int run(final Ice.Communicator communicator) {
		PaketeIce paketeIce = null;
		Pakete pakete = null;
		Boolean resultadoValidacion = true;
		PaketValidatorThread validador = new PaketValidatorThread();
		ConversorAPakete conversor = new ConversorAPakete();
		boolean salir = false;

		ObjectPrx obj = communicator.propertyToProxy("Server.Proxy").ice_twoway().ice_secure(true);
		PaketSenderPrx twoway = (PaketSenderPrx) PaketSenderPrxHelper.checkedCast(obj);

		if (twoway == null) {
			System.err.println("invalid proxy");
			return 1;
		}

		do {
			try {
				paketeIce = twoway.getPakete();
				if (paketeIce != null) {
					pakete = conversor.convertirPaketeIce(paketeIce);
					resultadoValidacion = validador.validarPakete(pakete);
					if (resultadoValidacion) {
						validador.meterPaketeEnBaseDeDatos(pakete);
					}
				}
				Thread.sleep(ESPERA_EN_MILISGUNDOS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IndexOutOfBoundsException e) {
				System.out.println("La lista de espera esta vacia");
			} catch (NullPointerException e) {
				System.out.println("Lista vacia y se devuelve un null");
			}
		} while (!salir);

		return 0;
	}

}
