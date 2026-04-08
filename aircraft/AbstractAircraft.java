package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.strategy.BulletShootStrategy;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 集成弹道策略模式，完全兼容 AbstractFlyingObject 父类
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {

    // 最大生命值
    protected int maxHp;
    protected int hp;
    // 子弹威力（不同飞机/火力加成下可调整）
    protected int power;
    // 弹道射击策略（策略模式核心）
    protected BulletShootStrategy shootStrategy;
    // 敌机专属分数（英雄机可设为0）
    protected int score;

    /**
     * 构造方法：完全继承父类坐标/速度逻辑，新增血量属性
     */
    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.power = 10; // 默认子弹威力
        this.score = 0;  // 默认分数（英雄机无分数，敌机子类覆盖）
    }

    /**
     * 扣血方法（原有逻辑保留）
     */
    public void decreaseHp(int decrease) {
        hp -= decrease;
        if (hp <= 0) {
            hp = 0;
            vanish(); // 调用父类的消失方法
        }
    }

    /**
     * 射击核心方法：委托给策略类实现（策略模式）
     * 子类无需重写，仅需设置 shootStrategy 即可
     */
    public List<BaseBullet> shoot() {
        // 空策略校验：无射击能力的飞机返回空列表
        if (shootStrategy == null) {
            return List.of();
        }
        return shootStrategy.shoot(this);
    }

    /**
     * 飞机移动方法：重写父类 forward()，避免父类默认的横向边界反向逻辑
     * （不同飞机的移动逻辑由子类实现，父类仅定义为抽象）
     */
    @Override
    public abstract void forward();

    // ========== Getter/Setter 方法（兼容所有属性） ==========
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.min(hp, maxHp); // 防止血量超过最大值
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public BulletShootStrategy getShootStrategy() {
        return shootStrategy;
    }

    public void setShootStrategy(BulletShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}