package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import factory.ItemFactory;
import item.AbstractItem;
import item.ItemType;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
/**
 * 王牌敌机
 * 核心特性：
 * 1. 向屏幕下方移动，同时可左右移动
 * 2. 扇形子弹
 * 3. 坠毁时概率掉落5种道具
 */

public class AceEnemy extends AbstractEnemy {
    private static final int SHOOT_NUM = 3;
    private static final int POWER = 20;
    private static final int SHOOT_INTERVAL = 15;
    private static final double SCATTER_ANGLE = 20;
    private int shootCounter = 0;

    private static final int LEFT_BOUND = 30;
    private static final int RIGHT_BOUND = Main.WINDOW_WIDTH - 30;
    private static final Random random = new Random();

    private static final double DROP_PROBABILITY = 0.7;
    private static final ItemType[] AVAILABLE_ITEMS = ItemType.values();

    public AceEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.score = 100;
    }

    @Override
    public void forward() {
        super.forward();
        if (locationX <= LEFT_BOUND) {
            speedX = Math.abs(speedX) + random.nextInt(2);
        } else if (locationX >= RIGHT_BOUND) {
            speedX = -Math.abs(speedX) - random.nextInt(2);
        }
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> bullets = new LinkedList<>();
        shootCounter++;

        if (shootCounter >= SHOOT_INTERVAL) {
            shootCounter = 0;
            int baseX = this.getLocationX();
            int baseY = this.getLocationY() + 25;
            int baseSpeedY = this.getSpeedY() + 6;

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

    @Override
    public List<AbstractItem> dropSupply() {
        List<AbstractItem> dropItems = new LinkedList<>();
        if (random.nextDouble() <= DROP_PROBABILITY) {
            AbstractItem item = ItemFactory.createRandomItem(
                    AVAILABLE_ITEMS,
                    this.getLocationX(),
                    this.getLocationY()
            );
            if (item != null) {
                dropItems.add(item);
            }
        }
        return dropItems;
    }
}