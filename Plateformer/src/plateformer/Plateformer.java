package plateformer;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;


/**
 * Classe permettant de lancer le jeu, comportant les méthodes héritées de StateBasedGame. 
 * @author Emmanuel Dietlin, Chloé Demuynck, Olivier Bemba et Maxence Durieu.
 *
 */
public class Plateformer extends StateBasedGame {

	/**
	 * Stocke le nombre de déplacements disponibles pour les entités (une entité n'utilise pas forcément tous les types de déplacements)
	 */
	private int nbrDéplacements;
	/**
	 * Stocke la taille du jeu sur l'écran
	 */
	
	private static int xGame = 800;
	private static int yGame = 480;
	
	/**
	 * Stocke le nombre de gemmes collectés lors de la partie
	 */
	private int nbG = 0;
	
	/**
	 * Constructeur de la classe Plateformer. 
	 * Fait uniquement un appel vers le constructeur de BasicGame.
	 */
	public Plateformer() {
		super("Jeu de Plateforme");
		nbrDéplacements = EntityDirection.NBR_MOVEMENTS.ordinal();
	}
	/**
	 * Méthode main de la classe. Construit un nouveau AppGameContainer.
	 * @param args String[] : 
	 * 		paramètre de la méthode main
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		
        new AppGameContainer(new Plateformer(), xGame, yGame, false).start();
    }
	  
	  /**
	   * classe de l'écran principal (menu)
	   */
	 
	  public class MainScreenGameState extends BasicGameState {
		  
			/**
			 *  Indique l'ID de la phase de jeu
			 */
			public static final int ID = 1;
			
			/**
			 * Stocke l'image du fond d'écran de menu principal
			 */
			  private Image background;
			  
			  /**
			   * Stocke le jeu pour l'extension StateBasedGame
			   */
			  private StateBasedGame game;
			  
			  /**
			   * Stocke la position de chaque ligne du texte 
			   */
			  private int ligne1Hauteur = 200;
			  private int ligne1Largeur = 240;
			  private int ligne2Hauteur = 250;
			  private int ligne2Largeur = 230;
					 
			  

			 /**
			  * Méthode qui initialise l'écran principal
			  */
			  public void init(GameContainer container, StateBasedGame game) throws SlickException {
			    this.game = game;
			    this.background = new Image("resources/1/Menu/Background.png");
			  }

			  /**
			   * Méthode qui affiche les différents éléments, le fond, le texte
			   */
			  public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
			    background.draw(0, 0, container.getWidth(), container.getHeight());
			    g.drawString("Appuyer sur Entre pour lancer le jeu", ligne1Largeur, ligne1Hauteur);
			    g.drawString("Appuyer sur Rshift pour les informations", ligne2Largeur, ligne2Hauteur);
			  }

