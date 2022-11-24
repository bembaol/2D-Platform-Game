package plateformer;

import org.newdawn.slick.SlickException;
/**
 * Classe générant un joueur et comporte les méthodes relatives à celui-ci.
 * @author Emmanuel Dietlin, Chloé Demuynck, Olivier Bemba, Maxence Durieu.
 *
 */
public class Player extends Entity {
	/**
	 * indique si le joueur est en mouvement ou non.
	 */
	private boolean moving; 
	/**
	 * indique quelle action le joueur réalise, les actions possibles sont les valeurs de l'énumération EntityDirection.
	 */
	private EntityDirection action;
	/**
	 * indique si le joueur est en contact avec le sol ou non.
	 */
	private boolean touchGround;
	/**
	 * stocke la position en X du joueur lorsqu'il initie son saut
	 */
	private int tempX;
	/**
	 * renvoie true si le joueur est en saut
	 */
	private boolean jump;

	/**
	 * Constructeur de la classe Player.
	 * @param path :
	 * 		Chemin vers l'image contenant les sprites du joueur.
	 * @param w  :
	 * 		Largeur des sprites du joueur.	
	 * @param h :
	 * 		Hauteur des sprites du joueur.
	 * @param spNritenbr 
	 * 		Nombre d'animations différentes que possède le joueur.
	 * @param x :
	 * 		Position initiale du joueur en absicsse.
	 * @param y :
	 * 		Position initiale du joueur en ordonnée.
	 * @throws SlickException
	 */
	public Player(String path, int w, int h, int spritenbr, int x, int y) throws SlickException  {
		super(path,w,h,spritenbr,x,y);
		moving = false;
		action = EntityDirection.STILL;
		touchGround = true;
		tempX = x;
	}
	

	
	public Player( int w, int h, int x, int y) {
		super(w, h, x, y);
	}



	/**
	 * Renvoie la valeur du champ 'action' de la classe.
	 * @return  
	 * 		'MOVE_RIGHT' indique que le joueur se déplace à droite. 'MOVE_LEFT' indique que le joueur se déplace à gauche.
	 * 		'JUMP_RIGHT' indique que le joueur saute à droite. 'JUMP_LEFT' indique que le joueur saute à gauche.
	 * 		'STILL' indique que le joueur ne bouge pas.
	 */
	public EntityDirection getAction() {
		return this.action;
	}
	/**
	 * Change la valeur du champ 'action' en la valeur donnée en paramètre.
	 * @param action : 
	 * 		 'MOVE_RIGHT' indique que le joueur se déplace à droite. 'MOVE_LEFT' indique que le joueur se déplace à gauche.
	 * 		'JUMP_RIGHT' indique que le joueur saute à droite. 'JUMP_LEFT' indique que le joueur saute à gauche.
	 * 		'STILL' indique que le joueur ne bouge pas.
	 */
	public void setAction(EntityDirection action) {
		this.action = action;
	}
	/**
	 * Renvoie la valeur du champ 'moving' de la classe.
	 * @return 
	 *		booléen contenu dans 'moving'. Si true, alors le joueur est en mouvement; si false, alors le joueur est immobile.
	 */
	public boolean isMoving() {
		return this.moving;
	}
	/**
	 * Change la valeur du champ 'moving' en le booléen donné en paramètre.
	 * @param m :
	 * 		 True indique que le joueur est en mouvement. False indique que le joueur est immobile.
	 */
	public void setMoving(boolean m) {
		this.moving = m;
	}
	/**
	 * Renvoie la valeur du champ 'touchGround' de la classe.
	 * @return 
	 * 		booléen contenu dans touchGround. Si true, alors le personnage touche le sol; si false, alors le personnage ne touche pas le sol.
	 */
	public boolean touchGround() {
		return this.touchGround;
	}
	/**
	 * Change la valeur de touchGround en la valeur donnée en paramètre. 
	 * @param m :
	 * 		 Si true, alors le joueur touche le sol. Si false, alors le joueur ne touche pas le sol.
	 */
	public void playerOnGround(boolean m) {
		this.touchGround = m;
	}
	/**
	 * renvoie la valeur de tempX du joueur
	 * @return 
	 * 		renvoie la valeur de tempX du joueur, c'est la valeur de x du joueur stockée lors du début du saut.
	 */
	public double getTempX() {
		return this.tempX;
	}
	/**
	 * Remplace l'ancienne valeur de tempX par celle donnée en paramètre
	 * @param x :
	 * 		Nouvelle valeur de tempX.
	 */
	public void setTempX(double x) {
		this.tempX = (int)x;
	}
	/**
	 * renvoie la valeur du champ jump de la classe player.
	 * @return Player.jump : 
	 * 		booléen égal à true si le joueur est en saut, false sinon
	 */
	public boolean jumpStatus() {
		return this.jump;
	}
	/**
	 * met à jour le champ jump de la classe Player avec la valeur mise en paramètre.
	 * @param m
	 * 		booléen correspondant à la nouvelle valeur du champ jump souhaitée.
	 */
	public void updateJumpStatus(boolean m) {
		this.jump = m;
	}
	/**
	 * Gère le mouvement du personnage, en fonction de la valeur du champ action de Player. La méthode
	 * gère les déplacement à gauche et à droite, et également le cas où le joueur ne se déplace pas.
	 * Le champ action utilise les valeurs de l'énumération EntityDirection comme directions.
	 * 
	 */
	public void movement() {
		if (moving) {
			switch(action) {
			case MOVE_RIGHT: this.updateX(3f); break;
			case MOVE_LEFT: this.updateX(-3f); break;
			}
		} else {
			action = EntityDirection.STILL;
		}
	}
	/**
	 * Met à jour la position en y du personnage en utilisant le résultat du calcul de la méthode jumpTrajectory.
	 */
	public void jump() {
		this.updateY(jumpTrajectory(this.getX(),this.getTempX()));

	}
	/**
	 * Méthode calculant la vitesse en y du personnage à partir de l'équation de trajectoire. L'équation de trajectoire est une parabole
	 * d'équation 0.005x² - 0.4x. La parabole est centrée en 0. Cependant, à partir du moment où la parabole renvoie une vitesse de 10 ou plus selon y, on renvoie alors une 
	 * vitesse de 10 au lieu de la vitesse renvoiée par la parabole. Cela permet de limiter la vitesse de chute du personnage et ainsi déviter qu'il ne passe à travers les plateformes du fait de sa vitesse trop élevée.
	 * @param x :	
	 * 			Position du personnage selon l'axe des x.
	 * @param xt : 
	 * 			Position du personnage au moment où il débute son saut. Ce paramètre est utilisé afin de normaliser le paramètre de l'équation de parabole.
	 * @return 
	 * 			Renvoie la position selon y du personnage déterminée par l'équation de trajectoire.
	 */
	public double jumpTrajectory(double x, double xt) {
		double ytemp = -0.4*Math.abs(x-xt) + 0.005*Math.abs((x-xt)*(x-xt));
		if (ytemp >= 10) {
			return 10;
		} else {
			return ytemp;
		}
	}
}
