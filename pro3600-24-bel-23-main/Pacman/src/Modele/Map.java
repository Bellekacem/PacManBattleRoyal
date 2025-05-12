package Modele;
import java.util.ArrayList;
import java.util.Random;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Vue.Game;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Map {
    public char[][] board;
    public ArrayList<Pacman> pacmans;
    public ArrayList<Fantome> fantomes;

    public boolean pause = false; // va nous servir pour mettre en pause le jeu après une rénitialisation

    public Map(String board){
        fantomes = new ArrayList<Fantome>(); // crée nos fantomes
        pacmans = new ArrayList<Pacman>();
        board = board.strip(); // retire les retours à la ligne au début et à la fin du string
        String[] lines = board.split("\n"); // récupère les lignes de notre map
        this.board = new char[lines[0].strip().length()][lines.length]; // initialise la taille du tableau
        for (int i=0; i < lines.length; i++){
            String line=lines[i].strip();
            for (int j=0; j < line.length(); j++){
                if (line.charAt(j) == 'P') {
                    Pacman pacman = new Pacman(j,i,20,3, 'P'); // on récupère le pacman
                    pacmans.add(pacman);
                    this.board[j][i] = 'P';
                }
                else if (line.charAt(j) == 'Q') {
                    Pacman pacman = new Pacman(j,i,20,3, 'Q'); // on récupère le pacman
                    pacmans.add(pacman);
                    this.board[j][i] = 'Q';
                }
                else if (line.charAt(j)=='F') {
                    Fantome fantome = new Fantome(j,i,20); // on récupère les fantomes
                    this.fantomes.add(fantome);
                    this.board[j][i] = 'F';
                } else {
                    this.board[j][i] = line.charAt(j);
                }
            }
        }
    }

    public int getWidth() {
        return this.board.length;
    }

    public int getHeight() {
        return this.board[0].length;
    }

    public char getElement(Position position){
        return this.board[position.getX()][position.getY()];
    }

    public void setElement(Position position, char value){
        this.board[position.getX()][position.getY()] = value;
    }

    //return la position du pacman
    public Position findPacmanJoueur1(){
        for (int k=0; k<board.length; k++){
            for(int j=0; j<board[0].length; j++){
                if(board[k][j] == 'P'){
                    Position position = new Position(k,j);
                    return position;
                }
            }
        }
        // probleme quand la position du pacman n'est pas trouver important pour IA
        return new Position(0, 0); // Renvoie une position par défaut, la dernière position valide connue
    }

    public Position findPacmanJoueur2(){
        for (int k=0; k<board.length; k++){
            for(int j=0; j<board[0].length; j++){
                if(board[k][j] == 'Q'){
                    Position position = new Position(k,j);
                    return position;
                }
            }
        }
        return new Position(0, 0); // Renvoie une position par défaut, la dernière position valide connue
    }

    public ArrayList<Position> findFantomes(){
        ArrayList<Position> positionsFantomes = new ArrayList<>();
        for (int k=0; k<board.length; k++){
            for(int j=0; j<board[0].length; j++){
                if(board[k][j] == 'F'){
                    Position position = new Position(k,j);
                    positionsFantomes.add(position);
                }
            }
        }
        return positionsFantomes;
    }

    //verifie l'existence ou non d'un pouvoir sur la map
    public boolean havePouvoir(){
        for (int k=0; k<board.length; k++){
            for(int j=0; j<board[0].length; j++){
                if(this.board[k][j]=='#'){
                    return true;
                }
            }
        }
        return false;
    }

    //renitialise la map sans passer par une nouvelle instance;
    public void reinitialiseMap(String board){
        fantomes = new ArrayList<Fantome>(); // crée nos fantomes
        board = board.strip(); // retire les retours à la ligne au début et à la fin du string
        String[] lines = board.split("\n"); // récupère les lignes de notre map
        this.board = new char[lines[0].strip().length()][lines.length]; // initialise la taille du tableau
        for (int i=0; i < lines.length; i++){
            String line=lines[i].strip();
            for (int j=0; j < line.length(); j++){
                if (line.charAt(j) == 'P') {
                    this.pacmans.get(0).position = new Position(j,i);
                    this.board[j][i] = 'P';
                }
                else if (line.charAt(j) == 'Q' && pacmans.size()==2) {
                    this.pacmans.get(1).position = new Position(j,i);
                    this.board[j][i] = 'Q';
                }
                else if (line.charAt(j) == 'Q' && pacmans.size()!=2){
                    this.board[j][i] = '.';
                }
                else if (line.charAt(j)=='F') {
                    Fantome fantome = new Fantome(j,i,20); // on récupère les fantomes
                    this.fantomes.add(fantome);
                    this.board[j][i] = 'F';
                } else {
                    this.board[j][i] = line.charAt(j);
                }
            }
        }
        //on enlève les pouvoirs/malus des pacman
        for(int k=0; k<pacmans.size();k++){
            pacmans.get(k).maudit=false;
            pacmans.get(k).vulnerable=true;
            pacmans.get(k).speed = 200;
            pacmans.get(k).pouvoir = null;
        }
    }

    public Pacman chercheAutrePacman(Pacman pacman){
        if(pacmans.size()==2) {
            if (pacman.equals(this.pacmans.get(0))) {
                return pacmans.get(1);
            } else {
                return pacmans.get(0);
            }
        }
        return null;
    }

    //changement de l'état de la map lors du déplacement du pacman
    public void deplacementPacman(Position positionFutur, String board, Pacman pacman) {
        if (pacman.lettre != 'F') { // si le pacman n'a pas été transformé en fantome
            this.setElement(pacman.position, ' ');
            if (this.getElement(positionFutur) == '.') { //récupération de la pacgomme
                pacman.changePoints(30);
                pacman.setPosition(positionFutur);
                this.setElement(pacman.position, pacman.lettre);
            } else if (this.getElement(positionFutur) == '#') { //récupération d'un pouvoir
                Pouvoir pouvoir = new Pouvoir(pacman);
                pouvoir.givePower();
                pacman.setPosition(positionFutur);
                this.setElement(pacman.position, pacman.lettre);
            } else if (this.getElement(positionFutur) == '/') { //prendre un portail
                //choix alétoire de la futur position
                Random random = new Random();
                int X = random.nextInt(this.getWidth());
                int Y = random.nextInt(this.getHeight());
                Position positionTeleportation = new Position(X, Y);
                while (this.getElement(positionTeleportation) == '/' || this.getElement(positionTeleportation) == 'F' || this.getElement(positionTeleportation) == '%') {
                    int X1 = random.nextInt(this.getWidth());
                    int Y1 = random.nextInt(this.getHeight());
                    positionTeleportation = new Position(X1, Y1);
                }
                //téléportation du pacman
                pacman.setPosition(positionTeleportation);
                this.setElement(pacman.position, pacman.lettre);

            } else if (this.getElement(positionFutur) == '$') {
                //verifie si la bombe lui appartient
                if (pacman.trouveBombeTouche(positionFutur) != null) {
                    pacman.trouveBombeTouche(positionFutur).toucheBombe(pacman);
                    pause = true; //on le met en false dans la classe game car c'est la classe game qui permet de mettre en pause le jeu
                    PauseTransition pause = new PauseTransition(Duration.millis(1500));
                    pause.setOnFinished(event -> {
                    	this.reinitialiseMap(board);
                    });
                    pause.play();
                } else {
                    Pacman pacmanAdverse = this.chercheAutrePacman(pacman);
                    pacmanAdverse.trouveBombeTouche(positionFutur).toucheBombe(pacman);
                    pause = true; //on le met en false dans la classe game car c'est la classe game qui permet de mettre en pause le jeu
                    PauseTransition pause = new PauseTransition(Duration.millis(1500));
                    pause.setOnFinished(event -> {
                    	this.reinitialiseMap(board);
                    });
                    pause.play();
                }
            } else if (this.getElement(positionFutur) == '€') {
                this.setElement(pacman.position, ' ');
                pacman.setPosition(positionFutur);
                pacman.ajoutBombe();
                this.setElement(positionFutur, pacman.lettre);
            } else if (this.getElement(positionFutur)=='#') {
                this.setElement(pacman.position, ' ');
                pacman.setPosition(positionFutur);
                Pouvoir pouvoir = new Pouvoir(pacman);
                this.setElement(positionFutur, pacman.lettre);
            } else if (this.getElement(positionFutur)=='@' || positionFutur==null) {

            } else{
                pacman.setPosition(positionFutur);
                this.setElement(pacman.position, pacman.lettre);
            }
        } else { //si c'est un fantome
            if (this.getElement(positionFutur) != '%') {
                this.setElement(pacman.position, pacman.lettreFuturPosition);
                pacman.lettreFuturPosition=this.getElement(positionFutur);
                pacman.setPosition(positionFutur);
            }

        }
    }

    public void transformationEnFantome(Pacman pacman){
        if(pacman.getLife()==0) {
            pacman.lettre = 'F';
            this.setElement(pacman.position, 'F');
        }
    }

    // permet de gérer les collisions lorsque les positions sont statiques
    public void collisionFantome(Pacman pacman, String board){
        //s'il est vulnérable
        if(pacman.vulnerable) {
            for (int k = 0; k < 4; k++) {
                if (pacman.position.equals(fantomes.get(k).position) && pacman.lettre != 'F') {
                    pacman.PerteVie();
                    pause = true; //on le met en false dans la classe game car c'est la classe game qui permet de mettre en pause le jeu
                    reinitialiseMap(board);
                }
            }
        }
        if (pacmans.size()==2) {
            //cas où l'autre pacman est un fantome
            if ((chercheAutrePacman(pacman).lettre == 'F' && chercheAutrePacman(pacman).position.equals(pacman.position)) || (chercheAutrePacman(pacman).lettre == 'F' && pacman.position.equals(chercheAutrePacman(pacman)))  ) {
                pacman.PerteVie();
                chercheAutrePacman(pacman).changePoints(300);
                pause = true; //on le met en false dans la classe game car c'est la classe game qui permet de mettre en pause le jeu
                reinitialiseMap(board);

            }
        }
    }


}