package edu.hitsz.strategy;

import edu.hitsz.aircraft.HeroAircraft;

public class BulletPlusFirePower implements FirePowerStrategy {
    @Override
    public void apply(HeroAircraft hero) {
        hero.setShootStrategy(new FiveFanScatterShoot());
        hero.setPower(20);
    }

    @Override
    public void cancel(HeroAircraft hero) {
        hero.setShootStrategy(new SingleStraightShoot());
        hero.setPower(10);
    }
}