/**
 * @author Sulliman Aïad
 * @author Olivier Vincent
 * 
 * Projet par Sulliman Aïad et Olivier Vincent     V
 * Dernière mise à jour : 05/12/2013              V V
 * 
 * Dépôt Mercurial : http://hg.sullimanaiad.com/Prj420203RE-TP2
 * 
 * TRAVAIL PRATIQUE 2 - HORIZONTAL SPACE INVADERS
 * Nom : Ally.java
 * Description : Vue
 *               Personnage allié.
 */
package ca.qc.bdeb.inf203.tp2.views.allies;

import ca.qc.bdeb.inf203.tp2.controllers.DataManager;
import ca.qc.bdeb.inf203.tp2.views.Piece;
import ca.qc.bdeb.inf203.tp2.views.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public abstract class Ally extends Piece implements ActionListener {
    /**
     * Nom de l'image de l'allié "banquier".
     */
    public static final String ALLY_BANQUIER = "banquier";
    
    /**
     * Nom de l'image de l'allié "vaisseau".
     */
    public static final String ALLY_VAISSEAU = "vaisseau";
    
    /**
     * Nom de l'image de l'allié "enterprise".
     */
    public static final String ALLY_ENTERPRISE = "enterprise";
    
    /**
     * Nom de l'image de l'allié "officier".
     */
    public static final String ALLY_OFFICIER = "officier";
    
    /**
     * Maximum de points de vie d'un allié.
     */
    private static final int MAX_HP = 10;
    
    // Variables d'instance.
    private int hp;
    private Timer animation;
    private final int posX;
    private final int posY;
    private final Window window;
    private final DataManager data;
    private int animationCounter;
    private boolean visible;
    
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
            switch(super.getType()) {
                case Ally.ALLY_BANQUIER:
                    ((Banquier) this).destroy();
                    break;
                
                case Ally.ALLY_ENTERPRISE:
                    ((Enterprise) this).destroy();
                    break;
                
                case Ally.ALLY_OFFICIER:
                    ((Officier) this).destroy();
                    break;
                
                case Ally.ALLY_VAISSEAU:
                    ((Vaisseau) this).destroy();
                    break;
            }
        }
    }
    
    /**
     * Détruit toute la pièce.
     */
    @Override
    public void destroy() {
        animation.stop();
        animation = null;
        
        super.destroy();
    }
    
    /**
     * Crée un ennemi de type spécifié à la position de la grille spécifiée.
     * 
     * @param posX Position horizontale sur la grille (1-9, G-D).
     * @param posY Position verticale sur la grille (1-5, H-B).
     * @param window Fenêtre sur laquelle ajouter l'élément.
     * @param data Gestionnaire de données.
     * @param type Nom du type d'ennemi (utiliser constantes).
     */
    public Ally(int posX, int posY, Window window, DataManager data, String type) {
        // On fait appel à la classe très générique "Piece" (pour du polymorphisme).
        super(posX, posY, window, data, type, PIECE_ALLY);
        
        // Définition des variables d'instance.
        this.posX = posX;
        this.posY = posY;
        this.window = window;
        this.data = data;
        animationCounter = 0;
        visible = true;
        
        // Ajout du timer animation.
        animation = new Timer(150, this);
        
        // Définition des points de vie.
        hp = MAX_HP;
    }
    
    /**
     * Crée un ennemi de type spécifié à la position de la grille spécifiée, avec un nombre
     * défini de points de vie.
     * 
     * @param posX Position horizontale sur la grille (1-9, G-D).
     * @param posY Position verticale sur la grille (1-5, H-B).
     * @param window Fenêtre sur laquelle ajouter l'élément.
     * @param data Gestionnaire de données.
     * @param type Nom du type d'ennemi (utiliser constantes).
     * @param maxHP Nombre de points de vie.
     */
    public Ally(int posX, int posY, Window window, DataManager data, String type, int maxHP) {
        // On fait appel à la classe très générique "Piece" (pour du polymorphisme).
        super(posX, posY, window, data, type, PIECE_ALLY);
        
        // Définition des variables d'instance.
        this.posX = posX;
        this.posY = posY;
        this.window = window;
        this.data = data;
        animationCounter = 0;
        visible = true;
        
        // Ajout du timer animation.
        animation = new Timer(150, this);
        
        // Définition des points de vie.
        hp = MAX_HP;
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