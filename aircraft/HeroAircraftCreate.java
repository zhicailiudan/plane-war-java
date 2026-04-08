package edu.hitsz.aircraft;

import edu.hitsz.main.Main;
import edu.hitsz.utils.ImageManager;


public class HeroAircraftCreate {
    private volatile static HeroAircraftCreate instance;
    private HeroAircraft heroAircraft;

    private HeroAircraftCreate () {

    }

    public static HeroAircraftCreate getInstance() {
        if (instance == null) {
            synchronized (HeroAircraftCreate.class){
                if (instance == null) {
                    instance = new HeroAircraftCreate();
                }
            }
        }
        return instance;
    }

    public void createHero() {
        this.heroAircraft = new HeroAircraft(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - (int) ImageManager.HERO_IMAGE.getHeight() ,
                0, 0, 100
        );
    }

    public HeroAircraft getHero() {
        if (this.heroAircraft == null) {
            createHero();
        }

        return this.heroAircraft;
    }
    public void clear () {
        this.heroAircraft = null;
    }
}
