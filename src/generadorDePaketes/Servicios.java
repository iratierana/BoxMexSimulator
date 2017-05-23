package generadorDePaketes;

import Demo.PaketeIce;
import Demo._PaketSenderDisp;
import Ice.Current;
import entitys.system.Pakete;

/**
 * The Class Servicios.
 */
public class Servicios extends _PaketSenderDisp {

	private static final long serialVersionUID = 1L;
	/** The pakete ice. */
	PaketeIce paketeIce = null;

	/**
	 * Instantiates a new servicios.
	 */
	public Servicios() {

	}

	/**
	 * @see Demo._PaketSenderOperations#getPakete(Ice.Current)
	 */
	@Override
	public PaketeIce getPakete(Current __current) throws IndexOutOfBoundsException {
		if (Main.listaPaketes.size() > 0) {
			Main.lock.lock();
			Pakete paketea = Main.listaPaketes.get(0);
			Main.listaPaketes.remove(0);
			Main.lock.unlock();
			Conversor conversor = new Conversor();
			paketeIce = conversor.convertirPakete(paketea);
			System.out.println("Pakete creado");
		} else {
			System.out.println("Lista vacia");
		}
		return paketeIce;
	}

}
