package com.company;

import java.util.Scanner;


public class briefSalleCinema {

    //initialisation du scanner
    static Scanner sc = new Scanner(System.in);

    //Définition des constantes nomées pour gérer la couleur du tableau "salle"
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";

    public static void main(String[] args) {

        //Variables
        char otherCommande;

        //Déclaration du tableau - le nbre de lignes et colonnes peut varier
        int nbRangeeSalle = 6;
        int nbColSalle = 7;

        //Création du tableau selon les variables
        String[][] salle = new String[nbRangeeSalle][nbColSalle];

        //Pour tests
            //salle[2][2] = "X";
            //salle[2][3] = "X";

        do {
            bookSeatsCinema(salle);

            //On pose la question si l'utilisateur souhaite commander d'autres places
            System.out.println("****************************************");
            System.out.println("Souhaitez-vous d'autres places ? (Y/N)");
            System.out.println("****************************************");
            sc.nextLine();
            otherCommande = sc.nextLine().charAt(0);
        }
        while (otherCommande == 'Y');

        // On réaffiche la grille
        afficherSalle(salle);
    }

    private static void bookSeatsCinema(String[][] salle) {
        //Exécution du programme
        //1- On commence par afficher un message puis on présente la salle vide (2 fonctions)
        editPlacesDispo();
        afficherSalle(salle);

        /*2- On demande la saisie de la rangée (=ligne) et on controle la saisie - tant que la saisie est hors tableau
         on redemande la saisie*/

        int rangee = 0;
        int rangeePosition = -1; //variable permettant de controler la saisie


        do {
            System.out.println("****************************************");
            System.out.println("Quelle rangée souhaitez-vous ? ");
            System.out.println("****************************************");
            rangee = sc.nextInt() - 1;

            //On vérifie que la saisie n'est pas hors borne du tableau
            if (isValidRow(salle, rangee)) {
                System.err.println("La saisie est erronnée");
            }
            ;
        } while (isValidRow(salle, rangee));


        //3- On demande la saisie du nombre de places

        int compteurPlaces;
        int nbplaces = 0;

        //on boucle si la saisie est erronnée afin de reposer la question
        do {
            System.out.println("****************************************");
            System.out.println("Combien de places souhaitez-vous ? ");
            System.out.println("****************************************");
            nbplaces = sc.nextInt();

            //on compte le nombre de places, c'est à dire le nombre de colonnes pour une rangée, non vide
            compteurPlaces = getAvailableleSeat(salle, rangee);

            //On vérifie qu'il y a assez de places dans la rangée demandée
            if (compteurPlaces < nbplaces) {
                System.err.println("Plus de places disponibles - Il ne reste que " + compteurPlaces + " places");
            }
        }
        //s'il y a plus de places demandées que de places diponibles on repose la question
        while (compteurPlaces < nbplaces);

        //s'il reste assez de places on coche les places dans la rangée
        for (int column = 0; column < nbplaces; column++) {
            if (salle[rangee][column] == null) {
                salle[rangee][column] = "X";
            }
        }
        afficherSalle(salle);
    }

    private static int getAvailableleSeat(String[][] salle, int rangee) {
        int compteurPlaces = 0;
        for (int columnCounter = 0; columnCounter < salle[rangee].length; columnCounter++) {
            if (salle[rangee][columnCounter] == null) {
                compteurPlaces += 1;
            }
        }
        return compteurPlaces;
    }

    private static boolean isValidRow(String[][] salle, int rangee) {
        return rangee < 0 || rangee > salle.length;
    }

    private static void editPlacesDispo() {
        System.out.println(GREEN + "** Voici les places disponibles : " + RESET);
    }

    private static boolean isPlaceConsecutive(int nbrePlace, int placesDispo) {
        // Je gagne si le consecutive count est supérieur ou égal au critère de victoire
        return nbrePlace >= placesDispo;
    }

    //private static int vérifHorizontal(String[][] salle, int linePosition, int columnPosition)
    //on part de la rangée demandée et on vérifie s'il y a un espace de disponible


    private static void afficherSalle(String[][] salle) {
        // on parcourt les lignes du tableau (rangée)
        for (int row = 0; row < salle.length; row++) {

            //Ensuite on parcourt les colonnes
            for (int column = 0; column < salle[row].length; column++) {

                //on affiche le numéro des rangées
                if (column == 0) {
                    System.out.print(row + 1 + "|");
                }

                //on initialise la place à null
                String place = "";
                //Si la place n'est pas null on renvoit la valeur sinon un "-"
                if (salle[row][column] != null) {
                    place = RED + salle[row][column] + RESET;
                } else place = YELLOW + "-" + RESET;


                System.out.printf("[ %1s ]", place);
                //System.out.printf("[ " + YELLOW + "_" + RESET + " ]", place)
                ;
            }
            System.out.println();
        }
    }
}
