package Modele;

import java.util.ArrayList;

import java.util.Timer;
import java.util.TimerTask;

public class Pacman extends Personnage {
    public int points;
    public int life;
    public int speed = 200;

    //pour le mode pouvoir :
    public boolean vulnerable = true;
    // pour le mode pouvoir :
    public boolean maudit = false;
    public int nbrBombes = 0;
    public char lettre;
    public char lettreFuturPosition; //permet de récuperer la lettre qu'il remplace lorsque qu'il se deplace (quand c'est un fantome)
    public ArrayList<BombeExplosive> bombes = new ArrayList<>();
    public String pouvoir;
    public int numero;

    public Pacman(int X, int Y, int Speed, int Life, char lettre) {
        super(X, Y, Speed);
        this.life=3;
        this.lettre = lettre;
        if(lettre == 'P') {
        	this.numero = 0;
        } else {
        	this.numero = 1;
        }
    }
    public int getLife(){
        return life;
    }
    public void PerteVie(){
        this.life -= 1;
    }
    public void changePoints(int points){
        this.points += points;
    }
    public void ajoutBombe(){
        nbrBombes ++;
    }
    public void adddSpeed(int add ){
        speed+=add;
    }

    public void poseBombe(Position position, Map map){
        if (nbrBombes != 0) {
            nbrBombes--;
            map.setElement(position, '@');

            BombeExplosive bombeRamasse = new BombeExplosive(position.getX(), position.getY());

            // Faire exploser la bombe après un certain temps
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    bombeRamasse.pacmanAppartient = Pacman.this; // affecte à la propriété pacmanAppartient de l'objet bombeRamasse une référence à l'instance actuelle de la classe Pacman
                    // Ajouter la bombe à la liste de bombes
                    bombes.add(bombeRamasse);
                    bombeRamasse.explose(map);
                    timer.cancel();



                }
            }, bombeRamasse.delaiAvantExplosion); // Délai avant explosion


            //disparition de la bombe
            Timer timer2 = new Timer();
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {

                    bombeRamasse.dispariton(map);
                    timer2.cancel();

                }
            }, bombeRamasse.delaiAvantExplosion + bombeRamasse.delaiExplosion); // Délai disparition



        }
    }

    public boolean verfieAppartenanceBombe(Position position){
        for(int k=0; k<bombes.size(); k++){
            for(int j=0; j<bombes.get(k).positions.size(); j++){
                if(position.equals(bombes.get(k).positions.get(j))){
                    return true;
                }
            }
        }
        return false;
    }

    public BombeExplosive trouveBombeTouche(Position position){
        for(int k=0; k<bombes.size(); k++){
            for(int j=0; j<bombes.get(k).positions.size(); j++){
                if(position.equals(bombes.get(k).positions.get(j))){
                    return bombes.get(k);
                }
            }
        }
        return null;
    }



}
