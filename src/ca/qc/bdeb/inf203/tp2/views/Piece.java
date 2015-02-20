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
 * Nom : Piece.java
 * Description : Vue
 *               Classe générique des personnages.
 */
package ca.qc.bdeb.inf203.tp2.views;

import ca.qc.bdeb.inf203.tp2.controllers.DataManager;
import ca.qc.bdeb.inf203.tp2.views.allies.Ally;
import ca.qc.bdeb.inf203.tp2.views.ennemies.Ennemy;
import java.util.ArrayList;
import javax.swing.JLabel;

public abstract class Piece {
    /**
     * Définissons une valeur booléenne à un personnage allié.
     */
    public static final boolean PIECE_ALLY = true;
    
    /**
     * Définissons une valeur booléenne à un personnage ennemi.
     */
    public static final boolean PIECE_ENNEMY = false;
    
    // Variables d'instance.
    private JLabel piece;
    private int posX;
    private int posY;
    private Window window;
    private String type;
    private boolean side;
    private DataManager data;
    private boolean renforced;
    
    // Liste de toutes les pièces instanciées.
    private static final ArrayList<Piece> pieces = new ArrayList<>();
    
    /**
     * Retourne la liste des pièces présentes sur le jeu. Mieux vaut la cloner
     * en read-only.
     * 
     * @return Liste des pièces sur le jeu.
     */
    public static ArrayList<Piece> getList() {
            return pieces;
    }
    
