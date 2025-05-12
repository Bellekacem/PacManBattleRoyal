package Modele;

public class Portail {
    Position position;
    int time; //temps avant de disparaitre

    Portail(int X, int Y, int time){
        position = new Position(X,Y);
        this.time = time;
    }
}
