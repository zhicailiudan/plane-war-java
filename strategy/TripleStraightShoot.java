package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import java.util.LinkedList;
import java.util.List;

public class TripleStraightShoot implements BulletShootStrategy {
    private static final int SHOOT_NUM = 3;
    private static final int POWER = 15;
    private static final int SHOOT_INTERVAL = 20;
    private int shootCounter = 0;

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> bullets = new LinkedList<>();
        shootCounter++;
        if (shootCounter >= SHOOT_INTERVAL) {
            shootCounter = 0;
            int x = aircraft.getLocationX();
            int y = aircraft.getLocationY() + 20;
            int speedY = aircraft.getSpeedY() + 5;
            for (int i = 0; i < SHOOT_NUM; i++) {
                int bulletX = x + (i * 15 - 15);
                BaseBullet bullet = new EnemyBullet(bulletX, y, 0, speedY, POWER);
                bullets.add(bullet);
            }
        }
        return bullets;
    }
}
