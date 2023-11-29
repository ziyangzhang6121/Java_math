package javac.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class AStarAlgorithm {

    private static final int[][] DIREC = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, 
        {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
    
    public static Stack<Position> road = new Stack<>();
    public static ArrayList<Integer> roadX = new ArrayList<>();
    public static ArrayList<Integer> roadY = new ArrayList<>();
    
    @SuppressWarnings("unused")
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter (rows cols x1 y1 x2 y2): ");
        final int rows = scanner.nextInt();
        final int cols = scanner.nextInt();
        int x1 = scanner.nextInt();
        int y1 = scanner.nextInt();
        int x2 = scanner.nextInt();
        int y2 = scanner.nextInt();
        scanner.close();

        // generate a two-dimension array filled with 0
        int map[][] = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            int tmp[] = new int[cols];
            Arrays.fill(tmp, 0);
            map[i] = tmp;
        }
        int midr = rows / 2;
        int midc = cols / 2;
        /*map[midr - 1][midc] = 1;
        map[midr][midc] = 1;
        map[midr + 1][midc] = 1;*/

        for (int i = 1; i < rows - 1; i++) {
            map[i][midc] = 1;
        }
        map[2][6] = 1;
        map[3][6] = 1;
        map[4][6] = 1;
        map[5][6] = 1;


        findPath(map, x1, y1, x2, y2);
    }

    @SuppressWarnings("unused")
	public static void findPath(int[][] map, int x1, int y1, int x2, int y2) {
    	List<Position> openList = new ArrayList<AStarAlgorithm.Position>();
        List<Position> closeList = new ArrayList<AStarAlgorithm.Position>();
        boolean findFlag = false;
        Position termPos = null;
        // ��ʼ��
        Position startPos = new Position(x1, y1, calcH(x1, y1, x2, y2));
        openList.add(startPos);
        do {
            // ͨ���ڿ����б����ҵ�Fֵ��С�ĵ���Ϊ��ǰ��
            Position currentPos = openList.get(0);
            for (int i = 0; i < openList.size(); i++) {
                if (currentPos.F > openList.get(i).F) {
                    currentPos = openList.get(i);
                }
            }
            // ���ҵ��ĵ�ǰ��ŵ��ر��б��У����ӿ����б���ɾ��
            closeList.add(currentPos);
            openList.remove(currentPos);

            //������ǰ���Ӧ��8�����ڵ�
            for (int i = 0; i < DIREC.length; i++) {
                int tmpX = currentPos.row + DIREC[i][0];
                int tmpY = currentPos.col + DIREC[i][1];
                if (tmpX < 0 || tmpX >= map.length || tmpY < 0 || tmpY >= map[0].length) {
                    continue;
                }
                //������Ӧ�ĵ�
                Position tmpPos = new Position(tmpX, tmpY, calcH(tmpX, tmpY, x2, y2), currentPos);
                //map�ж�Ӧ�ĸ����е�ֵΪ1���ϰ����� ���Ӧ�ĵ��Ѿ��ڹر��б���
                if (map[tmpX][tmpY] == 1 || closeList.contains(tmpPos)) {
                    continue;
                }
                //������ڿ����б��У�����뵽�����б�
                if (!openList.contains(tmpPos)) {
                    openList.add(tmpPos);
                } else {
                    // ����Ѿ����ڿ����б��У�����Gֵ�����µ�·���Ƿ���ã������·�����ã���Ѹ��ڵ�ĳɵ�ǰ�񲢴��¼���FGH
                    Position prePos = null;
                    for (Position pos : openList) {
                        if (pos.row == tmpX && pos.col == tmpY) {
                            prePos = pos;
                            break;
                        }
                    }
                    if (tmpPos.G < prePos.G) {
                    	//System.out.println(tmpPos.G+"   "+tmpPos.H);
                    	//System.out.println(prePos.G+"   "+prePos.H);
                    	//System.out.println(" ");
                        prePos.setFaPos(currentPos);
                    }
                }
            }
            // �ж��յ��Ƿ��ڿ����б���
            for (Position tpos : openList) {
                if (tpos.row == x2 && tpos.col == y2) {
                	termPos = tpos;
                    findFlag = true;
                    break;
                }
            }

        } while(openList.size() != 0);

        if(!findFlag) {
            System.out.println("no valid path!");
            return;
        }

        Stack<String> resStack = new Stack<String>();
        String pattern = "(%d, %d)";
        if (termPos != null) {
            //resStack.push(String.format(pattern, termPos.row, termPos.col));
        	road.push(termPos);
            while(termPos.fa != null) {
                termPos = termPos.fa;
                road.push(termPos);
                //resStack.push(String.format(pattern, termPos.row, termPos.col));
            }
        }
        StringBuilder sb = new StringBuilder();
        /*
        while (!road.empty()) {
            //sb.append(resStack.pop());
        	road.pop().print();
            if (!road.empty()) {
                //sb.append(" -> ");
            	road.pop().print();
            }
        }*/
        //System.out.println(sb.toString());
        
    }

    /**
     * ����ĳ�����ӵ�Hֵ
     * @param x
     * @param y
     * @param tx �յ��xֵ
     * @param ty �յ��yֵ
     * @return
     */
    private static int calcH(int x, int y, int tx, int ty) {
        int diff = Math.abs(x - tx) + Math.abs(y - ty);
        return diff * 10;
    }


    public static class Position {
        public int F;
        public int G;
        public int H;
        public Position fa;
        public int row;
        public int col;

        public Position() {
        }

        public Position(int row, int col, int H) {
            this(row, col, H, null);
        }

        public Position(int row, int col, int H, Position pos) {
            this.H = H;
            this.row = row;
            this.col = col;
            this.fa = pos;
            this.G = calcG();
            this.F = G + H;
        }

        /** 
         * ����ĳ���㵽��ʼ��Ĵ���G
         * @return
         */
        private int calcG() {
            if (fa == null) return 0;
            if (fa.row != this.row && fa.col !=  this.col) {
                return 14 + fa.G;
            }
            return 10 + fa.G;
        } 

        public void setFaPos(Position pos) {
            this.fa = pos;
            this.G = calcG();
            this.F = G + H;
        }
        
        public void print() {
        	System.out.print("("+row+","+col+") -> ");
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Position)) {
                return false;
            }
            Position pos = (Position) obj;
            return this.row == pos.row && this.col == pos.col;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + row;
            result = prime * result + col;
            return result;
        }

    }
    
}

