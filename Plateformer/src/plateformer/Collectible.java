package plateformer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Classe créant et gérant les objets récoltables du jeu
 * @author Emmanuel Dietlin, Chloé Demuynck, Olivier Bemba, Maxence Durieu
 *
 */
public class Collectible extends Entity {
	/**
	 * Stocke les différents mouvements du collectible.
	 */
	EntityDirection direction = EntityDirection.STILL;
	/**
	 * Stocke le choix alétoire de l'animation de chaque collectible généré sur la carte.
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
	 * 		Nombre de sprites différents que possède le collectible.
	 * @throws SlickException
	 */
	public Collectible(String path, int w, int h, int spritenbr, int x, int y) throws SlickException {
		super(path,w,h,spritenbr, x, y);
		this.w=w;
		this.h=h;
	}
	
	/**
	 * Permet de créer les différentes animations du collectible
	 * @param xf :
	 * 		Ordonnée à laquelle se termine l'animation sur le spritesheet.
	 */
	public void créerAnimations(int xf) {
		this.setAnimation(direction, 0, xf, 0);
	}
	
	/**
	 * Fait apparaître les collectibles sur la carte.
	 * @param g :
	 * 		Graphique sur lequel on souhaite faire apparaître le collectible.
	 * @param x :
	 * 		Abscisse du collectible sur la carte.
	 * @param y :
	 * 		Ordonnée du collectible sur la carte.
	 */
	public void obtenirAnimations(Graphics g, float x, float y) {
		g.drawAnimation(this.getAnimation(direction), x, y);
	}
	
	/**
	 * Détermine si le collectible entre en contact avec le joueur
	 * @param x :
	 * 		Abscisse du joueur
	 * @param y :
	 * 		Ordonnée du joueur
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
