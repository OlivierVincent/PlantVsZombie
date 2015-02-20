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
 * Nom : Vaisseau.java
 * Description : Vue
 *               Personnage allié "vaisseau".
 */
package ca.qc.bdeb.inf203.tp2.views.allies;

import ca.qc.bdeb.inf203.tp2.controllers.DataManager;
import ca.qc.bdeb.inf203.tp2.views.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Vaisseau extends Ally implements ActionListener {
    /**
     * Fréquence de tir, en secondes.
     */
    private static final int SHOOTING_FREQUENCY = 1;
    
    // Variables d'instance.
    private Timer timer;
    private Window window;
    private DataManager data;
    private final Vaisseau thisFinal;
    
    /**
     * Détruit toute la pièce.
     */
    @Override
    public void destroy() {
        timer.stop();
        timer = null;
        
        super.destroy();
    }
    
    /**
     * Crée un allié "vaisseau" à la position de la grille spécifiée.
     * 
     * @param posX Position horizontale sur la grille (1-9, G-D).
     * @param posY Position verticale sur la grille (1-5, H-B).
     * @param window Fenêtre sur laquelle ajouter l'élément.
     * @param data Gestionnaire de données.
     */
    public Vaisseau(int posX, int posY, final Window window, final DataManager data) {
        // On fait appel à la classe générique "Ally" (pour du polymorphisme).
        super(posX, posY, window, data, ALLY_VAISSEAU);
        
        // L'un des trucs les plus sketch que j'ai jamais eu à faire...
        thisFinal = this;
        
        // Protection.
        if(super.getType() != null) {
            // Définition des variables d'instance.
            this.window = window;
            this.data = data;

            // Un tir toutes les X secondes.
            int delay = SHOOTING_FREQUENCY * 1000;

            // Ajoutons le timer, et c'est parti !
            timer = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Bullet bullet = new Bullet(thisFinal, window, data);
                }
            });
            
            timer.start();
        }
    }
}