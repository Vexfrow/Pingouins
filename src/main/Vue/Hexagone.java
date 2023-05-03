package Vue;

import java.awt.Point;
import java.awt.Polygon;

public class Hexagone{
    private final double radius;

    private final Point centre;
    private final Polygon hexagone;

    public Hexagone(Point center, double radius) {
        this.centre = center;
        this.radius = radius;
        this.hexagone = createHexagon();
    }

    private Polygon createHexagon() {
        Polygon polygon = new Polygon();

        for (int i = 0; i < 6; i++) {
            double angle  = (60 * i - 30) * (Math.PI/180);
            int x = (int) (centre.x + radius * Math.cos(angle));
            int y = (int) (centre.y + radius * Math.sin(angle));
            polygon.addPoint(x, y);
        }

        return polygon;
    }

    public Polygon getHexagone(){
        return hexagone;
    }


}
