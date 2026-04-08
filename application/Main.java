package edu.hitsz.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 程序入口
 * @author hitsz & doctxing
 */
public class Main extends Application {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static void main(String[] args) {
        System.out.println("Aircraft War Start!");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();
        Scene scene = new Scene(game, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setTitle("Aircraft War");
        primaryStage.setScene(scene);

        // 固定窗口大小（内容区为 WINDOW_WIDTH/HEIGHT），并避免窗口管理器仍允许拉伸
        primaryStage.setResizable(false);
        primaryStage.setMaxWidth(WINDOW_WIDTH);
        primaryStage.setMaxHeight(WINDOW_HEIGHT);

        primaryStage.show();

        game.action();
    }
}
