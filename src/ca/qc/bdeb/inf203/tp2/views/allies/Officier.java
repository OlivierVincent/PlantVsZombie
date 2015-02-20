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
 * Nom : Officier.java
 * Description : Vue
 *               Personnage allié "officier".
 */
package ca.qc.bdeb.inf203.tp2.views.allies;

import ca.qc.bdeb.inf203.tp2.controllers.DataManager;
import ca.qc.bdeb.inf203.tp2.views.Window;

public class Officier extends Ally {
    /**
     * Nombre de points de vie de l'officier.
     */
    private static final int MAX_HP_OFFICER = 30;
    
    /**
     * Crée un allié "officier" à la position de la grille spécifiée.
     * 
     * @param posX Position horizontale sur la grille (1-9, G-D).
     * @param posY Position verticale sur la grille (1-5, H-B).
     * @param window Fenêtre sur laquelle ajouter l'élément.
     * @param data Gestionnaire de données.
     */
    public Officier(int posX, int posY, Window window, DataManager data) {
        // On fait appel à la classe générique "Ally" (pour du polymorphisme).
        super(posX, posY, window, data, ALLY_OFFICIER, MAX_HP_OFFICER);
    }
}