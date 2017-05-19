package generadorDePaketes;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import DTO.Pakete;

public class Main {

	public static void main(String[] args) {

		int numPacketGenThreads = 2;
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		ArrayList<Pakete> listaPaketes = new ArrayList<Pakete>();
		Lock lock = new ReentrantLock();
		
		for (int i = 0; i < numPacketGenThreads; i++) {
		    threadPool.submit(new PakectGeneratorThread(listaPaketes, lock));
		 }		 
		 threadPool.shutdown();
	}

}
