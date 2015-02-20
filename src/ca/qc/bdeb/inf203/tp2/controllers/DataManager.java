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
 * Nom : DataManager.java
 * Description : Contrôleur
 *               Gère les informations sur la partie.
 */
package ca.qc.bdeb.inf203.tp2.controllers;

import ca.qc.bdeb.inf203.tp2.models.GameData;
import ca.qc.bdeb.inf203.tp2.views.Window;
import java.awt.Color;
import javax.swing.JLabel;

public class DataManager {
    // Variables de classe.
    private final GameData data;
    private final Window window;
    private final JLabel spaceCash;
    private final JLabel killed;
    private final JLabel level;
    private final GameController game;
    
    /**
     * Ajouter de l'argent à l'utilisateur.
     * 
     * @param value Argent à ajouter.
     */
    public void addSpaceCash(int value) {
        data.setSpaceCash(data.getSpaceCash() + value);
        
        // Actualisation de l'affichage.
        spaceCash.setText(Integer.toString(data.getSpaceCash()));
        window.refresh();
    }
    
    /**
     * Enlever de l'argent à l'utilisateur.
     * 
     * @param value Argent à enlever.
     * 
     * @return Retourne "false" si les fonds sont insuffisants.
     */
    public boolean removeSpaceCash(int value) {
        if(data.getSpaceCash() >= value) {
            data.setSpaceCash(data.getSpaceCash() - value);

            // Actualisation de l'affichage.
            spaceCash.setText(Integer.toString(data.getSpaceCash()));
            window.refresh();

            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Augmenter le niveau du jeu (vague).
     */
    public void levelUp() {
        // On passe au niveau suivant.
        data.setLevel(data.getLevel() + 1);
        data.setKilledInThisLevel(0);
        
        // On génère la vague de nouveaux ennemis.
        game.generateEnnemies(window, this);
        
        // Actualisation de l'affichage.
        level.setText(Integer.toString(data.getLevel()));
        window.refresh();
    }
    
    /**
     * Augmenter le compteur de tués.
     */
    public void killCountUp() {
        data.setKilled(data.getKilled() + 1);
        data.setKilledInThisLevel(data.getKilledInThisLevel() + 1);
        
        // Actualisation de l'affichage.
        killed.setText(Integer.toString(data.getKilled()));
        window.refresh();
    }
    
    /**
     * Réglage du degré de difficulté du jeu. Par défaut, 20%.
     * 
     * @param difficultyPercent Difficulté du jeu, en pourcentage (ex: 50% => 50).
     */
    public void setDifficulty(int difficultyPercent) {
        data.setLevelMultiplicator(((double) (difficultyPercent + 100) / 100));
    }
    
    /**
     * Accès direct aux fonctions du modèle.
     * 
     * @return Référence au modèle GameData.
     */
    public GameData getModel() {
        return data;
    }
    
    /**
     * Création d'un gestionnaire de données qui s'occupe aussi de l'affichage.
     * 
     * @param window Fenêtre active.
     * @param game Contrôleur du jeu actif.
     */
    public DataManager(Window window, GameController game) {
        data = new GameData();
        this.window = window;
        this.game = game;
        
        // Ajout des valeurs de départ.
        spaceCash = window.addText(Integer.toString(data.getSpaceCash()), 15, 33, 100, 30, Color.RED, 30);
        killed = window.addText(Integer.toString(data.getKilled()), 815, 360, 100, 30, Color.RED, 30);
        level = window.addText(Integer.toString(data.getLevel()), 857, 335, 100, 25, Color.RED, 25);
        
        // Actualisation de la fenêtre.
        window.refresh();
    }
}