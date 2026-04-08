package aircraft;

import edu.hitsz.basic.AbstractFlyingObject;
import item.AbstractItem;
import java.util.List;
import java.util.LinkedList;


public abstract class AbstractEnemy extends AbstractFlyingObject {

    protected int maxHp;
    protected int hp;
    protected int power;
    protected int score;

    /**
     * 构造函数
     * @param locationX 初始X坐标
     * @param locationY 初始Y坐标
     * @param speedX X轴速度
     * @param speedY Y轴速度
     * @param hp 血量
     */
    public AbstractEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.power = 10; // 默认攻击力，子类可修改
        this.score = 0;  // 默认分数，子类可修改
    }

    /**
     * 核心方法：受到伤害
     * @param damage 伤害值
     * @return 是否死亡（方便调用者判断）
     */
    public boolean loseHp(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) {
            this.vanish(); // 血量归零，标记消失
            return true;
        }
        return false;
    }

    /**
     * 抽象方法：掉落物品
     * 不同的敌机（普通怪 vs BOSS）掉落概率和种类不同，交给子类实现
     * @return 掉落的道具列表（可能为空）
     */
    public abstract List<AbstractItem> dropSupply();

    /**
     * 抽象方法：射击
     * 普通怪可能只发射一颗子弹，BOSS可能发射多颗，交给子类实现
     * @return 发射的子弹列表（这里假设你有 AbstractBullet 类，如果没有可以用 Object 暂代）
     */
    // public abstract List<AbstractBullet> shoot();
    // 注意：如果你的子弹逻辑还没写好，这个方法可以先不写，或者只写空实现

    /**
     * Getter: 获取当前血量（用于UI绘制血条）
     */
    public int getHp() {
        return hp;
    }

    /**
     * Getter: 获取最大血量
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Getter: 获取分数
     */
    public int getScore() {
        return score;
    }
}