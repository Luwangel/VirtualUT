package fr.if26.virtualut.model;

import java.util.ArrayList;

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
    private ArrayList<Transaction> transactions;

    /* Constructeur */

    /**
     * Constructeur de membre
     * @param id
     * @param nom
     * @param prenom
     * @param email
     * @param credit
     */
    public Membre(int id, String nom, String prenom, String email, double credit) {
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setEmail(email);
        this.setCredit(credit);
    }

    /**
     * Constructeur d'un membre avec prénom et nom seuls
     * @param nom
     * @param prenom
     */
    public Membre(String nom, String prenom) {
        this(-1,nom,prenom,"",-1);
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

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void addOneTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
