package dopler.efecto.pruebas.Servicio;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import dopler.efecto.pruebas.Interfaz.CargaLista;

public class Servicio extends Service {
    private IBinder binder = new ParsingBinder();

    public Servicio() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class ParsingBinder extends Binder {
        public Servicio getServicio(){
            return Servicio.this;
        }
    }

    @Override
    public void onCreate() {
        Log.i("Servicio!!", "Servicio creado...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Servicio!!", "Servicio iniciado...");

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("Servicio!!", "Servicio destruido...");
    }

    // Estos métodos se invocan una vez se tiene el servicio en la actividad al haber usado ParsingBinder
    public String test() {
        Log.i("Mensaje servicio", "Mando msj desde el método del hilo");
        return "Estoy dentro del hilo";
    }

    public void descargarDatos(CargaLista cargar){
        CargaOps carga = new CargaOps(cargar);
        carga.execute();
    }
}
