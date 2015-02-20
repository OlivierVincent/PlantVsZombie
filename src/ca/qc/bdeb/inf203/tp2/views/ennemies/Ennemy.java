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
 * Nom : Ennemy.java
 * Description : Vue
 *               Personnage ennemi.
 */
package ca.qc.bdeb.inf203.tp2.views.ennemies;

import ca.qc.bdeb.inf203.tp2.controllers.DataManager;
import ca.qc.bdeb.inf203.tp2.controllers.EnnemyMove;
import ca.qc.bdeb.inf203.tp2.views.Piece;
import ca.qc.bdeb.inf203.tp2.views.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public abstract class Ennemy extends Piece implements ActionListener {
    /**
     * Nom de l'image de l'ennemi "white".
     */
    public static final String ENNEMY_WHITE = "white";
    
    /**
     * Nom de l'image de l'ennemi "blue".
     */
    public static final String ENNEMY_BLUE = "blue";
    
    /**
     * Nom de l'image de l'ennemi "pink".
     */
    public static final String ENNEMY_PINK = "pink";
    
    /**
     * Suffixe des images d'ennemis renforcés.
     */
    public static final String ENNEMY_RENFORCED_SUFFIX = "_vlc";
    
    /**
     * Maximum de points de vie d'un ennemi.
     */
    private static final int MAX_HP = 10;
    
    // Variables d'instance.
    private int hp;
    private Timer animation;
    private final Window window;
    private boolean visible;
    private int animationCounter;
    private final boolean renforced;
    private Timer move;
    private boolean lateralMoving;
    private final DataManager data;
    
    /**
     * Méthode appelée lorsque l'ennemi est attaqué,
     * réduit la vie de 1 et fait une animation.
     */
    public void attack() {
        // Animation de dégâts.
        animation.start();
        
        // Réduction de la vie.
        hp--;
                
        if(hp < 1) {
            // Mort.
            data.killCountUp();
            
            if(data.getModel().getKilledInThisLevel() == data.getModel().getNbEnnemiesOnLevel()) {
                // Si nous avons tué tous les ennemis de la vague courante, nous
                // passons au niveau suivant. Des ennemis seront générés.
                data.levelUp();
            }
            
            destroy();
        }
    }
    
    /**
     * Retourne si l'ennemi est autorisé à se déplacer latéralement.
     * 
     * @return Vrai, si l'ennemi est autorisé à se déplacer latéralement.
     */
    public boolean isLateralMoving() {
        return lateralMoving;
    }

    /**
     * Règle l'autorisation de l'ennemi à se déplacer latéralement.
     * 
     * @param lateralMoving Valeur de l'autorisation (vrai ou faux).
     */
    public void setLateralMoving(boolean lateralMoving) {
        this.lateralMoving = lateralMoving;
    }
    
    /**
     * Règle le facteur de vitesse de l'ennemi.
     * 
     * @param speedFactor Facteur de vitesse.
     */
    public void setSpeedFactor(double speedFactor) {
        move.setDelay((int) Math.round((1 / speedFactor) * 150));
    }
    
    /**
     * Détruit toute la pièce.
     */
    @Override
    public void destroy() {
        move.stop();
        move = null;
        animation.stop();
        animation = null;
        
        super.destroy();
    }
    
    /**
     * Crée un ennemi de type spécifié à la position de la grille spécifiée.
     * 
     * @param posY Position verticale sur la grille (1-5, H-B).
     * @param window Fenêtre sur laquelle ajouter l'élément.
     * @param data Gestionnaire de données.
     * @param type Nom du type d'ennemi (utiliser constantes).
     */
    public Ennemy(int posY, Window window, DataManager data, String type) {
        // On fait appel à la classe très générique "Piece" (pour du polymorphisme).
        super(posY, window, data, type, PIECE_ENNEMY);
        
        // On définit les variables d'instance.
        this.window = window;
        visible = true;
        animationCounter = 0;
        renforced = super.isRenforced();
        lateralMoving = false;
        animation = new Timer(150, this);
        this.data = data;
        
        if(renforced) {
            hp = 2 * MAX_HP;
        } else {
            hp = MAX_HP;
        }
        
        // Lançons le déplacement.
        int delay = 150;
        
        // Ajoutons le timer, et c'est parti !
        move = new Timer(delay, new EnnemyMove(this, data));
        move.start();
    }
    
    /**
     * Animation de dégâts.
     * 
     * @param e Informations sur l'évènement "ActionListener".
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Répétons l'action six fois.
        if(animationCounter < 6) {
            visible = !visible;
            
            this.getPiece().setVisible(visible);
            window.refresh();
            
            animationCounter++;
        } else {
            animation.stop();
            animationCounter = 0;
        }
    }
}