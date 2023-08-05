package org.kbc2d.scene;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import org.kbc2d.game.GameVars;
import org.kbc2d.game.object.*;
import org.kbc2d.game.ui.TextGame;
import org.kbc2d.utils.ImageManager;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

public class PvPScene extends BaseScene{
    public List<Ring> rings = new ArrayList<>();
    public List<River> rivers = new ArrayList<>();

    public boolean isEndGame;
    private double timeGame;
    public List<Pole> poles = new ArrayList<>();
    public Robot enemy = new Robot(Type.RED_TEAM);

    public Robot robot = new Robot(Type.BLUE_TEAM);
    public Floor floor = new Floor(Type.BLUE_TEAM);
    public Floor floorEnemy = new Floor(Type.RED_TEAM);
    public int PointTeam1 = 0;
    public int PointTeam2 = 0;
    TextGame pointString1 = new TextGame(Integer.toString(PointTeam1), 10, 10);
    TextGame pointString2 = new TextGame(Integer.toString(PointTeam2), 310, 10);

    Image background;

    float velX;
    float velY;
    float velZ;
    public PvPScene() {
        // khởi tạo thành phần của game
        //pole
        Pole pole1 = new Pole(10, 10, 560, 280);
        Pole pole2 = new Pole(10, 10, 480, 200);
        Pole pole3 = new Pole(10, 10, 640 - 8/2, 360 - 8/2);
        Pole pole4 = new Pole(10, 10, 480, 356);
        Pole pole5 = new Pole(10, 10, 480, 512);
        Pole pole6 = new Pole(10, 10, 792, 200);
        Pole pole7 = new Pole(10, 10, 792, 356);
        Pole pole8 = new Pole(10, 10, 792, 512);
        Pole pole9 = new Pole(10, 10, 560, 432);
        Pole pole10 = new Pole(10, 10, 712, 280);
        Pole pole11 = new Pole(10, 10, 712, 432);

        poles.add(pole3);
        poles.add(pole1);
        poles.add(pole2);
        poles.add(pole4);
        poles.add(pole5);
        poles.add(pole6);
        poles.add(pole7);
        poles.add(pole8);
        poles.add(pole9);
        poles.add(pole10);
        poles.add(pole11);

        //rings
        for(int i = 0; i < 5; i++) {
            Ring ring1 = new Ring(320, 40, Type.BLUE_TEAM);
            Ring ring2 = new Ring(320+640-Ring.widthRing, 40, Type.RED_TEAM);
            Ring ring3 = new Ring(320+640-Ring.widthRing, 40+640-Ring.widthRing, Type.RED_TEAM);
            Ring ring4 = new Ring(320, 40+640-Ring.widthRing, Type.BLUE_TEAM);
            rings.add(ring1);
            rings.add(ring2);
            rings.add(ring3);
            rings.add(ring4);
        }
        //rivers
        for(int i = 0; i < 12; i++) {
            River river1 = new River(448, 168+32*i);
            River river2 = new River(800, 168+32*i);
            rivers.add(river1);
            rivers.add(river2);
        }
        for(int i = 0; i < 10; i++) {
            if(i == 5 || i == 6) {
                continue;
            }
            River river = new River(480+i*32, 168);
            rivers.add(river);
        }
        for(int i = 0; i < 10; i++) {
            if(i == 3 || i == 4) {
                continue;
            }
            River river = new River(480+i*32, 520);
            rivers.add(river);
        }
        isEndGame = false;
        timeGame = 180;
        background = ImageManager.getImage("asset/Map.png");
    }
    @Override
    public void render() {
        floorEnemy.render();
        floor.render();
        GraphicsContext gc = GameVars.get("gc", GraphicsContext.class);
        gc.drawImage(ImageManager.getImage("asset/Map.png"), 320, 40, 640, 640);
        for(int i = 0; i < rivers.size(); i++) {
            rivers.get(i).render();
        }
        for(int i = 0; i < rings.size(); i++) {
            rings.get(i).render();
        }
        for(int i = 0; i < poles.size(); i++) {
            poles.get(i).render();
        }
        robot.render();
        enemy.render();

        pointString1.render();


        pointString2.render();
    }

    @Override
    public void update(float deltaTime) {
        enemy.update(deltaTime, this);
        enemy.update(rings, deltaTime);
        robot.update(deltaTime, this);
        robot.update(rings, deltaTime);
        for(int i = 0 ; i < rings.size(); i++) {
            rings.get(i).update(deltaTime);
        }

        //check va chạm giữa vòng và cột
        for(int i = 0; i < rings.size(); i++) {
            for(int j = 0; j < poles.size(); j++ ) {
                collisionCheck(rings.get(i), poles.get(j));
            }
        }
        timeGame -= deltaTime;
        if(timeGame <= 0) {
            isEndGame = true;
        }
        if(isEndGame) {
            String teamWin;
            if(PointTeam1 - PointTeam2 >= 0) {
                teamWin = "Team Blue";
            } else {
                teamWin = "Team Red";
            }
            System.out.println(teamWin + "chiến thắng với điểm số: " + Integer.toString(PointTeam1) + " " + Integer.toString(PointTeam2));
        }




    }

