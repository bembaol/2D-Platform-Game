package plateformer;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import java.util.ArrayList;

/**
 * Classe créant une liste de cartes et comportant les méthodes pour gérer celle-ci.
 * @author Emmanuel Dietlin, Chloé Demuynck, Olivier Bemba, Maxence Durieu.
 *
 */
public class Maps{
	/**
	 * Tableau extensible contenant toutes les cartes du jeu. 
	 * Les cartes sont de type TiledMap.
	 */
	private ArrayList<TiledMap> maps;
	
	/**
	 * Constructeur de Maps.
	 * Initialise le tableau extensible qui va contenir les cartes du jeu.
	 */
	public Maps() {
		maps = new ArrayList<TiledMap> ();
	}
	/**
	 * 
	 * @param path :
	 * 		Chemin vers la carte
	 * @throws SlickException
	 */
	public void addMap(String path) throws SlickException {
		maps.add(new TiledMap(path));
	}
	/**
	 * Renvoie la carte située à la position i dans la liste de cartes.
	 * @param i :
	 * 		Entier correspondant à l'indice de la carte que l'on souhaite récupérer dans le tableau extensible.
	 * 		Les indices commencent à 0.
	 * @return 
	 * 		Carte se trouvant à la position i dans le tableau de cartes.
	 */
	public TiledMap getMap(int i) {
		return maps.get(i);
	}
	/**
	 * Renvoie la hauteur de la carte située à la position i dans la liste de cartes.
	 * @param i :
	 * 		Entier correspondant à l'indice de la carte dans la liste de cartes.
	 * @return 
	 * 		hauteur de la carte en nombre de tuiles.
	 */
	public int getMapHeight(int i) {
		return getMap(i).getHeight();
	}
	/**
	 * Renvoie la largeur de la carte située à la position i dans la liste de cartes.
	 * @param i :
	 * 		Entier correspondant à l'indice de la carte dans la liste de cartes.
	 * @return 
	 * 		largeur de la carte en nombre de tuiles.
	 */
	public int getMapWidth(int i) {
		return getMap(i).getWidth();
	}
	/**
	 * Renvoie la hauteur des tuiles composant la carte. On considèrera ici que les tuiles sont carrées,
	 * donc on utilisera indifferement la hauteur ou la largeur des tuiles.
	 * @param i :
	 * 		Entier correspondant à l'indice de la carte dans la liste de cartes.
	 * @return :
	 * 		largeur hauteur des tuiles composant la carte.
	 */
	public int getMapTileSide(int i ) {
		return getMap(i).getTileHeight();
	}
	
	/**
	 * Renvoie la taille de la liste de cartes
	 * 
	 */
	public int getSize() {
		return maps.size();
	}
	
}
