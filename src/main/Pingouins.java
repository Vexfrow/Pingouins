package main;

import main.Interface.Fenetre;

import main.Model.Jeu;

public class Pingouins {

            public static void main(String[] args){
                //On crée un jeu avec les paramètres passé en paramètre
                Jeu j = new Jeu();
                //On crée un controleur à partir de ce jeu
                //On lance l'affichage de l'interface
                Fenetre.demarrer(j);
            }

}
