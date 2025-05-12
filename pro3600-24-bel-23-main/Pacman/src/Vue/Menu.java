package Vue;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import java.awt.Font;

public class Menu extends JFrame {

    public JPanel commencerPanel;
    public JLabel interface1Label, titreLabel, jouerLabel, reglesLabel, exitLabel, musicLabel, interface2Label, interface3Label, interface4Label;
    public JLabel joueurs2Label, joueurs4Label, choixNbrJoueursLabel, commencerLabel, continueLabel,check0Label, check1Label, check2Label,check3Label, check4Label,  pseudosLabel, continue1Label, modeLabel,classiqueLabel, bombermanLabel,portailLabel, pouvoirLabel ;
    public JLabel joueurPseudo1Label, joueurPseudo2Label, joueurPseudoLabel;

    public File file1, file2, file3; // sound files
    public static Clip clipBgSound, clipHoverSound, clipCommencerSound;
    public static int nbrJoueurs;
    public static ArrayList<String> pseudos;
    public JTextField joueur1PseudoTxt, joueur2PseudoTxt, joueurPseudoTxt;

    public boolean soundOn = true;
    public boolean selected1 = false;
    public boolean selected2 = false;
    public static boolean selected_pouvoir = false;
    public static boolean selected_portail = false;
    public static boolean selected_bombe = false;

    public static boolean multijoueur;