    /**
     * Déplacer la pièce à une position précise, si le déplacement est légal.
     * 
     * @param x Destination, en X.
     * @param y Destination, en Y.
     * 
     * @return Faux si le déplacement est illégal.
     */
    public boolean moveTo(int x, int y) {
        // Protection hardcodée basique.
        if(x > 0 && x <= Game.GRID_WIDTH && y > 0 && y <= Game.GRID_HEIGHT) {
            // Protection contre les collisions.
            for(Piece pc : pieces) {
                if(pc.getPosX() == x && pc.getPosY() == y) {
                    return false;
                }
            }
            
            // On actualise nos variables d'instance.
            posX = x;
            posY = y;
            
            // Ici, on actualise l'affichage.
            piece.setLocation(Game.GRID_POS_X + (x - 1) * Game.GRID_BLOCK_WIDTH,
                              Game.GRID_POS_Y + (y - 1) * Game.GRID_BLOCK_HEIGHT);
            
            window.refresh();
            
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Supprime la pièce du jeu.
     */
    public void destroy() {
        // On remet tout à zéro, afin que le garbage collector passe (peut-être ?)...
        posX = 0;
        posY = 0;
        type = null;
        side = false;
                
        // On supprime la pièce de la liste des pièces actives.
        pieces.remove(this);
        
        // On supprime la pièce de l'affichage et on actualise la vue.
        window.removeElement(piece);
        window.refresh();
    }
    
    /**
     * Retourne la position en X sur la grille de la pièce.
     * 
     * @return Position en X sur la grille de la pièce (1-9).
     */
    public int getPosX() {
        return posX;
    }
    
    /**
     * Retourne la position en Y sur la grille de la pièce.
     * 
     * @return Position en Y sur la grille de la pièce (1-5).
     */
    public int getPosY() {
        return posY;
    }
    
    /**
     * Retourne le type de la pièce.
     * 
     * @return Type de la pièce (comparer avec constantes, selon allégeance).
     */
    public String getType() {
        return type;
    }
    
    /**
     * Retourne l'allégeance de la pièce (allié ou ennemi).
     * 
     * @return Allégeance de la pièce (comparer avec constantes).
     */
    public boolean getSide() {
        return side;
    }
    
    /**
     * Retourne la référence à la pièce active.
     * 
     * @return Référence à la pièce active.
     */
    public JLabel getPiece() {
        return piece;
    }
    
    /**
     * Retourne la fenêtre active.
     * 
     * @return Fenêtre active.
     */
    public Window getWindow() {
        return window;
    }
    
    /**
     * Est-ce que la pièce est renforcée ?
     * 
     * @return Vrai, si la pièce est renforcée.
     */
    public boolean isRenforced() {
        return renforced;
    }
    
    /**
     * Crée une pièce de type et d'allégeance spécifiée à la position de la grille spécifiée.
     * 
     * @param posX Position horizontale sur la grille (1-9, G-D).
     * @param posY Position verticale sur la grille (1-5, H-B).
     * @param window Fenêtre sur laquelle ajouter l'élément.
     * @param data Gestionnaire de données.
     * @param type Nom du type de personnage.
     * @param side Ennemi ou allié (utiliser les constantes).
     */
    public Piece(int posX, int posY, Window window, DataManager data, String type, boolean side) {
        // Protection hardcodée basique (comprend interdiction aux tireurs de se mettre au bout).
        if(posX > 0 && posX <= Game.GRID_WIDTH && posY > 0 && posY <= Game.GRID_HEIGHT &&
           !((type.equals(Ally.ALLY_VAISSEAU) || type.equals(Ally.ALLY_ENTERPRISE)) && posX == 9)) {
            // Protection contre les collisions.
            boolean isColliding = false;
            
            for(Piece pc : pieces) {
                if(pc.getPosX() == posX && pc.getPosY() == posY) {
                    isColliding = true;
                    break;
                }
            }
            
            if(!isColliding) {
                piece = window.addImage("gridElements/" + type + ".png",
                                        Game.GRID_POS_X + (posX - 1) * Game.GRID_BLOCK_WIDTH,
                                        Game.GRID_POS_Y + (posY - 1) * Game.GRID_BLOCK_HEIGHT,
                                        Game.GRID_BLOCK_WIDTH, Game.GRID_BLOCK_HEIGHT);
                
                // Définissons nos variables d'instance.
                this.posX = posX;
                this.posY = posY;
                this.window = window;
                this.type = type;
                this.side = side;
                this.data = data;

                // On ajoute la pièce à la liste des pièces instanciées.
                pieces.add(this);

                // Rafraîchissons l'affichage !
                window.refresh();
            }
        }
    }
        
    /**
     * Crée une pièce de type et d'allégeance spécifiée à la position de la grille spécifiée.
     * Ce constructeur est appelé uniquement par les ennemis, qui n'ont pas de position de départ en X.
     * 
     * @param posY Position verticale sur la grille (1-5, H-B).
     * @param window Fenêtre sur laquelle ajouter l'élément.
     * @param data Gestionnaire de données.
     * @param type Nom du type de personnage.
     * @param side Ennemi ou allié (utiliser les constantes).
     */
    public Piece(int posY, Window window, DataManager data, String type, boolean side) {
        String image;
        if(side == PIECE_ENNEMY) {                    
            // Est-ce que notre pièce est renforcée ? Seul le hasard le sait.
            // Disons une chance sur trois d'être renforcée ?
            renforced = Math.random() < 0.3;
            
            if(renforced) {
                image = type + Ennemy.ENNEMY_RENFORCED_SUFFIX;
            } else {
                image = type;
            }
        } else {
            renforced = false;
            image = type;
        }
        
        piece = window.addImage("gridElements/" + image + ".png",
                                Game.GRID_POS_X + Game.GRID_WIDTH * Game.GRID_BLOCK_WIDTH,
                                Game.GRID_POS_Y + (posY - 1) * Game.GRID_BLOCK_HEIGHT,
                                Game.GRID_BLOCK_WIDTH, Game.GRID_BLOCK_HEIGHT);
        
        // Important pour les animations à suivre ! :)
        piece.setHorizontalAlignment(JLabel.LEFT);
        piece.setVisible(false);

        // Définissons nos variables d'instance.
        this.posY = posY;
        this.window = window;
        this.type = type;
        this.side = side;
        this.data = data;

        // On ajoute la pièce à la liste des pièces instanciées.
        pieces.add(this);

        // Rafraîchissons l'affichage !
        window.refresh();
    }
}