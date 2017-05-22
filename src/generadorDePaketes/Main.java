package generadorDePaketes;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import DTO.Pakete;

public class Main extends Ice.Application{
	
	public static ArrayList<Pakete> listaPaketes = new ArrayList<Pakete>();

	public static void main(String[] args) {

		int numPacketGenThreads = 2;
		
		Main app = new Main();
        int status = app.main("Server", args, "config.server");
        System.exit(status);
		
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		Lock lock = new ReentrantLock();
		
		for (int i = 0; i < numPacketGenThreads; i++) {
		    threadPool.submit(new PakectGeneratorThread(listaPaketes, lock));
		 }		 
		 threadPool.shutdown();
	}

	@Override
	public int run(String[] args) {
		
		if(args.length > 0)
        {
            System.err.println(appName() + ": too many arguments");
            return 1;
        }

        Ice.ObjectAdapter adapter = communicator().createObjectAdapter("Server");
        adapter.add(new Servicios(), Ice.Util.stringToIdentity("server"));
        adapter.activate();
        communicator().waitForShutdown();
        return 0;
	}

}
