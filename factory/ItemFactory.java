package edu.hitsz.factory;

import edu.hitsz.item.AbstractItem;
import edu.hitsz.item.BloodItem;
import edu.hitsz.item.BombItem;
import edu.hitsz.item.BulletItem;
import edu.hitsz.item.BulletPlusItem;
import edu.hitsz.item.FreezeItem;
import edu.hitsz.item.ItemType;
import java.util.Random;

public class ItemFactory {
    private static final Random random = new Random();

    public static AbstractItem createItem(ItemType type, int x, int y, int speedY) {
        switch (type) {
            case BULLET:
                return new BulletItem(x, y, speedY);
            case BLOOD:
                return new BloodItem(x, y, speedY);
            case BOMB:
                return new BombItem(x, y, speedY);
            case BULLET_PLUS:
                return new BulletPlusItem(x, y, speedY);
            case FREEZE:
                return new FreezeItem(x, y, speedY);
            default:
                return null;
        }
    }

    public static AbstractItem createItem(ItemType type, int x, int y) {
        return createItem(type, x, y, 5);
    }

    public static AbstractItem createRandomItem(ItemType[] availableTypes, int x, int y) {
        if (availableTypes == null || availableTypes.length == 0) {
            return null;
        }
        ItemType randomType = availableTypes[random.nextInt(availableTypes.length)];
        return createItem(randomType, x, y);
    }
}
