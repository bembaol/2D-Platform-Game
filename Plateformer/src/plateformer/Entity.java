package plateformer;

import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 * Classe comportant les m�thodes relatives aux entit�s (joueur et monstres)
 * @author Emmanuel Dietlin, Chlo� Demuynck, Olivier Bemba, Maxence Durieu
 *
 */
public class Entity {
	/**
	 * Position actuelle de l'entit� (� l'instant t) selon l'axe horizontal.
	 */
	private int x;
	/**
	 * Position actuelle de l'entit�(� l'instant t) selon l'axe vertical.
	 */
	private int y;
	/**
	 * SpriteSheet de l'entit�. C'est l'ensemble des sprites de l'entit�.
	 */
	private SpriteSheet sprite;
	/**
	 * Tableau contenant les diff�rentes animations de l'entit�.
	 */
	private Animation[] animation;
	/**
	 * Position de l'entit� � l'instant t+1.
	 */
	private int futureX;
	/**
	 * Position de l'entit� � l'instant t+1.
	 */
	private int futureY;

	/**
	 * Constructeur de la classe Entity. 
	 * @param path :
	 * 		Chemin vers l'image contenant les sprites de l'entit�.
	 * @param w :
	 * 		Largeur des sprites de l'entit�.
	 * @param h :
	 * 		Hauteur des sprites de l'entit�.
	 * @param spritenbr :
	 * 		Nombre d'animations diff�rentes que poss�de l'entit�.
	 * @param x :
	 * 		Position initiale de l'entit� en abscisse.
	 * @param y :
	 * 		Position initiale de l'entit� en ordonn�e
	 * @throws SlickException
	 */
	public Entity(String path, int w, int h, int spritenbr, int x, int y) throws SlickException {
		this.x = x;
		this.y = y;
		sprite = new SpriteSheet(path,w,h);
		animation = new Animation[spritenbr];
		futureX = x;
		futureY = y;

	}
	
	
	public Entity(int x, int y, int futureX, int futureY) {
		super();
		this.x = x;
		this.y = y;
		this.futureX = futureX;
		this.futureY = futureY;
	}


	/**
	 * Renvoie la SpriteSheet de l'entit�
	 * @return 
	 * 		Renvoie la valeur du champ sprite de Entity.
	 */
	public SpriteSheet getSprite() {
		return this.sprite;
	}
	/**
	 * Alloue une animation dans les tableau d'animation de l'entit�; la place dans le tableau correspond � l'ordinalit� de la direction donn�e en param�tre. On utilise plusieurs sprites afin de 
	 * g�n�rer une animation, on d�finit donc dans les param�tres la position du sprite de d�but et 
	 * la position du sprite de fin de l'animation, puis on ajoute l'ensemble des sprites entre ces deux-l� dans l'animation. 
	 * 
	 * @param direction : 
	 * 		Mouvement ou direction parmis ceux de l'�num�ration EntityDirection, selon ce que l'on souhaite faire de l'entit�.
	 * @param xd :
	 * 		Position du sprite de d�part selon x.
	 * @param xf :
	 * 		Position du sprite de fin selon x.
	 * @param yp : 
	 * 		Ligne o� se situent les sprites que l'on souhaite ajouter � l'animation.
	 */
	public void setAnimation(EntityDirection direction, int xd, int xf, int yp ) {
		this.animation[direction.ordinal()] = loadAnimation(this.sprite, xd, xf, yp);
	}
	/**
	 * Permet d'ajouter individuellement chaque sprite � l'animation. Cette m�thode est utilis�e par setAnimation afin
	 * de cr�er une animation.
	 * @param sprite :
	 * 		SpriteSheet de l'entit�.
	 * @param xd :
	 * 		Position du sprite de d�part selon x.
	 * @param xf :
	 * 		Position du sprite de fin selon x.
	 * @param yp :
	 * 		Ligne o� se situent les sprites que l'on souhaite ajouter � l'animation.
	 * @return 
	 * 		Renvoie un objet de type Animation, qui correspond donc � l'animation de l'entit�.
	 */
	private Animation loadAnimation(SpriteSheet sprite, int xd�but, int xfin, int y) {
		Animation animation = new Animation();
		for (int i = xd�but; i <= xfin; i++ ) {
			animation.addFrame(sprite.getSprite(i, y), 200);
		}
		return animation;
	}
	/**
	 * Renvoie l'animation correspondant � la direction entr�e en param�tre.
	 * @param direction :
	 * 		direction de l'animation que l'on souhaite r�cup�rer.
	 * @return 
	 * 		Renvoie un objet de type Animation, qui est l'animation de la direction entr�e en param�tre.
	 */
	public Animation getAnimation(EntityDirection direction) {
		return this.animation[direction.ordinal()];
	}
	/**
	 * Renvoie la position selon x de l'entit�.
	 * @return 
	 * 		Position en x de l'entit�
	 */
	public double getX() {
		return this.x;
	}
	/**
	 * Renvoie la position selon y de l'entit�.
	 * @return 
	 * 		Position en y de l'entit�.
	 */
	public double getY() {
		return this.y;
	}
	/**
	 * Met � jour la position selon x de l'entit�.
	 * @param x :
	 * 		Nouvelle position selon x de l'entit�.
	 */
	public void updateX(double x) {
		this.x += (int)x;
	}
	/**
	 * Met � jour la position selon y de l'entit�.
	 * @param y :
	 * 		Nouvelle position selon y de l'entit�.
	 */
	public void updateY(double y) {
		this.y += (int)y;
	}
	/**
	 * Renvoie la valeur de la position en x suivante de l'entit�.
	 * @return 
	 * 		Position suivante de l'entit�.
	 */
	public double getFutureX() {
		return this.futureX;
	}
	/**
	 * Renvoie la valeur de la position en y future de l'entit�.
	 * @return 
	 * 		Position suivante de l'entit�.
	 */
	public double getFutureY() {
		return this.futureY;
	}
	/**
	 * Met � jour la position en x future de l'entit�.
	 * @param x :
	 * 		Vitesse selon x de l'entit�.
	 */
	public void updateFutureX(double x) {
		this.futureX = (int)x;
	}
	/**
	 * Met � jour la position en y future de l'entit�.
	 * @param y :
	 * 		position en y future de l'entit�
	 */
	public void updateFutureY(double y) {
		this.futureY = (int)y;
	}
	
}