    /**
     * Lancer l'application
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Menu frame = new Menu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     * @throws IOException
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     */
    public Menu() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1300, 800);
        /*
         *  Panel du premiere interface du menu .
         */
        commencerPanel = new JPanel();
        commencerPanel.setBackground(new Color(204, 0, 204));
        commencerPanel.setBorder(new LineBorder(Color.BLACK, 2));
        setContentPane(commencerPanel);
        commencerPanel.setLayout(null);

        //Lancer la music au lancement du jeu
        file1 = new File("pro3600-24-bel-23-main/Pacman/src/Vue/Ressources/start_sound.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file1);
        clipBgSound = AudioSystem.getClip();
        clipBgSound.open(audioStream);
        clipBgSound.loop(100);

        //Charger le son de selection
        file2 = new File("pro3600-24-bel-23-main/Pacman/src/Vue/Ressources/hoversound.wav");

        //Charger le son de lancement de jeu
        file3 = new File("pro3600-24-bel-23-main/Pacman/src/Vue/Ressources/commencersound.wav");

        //Label du la premiere interface
        interface1Label = new JLabel("");
        interface1Label.setBounds(0, 0, 1300, 800);
        interface1Label.setIcon(new ImageIcon(getClass().getResource("Ressources/TOTO.png")));
        commencerPanel.add(interface1Label);

        //Label de la 2eme interface
        interface2Label = new JLabel("");
        interface2Label.setBounds(0, 0, 1300, 800);
        interface2Label.setIcon(new ImageIcon(getClass().getResource("Ressources/TOTO.png")));
        commencerPanel.add(interface2Label);

        //Label de la 3eme interface
        interface3Label = new JLabel("");
        interface3Label.setBounds(0, 0, 1300, 800);
        interface3Label.setIcon(new ImageIcon(getClass().getResource("Ressources/TOTO.png")));
        commencerPanel.add(interface3Label);

        //Label de la 4eme interface
        interface4Label = new JLabel("");
        interface4Label.setBounds(0, 0, 1300, 800);
        interface4Label.setIcon(new ImageIcon(getClass().getResource("Ressources/TOTO.png")));
        commencerPanel.add(interface4Label);


        //Label du titre


        titreLabel = new JLabel("");
        titreLabel.setBounds(400, 30, 490, 120);
        titreLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/test0.png")));
        interface1Label.add(titreLabel);

        //Label du bouton Jouer + ses event handlers
        jouerLabel = new JLabel("");
        jouerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    playSound(file3 , clipCommencerSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                // passer à l'interface suivante
                repaint();
                interface1Label.setVisible(false);
                interface2Label.setVisible(true);
                //interface2Label.add(titreLabel);
                interface2Label.add(exitLabel);
                interface2Label.add(musicLabel);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                jouerLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/jouer_hover.png")));
                try {
                    playSound(file2 ,clipHoverSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
            @Override
            public void mouseExited(MouseEvent e) {
                jouerLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/jouer.png")));

            }
        });
        jouerLabel.setBounds(500, 300, 283, 80);
        jouerLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/jouer.png")));
        interface1Label.add(jouerLabel);

        //Label du bouton Regles de jeu + ses event handlers
        reglesLabel = new JLabel("");
        reglesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                reglesLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/regles_hover.png")));
                try {
                    playSound(file2 ,clipHoverSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }			}
            @Override
            public void mouseExited(MouseEvent e) {
                reglesLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/regles.png")));

            }
        });
        reglesLabel.setBounds(50, 600, 400, 49);
        reglesLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/regles.png")));
        interface1Label.add(reglesLabel);

        //Label du bouton Exit + ses event handlers
        exitLabel = new JLabel("");
        exitLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                exitLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/Exit_hover.png")));
                try {
                    playSound(file2 ,clipHoverSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                exitLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/exit.png")));
            }
        });
        exitLabel.setBounds(1000, 600, 120, 49);
        exitLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/exit.png")));
        interface1Label.add(exitLabel);

        musicLabel = new JLabel("");
        musicLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Arrêter/Lancer la music en cliquant sur l'icone du son
                if(soundOn == true)
                {
                    musicLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/music_off.png")));
                    clipBgSound.stop();
                    soundOn = false;
                }
                else if(soundOn == false) {
                    musicLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/music_on.png")));
                    clipBgSound.start();
                    soundOn = true;
                }
            }
        });
        musicLabel.setBounds(1200, 10, 60, 60);
        musicLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/music_on.png")));
        interface1Label.add(musicLabel);

        joueurs2Label = new JLabel("");
        joueurs2Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    playSound(file3 , clipCommencerSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                nbrJoueurs = 1;
                selected2 = false;
                selected1 = true;
                joueurs2Label.setIcon(new ImageIcon(getClass().getResource("Ressources/1jjh.png")));
                joueurs4Label.setIcon(new ImageIcon(getClass().getResource("Ressources/2jj.png")));
                joueurs2Label.add(check1Label);

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                joueurs2Label.setIcon(new ImageIcon(getClass().getResource("Ressources/1jjh.png")));
                try {
                    playSound(file2 ,clipHoverSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(selected1 == false)
                    joueurs2Label.setIcon(new ImageIcon(getClass().getResource("Ressources/1jj.png")));

            }

        });
        joueurs2Label.setBounds(350, 300, 200, 200);
        joueurs2Label.setIcon(new ImageIcon(getClass().getResource("Ressources/1jj.png")));
        interface2Label.add(joueurs2Label);

        joueurs4Label = new JLabel("");
        joueurs4Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    playSound(file3 , clipCommencerSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                nbrJoueurs = 2;
                selected2 = true;
                selected1 = false;
                joueurs4Label.setIcon(new ImageIcon(getClass().getResource("Ressources/2jjh.png")));
                joueurs2Label.setIcon(new ImageIcon(getClass().getResource("Ressources/1jj.png")));
                joueurs4Label.add(check1Label);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                joueurs4Label.setIcon(new ImageIcon(getClass().getResource("Ressources/2jjh.png")));
                try {
                    playSound(file2 ,clipHoverSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(selected2 == false)
                    joueurs4Label.setIcon(new ImageIcon(getClass().getResource("Ressources/2jj.png")));

            }

        });
        joueurs4Label.setBounds(750, 300, 200, 200);
        joueurs4Label.setIcon(new ImageIcon(getClass().getResource("Ressources/2jj.png")));
        interface2Label.add(joueurs4Label);
        choixNbrJoueursLabel = new JLabel("");
        choixNbrJoueursLabel.setBounds(200, 150, 892, 87);
        choixNbrJoueursLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/nbr_joueurs.png")));
        interface2Label.add(choixNbrJoueursLabel);


        continueLabel = new JLabel("");
        continueLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    playSound(file3 , clipCommencerSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if(selected1 || selected2 ) {
                    System.out.println("nbr de joueurs choisi :"+ nbrJoueurs);
                    repaint();
                    interface1Label.setVisible(false);
                    interface2Label.setVisible(false);
                    interface3Label.setVisible(true);
                    //interface3Label.add(titreLabel);
                    interface3Label.add(exitLabel);
                    interface3Label.add(musicLabel);
                    if(nbrJoueurs == 1) {
                        interface3Label.add(joueurPseudoLabel);
                    }
                    else if(nbrJoueurs == 2) {
                        interface3Label.add(joueurPseudo1Label);
                        interface3Label.add(joueurPseudo2Label);

                    }
                }

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                continueLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/continue_hover.png")));
                try {
                    playSound(file2 ,clipHoverSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                continueLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/continue.png")));

            }
        });
        continueLabel.setBounds(500, 600,250, 51);
        continueLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/continue.png")));
        interface2Label.add(continueLabel);

        pseudosLabel = new JLabel("");
        pseudosLabel.setBounds(350, 150, 583, 87);
        pseudosLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/pseudo.png")));
        interface3Label.add(pseudosLabel);

        continue1Label = new JLabel("");
        continue1Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    playSound(file3 , clipCommencerSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                interface1Label.setVisible(false);
                interface2Label.setVisible(false);
                interface3Label.setVisible(false);
                interface4Label.setVisible(true);
                interface4Label.add(exitLabel);
                interface4Label.add(musicLabel);
                interface4Label.add(commencerLabel);

                pseudos = new ArrayList<String>();
                //Ajouter les pseudos entrées à la liste
                if(nbrJoueurs == 2) {
                    pseudos.add(joueur1PseudoTxt.getText());
                    pseudos.add(joueur2PseudoTxt.getText());
                    interface4Label.add(modeLabel);
                    interface4Label.add(bombermanLabel);
                    interface4Label.add(portailLabel);
                    interface4Label.add(pouvoirLabel);

                }
                else if(nbrJoueurs == 1) {
                    pseudos.add(joueurPseudoTxt.getText());
                    interface4Label.add(pouvoirLabel);
                    interface4Label.add(portailLabel);
                    interface4Label.add(modeLabel);
                    //interface4Label.add(classiqueLabel);



                }

                //Afficher les élément de la liste ( pour le test )
                for(String pseudo: pseudos)
                {
                    System.out.println (pseudo);
                    /*
                     * la liste contient bien les pseudos de joueurs, et puisque elle est statique
                     * on peut acceder ses elements a partir de la classe Joueur ( idem pour nbrJoueurs).
                     * les pseudos sont - par default - nommées joueur1..4
                     */
                }


            }
            @Override
            public void mouseEntered(MouseEvent e) {
                continue1Label.setIcon(new ImageIcon(getClass().getResource("Ressources/continue_hover.png")));
                try {
                    playSound(file2 ,clipHoverSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                continue1Label.setIcon(new ImageIcon(getClass().getResource("Ressources/continue.png")));

            }
        });
        continue1Label.setBounds(500, 600, 250, 51);
        continue1Label.setIcon(new ImageIcon(getClass().getResource("Ressources/continue.png")));
        interface3Label.add(continue1Label);

        // text area joueur 1 : en mode multi !
        joueurPseudo1Label = new JLabel("");
        joueurPseudo1Label.setBounds(270, 300, 200, 50);

        joueur1PseudoTxt = new JTextField();
        joueur1PseudoTxt.setText("Joueur 1");
        joueur1PseudoTxt.setFont(new Font("Arial", Font.BOLD, 14));
        joueur1PseudoTxt.setBounds(0, 0, 200, 50);
        joueurPseudo1Label.add(joueur1PseudoTxt);
        joueur1PseudoTxt.setColumns(10);

        // text area joueur 2
        joueurPseudo2Label = new JLabel("");
        joueurPseudo2Label.setBounds(770, 300, 200, 50);

        joueur2PseudoTxt = new JTextField();
        joueur2PseudoTxt.setText("Joueur 2");
        joueur2PseudoTxt.setFont(new Font("Arial", Font.BOLD, 14));
        joueur2PseudoTxt.setBounds(0, 0, 200, 50);
        joueurPseudo2Label.add(joueur2PseudoTxt);
        joueur2PseudoTxt.setColumns(10);

        // text area joueur 1 : en mode solo !!
        joueurPseudoLabel = new JLabel("");
        joueurPseudoLabel.setBounds(550, 300, 200, 50);

        joueurPseudoTxt = new JTextField();
        joueurPseudoTxt.setText("Joueur 1");
        joueurPseudoTxt.setFont(new Font("Arial", Font.BOLD, 14));
        joueurPseudoTxt.setBounds(0, 0, 200, 50);
        joueurPseudoLabel.add(joueurPseudoTxt);
        joueurPseudoTxt.setColumns(10);

        //Interface 4

        modeLabel = new JLabel("");
        modeLabel.setBounds(400, 25, 490, 120);
        modeLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/mode0.png")));

        pouvoirLabel = new JLabel("");
        pouvoirLabel.setBounds(500, 250, 385, 75);
        pouvoirLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/PouvoirBleu.png")));

        portailLabel = new JLabel("");
        portailLabel.setBounds(500, 350, 385, 75);
        portailLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/PortailBleu.png")));

        classiqueLabel = new JLabel("");
        classiqueLabel.setBounds(500, 450, 385, 75);
        classiqueLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/classique.png")));

        bombermanLabel = new JLabel("");
        bombermanLabel.setBounds(500, 450, 385, 75);
        bombermanLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/bombermanbleu.png")));

        commencerLabel = new JLabel("");
        commencerLabel.setBounds(350, 600, 544, 49);
        commencerLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/commencer.png")));

        pouvoirLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    playSound(file3 , clipCommencerSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if (selected_pouvoir == false) {
                    selected_pouvoir= true;
                    pouvoirLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/PouvoirVert.png")));
                    pouvoirLabel.add(check2Label);
                    check2Label.setVisible(true);


                }
                else {
                    selected_pouvoir=false;
                    pouvoirLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/PouvoirBleu.png")));
                    check2Label.setVisible(false);


                }

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                pouvoirLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/PouvoirVert.png")));
                try {
                    playSound(file2 ,clipHoverSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(selected_pouvoir == false)
                    pouvoirLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/PouvoirBleu.png")));

            }

        });


        portailLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    playSound(file3 , clipCommencerSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if (selected_portail == false) {
                    selected_portail= true;
                    portailLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/PortailVert.png")));
                    portailLabel.add(check3Label);
                    check3Label.setVisible(true);
                }
                else {
                    selected_portail=false;
                    portailLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/PortailBleu.png")));
                    check3Label.setVisible(false);


                }

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                portailLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/PortailVert.png")));
                try {
                    playSound(file2 ,clipHoverSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(selected_portail == false) {
                    portailLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/PortailBleu.png")));}

            }

        });


        bombermanLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    playSound(file3 , clipCommencerSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if (selected_bombe == false) {
                    selected_bombe= true;
                    bombermanLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/bombermanvert.png")));
                    bombermanLabel.add(check4Label);
                    check4Label.setVisible(true);

                }
                else {
                    selected_bombe=false;
                    bombermanLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/bombermanbleu.png")));
                    check4Label.setVisible(false);

                }

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                bombermanLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/bombermanvert.png")));
                try {
                    playSound(file2 ,clipHoverSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(selected_bombe == false)
                    bombermanLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/bombermanbleu.png")));

            }

        });


        commencerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    playSound(file3 , clipCommencerSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                //Affichage des modes choisis.
                boolean[] modes= { selected1, selected2, selected_bombe, selected_portail, selected_pouvoir};
                //selected1 et selected2 correspondent aux modes 1 et 2 joueurs.
                for (int k=0; k<modes.length; k++) {
                    System.out.println(modes[k]);
                }

                Menu.this.dispose(); //Ferme la fenêtre de menu
                System.out.println("Fermeture du menu !");

                if(nbrJoueurs==1){
                    multijoueur = false;
                } else {
                    multijoueur = true;
                }
                
                Game.launch(Game.class);

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                commencerLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/commencer_hover.png")));
                try {
                    playSound(file2 ,clipHoverSound);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                commencerLabel.setIcon(new ImageIcon(getClass().getResource("Ressources/commencer.png")));
            }
        });



        // Le gif de la validation du mode solo ou multijoueur.

        check1Label = new JLabel("");
        check1Label.setBounds(120, 90, 120, 120);
        check1Label.setIcon(new ImageIcon(getClass().getResource("Ressources/check.gif")));


        //Confirmations des modes avec les gifs. Ce sont les mêmes mais ils sont indépendants.

        check2Label = new JLabel("");
        check2Label.setBounds(280, -30, 120, 120);
        check2Label.setIcon(new ImageIcon(getClass().getResource("Ressources/check.gif")));

        check3Label = new JLabel("");
        check3Label.setBounds(280, -30, 120, 120);
        check3Label.setIcon(new ImageIcon(getClass().getResource("Ressources/check.gif")));

        check4Label = new JLabel("");
        check4Label.setBounds(280, -30, 120, 120);
        check4Label.setIcon(new ImageIcon(getClass().getResource("Ressources/check.gif")));


    }

    public void playSound(File file, Clip clip) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }

}