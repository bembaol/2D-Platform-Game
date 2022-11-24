package plateformer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Classe qui g�n�re et g�re les monstres du jeu
 * @author Emmanuel Dietlin, Chlo� Demuynck, Olivier Bemba, Maxence Durieu
 *
 */
public class Monster extends Entity {
	
	/**
	 * Stocke les diff�rents mouvements du monstre.
	 */
	EntityDirection[] directions;
	/**
	 * Stocke le nombre d'animations possibles du nombres.
	 */
	int nbAnim;
	/**
	 * Stocke le choix al�toire de l'animation de chaque monstre g�n�r� sur la carte.
	 */
	int choixAnim;
	/**
	 * Stocke la largeur d'un sprite
	 */
	int w;
	/**
	 * Stocke la hauteur d'un sprite
	 */
	int h;
	
	/**
	 * Constructeur de la classe Monster.
	 * @param path :
	 * 		Chemin vers l'image contenant les sprites du monstre.
	 * @param w :
	 * 		Largeur des sprites du monstre.
	 * @param h :
	 * 		Hauteur des sprites du monstre.
	 * @param spritenbr :
	 * 		Nombre de sprites diff�rents que poss�de le monstre.
	 * @param i :
	 * 		Nombre d'animations diff�rentes que poss�dent le montre.
	 * @param n :
	 * 		Nombre de monstres plac�s sur la map.
	 * @param d :
	 * 		Liste d'�num�rations contenant les types de mouvements effectu�s par le monstre.
	 * @throws SlickException
	 */
	public Monster(String path, int w, int h, int spritenbr, int x, int y, int n, EntityDirection[] d) throws SlickException {
		super(path,w,h,spritenbr, x, y);
		directions = d;
		nbAnim = n;
		choixAnim = (int)(Math.random()*nbAnim);
		this.w=w;
		this.h=h;
	}
	
	public Monster(int w, int h,int x, int y) throws SlickException {
		super(w,h,x, y);
	}
	
	/**
	 * Permet de cr�er les diff�rentes animations du monstre
	 * @param xf :
	 * 		Ordonn�e � laquelle se termine l'animation sur le spritesheet.
	 */
	public void cr�erAnimations(int xf) {
		int xd = 0; //L'abscisse de la case du premier sprite sur le spritesheet est toujours 0.
		for (int i=0; i<nbAnim; i++) {
			this.setAnimation(directions[i], xd, xf, i);
		}
	}
	
	/**
	 * Fait appara�tre les monstres sur la carte.
	 * @param g :
	 * 		Graphique sur lequel on souhaite faire appara�tre les monstres.
	 * @param x :
	 * 		Liste des diff�rentes abscisses de tous les monstres sur la carte.
	 * @param y :
	 * 		Liste des diff�rentes ordonn�es de tous les monstres sur la carte.
	 */
	public void obtenirAnimations(Graphics g, float x, float y) {
		g.drawAnimation(this.getAnimation(directions[choixAnim]), x, y);
	}
	
	/**
	 * D�termine si le monstre entre en contact avec le joueur
	 * @param x double :
	 * 		Abscisse du joueur
	 * @param y double :
	 * 		Ordonn�e du joueur
	 * @param h int :
	 * 		Hauteur du sprite du joueur
	 * @param w int :
	 * 		Largeur du sprite du joueur
	 * @return si oui ou non le monstre est en contact avec le joueur
	 */
	public boolean collision(double x, double y, int h, int w) {
		boolean a = (Math.abs(this.getX()-x)<Integer.max(w,this.w));
		boolean b = (Math.abs(this.getY()-y)<Integer.max(h,this.h));
		if (a && b) {
			return a;
		} else {
			return false;
		}
	}
}
