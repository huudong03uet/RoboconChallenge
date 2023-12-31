package org.kbc2d.game.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.kbc2d.game.GameVars;
import org.kbc2d.utils.ImageManager;

public class TextGame extends BaseComponent {
    String text;
    Font font;
    FontWeight fontWeight;
//    Text text_;

    public TextGame(String text, int x, int y) {
        super(x, y, ImageManager.getImage("asset/btn.png"));
        this.text = text;
//        text_ = new Text(text);
    }

    public void setFont_(Font font) {
//        text_.setFont(font);
        this.font = font;
    }
    public void setColor(Color color) {
//        text_.setFill(color);
        GameVars.get("gc", GraphicsContext.class).setFill(color);
    }

    public void setText_(String text) {
        this.text = text;
    }


    @Override
    public void render() {
        GameVars.get("gc", GraphicsContext.class).setFont(font);
        GameVars.get("gc", GraphicsContext.class).fillText(text, x, y);
    }

    public void setX_(int x) {
        this.x = x;
    }
    public void getLengthTextPixel() {


    }



    @Override
    public void update() {

    }

    @Override
    public void handleEvent() {

    }

    public void setFont(String arial, FontWeight bold, int i) {
    }

    public void formatText(int lenOfLine) {
        // if len(text) > i: break
        int count = 0;
        String result = "";
        for(int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') {
                count = 0;

            }
            count++;
            if (count > lenOfLine && text.charAt(i) == ' ') {
                count = 0;
                result += '\n';
                continue;
            }
            result += text.charAt(i);
        }
        this.text = result;
    }
}
