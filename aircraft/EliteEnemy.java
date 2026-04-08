package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import factory.ItemFactory; // 导入你的简单工厂
import item.AbstractItem;
import item.ItemType;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EliteEnemy extends AbstractEnemy {

    private int power = 10;
    private int shootNum = 1;
    private int direction = 1;


    private static final Random random = new Random();
    private static final double DROP_PROBABILITY = 0.5; // 50%概率掉落

    private static final ItemType[] AVAILABLE_ITEMS = {
            ItemType.BLOOD, ItemType.BULLET, ItemType.BULLET_PLUS
    };

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.score = 30;
    }
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction*5;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }

    @Override
    public List<AbstractItem> dropSupply() {
        List<AbstractItem> items = new LinkedList<>();
        // 概率判定是否掉落
        if (random.nextDouble() <= DROP_PROBABILITY) {
            // 调用简单工厂的随机道具生成方法
            AbstractItem dropItem = ItemFactory.createRandomItem(AVAILABLE_ITEMS, getLocationX(), getLocationY());
            if (dropItem != null) {
                items.add(dropItem);
            }
        }
        return items;
    }

}