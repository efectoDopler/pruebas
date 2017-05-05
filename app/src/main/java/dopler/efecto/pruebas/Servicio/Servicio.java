package dopler.efecto.pruebas.Servicio;

import android.app.Service;
import android.content.Intent;
import android.database.SQLException;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import dopler.efecto.pruebas.Modelo.Opiniones;

import static android.content.ContentValues.TAG;

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

    public String test() {
        Log.i("Mensaje servicio", "Mando msj desde el método del hilo");
        return "Estoy dentro del hilo";
    }

    public void descargarDatos(CargaLista cargar){
        CargaOps cargarAux = new CargaOps(cargar);
        cargarAux.execute();
    }
}
