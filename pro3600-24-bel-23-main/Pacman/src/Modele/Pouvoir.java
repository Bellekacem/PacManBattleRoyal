package Modele;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
public class Pouvoir {
    Position position;
    Pacman pacman; // pour associer le pouvoir à un pacman
    String pouvoir;

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Pouvoir(Pacman pacman) {

        //liste des pouvoirs possibles
        String[] pouvoirs = new String[]{"changementVitesse","invulnérable","malédiction"};

        //choix alétoire du pouvoir
        Random random = new Random();
        int i = random.nextInt(pouvoirs.length);
        this.pouvoir=pouvoirs[i];
        this.pacman = pacman;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public String getPouvoir() {
        return pouvoir;
    }

    public void givePower(){
    	if (pacman.pouvoir != null) {
    		pacman.vulnerable = true;
    		pacman.speed = 190;
    		pacman.maudit = false;
    		pacman.pouvoir = null;
    	} else {
    		if (pouvoir.equals("invulnérable")){
                pacman.vulnerable=false;
                pacman.pouvoir = "Invulnérabilité !";
                // Scheduler pour réactiver la vulnérabilité après 10 secondes
                PauseTransition attente1 = new PauseTransition(Duration.seconds(10));
                attente1.setOnFinished(event -> {
                	pacman.vulnerable = true;
                	pacman.pouvoir = null;
                });
                attente1.play();
            } else if (pouvoir.equals("changementVitesse")) {
                Random random = new Random();
                int randomNumber = random.nextInt(2); // génère 0 ou 1
                int result = (randomNumber == 0) ? -1 : 1; // convertit 0 en -1 et 1 en 1
                pacman.adddSpeed(result*100);
                pacman.pouvoir = "Changement de vitesse !";
                PauseTransition attente2 = new PauseTransition(Duration.seconds(10));
                attente2.setOnFinished(event -> {
                	pacman.speed = 190;
                	pacman.pouvoir = null;
                });
                attente2.play();
            }
            else if (pouvoir.equals("malédiction")){
                pacman.maudit = true;
                pacman.pouvoir = "Malédiction (sur lui-même !)";
                PauseTransition attente3 = new PauseTransition(Duration.seconds(10));
                attente3.setOnFinished(event -> {
                	pacman.maudit = false;
                	pacman.pouvoir = null;
                });
                attente3.play();
            }
    }
    }
}

