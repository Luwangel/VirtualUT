package fr.if26.virtualut.model;

import java.util.ArrayList;

/**
 * Created by Administrateur on 22/12/13.
 */
public class Transaction {

    //*** Attributs ***//

    private Membre sender;
    private Membre receiver;
    private String date;
    private int montant;
    private String libelle;

    //*** Constructeur & initialisation **//

    public Transaction() {
        this.initialiser();
    }

    public Transaction(Membre sender, Membre receiver, String date, int montant, String libelle) {
        this.initialiser(sender, receiver, date, montant, libelle);
    }

    /**
     * Initialise la connexion (par défaut déconnectée).
     */
    private void initialiser() {
        this.setSender(null);
        this.setReceiver(null);
        this.setDate(null);
        this.setMontant(0);
        this.setLibelle("");
    }

    /**
     * Initialise la connexion (par défaut déconnectée).
     */
    private void initialiser(Membre sender, Membre receiver, String date, int montant, String libelle) {
        this.setSender(sender);
        this.setReceiver(receiver);
        this.setDate(date);
        this.setMontant(montant);
        this.setLibelle(libelle);
    }

    //*** Getters & Setters ***//

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Membre getSender() {
        return sender;
    }

    public void setSender(Membre sender) {
        this.sender = sender;
    }

    public Membre getReceiver() {
        return receiver;
    }

    public void setReceiver(Membre receiver) {
        this.receiver = receiver;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
