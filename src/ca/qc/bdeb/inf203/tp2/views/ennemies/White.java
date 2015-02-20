/**
 * @author Sulliman Aïad
 * @author Olivier Vincent
 * 
 * Projet par Sulliman Aïad et Olivier Vincent     V
 * Dernière mise à jour : 04/12/2013              V V
 * 
 * Dépôt Mercurial : http://hg.sullimanaiad.com/Prj420203RE-TP2
 * 
 * TRAVAIL PRATIQUE 2 - HORIZONTAL SPACE INVADERS
 * Description : Vue
 *               Personnage ennemi "white".
 */
package ca.qc.bdeb.inf203.tp2.views.ennemies;

import ca.qc.bdeb.inf203.tp2.controllers.DataManager;
import ca.qc.bdeb.inf203.tp2.views.Window;

public class White extends Ennemy {
    /**
     * Crée un ennemi "white" à la position de la grille spécifiée.
     * 
     * @param posY Position verticale sur la grille (1-5, H-B).
     * @param window Fenêtre sur laquelle ajouter l'élément.
     * @param data Gestionnaire de données.
     */
    public White(int posY, Window window, DataManager data) {
        // On fait appel à la classe générique "Ennemy" (pour du polymorphisme).
        super(posY, window, data, ENNEMY_WHITE);
    }
}