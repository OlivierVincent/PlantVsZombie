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
 * Nom : GameController.java
 * Description : Contrôleur
 *               Permet d'afficher et de gérer la grille de jeu ainsi que les menus.
 */
package ca.qc.bdeb.inf203.tp2.controllers;

import ca.qc.bdeb.inf203.tp2.views.Game;
import ca.qc.bdeb.inf203.tp2.views.Window;
import ca.qc.bdeb.inf203.tp2.views.ennemies.Ennemy;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController extends EnnemyGenerator implements KeyListener {
    // Définition des variables de classe.
    private final Game game;
    private DataManager data;
    private String cheatSequence;
    
    /**
     * Génère une nouvelle vague d'ennemis, en gérant l'aléatoire du positionnement
     * et le temps d'apparition.
     * 
     * @param window Fenêtre active.
     * @param data Gestionnaire de données.
     */
    public void generateEnnemies(Window window, DataManager data) {
        for(int i = 0; i < data.getModel().getNbEnnemiesOnLevel(); i++){
            // On fait de l'aléatoire !
            double token = Math.random();
            
            if(token < 0.1) {
                // 10% de chances de tomber sur Pink.
                generateEnnemy(Ennemy.ENNEMY_PINK, window, data);
            } else if(token < 0.4) {
                // 30% de chances de tomber sur Blue.
                generateEnnemy(Ennemy.ENNEMY_BLUE, window, data);
            } else {
                // 60% de chances de tomber sur White.
                generateEnnemy(Ennemy.ENNEMY_WHITE, window, data);
            }
        }
    }
    
    /**
     * Enregistre les X dernières touches tapées dans la fenêtre donnée.
     * 
     * @param window Fenêtre active.
     * @param data Gestionnaire de données.
     */
    public void registerTypedKeys(Window window, DataManager data) {
        this.data = data;
        window.getJFrame().addKeyListener(this);
    }
    
    /**
     * Permet de gérer une grille de jeu et les différents menus.
     */
    public GameController() {
        // Affiche la planche de jeu.
        game = new Game(this);
    }
    
    /**
     * Évènement déclenché lors de l'appui complété d'une touche.
     * 
     * @param e Informations sur l'évènement "KeyListener".
     */
    @Override
    public void keyTyped(KeyEvent e) {
        cheatSequence = cheatSequence + e.getKeyChar();
        
        if(cheatSequence.contains("galarneau")) {
            data.addSpaceCash(100);
            System.out.println("Bonus activé ! Spacecash : +100");
            
            cheatSequence = null; 
        } else if(cheatSequence.contains("Monsanté")) {
            data.setDifficulty(50);
            System.out.println("Bonus activé ! Difficulté des vagues : =50%");
            
            cheatSequence = null;
        }
    }
    
    // Série de listeneners à vider.
    @Override public void keyPressed(KeyEvent ke) {/* Rien à faire...*/}
    @Override public void keyReleased(KeyEvent ke) { /* Rien à faire...*/ }
}