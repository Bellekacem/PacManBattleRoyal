package Modele;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.Random;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Mode {

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(); // pour gérer une tache qui s'exécute à une certaine fréquence
    private ScheduledExecutorService executorService2 = Executors.newSingleThreadScheduledExecutor(); // pour gérer une tache qui s'exécute à une certaine fréquence
    private ScheduledExecutorService executorService3 = Executors.newSingleThreadScheduledExecutor(); // pour gérer une tache qui s'exécute à une certaine fréquence

    Map map;
    boolean bomberman ;
    boolean pouvoir;
    boolean portail;
    boolean multijoueur;




    public Mode(boolean bomberman, boolean pouvoir, boolean portail, boolean multijoueur, Map map){
        this.map = map;
        this.bomberman= bomberman;
        this.pouvoir=pouvoir;
        this.portail=portail;
        this.multijoueur = multijoueur;

    }

    //ajout d'un portail dans la map + retourne sa position
    public Position ajoutPouvoir(){
        Random random = new Random();
        int X = random.nextInt(map.getWidth());
        int Y = random.nextInt(map.getHeight());
        Position positionPouvoir = new Position(X, Y);
        while (map.getElement(positionPouvoir) != ' ' && map.getElement(positionPouvoir) != '.') {
            int X1 = random.nextInt(map.getWidth());
            int Y1 = random.nextInt(map.getHeight());
            positionPouvoir = new Position(X1, Y1);
        }
        map.setElement(positionPouvoir, '#');
        return positionPouvoir;
    }

    public void modePouvoir(){
        executorService3.scheduleAtFixedRate(() -> {
            // si le mode pouvoir a été activé et que la map ne contient pas de pouvoir, on en pose un
            if(pouvoir && !map.havePouvoir()) {
                this.ajoutPouvoir();
            }
        }, 5,  7, TimeUnit.SECONDS);
    }


    //mode multijoueur : on enlève le pacman en trop
    public void modeMultijoueur(){
        if(!multijoueur){
            map.setElement(map.findPacmanJoueur2(),'.');
            map.pacmans.remove(1);
        }
    }

    //ajout d'un portail dans la map + retourne sa position
    public Position ajoutPortail(){
        Random random = new Random();
        int X = random.nextInt(map.getWidth());
        int Y = random.nextInt(map.getHeight());
        Position positionPortail = new Position(X, Y);
        while (map.getElement(positionPortail) == '/' ||map.getElement(positionPortail) == '$' || map.getElement(positionPortail) == 'F' || map.getElement(positionPortail) == '%' || map.getElement(positionPortail) == 'P' || map.getElement(positionPortail) == '@' || map.getElement(positionPortail) == '#') {
            int X1 = random.nextInt(map.getWidth());
            int Y1 = random.nextInt(map.getHeight());
            positionPortail = new Position(X1, Y1);
        }
        map.setElement(positionPortail, '/');
        return positionPortail;
    }

    public void modePortail() {
        if (portail) {
            ArrayList<Position> positionsPortails = new ArrayList<>();

            final int[] compteur = {0}; // on doit utiliser un tableau pour pouvoir l'utiliser dans un lambda expression

            // Planification de la tâche pour ajouter/supprimer un portail toutes les 30 secondes
            executorService.scheduleAtFixedRate(() -> {

                int indice = compteur[0] % 4;



                //gestion des 4 premiers portails
                if(positionsPortails.size() != 4){
                    positionsPortails.add(this.ajoutPortail());
                }
                else {
                    map.setElement(positionsPortails.get(indice), '.');
                    positionsPortails.set(indice, this.ajoutPortail());
                }
                compteur[0] += 1;


            }, 3, 3, TimeUnit.SECONDS);



        }
    }

     public Position ajoutBombeArecup(){
         Random random = new Random();
         int X = random.nextInt(map.getWidth());
         int Y = random.nextInt(map.getHeight());
         Position positionBombe = new Position(X, Y);
         while (map.getElement(positionBombe) != ' ' && map.getElement(positionBombe) != '.') {
             int X1 = random.nextInt(map.getWidth());
             int Y1 = random.nextInt(map.getHeight());
             positionBombe = new Position(X1, Y1);
         }
         map.setElement(positionBombe, '€');
         return positionBombe;
     }

    public void modeBomberman() {
        if (bomberman) {
            for(int k=0; k<map.pacmans.size(); k++){
                map.pacmans.get(k).nbrBombes= 3;
            }
            // Planification de la tâche pour ajouter une bombe à récupérer toutes les 45 secondes
            executorService2.scheduleAtFixedRate(() -> {
                Position positionBombe = this.ajoutBombeArecup();
                map.setElement(positionBombe, '€');
            }, 5,  10, TimeUnit.SECONDS);

        }
    }



}
