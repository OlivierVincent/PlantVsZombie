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
 * Nom : Banquier.java
 * Description : Vue
 *               Personnage allié "banquier".
 */
package ca.qc.bdeb.inf203.tp2.views.allies;

import ca.qc.bdeb.inf203.tp2.controllers.DataManager;
import ca.qc.bdeb.inf203.tp2.views.Game;
import ca.qc.bdeb.inf203.tp2.views.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Banquier extends Ally implements MouseListener {
    /**
     * Nombre de secondes avant génération de SpaceCash.
     */
    private static final int DELAY_BEFORE_SPACECASH = 30;
    
    /**
     * Nombre de SpaceCash à générer.
     */
    private static final int SPACECASH_TO_GENERATE = 25;
    
    // Variables d'instance.
    private Timer timer;
    private JLabel spaceCash;
    private final Window window;
    private final int posX;
    private final int posY;
    private final DataManager data;
    private final Banquier thisFinal;
    
    /**
     * Détruit toute la pièce.
     */
    @Override
    public void destroy() {
        timer.stop();
        timer = null;
        
        if(spaceCash instanceof JLabel) {
            spaceCash.removeMouseListener(thisFinal);
            window.removeElement(spaceCash);
            window.refresh();
        }
        
        super.destroy();
    }
    
    /**
     * Crée un allié "banquier" à la position de la grille spécifiée.
     * 
     * @param posX Position horizontale sur la grille (1-9, G-D).
     * @param posY Position verticale sur la grille (1-5, H-B).
     * @param window Fenêtre sur laquelle ajouter l'élément.
     * @param data Gestionnaire de données.
     */
    public Banquier(final int posX, final int posY, final Window window, DataManager data) {
        // On fait appel à la classe générique "Ally" (pour du polymorphisme).
        super(posX, posY, window, data, ALLY_BANQUIER);
        
        // L'un des trucs les plus sketch que j'ai jamais eu à faire...
        thisFinal = this;
        
        // Protection.
        if(super.getType() != null) {
            // Initialisation des variables d'instance.
            this.window = window;
            this.posX = posX;
            this.posY = posY;
            this.data = data;
            spaceCash = null;
            
            // Toutes les X secondes, on veut générer de l'argent.
            int delay = DELAY_BEFORE_SPACECASH * 1000;

            // Ajoutons le timer, et c'est parti !
            timer = new Timer((int) (delay * Game.TIME_MULTIPLICATOR),new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Créons le bloc de SpaceCash.
                    if(spaceCash == null) {
                        spaceCash = window.addImage("gridElements/spacecash.png",
                                                    Game.GRID_POS_X + (posX - 1) * Game.GRID_BLOCK_WIDTH,
                                                    Game.GRID_POS_Y + (posY - 1) * Game.GRID_BLOCK_HEIGHT,
                                                    Game.GRID_BLOCK_WIDTH, Game.GRID_BLOCK_HEIGHT);

                        window.refresh();
                        
                        // Affectons-y une action lors du clic.
                        spaceCash.addMouseListener(thisFinal);

                        // En attendant un clic, nous suspendons le timer.
                        timer.stop();
                    }
                }
            });
            
            timer.start();
        } else {
            // Aussi ridicule que cela puisse paraître, alors que le timer n'est même pas créé...
            timer = new Timer(0, null);
            this.window = null;
            this.posX = 0;
            this.posY = 0;
            this.data = null;
            spaceCash = null;
        }
    }
    
    /**
     * Évènement déclenché lors du clic sur du SpaceCash.
     * 
     * @param e Informations sur l'évènement "MouseEvent".
     */
    @Override 
    public void mouseClicked(MouseEvent e) {
        // On ajoute le SpaceCash et on détruit le billet affiché.
        data.addSpaceCash(SPACECASH_TO_GENERATE);
        spaceCash.removeMouseListener(this);
        window.removeElement(spaceCash);
        window.refresh();
        
        spaceCash = null;
        
        // ...Et on repart le timer !
        timer.start();
    }
    
    // Série de listeneners à vider.
    @Override public void mousePressed(MouseEvent e) { /* Rien à faire... */ }
    @Override public void mouseReleased(MouseEvent e) { /* Rien à faire... */ }
    @Override public void mouseEntered(MouseEvent e) { /* Rien à faire... */ }
    @Override public void mouseExited(MouseEvent e) { /* Rien à faire... */ }
}