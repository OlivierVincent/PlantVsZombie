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
 * Nom : Bullet.java
 * Description : Vue
 *               Balle envoyée par "Vaisseau" ou "Enterprise".
 */
package ca.qc.bdeb.inf203.tp2.views.allies;

import ca.qc.bdeb.inf203.tp2.controllers.DataManager;
import ca.qc.bdeb.inf203.tp2.views.Game;
import ca.qc.bdeb.inf203.tp2.views.Piece;
import ca.qc.bdeb.inf203.tp2.views.Window;
import ca.qc.bdeb.inf203.tp2.views.ennemies.Ennemy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Bullet implements ActionListener {
    // Variables d'instance.
    private final Ally from;
    private int posX;
    private int posY;
    private final Window window;
    private JLabel bullet;
    private Timer timer;
    private final DataManager data;
    
    /**
     * Effectue le lancement de la balle jusqu'à atteindre une cible ou un mur.
     */
    private void fireMahLazer() {
        // Créons un bloc-balle (disons...)
        bullet = window.addImage("gridElements/bullet.png", posX, posY,
                                 Game.GRID_BLOCK_WIDTH, Game.GRID_BLOCK_HEIGHT);
        
        window.refresh();
        
        // Ajoutons le timer, et c'est parti !
        timer = new Timer(30, this);
        timer.start();
    }
    
    /**
     * Pour détruire la balle.
     */
    private void destroy() {
        posX = 0;
        posY = 0;
        
        window.removeElement(bullet);
        window.refresh();
        
        timer.stop();
    }
    
    /**
     * Crée une balle qui se lance à partir du personnage spécifié.
     * 
     * @param shooter Personnage lançant la balle.
     * @param window Fenêtre sur laquelle ajouter l'élément.
     * @param data Gestionnaire de données.
     */
    public Bullet(Ally shooter, Window window, DataManager data) {
        // Définition des variables.
        this.from = shooter;
        this.posX = Game.GRID_POS_X + shooter.getPosX() * Game.GRID_BLOCK_WIDTH;
        this.posY = Game.GRID_POS_Y + (shooter.getPosY() - 1) * Game.GRID_BLOCK_HEIGHT;
        this.window = window;
        this.data = data;
        
        fireMahLazer();
    }
    
    /**
     * Crée une balle qui se lance à partir du personnage spécifié, mais à une position définie.
     * 
     * @param shooter Personnage lançant la balle.
     * @param posX Position sur la grille, en X, de la provenance du tir.
     * @param posY Position sur la grille, en Y, de la provenance du tir.
     * @param window Fenêtre sur laquelle ajouter l'élément.
     * @param data Gestionnaire de données.
     */
    public Bullet(Ally shooter, int posX, int posY, Window window, DataManager data) {
        // Définition des variables.
        this.from = shooter;
        this.posX = Game.GRID_POS_X + posX * Game.GRID_BLOCK_WIDTH;
        this.posY = Game.GRID_POS_Y + (posY - 1) * Game.GRID_BLOCK_HEIGHT;
        this.window = window;
        this.data = data;
        
        fireMahLazer();
    }
    
    /**
     * Animation d'avancement de la balle.
     * 
     * @param e Informations sur l'évènement "ActionListener".
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // On fait avancer de 20px.
        posX += 20;
        
        // On fait avancer la balle.
        bullet.setLocation(posX, posY);
        window.refresh();
        
        // On vérifie les collisions.
        for(Piece pc : (ArrayList<Piece>) Piece.getList().clone()) {
            if(posX >= pc.getPiece().getX() - 20 && posX <= pc.getPiece().getX() + 20 &&
               posY == pc.getPiece().getY() && pc.getSide() == Piece.PIECE_ENNEMY &&
               pc.getType() != null /* Pourrait arriver avant que le GC passe... */) {
                // On attaque !
                ((Ennemy) pc).attack();
                
                // ...mais on meurt ! :(
                destroy();
                
                // Petite sécurité pour être sûr qu'il n'y aura jamais deux cibles.
                break;
            }
        }
        
        if(posX > (Game.GRID_POS_X + (Game.GRID_WIDTH - 1) * Game.GRID_BLOCK_WIDTH) + 15) {
            // Si on arrive au bord de la grille, autodestruction !
            destroy();
        }
    }
}