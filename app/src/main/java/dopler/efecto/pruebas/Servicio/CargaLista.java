package dopler.efecto.pruebas.Servicio;

import java.util.ArrayList;

import dopler.efecto.pruebas.Modelo.Opiniones;

/**
 * Created by Efecto Dopler on 05/05/2017.
 * Interfaz que se implementa en la actividad Principal.
 * Sirve de nexo entre los datos cargados por el hilo secundario
 * y la actividad para actualizar el ListView de forma dinamica
 */

public interface CargaLista {
    public void cargarLista(ArrayList<Opiniones> lista);
}
