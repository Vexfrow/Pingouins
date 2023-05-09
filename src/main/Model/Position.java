package Model;


public class Position {

    public int x;
    public int y;


    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        String resultat = "(" + x + "," + y + ")";
        return resultat;
    }

    public boolean equals(Object o){
        if(!(o instanceof Position p))
            return false;

        return (x == p.x && y == p.y);
    }
}