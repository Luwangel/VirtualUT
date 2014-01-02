package fr.if26.virtualut.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luwangel on 02/01/14.
 */
public class ListeMembres {

    //*** Attributs statiques ***//

    private static ListeMembres instance;

    //*** Méthodes statiques ***//

    /**
     * Pattern singleton. Une seule connexion active à la fois.
     * @return
     */
    public static ListeMembres getInstance() {
        if(ListeMembres.instance == null) {
           ListeMembres.instance = new ListeMembres();
        }
        return ListeMembres.instance;
    }

    //***   Constructeur ***//

    private ListeMembres() {
        this.listeMembres = new ArrayList<Membre>();
    }

    //*** Attributs ***//
    private List<Membre> listeMembres;

    public List<Membre> getListeMembres() {
        return this.listeMembres;
    }

    public void setListeMembres(List<Membre> listeMembres) {
        this.listeMembres = listeMembres;
    }

    public Membre getMembreFromName(String name) {

        Membre membre = null;

        for(Membre m : this.listeMembres) {
            if((m.getPrenom() + " " + m.getNom()).equals(name)) {
                membre = m;
            }
        }

        return membre;
    }
}
