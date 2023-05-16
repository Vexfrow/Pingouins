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
        if(!(o instanceof Position))
            return false;
        else{
            Position p = (Position) o;
            return (x == p.x && y == p.y);
        }
    }

    public int hash(){
        return this.x*100+y;
    }

}