package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import java.util.LinkedList;
import java.util.List;

public class FanScatterShoot implements BulletShootStrategy {
    private static final int SHOOT_NUM = 3;
    private static final int POWER = 20;
    private static final int SHOOT_INTERVAL = 15;
    private static final double SCATTER_ANGLE = 20;
    private int shootCounter = 0;

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> bullets = new LinkedList<>();
        shootCounter++;
        if (shootCounter >= SHOOT_INTERVAL) {
            shootCounter = 0;
            int baseX = aircraft.getLocationX();
            int baseY = aircraft.getLocationY() + 25;
            int baseSpeedY = aircraft.getSpeedY() + 6;

            double angleStep = Math.toRadians(SCATTER_ANGLE);
            double startAngle = -SCATTER_ANGLE;

            for (int i = 0; i < SHOOT_NUM; i++) {
                double currentAngle = Math.toRadians(startAngle + i * SCATTER_ANGLE);
                int speedX = (int) (5 * Math.sin(currentAngle));
                int speedY = (int) (baseSpeedY * Math.cos(currentAngle));
                BaseBullet bullet = new EnemyBullet(baseX, baseY, speedX, speedY, POWER);
                bullets.add(bullet);
            }
        }
        return bullets;
    }
}