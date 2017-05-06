package dopler.efecto.pruebas.Actividad;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import dopler.efecto.pruebas.Modelo.Adaptador;
import dopler.efecto.pruebas.Modelo.HiloPruebas;
import dopler.efecto.pruebas.Modelo.Opiniones;
import dopler.efecto.pruebas.R;
import dopler.efecto.pruebas.Interfaz.CargaLista;
import dopler.efecto.pruebas.Servicio.Servicio;

public class Principal extends AppCompatActivity implements CargaLista {
    private ListView lista;
    private ProgressBar barra;
    private Adaptador adaptador;

    private ArrayList<Opiniones> opiniones;
    private Intent intentServicio;
    private Servicio servicio;
    private boolean mandar = false;
    private boolean ok = true;
    private HiloPruebas hilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        lista = (ListView) findViewById(R.id.lista);
        barra = (ProgressBar) findViewById(R.id.progreso);
        // Inicio el adaptador
        opiniones = new ArrayList<Opiniones>();
        adaptador = new Adaptador(this, opiniones);
        lista.setAdapter(adaptador);

        // Inicio la clase que tiene el hilo de descarga metiendole el interfaz que carga listas
        hilo = new HiloPruebas(this);

        // Inicio el servicio
        intentServicio = new Intent(getApplicationContext(), Servicio.class);
        getApplicationContext().startService(intentServicio);
        bindService(intentServicio, servCon, Context.BIND_AUTO_CREATE);
    }

    // Al pausarse la actividad se para el servicio
    protected void onPause(){
        super.onPause();

        // Paro el bucle del hilo
        mandar = false;
        hilo.pararHilo(mandar);

        // Paro el servicio
        getApplicationContext().stopService(intentServicio);
    }

    // Al volver a la actividad se vuelve a activar el servicio
    protected void onRestart(){
        super.onRestart();
        Log.i("Reinicio actividad", "Se ha finalizado la aplicacion");
        // Inicia de nuevo el servicio
        getApplicationContext().startService(intentServicio);
        bindService(intentServicio, servCon, Context.BIND_AUTO_CREATE);


        // Reinicio el bucle del hilo
        mandar = true;
        hilo.reiniciarHilo(mandar);
    }

    // Cuando se destruye la actividad se elimina el componente del servicio
    protected void onDestroy(){
        super.onDestroy();
        servicio = null;
        Log.i("Final actividad", "Se ha finalizado la aplicaciooon");
    }

    // Este objeto es el que se usa al hacer bindService
    private ServiceConnection servCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // Parseo el servicio
            servicio = ((Servicio.ParsingBinder) service).getServicio();
            Log.i("Inicio servicio", "He iniciado el servicio");
            mandar = true;

            // Inicio el hilo encargado de la descarga
            hilo.iniciarHilo(ok,mandar,servicio);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            servicio = null;
            mandar = false;
            Log.i("Servicio desconectado", "Parada de servicio");
        }
    };

    // Interfaz que es llamada desde el hilo secundario cuando se ha cargado una lista de internet
    @Override
    public void cargarLista(ArrayList<Opiniones> lista) {
        if(lista.size() != opiniones.size()){
            barra.setVisibility(View.INVISIBLE);
            opiniones = lista;
            adaptador.setList(opiniones);
            adaptador.notifyDataSetChanged();
        }

    }
}
