/**
 * @author Sulliman Aïad
 * @author Olivier Vincent
 * 
 * Projet par Sulliman Aïad et Olivier Vincent     V
 * Dernière mise à jour : 01/12/2013              V V
 * 
 * Dépôt Mercurial : http://hg.sullimanaiad.com/Prj420203RE-TP2
 * 
 * TRAVAIL PRATIQUE 2 - HORIZONTAL SPACE INVADERS
 * Nom : Main.java
 * Description : Point d'entrée du programme
 *               Inclusion du système MVC et démarrage.
 */
package ca.qc.bdeb.inf203.tp2;

import ca.qc.bdeb.inf203.tp2.controllers.GameController;

public class Main {
    /**
     * Point d'entrée du programme.
     * 
     * @param args Arguments d'entrée en ligne de commande.
     */
    public static void main(String[] args) {
        GameController gameController = new GameController();
    }
}