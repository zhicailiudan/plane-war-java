package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.CircleShootStrategy;
import factory.ItemFactory;
import item.AbstractItem;
import item.ItemType;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BossEnemy extends AbstractEnemy {

    private static final int LEFT_BOUND = 50;
    private static final int RIGHT_BOUND = Main.WINDOW_WIDTH - 50;
    private static final int BOSS_HEIGHT = 100;
    private static final Random random = new Random();

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootStrategy = new CircleShootStrategy();
        this.score = 500;
        this.maxHp = hp;
        this.hp = hp;
    }

    @Override
    public void forward() {
        locationX += speedX;
        if (locationX <= LEFT_BOUND) {
            speedX = Math.abs(speedX);
        } else if (locationX >= RIGHT_BOUND) {
            speedX = -Math.abs(speedX);
        }
        if (locationY <= 50 || locationY >= BOSS_HEIGHT) {
            speedY = -speedY;
        }
        locationY += speedY;
    }

    @Override
    public List<BaseBullet> shoot() {
        return shootStrategy.shoot(this);
    }

    @Override
    public List<AbstractItem> dropSupply() {
        List<AbstractItem> dropItems = new LinkedList<>();
        ItemType[] allItems = ItemType.values();
        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(allItems.length);
            AbstractItem item = ItemFactory.createItem(
                    allItems[randomIndex],
                    this.getLocationX() + random.nextInt(100) - 50,
                    this.getLocationY()
            );
            if (item != null) {
                dropItems.add(item);
            }
        }
        return dropItems;
    }
}
