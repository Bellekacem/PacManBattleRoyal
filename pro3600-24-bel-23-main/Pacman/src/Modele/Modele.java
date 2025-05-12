package Modele;

import java.util.Random;
public class Modele {
    public Map map;

    //Exemple de map

    /*
    % = mur
    . = pacgomme
    P = pacman
    # = pouvoirs
    F = fantome
    @ = bombes posé
    $ = bombe explosé
    / = portails
    € = bombe à récupérer
     */
    public String board = """
            %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
            %P................%%................Q%
            %.%%%%...%%%%%%...%%....%%%%%...%%%%.%
            %.%%%%...%%%%%%...%%....%%%%%...%%%%.%
            %....................................%
            %.%%%%...%......%%%%%%......%...%%%%.%
            %........%..................%........%
            %...%....%%%%............%%%%....%...%
            %..%%%...%....%..%FF%..%....%...%%%..%
            %........%....%..%FF%..%....%........%
            %...%%%%......%..%%%%..%......%%%%...%
            %...%.....%%%%%........%%%%%.....%...%
            %...%%%%%.....%........%.....%%%%%...%
            %.........%%%%%...%%...%%%%%.........%
            %.................%%.................%
            %..%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%..%
            %....................................%
            %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
            """;

    // initialisation du jeu avec la map, le pacman et les fantomes
    public Modele() {
        map = new Map(board);
    }

    //fin de jeu
    public boolean isEnd() {
        int nbrMort = 0;
        for(int k=0; k<map.pacmans.size(); k++){
            if(this.map.pacmans.get(k).getLife() == 0){
                nbrMort++;
                
            }
        }
        return (nbrMort == map.pacmans.size());
    }






}