package plateformer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Explosion extends Entity {
	/**
	 * Stocke le mouvement de l'explosion.
	 */
	EntityDirection direction = EntityDirection.STILL;

	/**
	 * Constructeur de la classe Explosion.
	 * @param path :
	 * 		Chemin vers l'image contenant les sprites de l'explosion.
	 * @param w :
	 * 		Largeur des sprites de l'explosion.
	 * @param h :
	 * 		Hauteur des sprites de l'explosion.
	 * @param spritenbr :
	 * 		Nombre de sprites différents que possède l'explosion.
	 *  @throws SlickException
	 */
	public Explosion(String path, int w, int h, int spritenbr, int x, int y) throws SlickException {
		super(path, w, h, spritenbr, x, y);
	}

	/**
	 * Permet de créer les différentes animations du monstre
	 * @param xf :
	 * 		Ordonnée à laquelle se termine l'animation sur le spritesheet.
	 */
	public void créerAnimations(int xf) { 
		this.setAnimation(direction, 0, xf, 0);
	}
	
	/**
	 * Fait apparaître le monstre sur la carte.
	 * @param g :
	 * 		Graphique sur lequel on souhaite faire apparaître les monstres.
	 * @param x :
	 * 		Abscisse du monstre sur la carte.
	 * @param y :
	 * 		Ordonnée du monstre sur la carte.
	 */
	public void obtenirAnimations(Graphics g, float x, float y) {
		g.drawAnimation(this.getAnimation(direction), x, y);
	}
}
