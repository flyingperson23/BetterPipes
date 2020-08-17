package flyingperson.BetterPipes.client;

public class Overlay {

    public int x, z;
    public double y;

    public Overlay(int x, double y, int z) {
        this.x = x;
        this.y = y + 0.01;
        this.z = z;
    }

}