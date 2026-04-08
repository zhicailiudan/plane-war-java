package edu.hitsz.strategy;

import edu.hitsz.aircraft.HeroAircraft;

public interface FirePowerStrategy {
    void apply(HeroAircraft hero);
    void cancel(HeroAircraft hero);
}