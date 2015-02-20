/**
 * @author Sulliman Aïad
 * @author Olivier Vincent
 * 
 * Projet par Sulliman Aïad et Olivier Vincent     V
 * Dernière mise à jour : 06/12/2013              V V
 * 
 * Dépôt Mercurial : http://hg.sullimanaiad.com/Prj420203RE-TP2
 * 
 * TRAVAIL PRATIQUE 2 - HORIZONTAL SPACE INVADERS
 * Nom : SpaceCash.java
 * Description : Vue
 *               Bloc SpaceCash cliquable qui tombe.
 */
package ca.qc.bdeb.inf203.tp2.views;

import ca.qc.bdeb.inf203.tp2.controllers.DataManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.Timer;

public class SpaceCash implements MouseListener, ActionListener {
    // Variables de classe.
    private final Timer timer;
    private int value;
    private final Window window;
    private final JLabel spaceCash;
    private final DataManager data;
    
    /**
     * Supprime l'évènement de tombée d'argent.
     */
    private void destroy() {
        // On met tout à zéro.
        value = 0;
        
        // On actualise la vue.
        window.removeElement(spaceCash);
        window.refresh();
        
        // On arrête le timer.
        timer.stop();
    }
    
    /**
     * Crée un bloc cliquable de SpaceCash.
     * 
     * @param posX Position de départ en X.
     * @param value Valeur, en SpaceCash.
     * @param window Fenêtre active.
     * @param data Gestionnaire de données.
     */
    public SpaceCash(int posX, int value, Window window, DataManager data) {
        // Stockons les valeurs.
        this.value = value;
        this.window = window;
        this.data = data;
        
        // Créons le bloc de SpaceCash.
        spaceCash = window.addImage("gridElements/spacecash.png",
                                    Game.GRID_POS_X + (posX - 1) * Game.GRID_BLOCK_WIDTH, -Game.GRID_BLOCK_HEIGHT,
                                    Game.GRID_BLOCK_WIDTH, Game.GRID_BLOCK_HEIGHT);
        
        window.refresh();
        
        // Affectons-y une action lors du clic.
        spaceCash.addMouseListener(this);
        
        // Animons-le !
        int delay = 20;
        
        // Ajoutons le timer, et c'est parti !
        timer = new Timer(delay, this);
        timer.start();
    }
    
    /**
     * Animation de la tombée du SpaceCash.
     * 
     * @param e Informations sur l'évènement "ActionListener".
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(spaceCash.getY() < Game.WINDOW_SIZE_Y) {
            // S'il reste de l'espace pour le faire descendre
            spaceCash.setLocation(spaceCash.getX(), spaceCash.getY() + 1);
            window.refresh();
        } else {
            // Sinon, on supprime simplement le SpaceCash.
            destroy();
        }
    }
    
    /**
     * Évènement déclenché lors du clic sur du SpaceCash.
     * 
     * @param e Informations sur l'évènement "MouseEvent".
     */
    @Override 
    public void mouseClicked(MouseEvent e) {
        // On ajoute le SpaceCash et on détruit le billet qui tombe.
        data.addSpaceCash(value);
        destroy();
    }
    
    // Série de listeneners à vider.
    @Override public void mousePressed(MouseEvent e) { /* Rien à faire... */ }
    @Override public void mouseReleased(MouseEvent e) { /* Rien à faire... */ }
    @Override public void mouseEntered(MouseEvent e) { /* Rien à faire... */ }
    @Override public void mouseExited(MouseEvent e) { /* Rien à faire... */ }
}