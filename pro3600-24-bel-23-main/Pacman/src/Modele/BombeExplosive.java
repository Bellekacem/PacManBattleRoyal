package Modele;

import java.util.ArrayList;
import java.util.Random;

public class BombeExplosive {
    public Pacman pacmanAppartient;
    public ArrayList<Position> positions; // une position s'il n a pas encore explosé et 4 positions s'il a explosé

    public int damage = 30; // points gagné si on touche son adversaire et points perdu si on s'auto touche

    public int delaiAvantExplosion = 3000; //millisecondes

    public int delaiExplosion = 5000; //millisecondes

    public BombeExplosive(int X, int Y ) {
        Position position = new Position(X,Y);
        positions = new ArrayList<Position>();
        positions.add(position);
    }

    public boolean aExplose(){ // verifie si la bombe a explosé ou non
        if(positions.size()==1){
            return false;
        }
        return true;
    }

    public void setPacmanAppartient(Pacman pacmanAppartient) {
        this.pacmanAppartient = pacmanAppartient;
    }


    public Pacman getPacmanAppartient() {
        return pacmanAppartient;
    }

    public void explose(Map map){ // fait explosé la bombe en lui "rajoutant" des positions

        //ajout des positions selon la règle de la croix s'il n y a pas de mur ou pacman ou fantome ou portail ou pouvoir
        Position positionHaut = new Position(positions.get(0).getX(), positions.get(0).getY()+1);
        Position positionBas  = new Position(positions.get(0).getX(), positions.get(0).getY()-1);
        Position positionGauche = new Position(positions.get(0).getX()-1, positions.get(0).getY());
        Position positionDroite = new Position(positions.get(0).getX()+1, positions.get(0).getY());

        if(map.getElement(positionHaut) ==' ' || map.getElement(positionHaut)=='.'){
            positions.add(positionHaut);
        }

        if(map.getElement(positionBas) ==' ' || map.getElement(positionBas)=='.'){
            positions.add(positionBas);
        }

        if(map.getElement(positionDroite) ==' ' || map.getElement(positionDroite)=='.'){
            positions.add(positionDroite);
        }

        if(map.getElement(positionGauche) ==' ' || map.getElement(positionGauche)=='.'){
            positions.add(positionGauche);
        }

        for(int k=0;k<4;k++){
            map.setElement(positions.get(k),'$');
        }

    }

    // verifie s'il y a une colision avec le pacman
    public void toucheBombe(Pacman pacman){
        if(pacman==this.pacmanAppartient){
            pacman.changePoints(-damage);
            pacman.PerteVie();
        } else {
            pacmanAppartient.changePoints(damage);
            pacman.PerteVie();
        }
    }

    public void dispariton(Map map){
        for(int k=0; k<positions.size(); k++){
            map.setElement(positions.get(k), '.');
        }
    }




}
