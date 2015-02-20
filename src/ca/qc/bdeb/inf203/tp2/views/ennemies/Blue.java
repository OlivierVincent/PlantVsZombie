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
 * Nom : Blue.java
 * Description : Vue
 *               Personnage ennemi "blue".
 */
package ca.qc.bdeb.inf203.tp2.views.ennemies;

import ca.qc.bdeb.inf203.tp2.controllers.DataManager;
import ca.qc.bdeb.inf203.tp2.views.Window;

public class Blue extends Ennemy {
    /**
     * Crée un ennemi "blue" à la position de la grille spécifiée.
     * 
     * @param posY Position verticale sur la grille (1-5, H-B).
     * @param data Gestionnaire de données.
     * @param window Fenêtre sur laquelle ajouter l'élément.
     */
    public Blue(int posY, Window window, DataManager data) {
        // On fait appel à la classe générique "Ennemy" (pour du polymorphisme).
        super(posY, window, data, ENNEMY_BLUE);
        
        // Blue est autorisé à se déplacer latéralement.
        super.setLateralMoving(true);
    }
    
}