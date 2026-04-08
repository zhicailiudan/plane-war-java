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
 * 精锐敌机
 * 核心特性：
 * 1. 向屏幕下方移动，同时可左右移动
 * 2. 按周期向下直射双排子弹
 * 3. 坠毁时概率掉落4种道具（不含冰冻）
 */
public class MasterEnemy extends AbstractEnemy {

    private static final int SHOOT_NUM = 2;       // 双排子弹
    private static final int POWER = 15;          // 子弹威力
    private static final int SHOOT_INTERVAL = 20; // 射击周期（和Game中shootCycle对齐）
    private int shootCounter = 0;                // 射击计数器

    // 移动配置：左右移动的边界和速度调整
    private static final int LEFT_BOUND = 50;
    private static final int RIGHT_BOUND = Main.WINDOW_WIDTH - 50;
    private static final Random random = new Random();

    private static final double DROP_PROBABILITY = 0.6;
    // 可掉落4种道具（不含FREEZE）
    private static final ItemType[] AVAILABLE_ITEMS = {
            ItemType.BLOOD, ItemType.BOMB, ItemType.BULLET, ItemType.BULLET_PLUS
    };


    public MasterEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.score = 80;
    }

    @Override
    public void forward() {
        super.forward();
        if (locationX <= LEFT_BOUND) {
            speedX = Math.abs(speedX); // 向右移动
        } else if (locationX >= RIGHT_BOUND) {
            speedX = -Math.abs(speedX); // 向左移动
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
            int x = this.getLocationX();
            int y = this.getLocationY() + 20;
            int speedY = this.getSpeedY() + 5;


            for (int i = 0; i < SHOOT_NUM; i++) {

                int bulletX = x + (i * 20 - 10);
                BaseBullet bullet = new EnemyBullet(bulletX, y, 0, speedY, POWER);
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