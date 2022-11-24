package plateformer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Classe cr�ant et g�rant les objets r�coltables du jeu
 * @author Emmanuel Dietlin, Chlo� Demuynck, Olivier Bemba, Maxence Durieu
 *
 */
public class Collectible extends Entity {
	/**
	 * Stocke les diff�rents mouvements du collectible.
	 */
	EntityDirection direction = EntityDirection.STILL;
	/**
	 * Stocke le choix al�toire de l'animation de chaque collectible g�n�r� sur la carte.
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
	 * Constructeur de la classe Collectible.
	 * @param path :
	 * 		Chemin vers l'image contenant les sprites du collectible.
	 * @param w :
	 * 		Largeur des sprites du collectible.
	 * @param h :
	 * 		Hauteur des sprites du collectible.
	 * @param spritenbr : 
	 * 		Nombre de sprites diff�rents que poss�de le collectible.
	 * @throws SlickException
	 */
	public Collectible(String path, int w, int h, int spritenbr, int x, int y) throws SlickException {
		super(path,w,h,spritenbr, x, y);
		this.w=w;
		this.h=h;
	}
	
	/**
	 * Permet de cr�er les diff�rentes animations du collectible
	 * @param xf :
	 * 		Ordonn�e � laquelle se termine l'animation sur le spritesheet.
	 */
	public void cr�erAnimations(int xf) {
		this.setAnimation(direction, 0, xf, 0);
	}
	
	/**
	 * Fait appara�tre les collectibles sur la carte.
	 * @param g :
	 * 		Graphique sur lequel on souhaite faire appara�tre le collectible.
	 * @param x :
	 * 		Abscisse du collectible sur la carte.
	 * @param y :
	 * 		Ordonn�e du collectible sur la carte.
	 */
	public void obtenirAnimations(Graphics g, float x, float y) {
		g.drawAnimation(this.getAnimation(direction), x, y);
	}
	
	/**
	 * D�termine si le collectible entre en contact avec le joueur
	 * @param x :
	 * 		Abscisse du joueur
	 * @param y :
	 * 		Ordonn�e du joueur
	 * @param h :
	 * 		Hauteur du sprite du joueur
	 * @param w :
	 * 		Largeur du sprite du joueur
	 * @return si oui ou non le collectible est en contact avec le joueur
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
