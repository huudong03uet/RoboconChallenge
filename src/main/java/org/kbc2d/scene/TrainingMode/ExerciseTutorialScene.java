package org.kbc2d.scene.TrainingMode;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.kbc2d.game.ui.*;
import org.kbc2d.scene.BaseScene;
import org.kbc2d.scene.SceneType;
import org.kbc2d.utils.Input;
import org.kbc2d.utils.SceneManager;
//import javafx.scene.web.WebView;

public class ExerciseTutorialScene extends BaseScene {
    ButtonGame backBtn;
//    ButtonGame backBtnC;
//    ButtonGame backE;
    ButtonGame nextBtn;
//    ButtonGame nextBtnC;
//    ButtonGame nextE;
//    WebView webView;
    TextGame textGame;

    BackgroundGame backgroundGame;
    VideoGame videoGame;








    public ExerciseTutorialScene() {

//        backBtn = new ButtonGame("asset/textures/ui/hexMenu/back.png", 10, 525);
//        backBtnC = new ButtonGame("asset/textures/ui/hexMenu/backHover.png", 10, 525);
//        backE = backBtn;
//        backgroundGame = new BackgroundGame("asset/textures/ui/rectMenu/Background.png");
//
//        nextBtn = new ButtonGame("asset/textures/ui/hexMenu/continue.png", 995, 525);
//        nextBtnC = new ButtonGame("asset/textures/ui/hexMenu/continueHover.png", 995, 525);
//        nextE = nextBtn;
//        webView = new WebView();
//        WebView webView = new WebView();
//        webView.getEngine().loadContent("<iframe width=\"930\ height=\"523\" src=\"https://www.youtube.com/embed/MpDN0gsiY8U\" title=\"Đừng Ai Nhắc Về Anh Ấy - Trà My Idol (Lyrics Video)\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>");
//        webView.setPrefSize(930, 523);
//        webView.setLayoutX(100);
//        webView.setLayoutY(100);
        textGame = new TextGame("Hãy xem video hướng dẫn sau và làm bài tập ở trang bên nhé.",250,100);

        textGame.setFont_(new Font("Monospaced",30));
        textGame.setColor(Color.WHITE);

        backgroundGame = new BackgroundGame("asset/textures/ui/rectMenu/Background.png");
        videoGame = new VideoGame("src/main/resources/org/kbc2d/asset/video/123.mp4", 300, 150);
        videoGame.setPlay();
        backBtn = new ButtonGame("asset/textures/ui/hexMenu/back.png", 10, 600, new DoClick() {
            @Override
            public void doClick() {

                SceneManager.setCurrentScene(SceneType.TRAINING_SCENE);
                videoGame.stop();
                videoGame.setPlay();
            }
        }, new DoHover() {
            @Override
            public void doHover() {
                backBtn.setImage("asset/textures/ui/hexMenu/backHover.png");
            }
        }, new DoNotHover() {
            @Override
            public void doNotHover() {
                backBtn.setImage("asset/textures/ui/hexMenu/back.png");
            }

        }
        );





        nextBtn = new ButtonGame("asset/textures/ui/hexMenu/continue.png", 1130, 600, new DoClick() {
            @Override
            public void doClick() {

                SceneManager.setCurrentScene(SceneType.QUESTION_TUTORIAL_SCENE);
                videoGame.stop();
                videoGame.setPlay();
            }
        }, new DoHover() {
            @Override
            public void doHover() {
                nextBtn.setImage("asset/textures/ui/hexMenu/continueHover.png");
            }
        }, new DoNotHover() {
            @Override
            public void doNotHover() {
                nextBtn.setImage("asset/textures/ui/hexMenu/continue.png");
            }

        }
        );




        Input.addObjHandleClick(backBtn);
        Input.addObjHandleHover(backBtn);

        Input.addObjHandleClick(nextBtn);
        Input.addObjHandleHover(nextBtn);



    }


    @Override
    public void render() {
        backgroundGame.render();
        backBtn.render();
        nextBtn.render();
        videoGame.render();

        textGame.render();


//        nextE.render();

//        GameVars.get("gc", javafx.scene.canvas.GraphicsContext.class).drawImage(webView.snapshot(null, null), 100, 100);

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void handleEvent() {


    }
}
