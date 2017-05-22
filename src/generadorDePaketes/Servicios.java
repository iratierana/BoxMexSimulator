package generadorDePaketes;

import DTO.Pakete;
import Demo.PaketeIce;
import Demo._PaketSenderDisp;
import Ice.Current;

public class Servicios extends _PaketSenderDisp{

	
	
	
	public Servicios(){
		
	}
	
	@Override
	public PaketeIce getPakete(Current __current) {
		Pakete paketea = Main.listaPaketes.get(0);
		Conversor conversor = new Conversor();
		PaketeIce paketeIce = conversor.convertirPakete(paketea);
		return paketeIce;
	}







}
