package Modele;

import java.util.ArrayList;
import java.util.List;

import java.util.*;
import java.util.HashMap;

public class Personnage {

    public Position position;

    public Position[] positionsPossible; // déplacement possible Haut,Bas,Gauche,Droite
    public int speed;

    public Personnage(int X, int Y, int speed) {
        position = new Position(X, Y);
        this.speed = speed;
        positionsPossible = new Position[4];
    }

    public int getSpeed() {
        return speed;
    }

    public Position getPosition() {
        return position;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String toString() {
        return "(" + position.getX() + ";" + position.getY() + ")";
    }


    //colision avec le mur + move possible
    public Position[] colisionMur(Map map) {
        Position Haut = new Position(this.position.getX(), this.position.getY() - 1);
        Position Bas = new Position(this.position.getX(), this.position.getY() + 1);
        Position Droite = new Position(this.position.getX() + 1, this.position.getY());
        Position Gauche = new Position(this.position.getX() - 1, this.position.getY());
        Position[] Direction = {Haut, Bas, Gauche, Droite};
        for (int k = 0; k < Direction.length; k++) {
            if (map.getElement(Direction[k]) == '%') {
                this.positionsPossible[k] = null;
            } else {
                this.positionsPossible[k] = Direction[k];
            }
        }
        return this.positionsPossible;
    }

    public Position[] movePossible(Map map) {
        return colisionMur(map);
    }

    public List<Position> getNeighbors(Position pos, Map map) {
        List<Position> neighbors = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            Position next = new Position(pos.getX() + dx[i], pos.getY() + dy[i]);
            if (map.getElement(next) != '%' && map.getElement(next) != 'F') {  // Assume '%' is a wall
                neighbors.add(next);
            }
        }
        return neighbors;
    }
    public void moveToPosition(Position targetPos, Map map) {
        // Parcour en Largeur pour determiner le chemin le plus cour vers le Pacman puisque
        Queue<Position> queue = new LinkedList<>();
        java.util.Map<Position,Position> prev = new HashMap<>();
        queue.add(this.position);
        prev.put(this.position, null);

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (current.equals(targetPos)) {
                break;
            }

            for (Position next : getNeighbors(current, map)) {
                if (!prev.containsKey(next)) {
                    queue.add(next);
                    prev.put(next, current);
                }
            }
        }

        // Backtrack from the target position to get the next step
        Position step = targetPos;
        while (!prev.get(step).equals(this.position)) {
            step = prev.get(step);
        }
        this.setPosition(step); // Move to the next step towards the target
    }
} 
