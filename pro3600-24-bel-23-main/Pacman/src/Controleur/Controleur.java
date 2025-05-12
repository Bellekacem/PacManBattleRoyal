package Controleur;

import Modele.*;
import Modele.Pacman;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class Controleur {
    public Modele modele;
    public Pacman pacman1;
    public Pacman pacman2;
    public Scene scene;

    // pour joueur 1
    boolean haut = false;
    boolean bas = false;
    boolean droite = false;
    boolean gauche = false;
    KeyCode memoire;
    private ScheduledFuture<?> pacmanTask; // évite de créer un déplacment plus rapide dans une direction ( accumulation de direction )

    // pour joueur 2
    boolean haut2 = false;
    boolean bas2 = false;
    boolean droite2 = false;
    boolean gauche2 = false;
    private ScheduledFuture<?> pacmanTask2; // évite de créer un déplacment plus rapide dans une direction ( accumulation de direction )
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(); // pour gérer une tache qui s'exécute à une certaine fréquence

    public Controleur(Modele modele, Scene scene) {
        this.modele = modele;
        this.pacman1= modele.map.pacmans.get(0);
        if (modele.map.pacmans.size()==2) {
            this.pacman2 = modele.map.pacmans.get(1);
        }
        this.scene = scene;
        setSceneEventHandler();
    }


    private void setSceneEventHandler() {
                // commande joueur 1
                scene.setOnKeyReleased(event -> {//commande du jeu
                    if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode()==KeyCode.LEFT || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.M ) {
                        if (memoire != event.getCode() || memoire == KeyCode.M) {
                            memoire = event.getCode();
                            if (memoire == KeyCode.UP && pacman1.movePossible(modele.map)[0] != null) {
                                haut = true;
                                bas = false;
                                droite = false;
                                gauche = false;
                            } else if (memoire == KeyCode.DOWN && pacman1.movePossible(modele.map)[1] != null) {
                                haut = false;
                                bas = true;
                                droite = false;
                                gauche = false;
                            } else if (memoire == KeyCode.LEFT && pacman1.movePossible(modele.map)[2] != null) {
                                haut = false;
                                bas = false;
                                droite = false;
                                gauche = true;
                            } else if (memoire == KeyCode.RIGHT && pacman1.movePossible(modele.map)[3] != null) {
                                haut = false;
                                bas = false;
                                droite = true;
                                gauche = false;
                            }
                            if (pacmanTask != null) { // Annuler la tâche précédente si elle existe
                                pacmanTask.cancel(false);
                            }

                            //pose la bombe "derrière" le pacman
                            // dans ce controleur si le pacman est immobile entre deux mur il ne peut pas poser de bombe sur le coté : donc à modifier si besoin/possible
                            if (memoire == KeyCode.M && pacman1.nbrBombes != 0) {
                                if (haut && !bas && !gauche && !droite && pacman1.positionsPossible[1] != null) {
                                    pacman1.poseBombe(pacman1.positionsPossible[1], modele.map);
                                } else if (!haut && bas && !gauche && !droite && pacman1.positionsPossible[0] != null) {
                                    pacman1.poseBombe(pacman1.positionsPossible[0], modele.map);
                                } else if (!haut && !bas && gauche && !droite && pacman1.positionsPossible[3] != null) {
                                    pacman1.poseBombe(pacman1.positionsPossible[3], modele.map);
                                } else if (!haut && !bas && !gauche && droite && pacman1.positionsPossible[2] != null) {
                                    pacman1.poseBombe(pacman1.positionsPossible[2], modele.map);
                                }
                            }

                            pacmanTask = executorService.scheduleAtFixedRate(() -> {
                                //si le jeu n'a pas été mis en pause
                                if (!modele.map.pause) {
                                    if (haut && !bas && !gauche && !droite) {
                                        modele.map.deplacementPacman(pacman1.movePossible(modele.map)[0], modele.board, pacman1);
                                    } else if (!haut && bas && !gauche && !droite) {
                                        modele.map.deplacementPacman(pacman1.movePossible(modele.map)[1], modele.board, pacman1);
                                    } else if (!haut && !bas && gauche && !droite) {
                                        modele.map.deplacementPacman(pacman1.movePossible(modele.map)[2], modele.board, pacman1);
                                    } else if (!haut && !bas && !gauche && droite) {
                                        modele.map.deplacementPacman(pacman1.movePossible(modele.map)[3], modele.board, pacman1);
                                    }
                                } else {
                                    memoire = null;
                                    haut = false;
                                    bas = false;
                                    gauche = false;
                                    droite = false;
                                    modele.map.deplacementPacman(null, modele.board, pacman1);
                                }
                            }, 0, pacman1.speed, TimeUnit.MILLISECONDS);
                        }
                    } else if ( event.getCode()==KeyCode.Z || event.getCode()==KeyCode.Q || event.getCode()==KeyCode.S || event.getCode()==KeyCode.D || event.getCode() == KeyCode.R ) {
                        if (memoire != event.getCode() || memoire == KeyCode.R) {
                            memoire = event.getCode();
                            //commande joueur 2
                            if (memoire == KeyCode.Z && pacman2.movePossible(modele.map)[0] != null) {
                                haut2 = true;
                                bas2 = false;
                                droite2 = false;
                                gauche2 = false;
                            } else if (memoire == KeyCode.S && pacman2.movePossible(modele.map)[1] != null) {
                                haut2 = false;
                                bas2 = true;
                                droite2 = false;
                                gauche2 = false;
                            } else if (memoire == KeyCode.Q && pacman2.movePossible(modele.map)[2] != null) {
                                haut2 = false;
                                bas2 = false;
                                droite2 = false;
                                gauche2 = true;
                            } else if (memoire == KeyCode.D && pacman2.movePossible(modele.map)[3] != null) {
                                haut2 = false;
                                bas2 = false;
                                droite2 = true;
                                gauche2 = false;
                            }
                            //si la pause est activé dans le jeu, ca veut dire qu'un pacman a perdu sa vie
                            if (pacmanTask2 != null) { // Annuler la tâche précédente si elle existe
                                pacmanTask2.cancel(false);
                            }

                            //pose la bombe "derrière" le pacman
                            // dans ce controleur si le pacman est immobile entre deux mur il ne peut pas poser de bombe sur le coté : donc à modifier si besoin/possible
                            if (memoire == KeyCode.R && pacman2.nbrBombes != 0) {
                                if (haut2 && !bas2 && !gauche2 && !droite2 && pacman2.positionsPossible[1] != null) {
                                    pacman2.poseBombe(pacman2.positionsPossible[1], modele.map);
                                } else if (!haut2 && bas2 && !gauche2 && !droite2 && pacman2.positionsPossible[0] != null) {
                                    pacman2.poseBombe(pacman2.positionsPossible[0], modele.map);

                                } else if (!haut2 && !bas2 && gauche2 && !droite2 && pacman2.positionsPossible[3] != null) {
                                    pacman2.poseBombe(pacman2.positionsPossible[3], modele.map);

                                } else if (!haut2 && !bas2 && !gauche2 && droite2 && pacman2.positionsPossible[2] != null) {
                                    pacman2.poseBombe(pacman2.positionsPossible[2], modele.map);
                                }
                            }


                            if (modele.map.pacmans.size() == 2) {
                                pacmanTask2 = executorService.scheduleAtFixedRate(() -> {
                                    if (!modele.map.pause) {
                                        if (haut2 && !bas2 && !gauche2 && !droite2) {
                                            modele.map.deplacementPacman(pacman2.movePossible(modele.map)[0], modele.board, pacman2);
                                        } else if (!haut2 && bas2 && !gauche2 && !droite2) {
                                            modele.map.deplacementPacman(pacman2.movePossible(modele.map)[1], modele.board, pacman2);
                                        } else if (!haut2 && !bas2 && gauche2 && !droite2) {
                                            modele.map.deplacementPacman(pacman2.movePossible(modele.map)[2], modele.board, pacman2);
                                        } else if (!haut2 && !bas2 && !gauche2 && droite2) {
                                            modele.map.deplacementPacman(pacman2.movePossible(modele.map)[3], modele.board, pacman2);
                                        }
                                    } else {
                                        memoire = null;
                                        haut2 = false;
                                        bas2 = false;
                                        gauche2 = false;
                                        droite2 = false;
                                        System.out.println("Le jeu est en pause. Arrêt des mouvements de Pac-Man.");
                                        modele.map.deplacementPacman(null, modele.board, pacman2);
                                    }
                                }, 0, pacman2.speed, TimeUnit.MILLISECONDS);
                            }
                        }
                    }

        });
    }

    public void poseBombe(){
        scene.setOnKeyReleased(event -> {
            memoire = event.getCode();
            //pose la bombe "derrière" le pacman
            // dans ce controleur si le pacman est immobile entre deux mur il ne peut pas poser de bombe sur le coté : donc à modifier si besoin/possible
            if (memoire == KeyCode.M && pacman1.nbrBombes !=0){
                if (haut && !bas && !gauche && !droite && pacman1.positionsPossible[1]!=null ) {
                    pacman1.poseBombe(pacman1.positionsPossible[1], modele.map);
                } else if (!haut && bas && !gauche && !droite && pacman1.positionsPossible[0]!=null) {
                    pacman1.poseBombe(pacman1.positionsPossible[0], modele.map);
                } else if (!haut && !bas && gauche && !droite && pacman1.positionsPossible[3]!=null) {
                    pacman1.poseBombe(pacman1.positionsPossible[3], modele.map);
                } else if (!haut && !bas && !gauche && droite && pacman1.positionsPossible[2]!=null) {
                    pacman1.poseBombe(pacman1.positionsPossible[2], modele.map);
                }
            }

            if (memoire == KeyCode.R && pacman2.nbrBombes !=0){
                if (haut2 && !bas2 && !gauche2 && !droite2 && pacman2.positionsPossible[1]!=null ) {
                    pacman2.poseBombe(pacman2.positionsPossible[1], modele.map);
                } else if (!haut2 && bas2 && !gauche2 && !droite2 && pacman2.positionsPossible[0]!=null) {
                    pacman2.poseBombe(pacman2.positionsPossible[0], modele.map);
                } else if (!haut2 && !bas2 && gauche2 && !droite2 && pacman2.positionsPossible[3]!=null) {
                    pacman2.poseBombe(pacman2.positionsPossible[3], modele.map);
                } else if (!haut2 && !bas2 && !gauche2 && droite2 && pacman2.positionsPossible[2]!=null) {
                    pacman2.poseBombe(pacman2.positionsPossible[2], modele.map);
                }
            }
        });
    }
}
