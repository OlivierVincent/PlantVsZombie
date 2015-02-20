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
 * Nom : EnnemyMove.java
 * Description : Contrôleur
 *               Gestionnaire du mouvement des ennemis.
 */
package ca.qc.bdeb.inf203.tp2.controllers;

import ca.qc.bdeb.inf203.tp2.views.Game;
import ca.qc.bdeb.inf203.tp2.views.Piece;
import ca.qc.bdeb.inf203.tp2.views.Window;
import ca.qc.bdeb.inf203.tp2.views.allies.Ally;
import ca.qc.bdeb.inf203.tp2.views.ennemies.Ennemy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class EnnemyMove implements ActionListener {
    /**
     * Déplacement vers la gauche.
     */
    private static final int LEFT = 1;
    
    /**
     * Déplacement vers le haut.
     */
    private static final int UP = 2;
    
    /**
     * Déplacement vers le bas.
     */
    private static final int DOWN = 3;
    
    // Variables d'instance.
    private final Ennemy source;
    private final JLabel piece;
    private final Window window;
    private final DataManager data;
    private int time;
    private ActionEvent e;
    
    /**
     * Permet un déplacement dans une direction donnée.
     * 
     * @param direction Sens du déplacement (utiliser constantes).
     */
    private void moveTo(int direction) {
        // On avance normalement !
        boolean moving = true;

        for(Piece pc : (ArrayList<Piece>) Piece.getList().clone()) {
            boolean conditions = false; // Sera écrasée.
            
            switch(direction) {
                case LEFT:
                    conditions = piece.getX() <= pc.getPiece().getX() + Game.GRID_BLOCK_WIDTH &&
                                 piece.getX() >= pc.getPiece().getX() &&
                                 pc.getPiece().getY() == piece.getY();
                    break;
                
                case UP:
                    conditions = piece.getX() < pc.getPiece().getX() + Game.GRID_BLOCK_WIDTH &&
                                 piece.getX() > pc.getPiece().getX() &&
                                 pc.getPiece().getY() == piece.getY() - Game.GRID_BLOCK_HEIGHT;
                    break;
                
                case DOWN:
                    conditions = piece.getX() < pc.getPiece().getX() + Game.GRID_BLOCK_WIDTH &&
                                 piece.getX() > pc.getPiece().getX() &&
                                 pc.getPiece().getY() == piece.getY() + Game.GRID_BLOCK_HEIGHT;
                    break;
            }
            
            if(conditions && pc.getSide() == Piece.PIECE_ALLY) {
                moving = false;
                
                // Sera vrai à chaque seconde.
                boolean eachSecond = (time * ((Timer) e.getSource()).getDelay()) % 1000 == 0;
                
                if(direction == LEFT && eachSecond) {
                    // On attaque la pièce. Une attaque par seconde * 10HP = 10 secondes de vie.
                    ((Ally) pc).attack();
                }

                // Sécurité pour pas que l'on attaque deux pièces.
                break;
            }
        }

        if(moving) {
            switch(direction) {
                case LEFT:
                    piece.setLocation(piece.getX() - 1, piece.getY());
                    break;
                
                case UP:
                    piece.setLocation(piece.getX(), piece.getY() - Game.GRID_BLOCK_HEIGHT);
                    break;
                
                case DOWN:
                    piece.setLocation(piece.getX(), piece.getY() + Game.GRID_BLOCK_HEIGHT);
                    break;
            }
        }
    }
    
    /**
     * Déplacement de l'ennemi.
     * 
     * @param e Informations sur l'évènement "ActionListener".
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        time++;
        this.e = e;
        
        piece.setVisible(true);
        
        if(piece.getX() > Game.GRID_POS_X) {
            if(piece.getX() > Game.GRID_POS_X + (Game.GRID_WIDTH - 1) * Game.GRID_BLOCK_WIDTH) {
                // Belle animation d'entrée. Les mathématiques sont magiques.
                piece.setSize(Game.GRID_POS_X + Game.GRID_WIDTH * Game.GRID_BLOCK_WIDTH - piece.getX() + 1,
                              Game.GRID_BLOCK_HEIGHT);
            }
            
            // Si l'ennemi peut se déplacer latéralement.
            if(source.isLateralMoving() && Math.random() < 0.01) {
                // Nous avons 1% de chance de lancer un déplacement latéral (calculé à chaque pixel).
                if(Math.random() < 0.5) {
                    if(piece.getY() - Game.GRID_BLOCK_HEIGHT >= Game.GRID_POS_Y) {
                        moveTo(UP);
                    } else if(piece.getY() + Game.GRID_BLOCK_HEIGHT <= Game.GRID_POS_Y +  (Game.GRID_HEIGHT - 1) * Game.GRID_BLOCK_HEIGHT) {
                        moveTo(DOWN);
                    } else {
                        // Si jamais nous avons une collision avec un mur, nous avançons (crée un effet
                        // "collé au mur") statistiquement, que nous expliquons par la connerie de Blue.
                        moveTo(LEFT);
                    }
                } else {
                    if(piece.getY() + Game.GRID_BLOCK_HEIGHT <= Game.GRID_POS_Y +  (Game.GRID_HEIGHT - 1) * Game.GRID_BLOCK_HEIGHT) {
                        moveTo(DOWN);
                    } else if(piece.getY() - Game.GRID_BLOCK_HEIGHT >= Game.GRID_POS_Y) {
                        moveTo(UP);
                    } else {
                        // Si jamais nous avons une collision avec un mur, nous avançons (crée un effet
                        // "collé au mur") statistiquement, que nous expliquons par la connerie de Blue.
                        moveTo(LEFT);
                    }
                }
            } else {
                // On se déplace à gauche !
                moveTo(LEFT);
            }
        } else if(source.getType() != null /* On pourrait avoir des pièces non-collectées par le GC. */) {
            // Une pièce touche la bordure gauche. Fin du jeu.
            source.destroy();
            
            // On détruit le timer.
            ((Timer) e.getSource()).stop();
            
            // FIN DU JEU ICI, LE MONDE EST PERDU !
            for(Piece pc : (ArrayList<Piece>) Piece.getList().clone()) {
                // On détruit toutes les pièces du jeu.
                // Devrait utiliser toutes les méthodes destroy accessibles.
                pc.destroy();
            }
            
            // On ferme la fenêtre.
            window.close();
            
            // On prépare les statistiques.
            int finalLevel = data.getModel().getLevel();
            int finalKilled = data.getModel().getKilled();
            
            // On demande si l'utilisateur veut rejouer.
            int response = JOptionPane.showConfirmDialog(null, "GAME OVER ! Voulez-vous refaire une partie ?\n"
                                                             + "(Vague #"+ finalLevel +", "
                                                             +   finalKilled +" tués, bravo !)");
            
            if(response == JOptionPane.YES_OPTION) {
                // On démarre une nouvelle partie.
                GameController gameController = new GameController();
            } else {
                // On quitte le jeu.
                System.exit(0);
            }
        }
        
        window.refresh();
    }
    
    /**
     * Gère le déplacement de l'ennemi.
     * 
     * @param source Référence à l'ennemi.
     * @param data Gestionnaire de données.
     */
    public EnnemyMove(Ennemy source, DataManager data) {
        piece = source.getPiece();
        window = source.getWindow();
        this.data = data;
        time = 0;
        
        this.source = source;
    }
}