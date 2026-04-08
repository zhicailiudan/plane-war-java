package factory;

import item.*;
import item.ItemType;
import java.util.Random;

/**
 * 道具简单工厂（适配所有5种道具创建，支持随机生成指定范围的道具）
 */
public class ItemFactory {
    private static final Random random = new Random();

    public static AbstractItem createItem(ItemType type, int x, int y) {
        switch (type) {
            case BULLET:
                return new BulletItem(x, y);
            case BLOOD:
                return new BloodItem(x, y);
            case BOMB:
                return new BombItem(x, y);
            case BULLET_PLUS:
                return new BulletPlusItem(x, y);
            case FREEZE:
                return new FreezeItem(x, y);
            default:
                return null;
        }
    }


    public static AbstractItem createRandomItem(ItemType[] availableTypes, int x, int y) {
        // 边界校验：可选类型为空则返回null
        if (availableTypes == null || availableTypes.length == 0) {
            return null;
        }
        // 随机选一种道具类型
        ItemType randomType = availableTypes[random.nextInt(availableTypes.length)];
        return createItem(randomType, x, y);
    }
}