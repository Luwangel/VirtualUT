package fr.if26.virtualut.service;

/**
 * Created by Luwangel on 16/12/13.
 * Interface pour implémenter un écouteur sur la connexion.
 */
public interface ConnexionSuccessListener {
    public void onConnexionSuccess();
    public void onConnexionFail();
    public void onConnexionTry();
}
