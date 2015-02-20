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
 * Nom : Game.java
 * Description : Vue
 *               Affichage graphique du jeu (grille de jeu et menus).
 */
package ca.qc.bdeb.inf203.tp2.views;

import ca.qc.bdeb.inf203.tp2.controllers.DataManager;
import ca.qc.bdeb.inf203.tp2.controllers.GameController;
import ca.qc.bdeb.inf203.tp2.controllers.SpaceCashEvent;
import ca.qc.bdeb.inf203.tp2.views.allies.Ally;
import ca.qc.bdeb.inf203.tp2.views.allies.Banquier;
import ca.qc.bdeb.inf203.tp2.views.allies.Vaisseau;
import ca.qc.bdeb.inf203.tp2.views.allies.Enterprise;
import ca.qc.bdeb.inf203.tp2.views.allies.Officier;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Game implements MouseListener, MouseMotionListener {
    /**
     * Largeur d'une case.
     */
    public static final int GRID_BLOCK_WIDTH = 69;
    
    /**
     * Hauteur d'une case.
     */
    public static final int GRID_BLOCK_HEIGHT = 72;
    
    /**
     * Position horizontale de la grille.
     */
    public static final int GRID_POS_X = 144;
    
    /**
     * Position verticale de la grille.
     */
    public static final int GRID_POS_Y = 38;
    
    /**
     * Largeur de la grille.
     */
    public static final int GRID_WIDTH = 9;
    
    /**
     * Hauteur de la grille.
     */
    public static final int GRID_HEIGHT = 5;
    
    /**
     * Taille horizontale de la fenêtre.
     */
    public static final int WINDOW_SIZE_X = 891;
    
    /**
     * Taille verticale de la fenêtre.
     */
    public static final int WINDOW_SIZE_Y = 485;
    
    /**
     * Coefficient du passage du temps (pour accélérer/ralentir le jeu !).
     * Valeur par défaut de 1.
     */
    public static final double TIME_MULTIPLICATOR = 1;
    
    // Définition des variables de classe.
    private final Window game;
    private JLabel bgImage;
    private JLabel activeImg;
    private String activeType;
    private int activePrice;
    private final DataManager data;
    private final SpaceCashEvent spaceCashEvent;
    private final GameController controller;
    
    /**
     * Permet d'afficher les éléments fixes de la fenêtre de jeu.
     */
    private void setBasicLayout() {
        // Mettons l'image de background du jeu.
        bgImage = game.addImage("background.png", 0, 0, WINDOW_SIZE_X, WINDOW_SIZE_Y);
        
        // Ajout des listeners.
        bgImage.addMouseListener(this);
        
        // Actualisation de l'affichage.
        game.refresh();
    }
    
    /**
     * Ouvre une fenêtre de jeu.
     * 
     * @param controller Référence au contrôleur parent.
     */
    public Game(GameController controller) {
        // Définition des variables de classe.
        this.controller = controller;
        
        // Créons d'abord la fenêtre.
        game = new Window("Horizontal Space Invaders (TM)", WINDOW_SIZE_X + 6, WINDOW_SIZE_Y + 29,
                          Color.BLACK, Window.EXIT_ON_CLOSE);
        
        // Affichons enfin le layout de base.
        setBasicLayout();
        
        // Créons un gestionnaire de données du jeu.
        data = new DataManager(game, controller);
        
        // Faisons maintenant tomber du SpaceCash (25 SC) à intervalles de 30 secondes !
        spaceCashEvent = new SpaceCashEvent(25, 30, game, data);
        
        // Réglons l'argent de départ à 50 SC.
        data.addSpaceCash(50);
        
        // On se met à enregistrer les touches tapées.
        controller.registerTypedKeys(game, data);
        
        // Et ça commence ! Premier niveau !
        data.levelUp();
        
        // On affiche un message de bienvenue !
        JOptionPane.showMessageDialog(null, "Bienvenue sur votre planche de jeu de Horizontal Space Invaders !\n"
                                           + "À compter de maintenant, vous avez 30 secondes pour placer votre "
                                           + "terrain avant l'arrivée des premiers Invaders.\nSi un Invader arrive "
                                           + "complètement à gauche du terrain, l'humanité est perdue. Bonne chance !");
    }
    
    /**
     * Remet à zéro les listeners et efface la pièce de sélection.
     */
    public void refreshListeners() {
        // On supprime l'image de sélection (si elle est définie).
        if(activeImg instanceof JLabel) {
            game.removeElement(activeImg);
        }
        
        // On remet à zéro les listeners.
        for(MouseListener ml : bgImage.getMouseListeners()) {
            bgImage.removeMouseListener(ml);
        }
        
        for(MouseMotionListener mml : bgImage.getMouseMotionListeners()) {
            bgImage.removeMouseMotionListener(mml);
        }
        
        bgImage.addMouseListener(this);
        
        // On rafraîchit l'affichage.
        game.refresh();
    }

    /**
     * Évènement déclenché lors du clic sur l'image de background.
     * 
     * @param e Informations sur l'évènement "MouseEvent".
     */
    @Override 
    public void mouseClicked(MouseEvent e) {
        // Captation des clics de la souris.
        refreshListeners();
        
        if(e.getX() > 8 && e.getX() < 97 && e.getY() > 147 && e.getY() < 191) {
            // Clic sur "Banquier" (50 SC).

            activeImg = game.addImage("gridElements/" + Ally.ALLY_BANQUIER + ".png",
                                      e.getX() - Game.GRID_BLOCK_WIDTH / 2,
                                      e.getY() - Game.GRID_BLOCK_HEIGHT / 2,
                                      Game.GRID_BLOCK_WIDTH, Game.GRID_BLOCK_HEIGHT);
            
            activeType = Ally.ALLY_BANQUIER;
            activePrice = 50;
        } else if(e.getX() > 5 && e.getX() < 106 && e.getY() > 200 && e.getY() < 232) {
            // Clic sur "Vaisseau" (100 SC).
            
            activeImg = game.addImage("gridElements/" + Ally.ALLY_VAISSEAU + ".png",
                                      e.getX() - Game.GRID_BLOCK_WIDTH / 2,
                                      e.getY() - Game.GRID_BLOCK_HEIGHT / 2,
                                      Game.GRID_BLOCK_WIDTH, Game.GRID_BLOCK_HEIGHT);
            
            activeType = Ally.ALLY_VAISSEAU;
            activePrice = 100;
        } else if(e.getX() > 2 && e.getX() < 103 && e.getY() > 240 && e.getY() < 283) {
            // Clic sur "Enterprise" (150 SC).
            
            activeImg = game.addImage("gridElements/" + Ally.ALLY_ENTERPRISE + ".png",
                                      e.getX() - Game.GRID_BLOCK_WIDTH / 2,
                                      e.getY() - Game.GRID_BLOCK_HEIGHT / 2,
                                      Game.GRID_BLOCK_WIDTH, Game.GRID_BLOCK_HEIGHT); 
            
            activeType = Ally.ALLY_ENTERPRISE;
            activePrice = 150;
        } else if(e.getX() > 2 && e.getX() < 88 && e.getY() > 289 && e.getY() < 334) {
            // Clic sur "Officier" (175 SC).
            
            activeImg = game.addImage("gridElements/" + Ally.ALLY_OFFICIER + ".png",
                                      e.getX() - Game.GRID_BLOCK_WIDTH / 2,
                                      e.getY() - Game.GRID_BLOCK_HEIGHT / 2,
                                      Game.GRID_BLOCK_WIDTH, Game.GRID_BLOCK_HEIGHT);
            
            activeType = Ally.ALLY_OFFICIER;
            activePrice = 175;
        } else {
            // Permet de ne pas exécuter le reste.
            return;
        }
        
        // On lance un déplacement.
        bgImage.addMouseMotionListener(this);
        
        // Lors du deuxième clic (confirmation).
        bgImage.addMouseListener(new MouseListener() {
                                     @Override
                                     public void mouseClicked(MouseEvent e) {
                                         if(e.getX() > Game.GRID_POS_X &&
                                            e.getX() < (Game.GRID_POS_X + Game.GRID_BLOCK_WIDTH * Game.GRID_WIDTH) &&
                                            e.getY() > Game.GRID_POS_Y &&
                                            e.getY() < (Game.GRID_POS_Y + Game.GRID_BLOCK_HEIGHT * Game.GRID_HEIGHT)) {
                                             // Si le clic est dans la grille.
                                             
                                             // On calcule la position du clic dans la grille.
                                             int posX = (e.getX() - Game.GRID_POS_X) / Game.GRID_BLOCK_WIDTH + 1;
                                             int posY = (e.getY() - Game.GRID_POS_Y) / Game.GRID_BLOCK_HEIGHT + 1;
                                             
                                             if(data.removeSpaceCash(activePrice)) {
                                                 // Si on a assez d'argent.
                                                 Piece pc = null;
                                                 
                                                 switch(activeType) {
                                                     case Ally.ALLY_BANQUIER:
                                                         pc = new Banquier(posX, posY, game, data);
                                                         break;
                                                     
                                                     case Ally.ALLY_VAISSEAU:
                                                         pc = new Vaisseau(posX, posY, game, data);
                                                         break;
                                                     
                                                     case Ally.ALLY_ENTERPRISE:
                                                         pc = new Enterprise(posX, posY, game, data);
                                                         break;
                                                     
                                                     case Ally.ALLY_OFFICIER:
                                                         pc = new Officier(posX, posY, game, data);
                                                         break;
                                                 }
                                                 
                                                 // On rembourse l'argent si collision.
                                                 if(pc == null || pc.getType() == null) {
                                                     data.addSpaceCash(activePrice);
                                                 }
                                             }
                                         }
                                         
                                         refreshListeners();
                                     }

                                     @Override public void mousePressed(MouseEvent e) { /* Rien à faire... */ }
                                     @Override public void mouseReleased(MouseEvent e) { /* Rien à faire... */ }
                                     @Override public void mouseEntered(MouseEvent e) { /* Rien à faire... */ }
                                     @Override public void mouseExited(MouseEvent e) { /* Rien à faire... */ }
                                 });
        
        game.refresh();
    }
    
    /**
     * Évènement déclenché lors du positionnement d'une pièce.
     * 
     * @param e Informations sur l'évènement "MouseMotionEvent".
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        // Déplacement à la souris.
        activeImg.setLocation(e.getX() - Game.GRID_BLOCK_WIDTH / 2, e.getY() - Game.GRID_BLOCK_HEIGHT / 2);
        game.refresh();
    }
    
    // Série de listeneners à vider.
    @Override public void mousePressed(MouseEvent e) { /* Rien à faire... */ }
    @Override public void mouseReleased(MouseEvent e) { /* Rien à faire... */ }
    @Override public void mouseEntered(MouseEvent e) { /* Rien à faire... */ }
    @Override public void mouseExited(MouseEvent e) { /* Rien à faire... */ }
    @Override public void mouseDragged(MouseEvent e) { /* Rien à faire... */ }
}