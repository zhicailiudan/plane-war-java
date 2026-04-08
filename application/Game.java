package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.HeroAircraftCreate;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.factory.EnemyFactory;
import edu.hitsz.item.AbstractItem;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.application.Main;

import java.util.LinkedList;
import java.util.List;

public class Game extends Pane {

    private int backGroundTop = 0;

    //游戏逻辑更新间隔
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    // 优化：泛型改为 AbstractEnemy（更精准，后续遍历/操作更方便）
    private final List<AbstractEnemy> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractItem> items;

    //屏幕中出现的敌机最大数量
    private final int enemyMaxNumber = 5;

    //敌机生成周期
    protected double enemySpawnCycle  =  20;
    private int enemySpawnCounter = 0;

    //英雄机和敌机射击周期
    protected double shootCycle = 20;
    private int shootCounter = 0;

    //当前玩家分数
    private int score = 0;

    //Boss 生成分数阈值
    private static final int BOSS_SPAWN_SCORE = 1000;
    private boolean bossSpawned = false;

    //游戏结束标志
    private boolean gameOverFlag = false;

    //画布和画笔
    private final Canvas canvas;
    private final GraphicsContext gc;

    //JavaFX 提供的动画定时器
    private AnimationTimer timer;
    //上一帧时刻(纳秒)
    private long lastNanoTime = 0L;
    //累加器，记录两帧之间经过的实际时间
    private long nanoAccumulator = 0L;

    private final EnemyFactory enemyFactory;

    public Game() {
        setPrefSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        setPickOnBounds(true);

        canvas = new Canvas(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        getChildren().add(canvas);

        HeroAircraftCreate heroair = HeroAircraftCreate.getInstance();
        heroair.createHero();
        this.heroAircraft = heroair.getHero();

        enemyFactory = new EnemyFactory();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        items = new LinkedList<>();

        new HeroController(this, heroAircraft);
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {
        if (timer != null) {
            return;
        }

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameOverFlag) {
                    stop();
                    return;
                }
                if (lastNanoTime == 0L) {
                    lastNanoTime = now;
                    return;
                }

                //固定时间步长
                long delta = now - lastNanoTime;
                lastNanoTime = now;
                nanoAccumulator += delta;

                long stepNanos = timeInterval * 1_000_000L;
                while (nanoAccumulator >= stepNanos && !gameOverFlag) {
                    nanoAccumulator -= stepNanos;
                    tick();
                }
                //重绘界面
                render();
            }
        };
        timer.start();
    }

    private void tick() {
        enemySpawnCounter++;
        if (enemySpawnCounter >= enemySpawnCycle && enemyAircrafts.size() < enemyMaxNumber) {
            AbstractEnemy randomEnemy = enemyFactory.createRandomNormalEnemy();
            enemyAircrafts.add(randomEnemy);
            enemySpawnCounter = 0;
        }

        if (!bossSpawned && score >= BOSS_SPAWN_SCORE) {
            BossEnemy boss = new BossEnemy(
                    Main.WINDOW_WIDTH / 2,
                    80,
                    3,
                    0,
                    200
            );
            enemyAircrafts.add(boss);
            bossSpawned = true;
        }

        shootAction();
        bulletsMoveAction();
        aircraftMoveAction();
        crashCheckAction();
        postProcessAction();
        checkResultAction();
    }

    //***********************
    //      Action 各部分
    //***********************
    private void shootAction() {
        shootCounter++;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            heroBullets.addAll(heroAircraft.shoot());
            for (AbstractEnemy enemy : enemyAircrafts) {
                enemyBullets.addAll(enemy.shoot());
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
        for (AbstractItem item : items) {
            item.forward();
        }
    }

    private void aircraftMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        score += enemyAircraft.getScore();
                        List<AbstractItem> dropItems = enemyAircraft.dropSupply();
                        if (dropItems != null) {
                            items.addAll(dropItems);
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        for (AbstractItem item : items) {
            if (item.notValid()) {
                continue;
            }
            if (heroAircraft.crash(item)) {
                item.applyEffect(heroAircraft);
                item.vanish();
                break;
            }
        }

    }

    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        items.removeIf(AbstractFlyingObject::notValid);
    }


    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction(){
        // 游戏结束检查英雄机是否存活
        if (heroAircraft.getHp() <= 0) {
            gameOverFlag = true;
            System.out.println("Game Over!");
        }
    };


    //***********************
    //      Render 各部分
    //***********************

    private void render() {
        // 背景滚动
        gc.clearRect(0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        gc.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT);
        gc.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        paintImageWithPositionRevised(enemyBullets);
        paintImageWithPositionRevised(heroBullets);
        paintImageWithPositionRevised(items);
        paintImageWithPositionRevised(enemyAircrafts);

        // 绘制英雄机
        gc.drawImage(
                ImageManager.HERO_IMAGE,
                heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2.0,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2.0
        );

        paintScoreAndLife();
    }

    private void paintImageWithPositionRevised(List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            Image image = object.getImage();
            if (image == null) {
                throw new IllegalStateException(objects.getClass().getName() + " has object without image: " + object.getClass().getName());
            }
            gc.drawImage(
                    image,
                    object.getLocationX() - image.getWidth() / 2.0,
                    object.getLocationY() - image.getHeight() / 2.0
            );
        }
    }

    private void paintScoreAndLife() {
        int x = 10;
        int y = 25;
        gc.setFill(Color.rgb(255, 0, 0));
        gc.setFont(Font.font("SansSerif", FontWeight.BOLD, 22));
        gc.fillText("SCORE:" + this.score, x, y);
        y = y + 20;
        gc.fillText("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

}
