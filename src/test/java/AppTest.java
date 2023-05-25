import org.enemies.Golem;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.main.Game;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class AppTest {
    private Game game;

    @Before
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "true");
    }

    @BeforeEach
    public void setUp() {
        try {
            game = new Game();
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        game.getPlaying().resetAll();
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
