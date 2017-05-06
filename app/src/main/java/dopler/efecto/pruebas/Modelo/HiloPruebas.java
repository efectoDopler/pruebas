package dopler.efecto.pruebas.Modelo;

import android.util.Log;

import dopler.efecto.pruebas.Interfaz.CargaLista;
import dopler.efecto.pruebas.Servicio.Servicio;

/**
 * Created by Efecto Dopler on 05/05/2017.
 * Hilo que se encarga de llevar en segundo plano el refresco de los datos de la vista
 * invocando al método del servicio encargado de ello siempre que la actividad no esté parada
 */

public class HiloPruebas {
    // Interfaz que se le debe pasar al servicio para que pueda hacer su trabajo
    CargaLista ICargarLista;
    private Servicio servicio = new Servicio();

    private boolean bucle = false;
    private boolean seguir = true;


    public HiloPruebas(CargaLista aux){
        ICargarLista = aux;
    }

    public void iniciarHilo(boolean bool1, boolean bool2, Servicio s){
        bucle = bool1;
        seguir = bool2;
        servicio = s;

        new Thread(new Runnable() {
            public void run() {
                while(bucle){
                    try {
                        Thread.sleep(10000);
                        if(seguir) {
                            Log.i("Mensaje de bucle hilo", servicio.test());
                            servicio.descargarDatos(ICargarLista);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void pararHilo(boolean mandar){
        seguir = mandar;
        Log.i("Parada del hilo", "Parada del hilo que carga datos");
    }

    public void reiniciarHilo(boolean mandar){
        seguir = mandar;
        Log.i("Reinicio del hilo", "Reinicio del hilo que carga datos");
    }
}
