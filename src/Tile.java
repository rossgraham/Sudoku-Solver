import java.util.ArrayList;
import java.util.Set;

public class Tile{

    private int num;
    private boolean filled;
    private int id;
    private ArrayList<Integer> possibles = new ArrayList<>();
    private int[] coord = new int[2];

    public Tile(int num, int id){

        this.id = id;

        if(num != 0){
            this.num = num;
            this.filled = true;
        }
        else {
            this.num = 0;
            this.filled = false;
        }

        this.coord[0] = id/9;
        this.coord[1] = id%9;
    }


    public int getNum() {
        return num;
    }

    public int getId() {
        return id;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setPossibles(ArrayList<Integer> possibles) {
        this.possibles = possibles;

    }

    public ArrayList<Integer> getPossibles() {
        return possibles;
    }

    public String getCoord() {
        return "(" + coord[0] + "," + coord[1] + ")";
    }

    public void setNum(int num) {
        this.num = num;
        filled = true;
    }

    public void printpossibles(){
        if(isFilled()){
            return;
        }
        System.out.println(getCoord());
        possibles.forEach(c -> System.out.print(c+" "));
        System.out.println();
        System.out.println();
    }
}
