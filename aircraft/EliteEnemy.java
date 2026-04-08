package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.SingleStraightShoot;
import factory.ItemFactory;
import item.AbstractItem;
import item.ItemType;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EliteEnemy extends AbstractEnemy {

    private static final Random random = new Random();
    private static final double DROP_PROBABILITY = 0.5;

    private static final ItemType[] AVAILABLE_ITEMS = {
            ItemType.BLOOD, ItemType.BULLET, ItemType.BULLET_PLUS
    };

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootStrategy = new SingleStraightShoot();
        this.score = 30;
        this.maxHp = hp;
        this.hp = hp;
    }

    @Override
    public void forward() {
        locationX += speedX;
        locationY += speedY;
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return shootStrategy.shoot(this);
    }

    @Override
    public List<AbstractItem> dropSupply() {
        List<AbstractItem> items = new LinkedList<>();
        if (random.nextDouble() <= DROP_PROBABILITY) {
            AbstractItem dropItem = ItemFactory.createRandomItem(AVAILABLE_ITEMS, getLocationX(), getLocationY());
            if (dropItem != null) {
                items.add(dropItem);
            }
        }
        return items;
    }

}
