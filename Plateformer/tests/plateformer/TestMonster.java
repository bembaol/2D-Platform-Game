package plateformer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.newdawn.slick.SlickException;
/**
 * Cette classe sert � tester certaines m�thodes de la classe Monster
 * @author Emmanuel Dietlin, Chlo� Demuynck, Olivier Bemba, Maxence Durieu
 *
 */
class TestMonster {
/**
 * Champ contenant le Monster qui servira � tester certaines m�thodes de la classe Monster.
 */
private Monster monster;
	/**
	 * On initialise d'abord le champ Monster de la classe.
	 * @throws SlickException
	 */
	@BeforeEach
	public void initialisation() throws SlickException {
		monster = new Monster(32,32,50,50);
	}
	/**
	 * A la fin, on supprime le Monster cr��.
	 */
	@AfterEach
	public void suppression() {
		monster = null;
	}

	/**
	 * On teste la m�thode collision() de Monster.
	 * On teste donc si lorsque le joueur et le monstre entrent en contact, la m�thode renvoie true,
	 * et si lorsqu'ils ne sont pas en contact, la m�thode renvoie false.
	 */
	@Test
	void testCollision() {
		assertEquals(true, monster.collision(45, 45, 16,32));
		assertEquals(false, monster.collision(150, 190, 16,32));
	}
}
