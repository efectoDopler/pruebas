package dopler.efecto.pruebas.Modelo;

/**
 * Created by Efecto Dopler on 05/05/2017.
 */

public class Opiniones {
    String id;
    String email;
    String opinion;

    public Opiniones(String id, String email, String opinion){
        this.id = id;
        this.email = email;
        this.opinion = opinion;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getOpinion() {
        return opinion;
    }
}
