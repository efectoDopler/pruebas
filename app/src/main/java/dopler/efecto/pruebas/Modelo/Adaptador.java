package dopler.efecto.pruebas.Modelo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dopler.efecto.pruebas.Modelo.Opiniones;
import dopler.efecto.pruebas.R;

/**
 * Created by Efecto Dopler on 05/05/2017.
 * Adaptador personalizado para mi listView de prueba
 */

public class Adaptador extends BaseAdapter {
    Activity actividad;
    ArrayList<Opiniones> opiniones;

    public Adaptador(Activity activity, ArrayList<Opiniones> opiniones){
        this.actividad = activity;
        this.opiniones = opiniones;
    }

    public void setList(ArrayList<Opiniones> opiniones){
        this.opiniones = opiniones;
    }

    @Override
    public int getCount() {
        return opiniones.size();
    }

    @Override
    public Object getItem(int position) {
        return opiniones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(opiniones.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) actividad.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_lista, null);
        }

        TextView id = (TextView) v.findViewById(R.id.id_film);
        TextView email = (TextView) v.findViewById(R.id.id_user);
        TextView opinion = (TextView) v.findViewById(R.id.opinion);

        id.setText(opiniones.get(position).getId());
        email.setText(opiniones.get(position).getEmail());
        opinion.setText(opiniones.get(position).getOpinion());
        return v;
    }
}
