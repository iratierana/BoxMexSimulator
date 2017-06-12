package generadorDePaketes;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import entitys.system.Pakete;

/**
 * The Class Main.
 */
public class Main extends Ice.Application {

	/** The Constant TAMANO_POOL. */
	public static final int TAMANO_POOL = 10;

	/** The lista paketes. */
	public static ArrayList<Pakete> listaPaketes = new ArrayList<Pakete>();

	/** The lock. */
	public final static Lock lock = new ReentrantLock();

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args) {

		int numPacketGenThreads = 2;

		ExecutorService threadPool = Executors.newFixedThreadPool(TAMANO_POOL);

		for (int i = 0; i < numPacketGenThreads; i++) {
			threadPool.submit(new PakectGeneratorThread(listaPaketes, lock));
		}

		Main app = new Main();
		int status = app.main("Server", args, "config.server");
		System.exit(status);

		threadPool.shutdown();
	}

	/**
	 * @see Ice.Application#run(java.lang.String[]).
	 * @return devuelve 0 como final de la funcion
	 * @param args los parametros que se quieran enviar. En este caso nada
	 */
	public int run(final String[] args) {

		if (args.length > 0) {
			return 1;
		}

		Ice.ObjectAdapter adapter = communicator().createObjectAdapter("Server");
		adapter.add(new Servicios(), Ice.Util.stringToIdentity("server"));
		adapter.activate();
		communicator().waitForShutdown();
		return 0;
	}

}
