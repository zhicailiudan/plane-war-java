package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.SingleStraightShoot;
import edu.hitsz.strategy.TripleStraightShoot;
import edu.hitsz.strategy.FiveFanScatterShoot;
import edu.hitsz.strategy.CircleShootStrategy;

import java.util.LinkedList;
import java.util.List;

public class HeroAircraft extends AbstractAircraft {

    private int shootNum = 1;
    private int power = 30;
    private int direction = -1;
    private int fireLevel = 0;

    public HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootStrategy = new SingleStraightShoot();
        this.power = 10;
    }

    @Override
    public void forward() {
    }

    public void powerUp(int level) {
        this.fireLevel = level;
        if (level >= 3) {
            this.shootStrategy = new CircleShootStrategy();
            this.power = 25;
        } else if (level >= 2) {
            this.shootStrategy = new FiveFanScatterShoot();
            this.power = 20;
        } else if (level >= 1) {
            this.shootStrategy = new TripleStraightShoot();
            this.power = 15;
        } else {
            this.shootStrategy = new SingleStraightShoot();
            this.power = 10;
        }
    }

    public void powerDown() {
        this.fireLevel = 0;
        this.shootStrategy = new SingleStraightShoot();
        this.power = 10;
    }

    public int getFireLevel() {
        return fireLevel;
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> bullets = shootStrategy.shoot(this);
        List<BaseBullet> heroBullets = new LinkedList<>();
        for (BaseBullet bullet : bullets) {
            HeroBullet heroBullet = new HeroBullet(
                bullet.getLocationX(),
                bullet.getLocationY(),
                bullet.getSpeedX(),
                bullet.getSpeedY() * -1,
                bullet.getPower()
            );
            heroBullets.add(heroBullet);
        }
        return heroBullets;
    }

}
