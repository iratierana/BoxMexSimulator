package generadorDePaketes;

import Demo.PaketeIce;
import Demo._PaketSenderDisp;
import Ice.Current;
import entitys.system.Pakete;

public class Servicios extends _PaketSenderDisp{

	PaketeIce paketeIce = null;
	
	
	public Servicios(){
		
	}
	
	@Override
	public PaketeIce getPakete(Current __current) throws IndexOutOfBoundsException{
		if(Main.listaPaketes.size() > 0 ){
			Pakete paketea = Main.listaPaketes.get(0);
			Conversor conversor = new Conversor();
			paketeIce = conversor.convertirPakete(paketea);
			System.out.println("Pakete creado");
		}else{
			System.out.println("Lista vacia");
		}
		return paketeIce;
	}







}
