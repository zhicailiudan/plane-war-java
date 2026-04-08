package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.DoubleStraightShoot;
import factory.ItemFactory;
import item.AbstractItem;
import item.ItemType;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MasterEnemy extends AbstractEnemy {

    private static final int LEFT_BOUND = 50;
    private static final int RIGHT_BOUND = Main.WINDOW_WIDTH - 50;
    private static final Random random = new Random();

    private static final double DROP_PROBABILITY = 0.6;
    private static final ItemType[] AVAILABLE_ITEMS = {
            ItemType.BLOOD, ItemType.BOMB, ItemType.BULLET, ItemType.BULLET_PLUS
    };

    public MasterEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootStrategy = new DoubleStraightShoot();
        this.score = 80;
        this.maxHp = hp;
        this.hp = hp;
    }

    @Override
    public void forward() {
        locationX += speedX;
        locationY += speedY;
        if (locationX <= LEFT_BOUND) {
            speedX = Math.abs(speedX);
        } else if (locationX >= RIGHT_BOUND) {
            speedX = -Math.abs(speedX);
        }
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
