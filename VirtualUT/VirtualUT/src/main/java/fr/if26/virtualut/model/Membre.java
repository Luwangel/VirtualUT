package fr.if26.virtualut.model;

import fr.if26.virtualut.fragment.MenuMainFragment;

/**
 * Created by Luwangel on 02/12/13.
 */
public class Membre {

    /* Attributs */


    private int id;
    private String nom;
    private String prenom;
    private String email;
    private double credit;

    /* Constructeur */

    public Membre(int id, String nom, String prenom, String email, double credit) {
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setEmail(email);
        this.setCredit(credit);
    }

    /* Getters et Setters */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}
