package validadorDePaketes;

import Demo.PaketSenderPrx;
import Demo.PaketSenderPrxHelper;
import Demo.PaketeIce;
import Ice.ObjectPrx;
import entitys.system.Pakete;

public class MainCliente {
	
	public MainCliente(){
		
	}
	
	public static void main(String[] args){
		int status = 0;
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        //
        // try with resource block - communicator is automatically destroyed
        // at the end of this try block
        //
        Ice.Communicator communicator = null;
        try
        {
        	communicator = Ice.Util.initialize(args);
            if(!extraArgs.isEmpty())
            {
                System.err.println("too many arguments");
                status = 1;
            }
            else
            {
                status = run(communicator);
            }
        }finally{
        	communicator.destroy();
        }

        System.exit(status);
	}
	
	private static int run(Ice.Communicator communicator){
		PaketeIce paketeIce = null;
		Pakete pakete = null;
		Boolean resultadoValidacion = false;
		PaketValidatorThread validador = new PaketValidatorThread();
		ConversorAPakete conversor = new ConversorAPakete();
		
		ObjectPrx obj = communicator.propertyToProxy("Server.Proxy").ice_twoway().ice_secure(true);
		PaketSenderPrx twoway = (PaketSenderPrx) PaketSenderPrxHelper.checkedCast(obj);
		
		if(twoway == null)
        {
            System.err.println("invalid proxy");
            return 1;
        }
		
		boolean secure = false;
		boolean salir = false;
        int timeout = -1;
        int delay = 0;
        
        do{
        	try {
        		paketeIce = twoway.getPakete();
        		if(paketeIce != null){
        			System.out.println("El pakete ha llegado");
	            	pakete = conversor.convertirPaketeIce(paketeIce);
	            	resultadoValidacion = validador.validarPakete(pakete);
	            	if(resultadoValidacion){
	            		validador.meterPaketeEnBaseDeDatos(pakete);
	            	}
	        	}
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IndexOutOfBoundsException e){
				System.out.println("La lista de espera esta vacia");
			} catch (NullPointerException e){
				System.out.println("Lista vacia y se devuelve un null");
			}
        }while(!salir);
		
		return 0;
	}

}