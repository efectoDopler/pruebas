package dopler.efecto.pruebas.Servicio;

import android.database.SQLException;
import android.os.AsyncTask;
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

/**
 * Created by Efecto Dopler on 05/05/2017.
 * Tarea asincrona que es llamada desde HiloPruebas y carga de internet
 * los datos que se usarán para actualizar el listView de la actividad principal
 */

public class CargaOps extends AsyncTask <Void, Void, ArrayList<Opiniones>> {
    ArrayList<Opiniones> opiniones;
    CargaLista cargar;


    public CargaOps(CargaLista cargar){
        opiniones = new ArrayList<Opiniones>();
        this.cargar = cargar;
    }

    @Override
    protected ArrayList<Opiniones> doInBackground(Void... params) {
        cargarOpiniones();
        return opiniones; // Este return lleva a onPostExecute
    }

    @Override
    protected void onPostExecute(ArrayList<Opiniones> opiniones) {
        super.onPostExecute(opiniones);
        cargar.cargarLista(opiniones);
    }

    private void cargarOpiniones(){
        try {
            URL url = new URL("http://www.midominiopruebas.esy.es/pruebas.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Habilito el poder recibir JSON a la conexión
            connection.setDoOutput(true);
            // No habilito el uso de caches
            connection.setUseCaches(false);
            // Especifico que mando JSON y algunas propiedades necesarias
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0"
                    + " (Linux, Android 1.5; es-Es) Ejemplo HTTP");

            // Realizo la conexión
            connection.connect();

            int respuesta = connection.getResponseCode();

            if (respuesta == HttpURLConnection.HTTP_OK) {
                // Declaro la lectura
                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                // Creo un buffer de lectura
                BufferedReader read = new BufferedReader(in);
                // String donde guardar los resultados
                StringBuilder result = new StringBuilder();
                // String donde ir guardando linea a linea
                String linea = read.readLine();

                while (linea != null) {
                    result.append(linea);
                    linea = read.readLine();
                }

                // Creo un JSON donde almaceno lo guardado en el while
                JSONObject respuestaJSON = new JSONObject(result.toString());
                // Guardo en la variable la respuesta "estado" de la consulta
                String estadoJSON = respuestaJSON.getString("estado");
                if(estadoJSON.equals("1")){

                    JSONArray opinionesJSON = respuestaJSON.getJSONArray("objeto");
                    for(int i = 0; i < opinionesJSON.length(); i++){
                        JSONObject opinion = opinionesJSON.getJSONObject(i);
                        Opiniones aux = new Opiniones(opinion.getString("id_film"), opinion.getString("id_user"), opinion.getString("opinion"));
                        opiniones.add(aux);
                    }
                }

                // Cierro la conexion
                connection.disconnect();
            }

        } catch (MalformedURLException e) {
            Log.i("Error", "Fallo en la URL");
        } catch (IOException e) {
            Log.i("Error", "Fallo IOException");
        } catch (JSONException e) {
            Log.i("Error", "Fallo de JSON");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
