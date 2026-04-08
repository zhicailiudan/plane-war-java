package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import java.util.LinkedList;
import java.util.List;

public class CircleShootStrategy implements BulletShootStrategy {
    private static final int BULLET_NUM = 20;
    private static final int POWER = 30;
    private static final int SHOOT_INTERVAL = 40;
    private static final int SPEED = 6;
    private int shootCounter = 0;

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> bullets = new LinkedList<>();
        shootCounter++;
        if (shootCounter >= SHOOT_INTERVAL) {
            shootCounter = 0;
            double centerX = aircraft.getLocationX();
            double centerY = aircraft.getLocationY();

            for (int i = 0; i < BULLET_NUM; i++) {
                double angle = i * 2 * Math.PI / BULLET_NUM;
                int speedX = (int) (SPEED * Math.cos(angle));
                int speedY = (int) (SPEED * Math.sin(angle));
                BaseBullet bullet = new EnemyBullet((int) centerX, (int) centerY, speedX, speedY, POWER);
                bullets.add(bullet);
            }
        }
        return bullets;
    }
}
