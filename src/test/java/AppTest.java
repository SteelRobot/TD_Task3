import org.enemies.Golem;
import org.junit.jupiter.api.AfterEach;

import org.main.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    private Game game = new Game();

    @AfterEach
    public void tearDown() {
        game.dispose();
        game = new Game();
    }

    @org.junit.jupiter.api.Test
    void hurtEnemyUntilDead() {
        Golem golem = new Golem(0, 0, 0, game.getPlaying().getEnemyManager());
        game.getPlaying().getEnemyManager().getEnemies().add(golem);
        int i = game.getPlaying().getEnemyManager().getAmountOfAllLiveEnemies();
        assertEquals(1, i);
        golem.hurt(250);
        i = game.getPlaying().getEnemyManager().getAmountOfAllLiveEnemies();
        assertEquals(0, i);
    }
    @org.junit.jupiter.api.Test
    void hurtEnemyUntilDead2() {
        Golem golem = new Golem(0, 0, 0, game.getPlaying().getEnemyManager());
        game.getPlaying().getEnemyManager().getEnemies().add(golem);
        int i = game.getPlaying().getEnemyManager().getAmountOfAllLiveEnemies();
        assertEquals(1, i);
        golem.hurt(250);
        i = game.getPlaying().getEnemyManager().getAmountOfAllLiveEnemies();
        assertEquals(0, i);
    }
}
