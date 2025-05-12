package Modele;
public class PacGomme{
    Position position;
    boolean taken;
    int points;
    public PacGomme(int X, int Y, int points) {
        position = new Position(X,Y);
        this.points=points;
        taken=false;
    }

    public int getPoints() {
        return points;
    }

    public Position getPosition() {
        return position;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}

