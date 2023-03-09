package OOP_itog;

import java.util.ArrayDeque;

class Coord {
    int X;
    int Y;

    Coord() {
        this.X = 0;
        this.Y = 0;
    }

    Coord(int x, int y) {
        this.X = x;
        this.Y = y;
    }
}

class MinCoord {
    int[] min;
    Coord[] co;
    static int it = 0;

    MinCoord() {
        this.min = new int[5];
        this.co = new Coord[5];
        this.co[0] = new Coord();
        this.co[1] = new Coord();
        this.co[2] = new Coord();
        this.co[3] = new Coord();
        this.co[4] = new Coord();
    }
    // void add(int m, Coord c){
    // if (countMinCoord<5){
    // this.min[countMinCoord] = m;
    // this.co[countMinCoord] = c;
    // countMinCoord += 1;}
    // else{System.out.println("Переполнение!");}
    // }
}

public class Wave_itog {

    public static void main(String[] args) {
        int[][] map = createMap(6, 6);
        System.out.println("Карта пуста");
        Map(map);
        Wall(4, 3, 2, 1, map);
        Wall(5, 6, 2, 0, map);
        System.out.println("Стены на карте");
        Map(map);
        ArrayDeque<Coord> Queue = new ArrayDeque<Coord>();
        Queue.add(addDog(2, 2, map));
        System.out.println("Cтены и собачка");
        Map(map);
        maping(map, Queue);
        System.out.println("Маршруты на карте");
        Map(map);
        Queue.remove();
        Queue.remove();
        Queue.add(addEscape(7, 4, map));
        Queue.add(addEscape(7, 7, map));
        System.out.println("Выходы");
        Map(map);
        Way(map, Queue);
        System.out.println("Путь");
        Map(map);
    }

    public static Coord addDog(int x, int y, int[][] map) {
        map[y][x] = 1;
        Coord posDog = new Coord(x, y);
        return posDog;
    }

    public static void maping(int[][] map, ArrayDeque<Coord> qC) {
        if (check(map, 0) == true) {
            Coord nP = qC.removeFirst();
            goUp(nP, qC, map);
            goRight(nP, qC, map);
            goDown(nP, qC, map);
            goLeft(nP, qC, map);
            maping(map, qC);
        }
    }

    public static void goUp(Coord pos, ArrayDeque<Coord> qC, int[][] map) {
        Coord temp = new Coord(0, 0);
        temp.X = pos.X;
        temp.Y = pos.Y;
        temp.Y = temp.Y + 1;
        if (map[temp.Y][temp.X] == 0) {
            qC.add(temp);
            map[temp.Y][temp.X] = map[pos.Y][pos.X] + 1;
        }

    }

    public static void goRight(Coord pos, ArrayDeque<Coord> qC, int[][] map) {
        Coord temp = new Coord(0, 0);
        temp.X = pos.X;
        temp.Y = pos.Y;
        temp.X = temp.X + 1;
        if (map[temp.Y][temp.X] == 0) {
            qC.add(temp);
            map[temp.Y][temp.X] = map[pos.Y][pos.X] + 1;
        }
    }

    public static void goDown(Coord pos, ArrayDeque<Coord> qC, int[][] map) {
        Coord temp = new Coord(0, 0);
        temp.X = pos.X;
        temp.Y = pos.Y;
        temp.Y = temp.Y - 1;
        if (map[temp.Y][temp.X] == 0) {
            qC.add(temp);
            map[temp.Y][temp.X] = map[pos.Y][pos.X] + 1;
        }
    }

    public static void goLeft(Coord pos, ArrayDeque<Coord> qC, int[][] map) {
        Coord temp = new Coord(0, 0);
        temp.X = pos.X;
        temp.Y = pos.Y;
        temp.X = temp.X - 1;
        if (map[temp.Y][temp.X] == 0) {
            qC.add(temp);
            map[temp.Y][temp.X] = map[pos.Y][pos.X] + 1;
        }
    }

