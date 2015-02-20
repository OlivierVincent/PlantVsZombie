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
 * Nom : EachSecond.java
 * Description : Contrôleur
 *               Gestion des évènements selon le temps.
 */
package ca.qc.bdeb.inf203.tp2.controllers;

import ca.qc.bdeb.inf203.tp2.views.Game;
import ca.qc.bdeb.inf203.tp2.views.Piece;
import ca.qc.bdeb.inf203.tp2.views.Window;
import ca.qc.bdeb.inf203.tp2.views.ennemies.Blue;
import ca.qc.bdeb.inf203.tp2.views.ennemies.Ennemy;
import ca.qc.bdeb.inf203.tp2.views.ennemies.Pink;
import ca.qc.bdeb.inf203.tp2.views.ennemies.White;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.Timer;

public abstract class EnnemyGenerator implements ActionListener {
    /**
     * Délai auquel se créent les ennemis d'une vague, en secondes.
     */
    private static final int TIME_BETWEEN_ENNEMIES = 10;
    
    // Variables de classe.
    private final Timer timer;
    private Window window;
    private DataManager data;
    private final Queue<String> queuedEnnemies;
    private int time;
    private static int posYPhase2;
    private static int waiter;
    
    /**
     * Génère un ennemi et le met en attente de création.
     * 
     * @param type Type d'ennemi (utiliser constantes).
     * @param window Fenêtre active.
     * @param data Gestionnaire de données.
     */
    protected void generateEnnemy(String type, Window window, DataManager data) {
        // On définit les références nécessaires.
        this.window = window;
        this.data = data;
        
        // On génère la position d'attaque stratégique de la phase 2.
        posYPhase2 = (int) (Math.random() * Game.GRID_HEIGHT + 1);
        
        // On met un petit module pour faire une petite pause entre les deux phases.
        waiter = 0;
        
        // On met l'ennemi en attente.
        queuedEnnemies.add(type);
    }
    
    /**
     * Déploie le prochain ennemi en attente.
     * 
     * @param posY Positon verticale de départ (1-5).
     */
    private void deployEnnemy(int posY) {
        switch(queuedEnnemies.poll()) {
            case Ennemy.ENNEMY_WHITE:
                Piece white = new White(posY, window, data);
                break;

            case Ennemy.ENNEMY_BLUE:
                Piece blue = new Blue(posY, window, data);
                break;

            case Ennemy.ENNEMY_PINK:
                Piece pink = new Pink(posY, window, data);
                break;
        }
    }
    
    /**
     * On fait appel à cette classe pour gérer la génération des ennemisdans une vague.
     */
    public EnnemyGenerator() {
        // On initialise.
        queuedEnnemies = new LinkedList<>();
        time = 0;
        posYPhase2 = 0;
        
        // Un délai de une seconde.
        int delay = 1000;
        
        // Ajoutons le timer, et c'est parti !
        timer = new Timer(delay, this);
        timer.start();
    }
    
    /**
     * Boucle qui s'exécute à chaques X secondes.
     * 
     * @param e Informations sur l'évènement "ActionListener".
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Augmentons le compteur de temps.
        time++;
        
        // Nous vérifions si on a des ennemis à placer, et si on respecte les 35 secondes d'attente
        // du début du jeu.
        if(!queuedEnnemies.isEmpty() && time >= 35) {
            // Prenons une position de départ aléatoire.
            int posY = (int) (Math.random() * Game.GRID_HEIGHT + 1);

            if(queuedEnnemies.size() > data.getModel().getNbEnnemiesOnLevel() / 2) {
                // Première phase : nous plaçons la moitié de la file un peu partout aux X secondes.
                if(time % TIME_BETWEEN_ENNEMIES == 0) {
                    deployEnnemy(posY);
                }
            } else {
                // Deuxième phase : nous envoyons l'autre moitié à une seule position
                // précise, avec deux secondes de délai les séparant.
                if(time % 2 == 0) {
                    if(waiter < 6) {
                        // On veut patienter douze secondes avant la 2ème phase.
                        waiter++;
                    } else {
                        deployEnnemy(posYPhase2);
                    }
                }
            }
        }
    }
}