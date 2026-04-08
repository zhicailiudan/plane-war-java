package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.AceEnemy;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.EnemyAircraftType;
import edu.hitsz.aircraft.MasterEnemy;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import java.util.Random;

public class EnemyFactory {
    private static final Random random = new Random();
    private static final EnemyAircraftType[] NORMAL_ENEMY_TYPES = {
            EnemyAircraftType.MOB,
            EnemyAircraftType.ELITE,
            EnemyAircraftType.ACE,
            EnemyAircraftType.MASTER
    };

    public AbstractEnemy createEnemy(EnemyAircraftType enemyType) {
        int initX = calculateInitX(enemyType);
        int initY = -getEnemyImageHeight(enemyType);

        return switch (enemyType) {
            case MOB -> new MobEnemy(initX, initY, 0, 10, 30);
            case ELITE -> new EliteEnemy(initX, initY, getRandomSpeedX(2), 8, 50);
            case ACE -> new AceEnemy(initX, initY, getRandomSpeedX(3), 6, 80);
            case MASTER -> new MasterEnemy(initX, initY, getRandomSpeedX(4), 4, 100);
            case BOSS -> new BossEnemy(Main.WINDOW_WIDTH / 2, initY, 1, 0, 500);
        };
    }

    public AbstractEnemy createRandomNormalEnemy() {
        EnemyAircraftType randomType = NORMAL_ENEMY_TYPES[random.nextInt(NORMAL_ENEMY_TYPES.length)];
        return createEnemy(randomType);
    }

    private int calculateInitX(EnemyAircraftType enemyType) {
        int imageWidth = getEnemyImageWidth(enemyType);
        return random.nextInt(Main.WINDOW_WIDTH - imageWidth);
    }

    private int getEnemyImageWidth(EnemyAircraftType enemyType) {
        return switch (enemyType) {
            case MOB -> ImageManager.MOB_ENEMY_IMAGE.getWidth();
            case ELITE -> ImageManager.ELITE_ENEMY_IMAGE.getWidth();
            case ACE -> ImageManager.ACE_ENEMY_IMAGE.getWidth();
            case MASTER -> ImageManager.MASTER_ENEMY_IMAGE.getWidth();
            case BOSS -> ImageManager.BOSS_ENEMY_IMAGE.getWidth();
        };
    }

    private int getEnemyImageHeight(EnemyAircraftType enemyType) {
        return switch (enemyType) {
            case MOB -> ImageManager.MOB_ENEMY_IMAGE.getHeight();
            case ELITE -> ImageManager.ELITE_ENEMY_IMAGE.getHeight();
            case ACE -> ImageManager.ACE_ENEMY_IMAGE.getWidth();
            case MASTER -> ImageManager.MASTER_ENEMY_IMAGE.getHeight();
            case BOSS -> ImageManager.BOSS_ENEMY_IMAGE.getHeight();
        };
    }

    private int getRandomSpeedX(int speed) {
        return random.nextBoolean() ? speed : -speed;
    }

    public AbstractEnemy createEnemy(String enemyTypeStr) {
        EnemyAircraftType type = EnemyAircraftType.valueOf(enemyTypeStr.toUpperCase());
        return createEnemy(type);
    }
}
