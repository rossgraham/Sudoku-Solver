import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;

public class Grid {

    public final ArrayList<Integer> complete = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

    private int Filled;

    private ArrayList<House> rows = new ArrayList<>();
    private ArrayList<House> columns = new ArrayList<>();
    private ArrayList<House> boxes = new ArrayList<>();
    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<House> houses = new ArrayList<>();
    private Hashtable<Tile, ArrayList<Integer>> tilePossibles = new Hashtable<>();


    public Grid(int[][] grid) {

        int id = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                Tile t = new Tile(grid[i][j], id);
                this.tiles.add(t);
                id++;

            }
        }

        populate();


        getTiles().forEach(c -> possible(c));

        int count = 0;

        for(Tile t: getTiles()){
            if (t.isFilled()){
                count++;
            }
        }

        Filled = count;




    }

    public void populate() {
        int rownum = 0;
        for (int i = 0; i < tiles.size(); i += 9) {
            ArrayList<Tile> row = new ArrayList(tiles.subList(i, i + 9));
            House h = new House(row, 'r', rownum);
            rows.add(rownum, h);
            houses.add(h);
            rownum++;
        }
        for (int i = 0; i < 9; i++) {

            ArrayList<Tile> column = new ArrayList();

            for (Tile t :
                    tiles) {
                if (t.getId() % 9 == i) {
                    column.add(t);
                }
            }
            House h = new House(column, 'c', i);
            houses.add(h);
            columns.add(i, h);


        }

        for (int i = 0; i < 9; i++) {

            ArrayList<Tile> box = new ArrayList<>();

            for (Tile t :
                    tiles) {
                int id = t.getId();
                int row = id / 9;
                int col = id % 9;

                if (((row / 3) * 3 + (col / 3)) == i) {

                    box.add(t);

                }
            }
            House h = new House(box, 'b', i);
            boxes.add(i, h);
            houses.add(h);

        }
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }


    public ArrayList<House> getHouses() {
        return houses;
    }

    public ArrayList<House> getTileHouses(Tile t) {

        ArrayList<House> houses = new ArrayList<>();

        for (House h :
                getHouses()) {

            if (h.getTiles().contains(t)) {
                houses.add(h);
            }

        }

        return houses;

    }

    public ArrayList<Integer> getVisible(Tile tile) {
        ArrayList<House> houses1 = getTileHouses(tile);
        ArrayList<Integer> visible = new ArrayList<>();
        for (House h : houses1) {
            for (Tile t : h.getTiles()) {
                if (t.equals(tile)) {
                    continue;
                } else {
                    visible.add(t.getNum());
                }
            }
        }
        return visible;
    }

    public void setFilled(int filled) {
        Filled = filled;
    }

    public boolean isLegal(Tile tile, int num){
        ArrayList<House> houses = getTileHouses(tile);
        for(House h: houses){
            for(Tile t: h.getTiles()){
                if(t.equals(tile)){
                    continue;
                }
                if(t.isFilled()){
                    if(t.getNum() == num){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<Tile> getEmpties(){

        ArrayList<Tile> empties = new ArrayList<>();

        for(Tile t: getTiles()){
            if(!t.isFilled()){
                empties.add(t);
            }
        }
        return empties;
    }


    public void printGrid() {
        System.out.println("_______________________________________________________");
        for (House r :
                rows) {
            System.out.println("|     |     |     |     |     |     |     |     |     |");
            for (Tile t : r.getTiles()) {
                if (t.getNum() == 0) {
                    System.out.print("|     ");
                } else {
                    System.out.print("| " + t.getNum() + "   ");
                }
            }
            System.out.print("|");
            System.out.println();
            System.out.println("|_____|_____|_____|_____|_____|_____|_____|_____|_____|");

        }


    }

    public void printGridwithPossibles() {
        System.out.println("_______________________________________________________");
        for (House r :
                rows) {
            String celltop ="| ";
            String cells = "| ";
            String cellbottom="|";

            for (int i = 0; i <9; i++) {

                Tile t = r.getTiles().get(i);

                if(t.isFilled()){
                    if(t.getId()%9 == 0) {
                        celltop += "    ";
                        cells += " " + t.getNum() + "  ";
                        cellbottom += "_____";
                    }
                    else{
                        celltop += "|     ";
                        cells += "|  " + t.getNum() + "  ";
                        cellbottom += "|_____";
                    }
                }
                else{
                    String celltop1 ="| ";
                    String cells1 = "| ";
                    String cellbottom1="|";
                        for(Integer in : t.getPossibles()){
                            if(celltop1.length()<6) {
                                celltop1 += in + " ";
                            }
                            else if(cells1.length()<6){
                                cells1+= in + " ";
                            }
                            else if(cellbottom1.length()< 6){

                                cellbottom1+= in +" ";
                            }

                        }

                        if (celltop1.length()<6){
                            while(celltop1.length()<6){
                                celltop1+=" ";
                            }
                        }
                        if (cells1.length()<6){
                            while(cells1.length()<6){
                                cells1+=" ";
                            }
                         }
                        if (cellbottom1.length()<6){
                            while(cellbottom1.length()<6){
                             cellbottom1+="_";
                            }
                        }

                        if(t.getId()%9 == 0){
                            celltop = celltop1;
                            cells = cells1;
                            cellbottom = cellbottom1;
                        }
                        else {
                            celltop += celltop1;
                            cells += cells1;
                            cellbottom += cellbottom1;
                        }

                }



            }
            celltop+="|";
            cells+="|";
            cellbottom+="|";
            System.out.println(celltop);
            System.out.println(cells);
            System.out.println(cellbottom);

        }


    }

    public void possible(Tile tile) {


        if (tile.isFilled()) {
            return;
        } else {

            ArrayList<Integer> init = new ArrayList<>();
            ArrayList<Integer> visibles = getVisible(tile);
            for (Integer i : complete) {
                if (!visibles.contains(i)) {
                    init.add(i);
                }
            }

            tile.setPossibles(init);
        }
    }

    public void cleanpossible(){
        for(Tile t: getTiles()){
            ArrayList<Integer> possible = new ArrayList<>();
            for(Integer i: t.getPossibles()){
                if(isLegal(t, i)){
                    possible.add(i);
                }
            }
            t.setPossibles(possible);
        }
    }

    public void check(){

        setFilled(0);

        int count = 0;

        for(Tile t: getTiles()){
            if(t.isFilled()){
                count ++;
                setFilled(count);
                continue;
            }
            else {
                ArrayList<Integer> poss = t.getPossibles();
                ArrayList<Integer> vis = getVisible(t);
                for (Integer i: vis){
                    if(poss.contains(i)){
                        poss.remove(i);
                    }
                }
            }
        }


        System.out.println("BOXES FILLED: "+ count);
    }

    public boolean finalcheck(){
        for (House h:
             getHouses()) {

            if(!h.toIntArray().containsAll(complete)){
                System.out.println("Problem with "+ h.getID());
                return false;
            }

        }
        return true;
    }

    public boolean solve(int count) {

        if (Filled == 81 || count > 99) {
            if(finalcheck()){
                System.out.println("WINNER");
                printGrid();
                return true;
            }
            else{
                printGridwithPossibles();
                return false;
            }

        } else {

            getHouses().forEach(c ->{
                    c.tupler();

            });
            cleanpossible();
            getHouses().forEach(c ->{
                c.onlyOne();

            });

            for (Tile t :
                    getEmpties()) {
                if (t.getPossibles().size() == 1) {
                    t.setNum(t.getPossibles().get(0));
                }
            }


            int next = count + 1;


            check();



            solve(next);
        }

        return true;




    }



    public static void main(String[] args) {

        int[][] grid = new int[9][9];

        Scanner sc = new Scanner(System.in);

        System.out.println("Please input a 9-digit string representing the first row of the Sudoku grid. If the cell is empty, input a zero. Once you have finished the row, press enter.");



        System.out.println("You must continue this for each row of the puzzle.");


        for (int i = 0; i < 9 ; i++) {

            System.out.println("Row "+i+": ");

            String row = sc.nextLine();
            char[] row1 = row.toCharArray();

            for (int j = 0; j < 9; j++) {

                grid[i][j] = Character.getNumericValue(row1[j]);
            }


        }

        Grid puzzle = new Grid(grid);

        puzzle.printGrid();

        System.out.println("Now Solving");

        puzzle.solve(0);








    }
}