			  /**
			   * Méthode update (vide) 
			   */
			  public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
			  }

			 /**
			  * Méthode qui indique ce qu'il se passe lorsqu'on appuie sur une touche particulière (ici changement de phase de jeu)
			  */
			  public void keyPressed(int key, char c) {
				  switch(key) {
					case Input.KEY_ENTER:
						game.enterState(MapGameState.ID);
						break;	
						
					case Input.KEY_RSHIFT:
						game.enterState(InfoGameState.ID);
						break;
					}
				  
			    
			  }

			  /**
			   * L'identifiant permet d'identifier les differentes boucles.
			   * 
			   */
			  @Override
			  public int getID() {
			    return ID;
			  }
			}


	  /**
	   * classe comportant le jeu principal
	   */
	  public class MapGameState extends BasicGameState{
		  
		  /**
		   * Indique l'ID de cette phase de jeu
		   */
		  			
			public static final int ID = 2;
			/**
			* Contient le GameContainer du jeu.
			*/
			private GameContainer gc;
			/**
			 * Contient les morceaux de la carte choisis aléatoirement
			 */
			private Maps cartes;
			/**
			* contient le joueur du jeu.
			*/
			private Player joueur;
			/**
			* contient la liste des méchants du jeu.
			*/
			private ArrayList<Monster> méchants;
			/**
			 * Contient la liste des gems;
			 */
			private ArrayList<Collectible> gems;
			/**
			* stocke la position en x de la caméra
			*/
			private float xCamera;
			/**
			* stocke la position en y de la caméra 
			*/
			private float yCamera;
			private double ytemp = 400;
			/**
			 * Stocke la liste des morceaux de cartes disponibles.
			 */
			private String[] listeCartes;
			/**
			 * Stocke le numéro de la carte dans laquelle se trouve le joueur.
			 */
			private int playerMapNbr;
			
			/**
			 * stocke des min et max pour le calcul ci après
			 */
			private int min1 = 0;
			private int max1 = 3;
			private int min2 = 4;
			private int max2 = 7;
			private int min3 = 8;
			private int max3 = 11;
			private int min4 = 12;
			private int max4 = 15;
		
			
			
			/**
			 * Contient des entiers pour la génération aléatoire de bouts de maps. 
			 * Le background de taille 845*4 a été divisé en 4 pour la création de l'interface graphique. 
			 * Cela donne 4 "groupes" de map avec un fond différent. On va mettre bout à bout à la suite les bouts 
			 * de map à la suite pour que le fond coincide bien sur tout le niveau.
			 * Ici on génère des entiers aléatoires pour qu'aléatoirement une carte à la position carteXAleat dans la liste de map
			 * apparaisse en position i sur le terrain. 
			 * Chaque groupe possède 4 maps possibles, d'où la génération suivante d'entier
			 */
			
			int carte1Aleat = min1 + (int)(Math.random() * ((max1 - min1) + 1)); //choix entre 0 et 3
			int carte2Aleat = min2 + (int)(Math.random() * ((max2 - min2) + 1)); // choix entre 4 et 7
			int carte3Aleat = min3 + (int)(Math.random() * ((max3 - min3) + 1));// choix entre 8 et 11
			int carte4Aleat = min4 + (int)(Math.random() * ((max4 - min4) + 1));// choix entre 12 et 15
			int carte5Aleat = min1 + (int)(Math.random() * ((max1 - min1) + 1));
			int carte6Aleat = min2 + (int)(Math.random() * ((max2 - min2) + 1));
			int carte7Aleat = min3 + (int)(Math.random() * ((max3 - min3) + 1));
			int carte8Aleat = min4 + (int)(Math.random() * ((max4 - min4) + 1));
			int carte9Aleat = min1 + (int)(Math.random() * ((max1 - min1) + 1));
			int carte10Aleat = min2 + (int)(Math.random() * ((max2 - min2) + 1));
			int carte11Aleat = min3 + (int)(Math.random() * ((max3 - min3) + 1));
			int carte12Aleat = min4 + (int)(Math.random() * ((max4 - min4) + 1));
			
			/**
			 * Contient la taille d'un bout de map 
			 */
			private int taillemap = 845;
			
			/**
			 * Contient le nombre de bouts de map sur un niveau
			 */
			private int nbmaps = 13;
			
			private int calque0 = 0;
			private int calque1 = 1;
			private int calque2 = 2;
			private int calque3 = 3;
			private int calque4 = 4;
			private int calque5 = 5;
			
			private int coordFin=10240;
			
			
			/**
			 * Méthode qui initialise les objets du jeu
			 */
			public void init(GameContainer gc, StateBasedGame game) throws SlickException {
				
					/**
					 * Initialisation du gamecontainer
					 */
				this.gc = gc;
				
				/**
				 * Initialisation de la liste de carte 
				 */
				cartes = new Maps();
				listeCartes = new String[] {"resources/1/Map_1_1.tmx","resources/1/Map_1_2.tmx","resources/1/Map_1_3.tmx","resources/1/Map_1_4.tmx","resources/1/Map_2_1.tmx",
						"resources/1/Map_2_2.tmx","resources/1/Map_2_3.tmx","resources/1/Map_2_4.tmx","resources/1/Map_3_1.tmx","resources/1/Map_3_2.tmx",
						"resources/1/Map_3_3.tmx","resources/1/Map_3_4.tmx","resources/1/Map_4_1.tmx","resources/1/Map_4_2.tmx","resources/1/Map_4_3.tmx","resources/1/Map_4_4.tmx"};
				
				cartes.addMap(listeCartes[carte1Aleat]);
				cartes.addMap(listeCartes[carte2Aleat]);
				cartes.addMap(listeCartes[carte3Aleat]);
				cartes.addMap(listeCartes[carte4Aleat]);
				cartes.addMap(listeCartes[carte5Aleat]);
				cartes.addMap(listeCartes[carte6Aleat]);
				cartes.addMap(listeCartes[carte7Aleat]);
				cartes.addMap(listeCartes[carte8Aleat]);
				cartes.addMap(listeCartes[carte9Aleat]);
				cartes.addMap(listeCartes[carte10Aleat]);
				cartes.addMap(listeCartes[carte11Aleat]);
				cartes.addMap(listeCartes[carte12Aleat]);
				cartes.addMap("resources/1/fin.tmx");
				
				/**
				 * Initialisation du joueur 
				 */
				joueur = new Player("resources/1/Fumiko.png",24,32,12,10,204);
				joueur.setAnimation(EntityDirection.MOVE_RIGHT, 0, 2, 1);
				joueur.setAnimation(EntityDirection.MOVE_LEFT, 0, 2, 3);
				joueur.setAnimation(EntityDirection.JUMP_RIGHT, 10, 10, 1);
				joueur.setAnimation(EntityDirection.JUMP_LEFT, 10, 10, 3);
				joueur.setAnimation(EntityDirection.STILL, 0, 2, 2);
				playerMapNbr = 0;
				joueur.updateFutureX(joueur.getX());
				joueur.updateFutureY(joueur.getY());

				
				//Liste qui contient les gems
				gems = new ArrayList<Collectible>();
				
				//Création des gemmes
				int[] Xg = {1300,0,1400,1285,2085,2950,2860,2900}; //Ces deux listes correspondent aux abscisses et aux ordonnées voulues des gemmes qui vont apparaître selon la map qui apparaît. Les coordonnées (Xg[1],Yg[1]) correspondent au cas où carte2/6/10Aleat = 5, il ne sera pas traité. 
				int[] Yg = {200,0,100,290,190,50,250,420}; //Les coordonnées ont été trouvées en testant carte par carte.
				int[] caracteristiquesGs = {15,12,5,3};
				//Nous allons faire apparaître des gemmes seulement sur les bouts de cartes 2_1, 2_3, 2_4, 3_3, 4_2, 4_3, 4_4
				if (carte2Aleat != 5) { //carte 2_1, 2_3 ou 2_4. Carte2Aleat != 5 <-> On ne traite pas la carte carte 2_2
					Collectible gem1 = new Collectible("resources/1/gem.png", caracteristiquesGs[0], caracteristiquesGs[1], caracteristiquesGs[2], Xg[carte2Aleat-4], Yg[carte2Aleat-4]);
					gems.add(gem1);
					gem1.créerAnimations(caracteristiquesGs[3]);
				} 
				if (carte6Aleat != 5) { //carte 2_1, 2_3 ou 2_4
					Collectible gem2 = new Collectible("resources/1/gem.png", caracteristiquesGs[0], caracteristiquesGs[1], caracteristiquesGs[2], 4*taillemap+Xg[carte6Aleat-4], Yg[carte6Aleat-4]);
					gems.add(gem2);
					gem2.créerAnimations(caracteristiquesGs[3]);
				} 
				if (carte10Aleat != 5) { //carte 2_1, 2_3 ou 2_4
					Collectible gem3 = new Collectible("resources/1/gem.png", caracteristiquesGs[0], caracteristiquesGs[1], caracteristiquesGs[2], 8*taillemap+Xg[carte10Aleat-4], Yg[carte10Aleat-4]);
					gems.add(gem3);
					gem3.créerAnimations(caracteristiquesGs[3]);
				}
				if (carte3Aleat == 10) { //carte 3_3
					Collectible gem4 = new Collectible("resources/1/gem.png", caracteristiquesGs[0], caracteristiquesGs[1], caracteristiquesGs[2], Xg[4], Yg[4]);
					gems.add(gem4);
					gem4.créerAnimations(caracteristiquesGs[3]);
				}
				if (carte7Aleat == 10) { //carte 3_3
					Collectible gem5 = new Collectible("resources/1/gem.png", caracteristiquesGs[0], caracteristiquesGs[1], caracteristiquesGs[2], 4*taillemap+Xg[4], Yg[4]);
					gems.add(gem5);
					gem5.créerAnimations(caracteristiquesGs[3]);
				}
				if (carte11Aleat == 10) { //carte 3_3	
					Collectible gem6 = new Collectible("resources/1/gem.png", caracteristiquesGs[0], caracteristiquesGs[1], caracteristiquesGs[2], 8*taillemap+Xg[4], Yg[4]);
					gems.add(gem6);
					gem6.créerAnimations(caracteristiquesGs[3]);
				}
				if (carte4Aleat != 12) { //carte 4_2, 4_3 ou 4_4, on ne traite pas la carte 4_1 <-> carte4Aleat != 12
					Collectible gem7 = new Collectible("resources/1/gem.png", caracteristiquesGs[0], caracteristiquesGs[1], caracteristiquesGs[2], Xg[carte4Aleat-8], Yg[carte4Aleat-8]);
					gems.add(gem7);
					gem7.créerAnimations(caracteristiquesGs[3]);
				}
				if (carte8Aleat != 12) { //carte 4_2, 4_3 ou 4_4
					Collectible gem8 = new Collectible("resources/1/gem.png", caracteristiquesGs[0], caracteristiquesGs[1], caracteristiquesGs[2], 4*taillemap + Xg[carte8Aleat-8], Yg[carte8Aleat-8]);
					gems.add(gem8);
					gem8.créerAnimations(caracteristiquesGs[3]);
				}
				if (carte4Aleat != 12) { //carte 4_2, 4_3 ou 4_4
					Collectible gem9 = new Collectible("resources/1/gem.png", caracteristiquesGs[0], caracteristiquesGs[1], caracteristiquesGs[2], 8*taillemap + Xg[carte12Aleat-8], Yg[carte12Aleat-8]);
					gems.add(gem9);
					gem9.créerAnimations(caracteristiquesGs[3]);
				}
				
				//Liste qui va contenir les méchants du jeu
				méchants = new ArrayList<Monster>();
				
				//Création des yeux volants
				int[] yY = {175, 105, 420, 370}; //Liste de tous les ordonnées des yeux qui peuvent apparaître
				int[] caracteristiquesY = {32,32,12,taillemap,3,7}; // caracteristiques = {wide, height, nbSprite, x, nbAnim, xF}
				EntityDirection[] directionsY = {EntityDirection.UP, EntityDirection.LEFT, EntityDirection.STILL};
				Monster oeil1 = new Monster("resources/1/eyeball spritesheet.png", caracteristiquesY[0], caracteristiquesY[1], caracteristiquesY[2], caracteristiquesY[3], yY[carte1Aleat], caracteristiquesY[4], directionsY);
				méchants.add(oeil1);
				oeil1.créerAnimations(caracteristiquesY[5]);
				
				Monster oeil2 = new Monster("resources/1/eyeball spritesheet.png", caracteristiquesY[0], caracteristiquesY[1], caracteristiquesY[2], caracteristiquesY[3]+4*taillemap, yY[carte5Aleat], caracteristiquesY[4], directionsY);
				méchants.add(oeil2);
				oeil2.créerAnimations(caracteristiquesY[5]);
				
				Monster oeil3 = new Monster("resources/1/eyeball spritesheet.png", caracteristiquesY[0], caracteristiquesY[1], caracteristiquesY[2], caracteristiquesY[3]+8*taillemap, yY[carte9Aleat], caracteristiquesY[4], directionsY);
				méchants.add(oeil3);
				oeil3.créerAnimations(caracteristiquesY[5]);
				
				
			
				
				

				//Creation des aigles
				int[] yA= {75, 250, 40, 280}; //Liste des ordonnées des aigles qui vont apparaître sur la map, idem trouvées au cas par cas
				int[] caracteristiquesE = {40,40,5,taillemap,1,3}; // caracteristiques = {wide, height, nbSprite, x, nbAnim, xF}
				EntityDirection[] directionsE = {EntityDirection.LEFT};
				Monster aigle1 = new Monster("resources/1/PNG/eagle-attack.png", caracteristiquesE[0], caracteristiquesE[1], caracteristiquesE[2], caracteristiquesE[3], yA[carte1Aleat], caracteristiquesE[4], directionsE);
				méchants.add(aigle1);
				aigle1.créerAnimations(caracteristiquesE[5]);
				
				Monster aigle2 = new Monster("resources/1/PNG/eagle-attack.png", caracteristiquesE[0], caracteristiquesE[1], caracteristiquesE[2], caracteristiquesE[3]+4*taillemap, yA[carte5Aleat], caracteristiquesE[4], directionsE);
				méchants.add(aigle2);
				aigle2.créerAnimations(caracteristiquesE[5]);
				
				Monster aigle3 = new Monster("resources/1/PNG/eagle-attack.png", caracteristiquesE[0], caracteristiquesE[1], caracteristiquesE[2], caracteristiquesE[3]+8*taillemap, yA[carte9Aleat], caracteristiquesE[4], directionsE);
				méchants.add(aigle3);
				aigle3.créerAnimations(caracteristiquesE[5]);
				
				//Création des sbires
				int[] caracteristiquesM = {45,66,6,2,2}; //caracteristiques = {wide, height, nbSprite, nbAnim, xF}
				int[] xS = {2200,250,2905,1960}; //Listes des coordonnées des sbires qui apparaissent
				int[] yS = {400,410,420,370}; //idem trouvées au cas par cas
				EntityDirection[] directionsM = {EntityDirection.RIGHT, EntityDirection.LEFT};
				//Pour le sbire on ne va le faire apparaître que pour pour les maps 1_3, 3_1, 3_3 et 4_2
				if (carte1Aleat == 2) {  
					Monster sbire1 = new Monster("resources/1/minion.png", caracteristiquesM[0], caracteristiquesM[1], caracteristiquesM[2], xS[1], yS[1], caracteristiquesM[3], directionsM);
					méchants.add(sbire1);
					sbire1.créerAnimations(caracteristiquesM[4]);
				}
				if (carte5Aleat == 2) { //carte 1_3
					Monster sbire2 = new Monster("resources/1/minion.png", caracteristiquesM[0], caracteristiquesM[1], caracteristiquesM[2], 4*taillemap + xS[1], yS[1], caracteristiquesM[3], directionsM);
					méchants.add(sbire2);
					sbire2.créerAnimations(caracteristiquesM[4]);
				}
				if (carte9Aleat == 2) { //carte 1_3
					Monster sbire3 = new Monster("resources/1/minion.png", caracteristiquesM[0], caracteristiquesM[1], caracteristiquesM[2], 8*taillemap + xS[1], yS[1], caracteristiquesM[3], directionsM);
					méchants.add(sbire3);
					sbire3.créerAnimations(caracteristiquesM[4]);
				}
				if (carte3Aleat == 8 || carte3Aleat == 11) { //carte 3_1 ou 3_3
					Monster sbire4 = new Monster("resources/1/minion.png", caracteristiquesM[0], caracteristiquesM[1], caracteristiquesM[2], xS[carte3Aleat%4], yS[carte3Aleat%4], caracteristiquesM[3], directionsM);
					méchants.add(sbire4);
					sbire4.créerAnimations(caracteristiquesM[4]);
				} 
				if (carte7Aleat == 8 || carte7Aleat == 11) { //carte 3_1 ou 3_3
					Monster sbire5 = new Monster("resources/1/minion.png", caracteristiquesM[0], caracteristiquesM[1], caracteristiquesM[2], 4*taillemap + xS[carte7Aleat%4], yS[carte7Aleat%4], caracteristiquesM[3], directionsM);
					méchants.add(sbire5);
					sbire5.créerAnimations(caracteristiquesM[4]);
				} 
				if (carte11Aleat == 8 || carte11Aleat == 11) { //carte 3_1 ou 3_3
					Monster sbire6 = new Monster("resources/1/minion.png", caracteristiquesM[0], caracteristiquesM[1], caracteristiquesM[2], 8*taillemap + xS[carte11Aleat%4], yS[carte11Aleat%4], caracteristiquesM[3], directionsM);
					méchants.add(sbire6);
					sbire6.créerAnimations(caracteristiquesM[4]);
				} 
				if (carte4Aleat == 13) { //carte 4_2
					Monster sbire7 = new Monster("resources/1/minion.png", caracteristiquesM[0], caracteristiquesM[1], caracteristiquesM[2], xS[2], yS[2], caracteristiquesM[3], directionsM);
					méchants.add(sbire7);
					sbire7.créerAnimations(caracteristiquesM[4]);
				}
				if (carte8Aleat == 13) { //carte 4_2
					Monster sbire8 = new Monster("resources/1/minion.png", caracteristiquesM[0], caracteristiquesM[1], caracteristiquesM[2], 4*taillemap + xS[2], yS[2], caracteristiquesM[3], directionsM);
					méchants.add(sbire8);
					sbire8.créerAnimations(caracteristiquesM[4]);
				}
				if (carte12Aleat == 13) { //carte 4_2
					Monster sbire9 = new Monster("resources/1/minion.png", caracteristiquesM[0], caracteristiquesM[1], caracteristiquesM[2], 8*taillemap + xS[2], yS[2], caracteristiquesM[3], directionsM);
					méchants.add(sbire9);
					sbire9.créerAnimations(caracteristiquesM[4]);
				}
				
				//Creation d'Andromalius
				int[] caracteristiquesA = {57,88,24,3,7}; //caracteristiques = {wide, height, nbSprite,nbAnim, xF}
				int[] xAn = {1000, 1580, 1900, 2820}; //Listes des coordonnées des Andromalius qui apparaissent sur la carte
				int[] yAn = {200, 240, 190, 280}; //Pareil, mais trouvées au cas par cas
				EntityDirection[] directionsA = {EntityDirection.RIGHT, EntityDirection.LEFT, EntityDirection.STILL};
				//Pour l'Andromalius, on ne le fait apparaître que sur les maps 2_1, 2_2, 3_3 et 4_4
				if (carte2Aleat == 4 || carte2Aleat == 5) { //Carte 2_1 ou 2_2
					Monster andromalius1 = new Monster("resources/1/andromalius.png", caracteristiquesA[0], caracteristiquesA[1], caracteristiquesA[2], xAn[carte2Aleat%4], yAn[carte2Aleat%4], caracteristiquesA[3], directionsA);
					méchants.add(andromalius1);
					andromalius1.créerAnimations(caracteristiquesA[4]);
				}
				if (carte6Aleat == 4 || carte6Aleat == 5) { //Carte 2_1 ou 2_2
					Monster andromalius2 = new Monster("resources/1/andromalius.png", caracteristiquesA[0], caracteristiquesA[1], caracteristiquesA[2], 4*taillemap + xAn[carte6Aleat%4], yAn[carte6Aleat%4], caracteristiquesA[3], directionsA);
					méchants.add(andromalius2);
					andromalius2.créerAnimations(caracteristiquesA[4]);
				}
				if (carte10Aleat == 4 || carte10Aleat == 5) { //Carte 2_1 ou 2_2
					Monster andromalius3 = new Monster("resources/1/andromalius.png", caracteristiquesA[0], caracteristiquesA[1], caracteristiquesA[2], 8*taillemap + xAn[carte10Aleat%4], yAn[carte10Aleat%4], caracteristiquesA[3], directionsA);
					méchants.add(andromalius3);
					andromalius3.créerAnimations(caracteristiquesA[4]);
				}
				if (carte3Aleat == 10) { //Carte 3_3
					Monster andromalius4 = new Monster("resources/1/andromalius.png", caracteristiquesA[0], caracteristiquesA[1], caracteristiquesA[2], xAn[carte3Aleat%4], yAn[carte3Aleat%4], caracteristiquesA[3], directionsA);
					méchants.add(andromalius4);
					andromalius4.créerAnimations(caracteristiquesA[4]);
				}
				if (carte7Aleat == 10) { //Carte 3_3
					Monster andromalius5 = new Monster("resources/1/andromalius.png", caracteristiquesA[0], caracteristiquesA[1], caracteristiquesA[2], 4*taillemap + xAn[carte7Aleat%4], yAn[carte7Aleat%4], caracteristiquesA[3], directionsA);
					méchants.add(andromalius5);
					andromalius5.créerAnimations(caracteristiquesA[4]);
				}
				if (carte11Aleat == 10) { //Carte 3_3
					Monster andromalius6 = new Monster("resources/1/andromalius.png", caracteristiquesA[0], caracteristiquesA[1], caracteristiquesA[2], 8*taillemap + xAn[carte11Aleat%4], yAn[carte11Aleat%4], caracteristiquesA[3], directionsA);
					méchants.add(andromalius6);
					andromalius6.créerAnimations(caracteristiquesA[4]);
				}
				if (carte4Aleat == 15) { //Carte 4_4
					Monster andromalius7 = new Monster("resources/1/andromalius.png", caracteristiquesA[0], caracteristiquesA[1], caracteristiquesA[2], xAn[carte4Aleat%4], yAn[carte4Aleat%4], caracteristiquesA[3], directionsA);
					méchants.add(andromalius7);
					andromalius7.créerAnimations(caracteristiquesA[4]);
				}
				if (carte8Aleat == 15) { //Carte 4_4
					Monster andromalius8 = new Monster("resources/1/andromalius.png", caracteristiquesA[0], caracteristiquesA[1], caracteristiquesA[2], 4*taillemap + xAn[carte8Aleat%4], yAn[carte8Aleat%4], caracteristiquesA[3], directionsA);
					méchants.add(andromalius8);
					andromalius8.créerAnimations(caracteristiquesA[4]);
				}
				if (carte12Aleat == 15) { //Carte 4_4
					Monster andromalius9 = new Monster("resources/1/andromalius.png", caracteristiquesA[0], caracteristiquesA[1], caracteristiquesA[2], 8*taillemap + xAn[carte12Aleat%4], yAn[carte12Aleat%4], caracteristiquesA[3], directionsA);
					méchants.add(andromalius9);
					andromalius9.créerAnimations(caracteristiquesA[4]);
				}
				
				gc.setTargetFrameRate(60);
				
			}
			
			/**
			 * Méthode qui affiche les différents éléments sur la carte
			 */
			public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		   
				g.translate(xCamera, yCamera);
				
				/**
				 * Affiche les 4 calques en arrière plan du joueur (Background, Background2(grotte) , Water, Plateformes)
				 * pour la carte à l'entier carte1Aleat
				 */
				this.cartes.getMap(0).render(0, 0, calque0);
				this.cartes.getMap(0).render(0, 0, calque1);
				this.cartes.getMap(0).render(0, 0, calque2);
				this.cartes.getMap(0).render(0, 0, calque3);
				this.cartes.getMap(0).render(0, 0, calque4);
				this.cartes.getMap(0).render(0, 0, calque5);
				/**
				 * Affichage idem pour les 11 autres bouts de map de l'arrière plan ainsi que la fin du niveau
				 */
				this.cartes.getMap(1).render(taillemap, 0, calque0);
				this.cartes.getMap(1).render(taillemap, 0, calque1);
				this.cartes.getMap(1).render(taillemap, 0, calque2);
				this.cartes.getMap(1).render(taillemap, 0, calque3);
				this.cartes.getMap(1).render(taillemap, 0, calque4);
				this.cartes.getMap(1).render(taillemap, 0, calque5);
				
				this.cartes.getMap(2).render(2*taillemap, 0, calque0);
				this.cartes.getMap(2).render(2*taillemap, 0, calque1);
				this.cartes.getMap(2).render(2*taillemap, 0, calque2);
				this.cartes.getMap(2).render(2*taillemap, 0, calque3);
				this.cartes.getMap(2).render(2*taillemap, 0, calque4);
				this.cartes.getMap(2).render(2*taillemap, 0, calque5);
				
				this.cartes.getMap(3).render(3*taillemap, 0, calque0);
				this.cartes.getMap(3).render(3*taillemap, 0, calque1);
				this.cartes.getMap(3).render(3*taillemap, 0, calque2);
				this.cartes.getMap(3).render(3*taillemap, 0, calque3);
				this.cartes.getMap(3).render(3*taillemap, 0, calque4);
				this.cartes.getMap(3).render(3*taillemap, 0, calque5);
				
				
				this.cartes.getMap(4).render(4*taillemap, 0, calque0);
				this.cartes.getMap(4).render(4*taillemap, 0, calque1);
				this.cartes.getMap(4).render(4*taillemap, 0, calque2);
				this.cartes.getMap(4).render(4*taillemap, 0, calque3);
				this.cartes.getMap(4).render(4*taillemap, 0, calque4);
				this.cartes.getMap(4).render(4*taillemap, 0, calque5);
				
				this.cartes.getMap(5).render(5*taillemap, 0, calque0);
				this.cartes.getMap(5).render(5*taillemap, 0, calque1);
				this.cartes.getMap(5).render(5*taillemap, 0, calque2);
				this.cartes.getMap(5).render(5*taillemap, 0, calque3);
				this.cartes.getMap(5).render(5*taillemap, 0, calque4);
				this.cartes.getMap(5).render(5*taillemap, 0, calque5);
				
				this.cartes.getMap(6).render(6*taillemap, 0, calque0);
				this.cartes.getMap(6).render(6*taillemap, 0, calque1);
				this.cartes.getMap(6).render(6*taillemap, 0, calque2);
				this.cartes.getMap(6).render(6*taillemap, 0, calque3);
				this.cartes.getMap(6).render(6*taillemap, 0, calque4);
				this.cartes.getMap(6).render(6*taillemap, 0, calque5);
				
				this.cartes.getMap(7).render(7*taillemap, 0, calque0);
				this.cartes.getMap(7).render(7*taillemap, 0, calque1);
				this.cartes.getMap(7).render(7*taillemap, 0, calque2);
				this.cartes.getMap(7).render(7*taillemap, 0, calque3);
				this.cartes.getMap(7).render(7*taillemap, 0, calque4);
				this.cartes.getMap(7).render(7*taillemap, 0, calque5);
				
				this.cartes.getMap(8).render(8*taillemap, 0, calque0);
				this.cartes.getMap(8).render(8*taillemap, 0, calque1);
				this.cartes.getMap(8).render(8*taillemap, 0, calque2);
				this.cartes.getMap(8).render(8*taillemap, 0, calque3);
				this.cartes.getMap(8).render(8*taillemap, 0, calque4);
				this.cartes.getMap(8).render(8*taillemap, 0, calque5);
				
				this.cartes.getMap(9).render(9*taillemap, 0, calque0);
				this.cartes.getMap(9).render(9*taillemap, 0, calque1);
				this.cartes.getMap(9).render(9*taillemap, 0, calque2);
				this.cartes.getMap(9).render(9*taillemap, 0, calque3);
				this.cartes.getMap(9).render(9*taillemap, 0, calque4);
				this.cartes.getMap(9).render(9*taillemap, 0, calque5);
				
				this.cartes.getMap(10).render(10*taillemap, 0, calque0);
				this.cartes.getMap(10).render(10*taillemap, 0, calque1);
				this.cartes.getMap(10).render(10*taillemap, 0, calque2);
				this.cartes.getMap(10).render(10*taillemap, 0, calque3);
				this.cartes.getMap(10).render(10*taillemap, 0, calque4);
				this.cartes.getMap(10).render(10*taillemap, 0, calque5);
				
				this.cartes.getMap(11).render(11*taillemap, 0, calque0);
				this.cartes.getMap(11).render(11*taillemap, 0, calque1);
				this.cartes.getMap(11).render(11*taillemap, 0, calque2);
				this.cartes.getMap(11).render(11*taillemap, 0, calque3);
				this.cartes.getMap(11).render(11*taillemap, 0, calque4);
				this.cartes.getMap(11).render(11*taillemap, 0, calque5);
				
				this.cartes.getMap(12).render(12*taillemap,0,calque0);
				this.cartes.getMap(12).render(12*taillemap,0,calque1);
				this.cartes.getMap(12).render(12*taillemap,0,calque2);
				this.cartes.getMap(12).render(12*taillemap,0,calque3);
				this.cartes.getMap(12).render(12*taillemap,0,calque4);
				this.cartes.getMap(12).render(12*taillemap,0,calque5);
				
				switch (joueur.getAction()) {
					case MOVE_RIGHT: g.drawAnimation(joueur.getAnimation(EntityDirection.MOVE_RIGHT), (float)joueur.getX(), (float)joueur.getY()); break;
					case MOVE_LEFT: g.drawAnimation(joueur.getAnimation(EntityDirection.MOVE_LEFT), (float)joueur.getX(), (float)joueur.getY()); break;
					case JUMP_RIGHT: g.drawAnimation(joueur.getAnimation(EntityDirection.JUMP_RIGHT), (float)joueur.getX(), (float)joueur.getY()); break;
					case JUMP_LEFT: g.drawAnimation(joueur.getAnimation(EntityDirection.JUMP_LEFT), (float)joueur.getX(), (float)joueur.getY()); break;
					case STILL:  g.drawAnimation(joueur.getAnimation(EntityDirection.STILL), (float)joueur.getX(), (float)joueur.getY()); break;
				}
				
				
				for (Collectible c : gems) {
					c.obtenirAnimations(g, (float)c.getX(), (float)c.getY());
				}
				
				for (Monster m : méchants) {
					m.obtenirAnimations(g, (float)m.getX(), (float)m.getY());
				}
				
				/**
				 * Affichage du premier plan (calque Foreground) pour chaque bout de map
				 */
				
				this.cartes.getMap(0).render(0, 0, calque4);
				this.cartes.getMap(1).render(taillemap, 0, calque4);
				this.cartes.getMap(2).render(2*taillemap, 0, calque4);
				this.cartes.getMap(3).render(3*taillemap, 0, calque4);
				this.cartes.getMap(4).render(4*taillemap, 0, calque4);
				this.cartes.getMap(5).render(5*taillemap, 0, calque4);
				this.cartes.getMap(6).render(6*taillemap, 0, calque4);
				this.cartes.getMap(7).render(7*taillemap, 0, calque4);
				this.cartes.getMap(8).render(8*taillemap, 0, calque4);
				this.cartes.getMap(9).render(9*taillemap, 0, calque4);
				this.cartes.getMap(10).render(10*taillemap, 0, calque4);
				this.cartes.getMap(11).render(11*taillemap, 0, calque4);
				this.cartes.getMap(12).render(12*taillemap,0,calque4);
				
				
				
				
			}

		  /**
		   * Méthode qui met à jour les différents éléments du jeu à chaque frame.
		   */
			public void update(GameContainer gc,StateBasedGame game, int arg1) throws SlickException {
				this.futurePosition(game);
				this.joueur.movement();
				double[] vitY = {-3f,-3f,-2f,-8f};
				double[] vitA = {-2f,-2f,-2f,-3f};
				if (joueur.getX()<taillemap) {
					méchants.get(0).updateX(vitY[carte1Aleat]);
					méchants.get(3).updateX(vitA[carte1Aleat]);
				} else if (joueur.getX()>4*taillemap && joueur.getX()<5*taillemap){
					méchants.get(1).updateX(vitY[carte5Aleat]);
					méchants.get(4).updateX(vitA[carte5Aleat]);
				} else if (joueur.getX()>8*taillemap && joueur.getX()<9*taillemap) {
					méchants.get(2).updateX(vitY[carte9Aleat]);
					méchants.get(5).updateX(vitA[carte9Aleat]);
				}
				
				int y = 600; //Utile pour faire disparaître les collectibles/monstres qui entrent en contact avec le joueur
				for (Collectible c : gems) { //Système de collecte des gemmes
					if (c.collision(joueur.getX(), joueur.getY(), 32, 24)){
						c.updateY(y);
						nbG +=1;
					}
				}
				
				for (Monster m : méchants) { //Gestion de la sortie du jeu en cas de collision avec un ennemi
					if (m.collision(joueur.getX(), joueur.getY(), 32, 24)) {
						m.updateY(y);
						game.enterState(FailureGameState.ID);
						;
					}
				}
				
				System.out.println(joueur.getX() + " " + joueur.getY());
				System.out.println(joueur.getAction());
				cameraPosition(gc);
				
				
				/**
				 * Si le joueur atteint le drapeau de fin, il arrive sur un écran de fin
				 */
				if(joueur.getX()==coordFin) {
					game.enterState(SuccessGameState.ID);
					
			}
			
		
			}

		  
			@Override
			public void keyReleased(int key, char c) {
				if (joueur.touchGround()) {
					joueur.setMoving(false);
				}
				if (Input.KEY_ESCAPE == key) {
		            gc.exit();
		        }
			}
			@Override
			public void keyPressed(int key, char c) {
				if (! joueur.jumpStatus()) {
				switch(key) {
				case Input.KEY_RIGHT:
					joueur.setAction(EntityDirection.MOVE_RIGHT);
					joueur.setMoving(true);
					break;
				case Input.KEY_LEFT:
					joueur.setAction(EntityDirection.MOVE_LEFT);
					joueur.setMoving(true);
					break;
				case Input.KEY_UP:
					if (joueur.touchGround()) {
						if (joueur.getAction() == EntityDirection.MOVE_RIGHT) {
							joueur.setAction(EntityDirection.JUMP_RIGHT);
							joueur.updateY(-16);
							joueur.playerOnGround(false);
							joueur.updateJumpStatus(true);
							joueur.setTempX(joueur.getX());
						} else if (joueur.getAction() == EntityDirection.MOVE_LEFT) {
							joueur.setAction(EntityDirection.JUMP_LEFT);
							joueur.updateY(-16);
							joueur.playerOnGround(false);
							joueur.updateJumpStatus(true);
							joueur.setTempX(joueur.getX());
						}
						joueur.setMoving(true);
						break;
						}
					}
				}
			}
			/**
			 * Méthode mettant à jour la position de la caméra selon la position du joueur. La caméra est
			 * une caméra suiveuse, elle est centrée sur le joueur et se déplace en même temps que lui.
			 * @param gc GameContainer : 
			 * 		prend en paramètre le GameContainer de la classe
			 */
			public void cameraPosition(GameContainer gc) {
				int taillefin = 7;
				int wc = gc.getWidth()/2;
				int hc = gc.getHeight()/2;
				yCamera = 0;
				if (wc - joueur.getX() > 0) {
					xCamera = 0;
				} else if (xCamera - wc*2 > -1*(taillefin+cartes.getMapWidth(0)*(nbmaps-2)+cartes.getMapWidth(8))*cartes.getMapTileSide(0)) {
                    xCamera = wc - (float)joueur.getX();
				}
				
				
			}
			/**
			 * Méthode définissant la position future du personnage. Pour cela, la position future est d'abord déterminée en prenant en compte
			 * le mouvement qu'effectue le personnage, puis on regarde s'il se trouve pas hors des limites de la carte à sa prochaine position. Si il reste dans les 
			 * limites du jeu, alors il effectue soit un mouvement sur le sol, soit un mouvement ne touchant pas le sol. On regarde alors si il ne va pas rencontrer d'obstacles 
			 * lors de son mouvement. Si pas d'obstacles, il peut effectuer son mouvement; sinon, il reste sur place ou chute, dépendant de son mouvement.  On regarde enfin si le personnage touche le sol ou non à la nouvelle position, afin de pouvoir déterminer la position future à l'itération suivante.
			 */
			public void futurePosition(StateBasedGame game) {
				if (joueur.isMoving()) {
					switch(joueur.getAction()) {
					case MOVE_LEFT: joueur.updateFutureX(-3f+joueur.getX());joueur.updateFutureY(joueur.getY());break;
					case MOVE_RIGHT: joueur.updateFutureX(3f+joueur.getX());joueur.updateFutureY(joueur.getY());break;
					case JUMP_RIGHT: joueur.updateFutureX(2+joueur.getX());joueur.updateFutureY(joueur.getY()+joueur.jumpTrajectory(joueur.getX(), joueur.getTempX()));break;
					case JUMP_LEFT: joueur.updateFutureX(-2+joueur.getX());joueur.updateFutureY(joueur.getY()+joueur.jumpTrajectory(joueur.getX(), joueur.getTempX()));break;
					case STILL: joueur.updateFutureX(joueur.getX());joueur.updateFutureY(joueur.getY());break;
					}
				} else if (! joueur.touchGround()){
					joueur.updateFutureY(joueur.getY()+5);
				} else {
					joueur.updateFutureX(joueur.getX());
					joueur.updateFutureY(joueur.getY());
				}
				if (joueur.getFutureX() >= taillemap*(playerMapNbr+1) && playerMapNbr <= 12) {
					playerMapNbr++;
				} else if (joueur.getFutureX() <= taillemap*(playerMapNbr) && playerMapNbr > 0) {
					playerMapNbr--;
				}
				System.out.println(playerMapNbr);
				System.out.println("Position actuelle :" + joueur.getX() + " " + joueur.getY());
				System.out.println("Position future :" + joueur.getFutureX() + " " + joueur.getFutureY());
				if (! outOfBounds()) {
					if (! joueur.touchGround()) {
						jumpCollision();
					} else {
						if (joueur.jumpStatus()) {
							joueur.updateJumpStatus(false);
							joueur.setAction(EntityDirection.STILL);
							System.out.println("saut terminé");
							joueur.playerOnGround(true);
						} else {
							joueur.movement();
						}
					}
					joueur.playerOnGround(groundCollision());
					System.out.println(joueur.touchGround());
				} else {
					if (joueur.getFutureX() < 0) {
						joueur.updateX(3f);
					} else if (joueur.getFutureY() < 0) {
						joueur.updateY(5);
					} else if(joueur.getFutureY()>= 28*16) {
						game.enterState(FailureGameState.ID);
					}
				}
				
			}
			/**
			 * Cette méthode sert à donner la nouvelle position du joueur lorsque celui-ci ne touche pas le sol. S'il était en saut, on regarde alors si à la prochaine position
			 * se trouve un obstacle. Si non, le joueur peut continuer son saut. Si oui, le joueur chute. Si le joueur n'est pas en saut, alors il est en chute libre; on le fait donc tomber en ligne droite.
			 */
			public void jumpCollision() {
				int tileSize = this.cartes.getMapTileSide(0);
				int futureTileX1 = (int)((joueur.getFutureX()-taillemap*playerMapNbr)/tileSize);
				int futureTileY1 = (int)((joueur.getFutureY())/tileSize);
				int futureTileX2 = (int)((joueur.getFutureX()-taillemap*playerMapNbr)/tileSize);
				int futureTileY2 = (int)((joueur.getFutureY()+1)/tileSize);
				if (joueur.getAction() == EntityDirection.JUMP_RIGHT) {
					if (this.cartes.getMap(playerMapNbr).getTileImage(futureTileX1, futureTileY1, this.cartes.getMap(playerMapNbr).getLayerIndex("collisions")) == null || this.cartes.getMap(playerMapNbr).getTileImage(futureTileX2, futureTileY2, this.cartes.getMap(playerMapNbr).getLayerIndex("collisions")) == null ) {
						joueur.updateX(2);
						joueur.jump();
					} else {
						joueur.updateY(5);

					}
				} else if (joueur.getAction() == EntityDirection.JUMP_LEFT) {
					if (this.cartes.getMap(playerMapNbr).getTileImage(futureTileX1, futureTileY1, this.cartes.getMap(playerMapNbr).getLayerIndex("collisions")) == null || this.cartes.getMap(playerMapNbr).getTileImage(futureTileX2, futureTileY2, this.cartes.getMap(playerMapNbr).getLayerIndex("collisions")) == null ) {
						joueur.updateX(-2);
						joueur.jump();
					} else {
						joueur.updateY(5);


					}
				} else {
					joueur.updateY(5);
				}
			}
			/**
			 * Cette méthode renvoie un booléen indiquant si le joueur se trouve ou non sur le sol.
			 * @return
			 * 		Renvoie un booléen qui vaut true si le joueur touche le sol, et false sinon.
			 */
			public boolean groundCollision() {
				int tileSize = this.cartes.getMapTileSide(playerMapNbr);
				return (this.cartes.getMap(playerMapNbr).getTileImage((int)((joueur.getFutureX()-taillemap*playerMapNbr)/tileSize), (int)((joueur.getFutureY()/tileSize)+2), this.cartes.getMap(playerMapNbr).getLayerIndex("collisions")) != null);
			}
			/**
			 * Cette méthode renvoie un booléen qui indique si le joueur est en dehors ou non des limites de la carte.
			 * @return
			 * 		Renvoie un booléen qui vaut true si le joueur est en dehors des limites de la carte et false sinon.
			 */
			public boolean outOfBounds() {
				return !(joueur.getFutureX() >= 0 && joueur.getFutureY() >= 0 && joueur.getFutureX() < 10240 && joueur.getFutureY() < 28*16);
			}
			

			
			

		  /**
		   * Méthode qui retourne l'identifiant de la phase de jeu
		   */
		  public int getID() {
		    return ID;
		  }
		  
		}
				 

	  /**
	   * classe de l'écran d'information
	   */
	  public class InfoGameState extends BasicGameState{
		  /**
		   * contient l'ID de la phase de jeu
		   */
		  public static final int ID = 3;
		 /**
		  * Contient l'image de fond d'écran
		  */
		  private Image background;
		  /**
		   * Contient le jeu pour l'extension StateBasedGame
		   */
		  private StateBasedGame game;
		  
		  /**
		   * Contient les largeurs et hauteurs des textes 
		   */
		  private int ligne1Largeur = 320;
		  private int ligne1Hauteur = 190;
		  private int ligne2Largeur = 130;
		  private int ligne2Hauteur = 220;
		  private int ligne3Largeur = 230;
		  private int ligne3Hauteur = 270;
		  private int ligne4Largeur = 190;
		  private int ligne4Hauteur = 300;
		  
		  /**
		   * Méthode d'initialisation de la phase écran d'information
		   */
		@Override
		public void init(GameContainer container , StateBasedGame game) throws SlickException {
			/**
			 * Initialise le jeu 
			 */
			this.game = game;
			/**
			 * Initialise l'image de fond d'écran
			 */
			this.background = new Image("resources/1/Menu/Background.png");
			
		}

		/**
		 * Méthode qui affiche les différents éléments sur le menu d'informations (fond, texte)
		 */
		@Override
		public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
			background.draw(0, 0, container.getWidth(), container.getHeight());
			g.drawString("Jeu développé par :", ligne1Largeur, ligne1Hauteur);
			g.drawString("Emmanuel Dietlin, Chloé Demuynck, Olivier Bemba, Maxence Durieu", ligne2Largeur, ligne2Hauteur);
			g.drawString("Projet PRO3600 Télécom Sud Paris 2021", ligne3Largeur, ligne3Hauteur);
			g.drawString("Appuyer sur <- pour revenir au Menu principal", ligne4Largeur, ligne4Hauteur);
		}

		/**
		 * Méthode update (vide)
		 */
		@Override
		public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {		
		}
	
		/**
		 * méthode qui indique ce que provoque d'appuyer sur une touche dans le menu d'informations
		 */
		@Override
		public void keyPressed(int key, char c) {
			switch(key ) {
			case Input.KEY_BACK:
				game.enterState(MainScreenGameState.ID);
				break;
			}
		}
		
		
		/**
		 * méthode qui renvoie l'ID du State
		 */
		@Override
		public int getID() {
			return ID;
		}
	  
		

	}
	  /**
	   * 
	   * Classe de l'affichage lors de la réussite d'un niveau
	   *
	   */
	  public class SuccessGameState extends BasicGameState {
		  /**
		   * contient l'ID de la phase de jeu
		   */
		  public static final int ID = 4;
		  /**
			  * Contient l'image de fond d'écran
			  */
			  private Image background;
			  /**
			   * Stocke le gemme sur l'écran de fin
			   */
			  private Collectible gemme;
			  /**
			   * Contient le jeu pour l'extension StateBasedGame
			   */
			  private StateBasedGame game;
			  
			  
			 /**
			  * contient les coordonnées du texte
			  */
			  
			  private int ligne1Hauteur = 200;
			  private int ligne1Largeur = 250;
			  private int ligne2Hauteur = 350;
			  private int ligne2Largeur = 270;
			  /**
			   * Contient le gamecontainer du jeu
			   */
			  private GameContainer gc;
			  /**
			   * Méthode d'initialisation de la phase écran d'information
			   */
			@Override
			public void init(GameContainer gc , StateBasedGame game) throws SlickException {
				/**
				 * initialise le gc
				 */
				this.gc=gc;
				
				/**
				 * Initialise le jeu 
				 */
				this.game = game;
				/**
				 * Initialise l'image de fond d'écran
				 */
				this.background = new Image("resources/1/Menu/Background.png");
				
				int[] caracteristiquesGs = {15,12,5,3};
				gemme = new Collectible("resources/1/gem.png", caracteristiquesGs[0], caracteristiquesGs[1], caracteristiquesGs[2], 480, 310);
				gemme.créerAnimations(caracteristiquesGs[3]);
			}

			/**
			 * Méthode qui affiche les différents éléments sur le menu d'informations (fond, texte)
			 */
			@Override
			public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
				background.draw(0, 0, container.getWidth(), container.getHeight());
				g.drawString("Bravo ! Vous avez fini un niveau !", ligne1Largeur, ligne1Hauteur);
				g.drawString("Vous avez obtenu " + Integer.toString(nbG) + "x", 300, 305);
				gemme.obtenirAnimations(g, (float)gemme.getX(), (float)gemme.getY());
				g.drawString("Appuyer sur Echap pour quitter", ligne2Largeur, ligne2Hauteur);
				
			}

			/**
			 * Méthode update (vide)
			 */
			@Override
			public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {		
			}
			
		
			/**
			 * méthode qui indique ce que provoque d'appuyer sur une touche dans le menu d'informations
			 */
			@Override
			public void keyPressed(int key, char c) {
				switch(key ) {
				case Input.KEY_ESCAPE:
					gc.exit();
					break;
				}
			}
			
			
			/**
			 * méthode qui renvoie l'ID du State
			 */
			@Override
			public int getID() {
				return ID;
			}
			  
		  
	  }
	  /**
	   * 
	   * Classe de l'affichage lors de l'échec d'un niveau
	   *
	   */
	  public class FailureGameState extends BasicGameState {
		  /**
		   * contient l'ID de la phase de jeu
		   */
		  public static final int ID = 5;
		  /**
		   * Contient l'image de fond d'écran
		   */
		  private Image background;
		  /**
		   * Contient le jeu pour l'extension StateBasedGame
		   */
		  private StateBasedGame game;
		  /**
		   * contient les explosions sur l'écran d'échec
		   */
		  private ArrayList<Explosion> explosions;
		  /**
		   * Stocke le gemme sur l'écran de fin
		   */
		  private Collectible gemme;
		  /**
		   * Contient les phrases possibles sur l'écran d'echec
		   */
		  String[] phrasesEchec = {"Vous avez ÉCHOUÉ, quelle honte...", "Et c'est un échec... Accrochez vous !", "Dommage, vous y étiez presque...", "BOUHHH le nul, vous avez encore perdu !", "GAME OVER", "Essayez encore" };
		  /**
		   * Contient LA phrase qui va apparaitre sur l'écran d'échec
		   */
		  private String phraseEchec;
		  /**
		   * contient les coordonnées du texte
		   */  
		  private int ligne1Hauteur = 200;
		  private int ligne1Largeur = 250;
		  private int ligne2Hauteur = 350;
		  private int ligne2Largeur = 270;
		  /**
		   * Contient le gamecontainer du jeu
		   */
		  private GameContainer gc;
		  
		  /**
		   * Méthode d'initialisation de la phase écran d'information
		   */  
			@Override
			public void init(GameContainer gc , StateBasedGame game) throws SlickException {
				/**
				 * initialise le gc
				 */
				this.gc=gc;
				
				/**
				 * Initialise le jeu 
				 */
				this.game = game;
				/**
				 * Initialise l'image de fond d'écran
				 */
				this.background = new Image("resources/1/Menu/Background.png");
				/**
				 * Initialise la phrase qui va apparaître sur l'écran d'échec
				 */
				this.phraseEchec=phrasesEchec[(int)(Math.random()*(phrasesEchec.length))];
				/**
				 * Initialisation de l'image de l'explosion
				 */
				int[] CaractéristiquesE = {40,6,240,5}; //{longueur et largeur du sprite, nombre de sprites, abscisse du sprite de fin, ordonnées ou vont apparaître les explosions}
				int[] xE = {250, 380, 510}; //Listes des abscisses des trois explosions trouvées au cas par cas.
				
				explosions = new ArrayList<Explosion>();
				
				Explosion explosion1 = new Explosion("resources/1/death.png", CaractéristiquesE[0], CaractéristiquesE[0], CaractéristiquesE[1], xE[0], CaractéristiquesE[2]);
				explosion1.créerAnimations(CaractéristiquesE[3]);
				explosions.add(explosion1);
				
				Explosion explosion2 = new Explosion("resources/1/death.png", CaractéristiquesE[0], CaractéristiquesE[0], CaractéristiquesE[1], xE[1], CaractéristiquesE[2]);
				explosion2.créerAnimations(CaractéristiquesE[3]);
				explosions.add(explosion2);
				
				Explosion explosion3 = new Explosion("resources/1/death.png", CaractéristiquesE[0], CaractéristiquesE[0], CaractéristiquesE[1], xE[2], CaractéristiquesE[2]);
				explosion3.créerAnimations(CaractéristiquesE[3]);
				explosions.add(explosion3);
				
				int[] caracteristiquesGs = {15,12,5,3};
				gemme = new Collectible("resources/1/gem.png", caracteristiquesGs[0], caracteristiquesGs[1], caracteristiquesGs[2], 480, 310);
				gemme.créerAnimations(caracteristiquesGs[3]);
				
			}

			/**
			 * Méthode qui affiche les différents éléments sur l'écran de l'échec (fond, texte)
			 */
			@Override
			public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
				background.draw(0, 0, container.getWidth(), container.getHeight());
				g.drawString(phraseEchec, ligne1Largeur, ligne1Hauteur);
				g.drawString("Appuyer sur Echap pour quitter", ligne2Largeur, ligne2Hauteur);
				
				for (Explosion e : explosions) {
					e.obtenirAnimations(g, (float)e.getX(), (float)e.getY());
				}
				
				g.drawString("Vous avez obtenu " + Integer.toString(nbG) + "x", 300, 305);
				gemme.obtenirAnimations(g, (float)gemme.getX(), (float)gemme.getY());
			}

			/**
			 * Méthode update (vide)
			 */
			@Override
			public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {		
			}
			
		
			/**
			 * méthode qui indique ce que provoque d'appuyer sur une touche dans l'écran de l'échec
			 */
			@Override
			public void keyPressed(int key, char c) {
				switch(key) {
				case Input.KEY_ESCAPE:
					gc.exit();
					break;
				}
			}
			
			
			/**
			 * méthode qui renvoie l'ID du State
			 */
			@Override
			public int getID() {
				return ID;
			}
			  
		  
	  }
	/**
	 * Methode qui liste les differents State (phases) du jeu (ecran principal, informations, jeu en lui meme)
	 */
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainScreenGameState());
		addState(new MapGameState());
		addState(new InfoGameState());
		addState(new SuccessGameState());
		addState(new FailureGameState());
	}

	
}

