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
 * Nom : GameData.java
 * Description : Modèle
 *               Stocke les informations sur la partie.
 */
package ca.qc.bdeb.inf203.tp2.models;

public class GameData {
    /**
     * Nombre d'ennemis au début du jeu.
     */
    private static final int ENNEMY_FIRST_LEVEL = 5;
    
    // Variables de classe.
    private int spaceCash;
    private int level;
    private int killed;
    private int killedInThisLevel;
    private double levelMultiplicator;
    
    /**
     * Donne une nouvelle valeur au stock de SpaceCash.
     * 
     * @param spaceCash Nouvelle valeur.
     */
    public void setSpaceCash(int spaceCash) {
        this.spaceCash = spaceCash;
    }
    
    /**
     * Donne une nouvelle valeur au niveau actif.
     * 
     * @param level Nouveau niveau.
     */
    public void setLevel(int level) {
        this.level = level;
    }
    
    /**
     * Donne une nouvelle valeur au nombre de tués.
     * 
     * @param killed Nouveau nombre de tués.
     */
    public void setKilled(int killed) {
        this.killed = killed;
    }
    
    /**
     * Donne une nouvelle valeur au nombre de tués durant la vague.
     * 
     * @param killedInThisLevel Nouveau nombre de tués.
     */
    public void setKilledInThisLevel(int killedInThisLevel) {
        this.killedInThisLevel = killedInThisLevel;
    }
    
    /**
     * Donne une nouvelle valeur au coefficient de difficulté.
     * 
     * @param levelMultiplicator Nouveau coefficient de difficulté (multiplie le numéro du niveau).
     */
    public void setLevelMultiplicator(double levelMultiplicator) {
        this.levelMultiplicator = levelMultiplicator;
    }
    
    /**
     * Retourne l'argent possédé.
     * 
     * @return Nombre de SpaceCash du joueur.
     */
    public int getSpaceCash() {
        return spaceCash;
    }
    
    /**
     * Retourne le niveau actif.
     * 
     * @return Niveau actif.
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Retourne le nombre de tués.
     * 
     * @return Nombre de tués.
     */
    public int getKilled() {
        return killed;
    }
    
    /**
     * Retourne le nombre de tués.
     * 
     * @return Nombre de tués durant la vague.
     */
    public int getKilledInThisLevel() {
        return killedInThisLevel;
    }
    
    /**
     * Retourne le coefficient de difficulté.
     * 
     * @return Coefficient de difficulté du jeu (multiplicateur qui multiplie le niveau).
     */
    public double getLevelMultiplicator() {
        return levelMultiplicator;
    }
    
    /**
     * Retourne le nombre d'ennemis à générer pour un niveau donné, avec une précision
     * plus poussée, car on conserve les chiffres après la virgule.
     * 
     * @param level Niveau donné.
     * 
     * @return Nombre d'ennemis.
     */
    public double getMathNbEnnemiesOnLevel(int level) {
        if(level == 1) {
            return ENNEMY_FIRST_LEVEL;
        } else {
            return getMathNbEnnemiesOnLevel(level - 1) * levelMultiplicator;
        }
    }
    
    /**
     * Retourne le nombre d'ennemis à générer pour un niveau donné.
     * 
     * @param level Niveau donné.
     * 
     * @return Nombre d'ennemis.
     */
    public int getNbEnnemiesOnLevel(int level) {
        if(level == 1) {
            return ENNEMY_FIRST_LEVEL;
        } else {
            return (int) Math.round(getMathNbEnnemiesOnLevel(level - 1) * levelMultiplicator);
        }
    }
    
    /**
     * Retourne le nombre d'ennemis à générer pour le niveau actif.
     * 
     * @return Nombre d'ennemis.
     */
    public int getNbEnnemiesOnLevel() {
        return getNbEnnemiesOnLevel(level);
    }
    
    /**
     * Réglage des données par défaut.
     */
    public GameData() {
        spaceCash = 0;
        level = 0; // Le jeu commence par un levelUp().
        killed = 0;
        killedInThisLevel = 0;
        
        // 20% pour chaque nouvelle vague, par défaut.
        levelMultiplicator = 1.2;
    }
}