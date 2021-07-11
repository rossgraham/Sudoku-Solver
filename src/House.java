
import java.util.*;


public class House {
    public enum HouseType {
        ROW, COLUMN, BOX
    }

    public final ArrayList<Integer> complete = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

    private ArrayList<Tile> tiles;

    private HouseType type;

    private int num;


    public House(ArrayList<Tile> tiles, char t, int num) {

        this.num = num;
        if (t == 'r') {
            this.type = HouseType.ROW;


        } else if (t == 'c') {
            this.type = HouseType.COLUMN;


        } else if (t == 'b') {
            this.type = HouseType.BOX;
        }

        this.tiles = tiles;

        ArrayList<Integer> values = toIntArray();


    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Integer> getStillneed() {
        ArrayList<Integer> stillneed = new ArrayList<>(complete);
        for (Integer i : complete) {
            if (hasNum(i)) {
                stillneed.remove(i);
            }
        }
        return stillneed;
    }

    public ArrayList<Tile> getEmpties() {
        ArrayList<Tile> emptytiles = new ArrayList<>();

        for (Tile t : getTiles()) {
            if (!t.isFilled()) {
                emptytiles.add(t);
            }
        }
        return emptytiles;
    }


    public int getNum() {
        return num;
    }

    public HouseType getType() {
        return type;
    }

    public boolean hasNum(int i) {
        for (Tile t : getTiles()) {
            if (t.getNum() == i) {
                return true;
            }
        }
        return false;
    }

    public String getID() {
        String id = getType().toString() + " " + getNum();
        return id;
    }


    public ArrayList<Integer> toIntArray() {
        ArrayList<Integer> intArray = new ArrayList<>();
        getTiles().forEach(c ->
        {
            intArray.add(c.getNum());
        });
        return intArray;
    }


    public static <T> ArrayList<ArrayList<T>> powerSet(ArrayList<T> originalSet) {
        ArrayList<ArrayList<T>> sets = new ArrayList<ArrayList<T>>();
        if (originalSet.isEmpty()) {
            sets.add(new ArrayList<T>());
            return sets;
        }
        List<T> list = new ArrayList<T>(originalSet);
        T head = list.get(0);
        ArrayList<T> rest = new ArrayList<T>(list.subList(1, list.size()));
        for (ArrayList<T> set : powerSet(rest)) {
            ArrayList<T> newSet = new ArrayList<T>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

    public void tupler() {

        ArrayList<ArrayList<Tile>> emptypower = powerSet(getEmpties());

        emptypower.sort(Comparator.comparing( c-> c.size()));

        for(ArrayList<Tile> list: emptypower){
            if(!(list.size() == 1)){
                HashSet<Integer> vals = new HashSet<>();
                for (Tile t:
                     list) {
                    vals.addAll(t.getPossibles());
                }
                if(vals.size() == list.size()){
                    for (Tile tile:
                         getEmpties()) {
                        if(!list.contains(tile)){
                            ArrayList<Integer> newposs = new ArrayList<>(tile.getPossibles());
                            newposs.removeIf(c -> vals.contains(c));
                            tile.setPossibles(newposs);
                        }
                    }
                }
            }
        }

    }



    public void onlyOne() {


        Hashtable<Integer, ArrayList<Tile>> tileset = new Hashtable<>();

        ArrayList<Integer> needed = getStillneed();


        needed.forEach(c -> tileset.put(c, new ArrayList<>()));


        for (Tile t : getEmpties()) {

            ArrayList<Integer> poss = new ArrayList<>(t.getPossibles());

            for (Integer i : t.getPossibles()) {
                if (toIntArray().contains(i)) {
                    poss.remove(i);
                }
            }

            for (Integer i : poss) {
                try {
                    tileset.get(i).add(t);

                } catch (NullPointerException e) {

                }

            }


        }
        tileset.forEach((k, v) -> {
            if (v.size() == 1) {
                v.get(0).setNum(k);

            }
        });

    }


}




