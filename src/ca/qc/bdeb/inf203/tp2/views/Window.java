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
 * Nom : Window.java
 * Description : Vue
 *               Classe générique de création et gestion de fenêtres (réutilisable).
 */
package ca.qc.bdeb.inf203.tp2.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.border.LineBorder;

public class Window {
    /**
     * Fermer le programme en quittant.
     */
    public static final int EXIT_ON_CLOSE = JFrame.EXIT_ON_CLOSE;
    
    /**
     * Fermer uniquement la fenêtre en quittant.
     */
    public static final int DISPOSE_ON_CLOSE = JFrame.DISPOSE_ON_CLOSE;
    
    // Fenêtre active.
    private final JFrame fenetre;
    
    /**
     * Crée des boutons d'action personnalisés et les ajoute à la fenêtre.
     * 
     * @param name Nom à afficher sur le bouton.
     * @param posX Position horizontale du bouton.
     * @param posY Position verticale du bouton.
     * @param width Largeur du bouton.
     * @param height Hauteur du bouton.
     * @param bgColor Couleur à mettre en fond du bouton (au format hexadécimal).
     * @param fgColor Couleur du texte sur le bouton (au format hexadécimal).
     * @param borderColor Couleur de la bordure (1px) autour du bouton (au format hexadécimal).
     * @param action Élément ActionListener de l'action à effectuer en cas d'appui sur le bouton.
     */
    public void addActionButton(String name, int posX, int posY, int width, int height, int bgColor, int fgColor, int borderColor, ActionListener action) {
        JButton bouton = new JButton(name);
        bouton.setBounds(posX, posY, width, height);
        bouton.setBackground(Color.decode(Integer.toString(bgColor)));
        bouton.setForeground(Color.decode(Integer.toString(fgColor)));
        bouton.setBorder(new LineBorder(Color.decode(Integer.toString(borderColor)), 1));
        bouton.addActionListener(action);
        
        fenetre.add(bouton, 0);
    }
    
    /**
     * Crée des boutons d'action personnalisés et les ajoute à la fenêtre.
     * 
     * @param name Nom à afficher sur le bouton.
     * @param posX Position horizontale du bouton.
     * @param posY Position verticale du bouton.
     * @param width Largeur du bouton.
     * @param height Hauteur du bouton.
     * @param bgColor Couleur à mettre en fond du bouton (au format hexadécimal).
     * @param fgColor Couleur du texte sur le bouton (au format hexadécimal).
     * @param borderColor Couleur de la bordure (1px) autour du bouton (au format hexadécimal).
     * @param action Élément ActionListener de l'action à effectuer en cas d'appui sur le bouton.
     * @param command Nom de commande personnalisé.
     */
    public void addActionButton(String name, int posX, int posY, int width, int height, int bgColor, int fgColor, int borderColor, ActionListener action, String command) {
        JButton bouton = new JButton(name);
        bouton.setBounds(posX, posY, width, height);
        bouton.setBackground(Color.decode(Integer.toString(bgColor)));
        bouton.setForeground(Color.decode(Integer.toString(fgColor)));
        bouton.setBorder(new LineBorder(Color.decode(Integer.toString(borderColor)), 1));
        bouton.setActionCommand(command);
        bouton.addActionListener(action);
        
        fenetre.add(bouton, 0);
    }
    
    /**
     * Permet d'ajouter une image à la fenêtre, à une position définie.
     * 
     * @param imageLocation Lien vers l'image.
     * @param posX Position horizontale.
     * @param posY Position verticale.
     * @param width Largeur de l'image.
     * @param height Hauteur de l'image.
     * 
     * @return Référence de l'image ajoutée, pour modification ultérieure.
     */
    public JLabel addImage(String imageLocation, int posX, int posY, int width, int height) {
        ImageIcon image =  new ImageIcon(Window.class.getResource("./" + imageLocation)); 
        JLabel imageLbl = new JLabel(image);
        imageLbl.setBounds(posX, posY, width, height);
        
        fenetre.add(imageLbl, 0);
        
        return imageLbl;
    }
    
    /**
     * Permet d'ajouter du texte à la fenêtre.
     * 
     * @param text Texte à ajouter, HTML compatible.
     * @param posX Position du bloc texte, en X.
     * @param posY Position du bloc texte, en Y.
     * @param width Largeur du bloc texte.
     * @param height Hauteur du bloc texte.
     * @param color Couleur du texte.
     * @param fontSize Taille du texte (habituellement 14).
     * 
     * @return Référence du texte ajouté, pour modification ultérieure.
     */
    public JLabel addText(String text, int posX, int posY, int width, int height, Color color, int fontSize) {
        JLabel textLbl = new JLabel(text);
        textLbl.setBounds(posX, posY, width, height);
        textLbl.setForeground(color);
        textLbl.setFont(new Font("Serif", Font.PLAIN, fontSize));
        
        fenetre.add(textLbl, 0);
        
        return textLbl;
    }
    
    /**
     * Ajouter un élément défini dans la vue à la fenêtre.
     * 
     * @param element Élément à ajouter.
     */
    public void addElement(Component element) {
        fenetre.add(element, 0);
    }
    
    /**
     * Supprimer un élément défini dans la vue à la fenêtre.
     * 
     * @param element Élément à supprimer.
     */
    public void removeElement(Component element) {
        fenetre.remove(element);
    }
    
    /**
     * Ajouter une barre de menu à la fenêtre.
     * 
     * @param menuBar Barre de menu à ajouter.
     */
    public void setMenuBar(JMenuBar menuBar) {
        fenetre.setJMenuBar(menuBar);
    }
    
    /**
     * Rafraîchit l'affichage de la fenêtre. À utiliser lorsque tout est généré et placé seulement.
     */
    public void refresh() {
        fenetre.revalidate();
        fenetre.repaint();
    }
    
    /**
     * Ferme la fenêtre.
     */
    public void close() {
        fenetre.dispose();
    }
    
    /**
     * Supprime tout le contenu de la fenêtre.
     */
    public void removeAll() {
        fenetre.getContentPane().removeAll();
    }
    
    /**
     * Retourne directement la référence à la fenêtre active.
     * 
     * @return JFrame actif.
     */
    public JFrame getJFrame() {
        return fenetre;
    }
    
    /**
     * Instanciation d'une fenêtre.
     * 
     * @param title Titre de la fenêtre.
     * @param width Largeur de la fenêtre.
     * @param height Hauteur de la fenêtre.
     * @param bgColor Couleur de l'arrière-plan de la fenêtre.
     * @param closeOperation Opération à exécuter lors de la fermeture de la fenêtre.
     */
    public Window(String title, int width, int height, Color bgColor, int closeOperation) {
        // Création d'une nouvelle fenêtre lors de l'instanciation
        fenetre = new JFrame();
        
        fenetre.setSize(width, height);
        fenetre.setTitle(title);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false); // Taille fixe
        fenetre.setLayout(null); // Pas de layout, TRÈS IMPORTANT AFIN DE POUVOIR UTILISER REVALIDATE() !
        fenetre.getContentPane().setBackground(bgColor);
        
        // Afficher la fenêtre
        fenetre.setVisible(true);
        
        // Fermeture automatique lorsque le JFrame est fermé
        fenetre.setDefaultCloseOperation(closeOperation);
    }
}