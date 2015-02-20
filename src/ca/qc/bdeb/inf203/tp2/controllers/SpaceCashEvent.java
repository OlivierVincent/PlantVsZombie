/**
 * @author Sulliman Aïad
 * @author Olivier Vincent
 * 
 * Projet par Sulliman Aïad et Olivier Vincent     V
 * Dernière mise à jour : 02/12/2013              V V
 * 
 * Dépôt Mercurial : http://hg.sullimanaiad.com/Prj420203RE-TP2
 * 
 * TRAVAIL PRATIQUE 2 - HORIZONTAL SPACE INVADERS
 * Nom : SpaceCashEvent.java
 * Description : Contrôleur
 *               Gestionnaire de blocs SpaceCash qui tombent.
 */
package ca.qc.bdeb.inf203.tp2.controllers;

import ca.qc.bdeb.inf203.tp2.views.Game;
import ca.qc.bdeb.inf203.tp2.views.SpaceCash;
import ca.qc.bdeb.inf203.tp2.views.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class SpaceCashEvent {
    // Variables de classe.
    private Timer timer;
    private int value;
    private final Window window;
    private final DataManager data;
    
    /**
     * Opérations à effectuer à chaque tombée.
     */
    private void spaceCashFall() {
        // Pour obtenir une rangée aléatoire sur laquelle aura lieu la tombée.
        int posX = (int) (Math.random() * Game.GRID_WIDTH + 1);
        
        // Faisons tomber l'argent !
        SpaceCash spaceCash = new SpaceCash(posX, value, window, data);
    }
    
    /**
     * Supprime l'évènement de tombée d'argent.
     */
    public void destroy() {
        // On met tout à zéro.
        timer = null;
        value = 0;
        
        // On arrête le timer.
        timer.stop();
    }
    
    /**
     * Crée un évènement régulier de tombée de SpaceCash.
     * 
     * @param value Valeur, en SpaceCash.
     * @param delay Nombre de secondes entre chaque tombée.
     * @param window Fenêtre active.
     * @param data Gestionnaire de données.
     */
    public SpaceCashEvent(int value, int delay, Window window, DataManager data) {
        // Stockons les valeurs.
        this.value = value;
        this.window = window;
        this.data = data;
        
        // Il y a 1000ms dans 1s.
        delay *= 1000;
        
        // On fait appel à la fonction.
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                spaceCashFall();
            }
        };
        
        // Ajoutons le timer, et c'est parti !
        timer = new Timer((int) (delay * Game.TIME_MULTIPLICATOR), action);
        timer.start();
    }
}