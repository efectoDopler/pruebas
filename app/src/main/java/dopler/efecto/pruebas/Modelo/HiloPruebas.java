package dopler.efecto.pruebas.Modelo;

import android.util.Log;

import dopler.efecto.pruebas.Servicio.CargaLista;
import dopler.efecto.pruebas.Servicio.CargaOps;
import dopler.efecto.pruebas.Servicio.Servicio;

/**
 * Created by Efecto Dopler on 05/05/2017.
 * Hilo que se encarga de llevar en segundo plano el refresco de los datos de la vista
 * siempre que el servicio del que depende esté activo o la actividad no este detenida
 */

public class HiloPruebas {
    CargaLista cargar;

    private boolean bucle = false;
    private boolean seguir = true;
    private Servicio servicio = new Servicio();

    public HiloPruebas(CargaLista aux){
        cargar = aux;
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
                            servicio.descargarDatos(cargar);
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
