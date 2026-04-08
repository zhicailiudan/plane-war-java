package edu.hitsz.strategy;

import edu.hitsz.aircraft.HeroAircraft;

public class BulletFirePower implements FirePowerStrategy {
    @Override
    public void apply(HeroAircraft hero) {
        hero.setShootStrategy(new TripleStraightShoot());
        hero.setPower(15);
    }

    @Override
    public void cancel(HeroAircraft hero) {
        hero.setShootStrategy(new SingleStraightShoot());
        hero.setPower(10);
    }
}