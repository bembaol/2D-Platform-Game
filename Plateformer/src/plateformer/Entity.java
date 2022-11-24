package plateformer;

import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 * Classe comportant les méthodes relatives aux entités (joueur et monstres)
 * @author Emmanuel Dietlin, Chloé Demuynck, Olivier Bemba, Maxence Durieu
 *
 */
public class Entity {
	/**
	 * Position actuelle de l'entité (à l'instant t) selon l'axe horizontal.
	 */
	private int x;
	/**
	 * Position actuelle de l'entité(à l'instant t) selon l'axe vertical.
	 */
	private int y;
	/**
	 * SpriteSheet de l'entité. C'est l'ensemble des sprites de l'entité.
	 */
	private SpriteSheet sprite;
	/**
	 * Tableau contenant les différentes animations de l'entité.
	 */
	private Animation[] animation;
	/**
	 * Position de l'entité à l'instant t+1.
	 */
	private int futureX;
	/**
	 * Position de l'entité à l'instant t+1.
	 */
	private int futureY;

	/**
	 * Constructeur de la classe Entity. 
	 * @param path :
	 * 		Chemin vers l'image contenant les sprites de l'entité.
	 * @param w :
	 * 		Largeur des sprites de l'entité.
	 * @param h :
	 * 		Hauteur des sprites de l'entité.
	 * @param spritenbr :
	 * 		Nombre d'animations différentes que possède l'entité.
	 * @param x :
	 * 		Position initiale de l'entité en abscisse.
	 * @param y :
	 * 		Position initiale de l'entité en ordonnée
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
	 * Renvoie la SpriteSheet de l'entité
	 * @return 
	 * 		Renvoie la valeur du champ sprite de Entity.
	 */
	public SpriteSheet getSprite() {
		return this.sprite;
	}
	/**
	 * Alloue une animation dans les tableau d'animation de l'entité; la place dans le tableau correspond à l'ordinalité de la direction donnée en paramètre. On utilise plusieurs sprites afin de 
	 * générer une animation, on définit donc dans les paramètres la position du sprite de début et 
	 * la position du sprite de fin de l'animation, puis on ajoute l'ensemble des sprites entre ces deux-là dans l'animation. 
	 * 
	 * @param direction : 
	 * 		Mouvement ou direction parmis ceux de l'énumération EntityDirection, selon ce que l'on souhaite faire de l'entité.
	 * @param xd :
	 * 		Position du sprite de départ selon x.
	 * @param xf :
	 * 		Position du sprite de fin selon x.
	 * @param yp : 
	 * 		Ligne où se situent les sprites que l'on souhaite ajouter à l'animation.
	 */
	public void setAnimation(EntityDirection direction, int xd, int xf, int yp ) {
		this.animation[direction.ordinal()] = loadAnimation(this.sprite, xd, xf, yp);
	}
	/**
	 * Permet d'ajouter individuellement chaque sprite à l'animation. Cette méthode est utilisée par setAnimation afin
	 * de créer une animation.
	 * @param sprite :
	 * 		SpriteSheet de l'entité.
	 * @param xd :
	 * 		Position du sprite de départ selon x.
	 * @param xf :
	 * 		Position du sprite de fin selon x.
	 * @param yp :
	 * 		Ligne où se situent les sprites que l'on souhaite ajouter à l'animation.
	 * @return 
	 * 		Renvoie un objet de type Animation, qui correspond donc à l'animation de l'entité.
	 */
	private Animation loadAnimation(SpriteSheet sprite, int xdébut, int xfin, int y) {
		Animation animation = new Animation();
		for (int i = xdébut; i <= xfin; i++ ) {
			animation.addFrame(sprite.getSprite(i, y), 200);
		}
		return animation;
	}
	/**
	 * Renvoie l'animation correspondant à la direction entrée en paramètre.
	 * @param direction :
	 * 		direction de l'animation que l'on souhaite récupérer.
	 * @return 
	 * 		Renvoie un objet de type Animation, qui est l'animation de la direction entrée en paramètre.
	 */
	public Animation getAnimation(EntityDirection direction) {
		return this.animation[direction.ordinal()];
	}
	/**
	 * Renvoie la position selon x de l'entité.
	 * @return 
	 * 		Position en x de l'entité
	 */
	public double getX() {
		return this.x;
	}
	/**
	 * Renvoie la position selon y de l'entité.
	 * @return 
	 * 		Position en y de l'entité.
	 */
	public double getY() {
		return this.y;
	}
	/**
	 * Met à jour la position selon x de l'entité.
	 * @param x :
	 * 		Nouvelle position selon x de l'entité.
	 */
	public void updateX(double x) {
		this.x += (int)x;
	}
	/**
	 * Met à jour la position selon y de l'entité.
	 * @param y :
	 * 		Nouvelle position selon y de l'entité.
	 */
	public void updateY(double y) {
		this.y += (int)y;
	}
	/**
	 * Renvoie la valeur de la position en x suivante de l'entité.
	 * @return 
	 * 		Position suivante de l'entité.
	 */
	public double getFutureX() {
		return this.futureX;
	}
	/**
	 * Renvoie la valeur de la position en y future de l'entité.
	 * @return 
	 * 		Position suivante de l'entité.
	 */
	public double getFutureY() {
		return this.futureY;
	}
	/**
	 * Met à jour la position en x future de l'entité.
	 * @param x :
	 * 		Vitesse selon x de l'entité.
	 */
	public void updateFutureX(double x) {
		this.futureX = (int)x;
	}
	/**
	 * Met à jour la position en y future de l'entité.
	 * @param y :
	 * 		position en y future de l'entité
	 */
	public void updateFutureY(double y) {
		this.futureY = (int)y;
	}
	
}