    private static boolean check(int[][] map, int toCheckValue) {
        boolean test = false;
        for (int yi = 2; yi < map.length; yi += 1) {
            for (int xi = 2; xi < map[0].length; xi += 1) {
                if (map[yi][xi] == toCheckValue) {
                    test = true;
                    break;
                }
            }
        }
        return test;
    }

    public static void Way(int[][] map, ArrayDeque<Coord> qC) {
        if (map[qC.getFirst().Y + 1][qC.getFirst().X] != 1 &&
                map[qC.getFirst().Y - 1][qC.getFirst().X] != 1 &&
                map[qC.getFirst().Y][qC.getFirst().X + 1] != 1 &&
                map[qC.getFirst().Y][qC.getFirst().X - 1] != 1) {
            findMin(map, qC);
            map[qC.getFirst().Y][qC.getFirst().X] = 0;
            Way(map, qC);
        }
        map[qC.getFirst().Y][qC.getFirst().X] = 0;

    }

    public static void findMin(int[][] map, ArrayDeque<Coord> qC) {
        MinCoord mC = new MinCoord();
        while (qC.peek() != null) {
            Coord temp = new Coord(0, 0);
            temp.X = qC.getFirst().X;
            temp.Y = qC.getFirst().Y;
            qC.remove();
            checkRight(temp, map, mC);
            checkDown(temp, map, mC);
            checkLeft(temp, map, mC);
            checkUp(temp, map, mC);
            for (int i = 0; i < 4; i++) {
                if (mC.min[i] > 0) {
                    if (mC.min[4] > mC.min[i] || mC.min[4] == 0) {
                        mC.min[4] = mC.min[i];
                        mC.co[4].X = mC.co[i].X;
                        mC.co[4].Y = mC.co[i].Y;
                    }
                }
            }

        }
        qC.add(mC.co[4]);
    }

    public static void checkUp(Coord pos, int[][] map, MinCoord mC) {

        mC.min[0] = map[pos.Y - 1][pos.X];
        mC.co[0].X = pos.X;
        mC.co[0].Y = pos.Y - 1;

    }

    public static void checkRight(Coord pos, int[][] map, MinCoord mC) {
        mC.min[1] = map[pos.Y][pos.X + 1];
        mC.co[1].X = pos.X + 1;
        mC.co[1].Y = pos.Y;
    }

    public static void checkDown(Coord pos, int[][] map, MinCoord mC) {
        mC.min[2] = map[pos.Y + 1][pos.X];
        mC.co[2].X = pos.X;
        mC.co[2].Y = pos.Y + 1;
    }

    public static void checkLeft(Coord pos, int[][] map, MinCoord mC) {
        mC.min[3] = map[pos.Y][pos.X - 1];
        mC.co[3].X = pos.X - 1;
        mC.co[3].Y = pos.Y;
    }

    public static Coord addEscape(int x, int y, int[][] map) {
        map[y][x] = -2;
        Coord posEsc = new Coord(x, y);
        return posEsc;
    }

    public static void Map(int[][] map) {
        for (int yi = 0; yi < map.length; yi += 1) {
            for (int xi = 0; xi < map[0].length; xi += 1) {
                System.out.format("%2d | ", map[yi][xi]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int[][] createMap(int x, int y) {
        int[][] map = new int[y + 3][x + 3];
        for (int yi = 0; yi < y + 3; yi += 1) {
            for (int xi = 0; xi < x + 3; xi += 1) {
                if (yi == 0)
                    map[0][xi] = xi;
                if ((yi == 1 && xi != 0) || (xi == 1 && yi != 0) || (yi == y + 2 && xi != 0)
                        || (xi == x + 2 && yi != 0))
                    map[yi][xi] = -5;
            }
            map[yi][0] = yi;
        }
        return map;
    }

    public static void Wall(int x, int y, int size, int rot, int[][] map) {
        for (int i = 0; i < size; i += 1) {
            if (rot == 1) { // rot == 1 -> | rot == 2 -> _
                map[y + i][x] = -1;
            } else
                map[y][x + i] = -1;
        }
    }
}
