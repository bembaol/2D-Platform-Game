package plateformer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.newdawn.slick.SlickException;



class TestPlayer {
	/**
	 * Champ de la classe contenant le Player qui servira à tester les méthodes de la classe Player.
	 */
	private Player player;
	
	/**
	 * On initialise d'abord le champ Player de la classe.
	 */
	@BeforeEach
	public void initialisation() {
		player = new Player(50,50,50,50);
	}
	/**
	 * A la fin, on supprime le Player créé.
	 */
	@AfterEach
	public void suppression() {
		player = null;
	}

	/**
	 * On teste la méthode jumpTrajectory de Player, qui calcule donc la prochaine position en y du saut
	 * du joueur. On regarde donc si la valeur renvoyée par la méthode est bien la valeur attendue.
	 */
	@Test
	void testJumpTrajectory() {
		int xt = (int)player.getX();
		player.updateX(10);
		assertEquals(-3.5, player.jumpTrajectory(player.getX(), xt));
		player.updateX(80);
		assertEquals(4.5, player.jumpTrajectory(player.getX(), xt));
		player.updateX(80);
		assertEquals(10, player.jumpTrajectory(player.getX(), xt));
	}

}
