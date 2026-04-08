package edu.hitsz.application;


import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, Image> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static Image BACKGROUND_IMAGE;
    public static Image HERO_IMAGE;
    public static Image HERO_BULLET_IMAGE;
    public static Image ENEMY_BULLET_IMAGE;
    public static Image MOB_ENEMY_IMAGE;

    static {
        try {

            BACKGROUND_IMAGE = loadFromResources("/images/bg.jpg");

            HERO_IMAGE = loadFromResources("/images/hero.png");
            MOB_ENEMY_IMAGE = loadFromResources("/images/mob.png");
            HERO_BULLET_IMAGE = loadFromResources("/images/bullet_hero.png");
            ENEMY_BULLET_IMAGE = loadFromResources("/images/bullet_enemy.png");

            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static Image get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static Image get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }


    private static Image loadFromResources(String resourcePath) throws IOException {
        try (InputStream input = ImageManager.class.getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return new Image(input);
        }
    }
}
