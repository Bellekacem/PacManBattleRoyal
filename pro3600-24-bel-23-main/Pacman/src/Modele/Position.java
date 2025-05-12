package Modele;
public class Position {
    public int X;
    public int Y;
    public Position(int X,int Y){
        this.X=X;
        this.Y=Y;

    }
    public int getX(){
        return X;
    }
    public int getY(){
        return Y;
    }
    public void setX(int X){
        this.X=X;
    }
    public void setY(int Y){
        this.Y=Y;
    }

    public int dist(Position other){
        return Math.abs(this.X - other.X) + Math.abs(this.Y - other.Y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Position position = (Position) o;
        return X == position.X && Y == position.Y;
    }
}