    public void collisionCheck(Ring ring, Pole pole) {
        double epsilon = 2; // Giá trị epsilon (có thể điều chỉnh tùy theo yêu cầu)

        double distance = ring.getCenter().distanceTo(pole.getCenter());
        double sumOfHeights = ring.getWidth()/2 + pole.getWidth()/2;
        //vòng trúng vào cột
        if( ring.getHigh() < pole.getHeightPole() && distance - 2 < ring.getWidth()/2-pole.getWidth()/2 && !ring.getIn()) {
            if(ring.getTeam() == Type.BLUE_TEAM && pole.getTeam() == Type.RED_TEAM) {
                System.out.println(1);
                PointTeam1 += pole.getPoint();
                PointTeam2 -= pole.getPoint();
                pointString1 = new TextGame(Integer.toString(PointTeam1), 10, 10);
                pointString2 = new TextGame(Integer.toString(PointTeam2), 310, 10);
            } else if(ring.getTeam() == Type.BLUE_TEAM && pole.getTeam() == Type.NO_TEAM) {
                System.out.println(2);
                PointTeam1 += pole.getPoint();
                pointString1 = new TextGame(Integer.toString(PointTeam1), 10, 10);
            } else if(ring.getTeam() == Type.RED_TEAM && pole.getTeam() == Type.NO_TEAM) {
                System.out.println(3);
                PointTeam2 += pole.getPoint();
                pointString2 = new TextGame(Integer.toString(PointTeam2), 310, 10);
            } else if(ring.getTeam() == Type.RED_TEAM && pole.getTeam() == Type.BLUE_TEAM) {
                System.out.println(4);
                PointTeam1 -= pole.getPoint();
                PointTeam2 += pole.getPoint();
                pointString1 = new TextGame(Integer.toString(PointTeam1), 10, 10);
                pointString2 = new TextGame(Integer.toString(PointTeam2), 310, 10);
            }
            System.out.println(ring.getTeam());
            System.out.println(pole.getTeam());
            System.out.println(5);
            ring.setIn(true);
            //check còn cột nào chưa có không
            isEndGame = true;
            pole.setTeam(ring.getTeam());

            for(int i = 0; i < poles.size(); i++) {
                System.out.println(poles.get(i).getTeam());
                System.out.println(ring.getTeam());
                if(poles.get(i).getTeam() != ring.getTeam()) {
                    isEndGame = false;
                }
            }
            System.out.println(123456);
        }

        //vòng chạm vào cạnh của cột khi không vào cột
        if (distance < sumOfHeights && ring.getHigh() < pole.getHeightPole() && !ring.getIn() && ring.getCenter().vectorBetween(pole.getCenter()).cosAngleBetween(ring.getSpeed()) >= 0) {
            System.out.println("abc");
            // Khoảng cách gần bằng tổng chiều cao với sai số epsilon cho phép
            // Thực hiện các hành động tương ứng
            Vector2D difference = ring.getSpeed().subtract(projectVectorOntoPlane(ring.getSpeed(), ring.getCenter(), pole.getCenter()));
            Vector2D scaled = difference.multiply(2);
            ring.setSpeed(ring.getSpeed().subtract(scaled));
        }

        //vòng trúng vào cột và tiếp tục đập vào cột
        if(ring.getIn() && distance + 2> ring.getWidth()/2 - pole.getWidth()/2 && ring.getCenter().vectorBetween(pole.getCenter()).cosAngleBetween(ring.getSpeed()) <= 0) {
            System.out.println(12345);
            Vector2D difference = ring.getSpeed().subtract(projectVectorOntoPlane(ring.getSpeed(), ring.getCenter(), pole.getCenter()));
            Vector2D scaled = difference.multiply(2);
            ring.setSpeed(ring.getSpeed().subtract(scaled));
        }
    }
    //bộ trợ check va chạm
    public Vector2D projectVectorOntoPlane(Vector2D v, Vector2D a, Vector2D b) {
        Vector2D ab = new Vector2D(b.x - a.x, b.y - a.y); // Vector định hướng mặt phẳng
        Vector2D normal = new Vector2D(-ab.y, ab.x); // Vector pháp tuyến của mặt phẳng

        double dotProduct = v.x * normal.x + v.y * normal.y;
        double normalDotNormal = normal.x * normal.x + normal.y * normal.y;

        double projectionX = (dotProduct / normalDotNormal) * normal.x;
        double projectionY = (dotProduct / normalDotNormal) * normal.y;

        return new Vector2D(projectionX, projectionY);
    }

    @Override
    public void handleEvent() {

    }
}
