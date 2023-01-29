import java.util.Random;
import java.util.Scanner;
public class MyMaze {
    Cell[][] maze;
    int startRow;
    int endRow;
    //constructor
    public MyMaze(int rows, int cols, int startRow, int endRow) {
        maze = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = new Cell();
            }
        }
        this.startRow = startRow;
        this.endRow = endRow;
    }

    //obtains a randomNeighbor dependent on Roman Numerals, I means Up, II means down, III means left, and IV means right
    public static String randomNeighbor() {
        Random generator = new Random();
        String[] arr = new String[]{"I", "II", "III", "IV"};
        int rnd = generator.nextInt(arr.length);
        return arr[rnd];

    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    //creates the actual maze by taking the users input of the row and col and creates the size of the maze based on their input.
    public static MyMaze makeMaze() {
        Scanner Obj1 = new Scanner(System.in);
        System.out.println("Enter Rows And Cols: ");
        int rows = Obj1.nextInt();
        int cols = Obj1.nextInt();
        //ensures that the user inputs a row and col that are inbounds of 5 and 20
        boolean checkBounds = rows <= 20 && rows >= 5 && cols <= 20 && cols >= 5;
        while (!checkBounds) {
            System.out.println("Invalid input!");
            System.out.println("Enter Rows And Cols: ");
            rows = Obj1.nextInt();
            cols = Obj1.nextInt();
            if (rows <= 20 && rows >= 5 && cols <= 20 && cols >= 5){
                checkBounds = true;
            }
            else {
                checkBounds = false;
            }
        }
        Random rand = new Random();
        int startRow = rand.nextInt(rows);
        int endRow = rand.nextInt(rows);

        MyMaze maz = new MyMaze(rows, cols, startRow, endRow);

        Stack1Gen<int[]> stack1 = new Stack1Gen<>();
        stack1.push(new int[]{maz.startRow, 0});
        maz.maze[maz.startRow][0].setVisited(true);

        while (!stack1.isEmpty()) {
            int[] peek = stack1.top();
            int row = peek[0];
            int col = peek[1];
            boolean pathUp = false;
            boolean pathDown = false;
            boolean pathLeft = false;
            boolean pathRight = false;
            boolean pathAvailable = false;
            //up
            if (row - 1 >= 0) {
                if (!maz.maze[row - 1][col].getVisited()) {
                    pathUp = true;
                    pathAvailable = true;
                }
            }
            //down
            if (row + 1 <= rows - 1) {
                if (!maz.maze[row + 1][col].getVisited()) {
                    pathDown = true;
                    pathAvailable = true;
                }
            }
            //left
            if (col - 1 >= 0) {
                if (!maz.maze[row][col - 1].getVisited()) {
                    pathLeft = true;
                    pathAvailable = true;
                }
            }
            //right
            if (col + 1 <= cols - 1) {
                if (!maz.maze[row][col + 1].getVisited()) {
                    pathRight = true;
                    pathAvailable = true;
                }
            }
            //if there isn't atleast 1 path available, pop the top of the stack.
            if (!pathUp && !pathDown && !pathLeft && !pathRight) {
                stack1.pop();
            }
            while (pathUp || pathDown || pathLeft || pathRight) {
                String randomNeighbor = randomNeighbor();
                if (randomNeighbor.equals("I")) {
                    if (pathUp) {
                        if (!maz.maze[row - 1][col].getVisited()) {
                            maz.maze[row - 1][col].setBottom(false);
                            maz.maze[row - 1][col].setVisited(true);
                            row--;
                            int[] newCell = {row, col};
                            stack1.push(newCell);
                        }
                    }
                }
                pathUp = false;
                if (randomNeighbor.equals("II")) {
                    if (pathDown) {
                        if (!maz.maze[row + 1][col].getVisited()) {
                            maz.maze[row][col].setBottom(false);
                            maz.maze[row + 1][col].setVisited(true);
                            row++;
                            int[] newCell = {row, col};
                            stack1.push(newCell);
                        }
                    }
                }
                pathDown = false;
                if (randomNeighbor.equals("III")) {
                    if (pathLeft) {
                        if (!maz.maze[row][col - 1].getVisited()) {
                            maz.maze[row][col - 1].setRight(false);
                            maz.maze[row][col - 1].setVisited(true);
                            col--;
                            int[] newCell = {row, col};
                            stack1.push(newCell);
                        }
                    }
                }
                pathLeft = false;
                if (randomNeighbor.equals("IV")) {
                    if (pathRight) {
                        if (!maz.maze[row][col + 1].getVisited()) {
                            maz.maze[row][col].setRight(false);
                            maz.maze[row][col + 1].setVisited(true);
                            col++;
                            int[] newCell = {row, col};
                            stack1.push(newCell);
                        }
                    }
                }
                pathRight = false;
            }

        }
        //set all the cells to unvisited
        for (int i = 0; i < maz.maze.length; i++) {
            for (int j = 0; j < maz.maze[i].length; j++){
                maz.maze[i][j].setVisited(false);
            }
        }
        maz.maze[endRow][cols - 1].setRight(false);
        return maz;
    }

    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze() {
        //top borders
        for (int i = 0; i < maze[0].length; i++) {
            System.out.print("|---");
        }
        System.out.print("|");

        System.out.print("\n");
        int i = 0;
        while(i < maze.length) {
            if (startRow == i) {
                System.out.print(" ");
            } else {
                System.out.print("|");
            }
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j].getRight()) {
                    if (maze[i][j].getVisited()) {
                        System.out.print(" * |");
                    }
                    if (!maze[i][j].getVisited()) {
                        System.out.print("   |");
                    }
                } else if (maze[i][j].getVisited()) {
                    if (!maze[i][j].getRight()) {
                        System.out.print(" *  ");
                    }
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();

            System.out.print("|");
            //bottom borders
            int j = 0;
            while (j < maze[0].length) {
                if (maze[i][j].getBottom()) {
                    System.out.print("---|");
                } else {
                    System.out.print("   |");
                }
                j++;
            }
            System.out.println();
            i++;
        }
    }




    /* TODO: Solve the maze using the algorithm found in the writeup. */
    //creates an actual path of asterisks within the maze, from the startRow to the endRow.
    public void solveMaze () {
        Q1Gen<int[]> q = new Q1Gen<>();
        q.add(new int[]{startRow, 0});
        while (q.length() != 0) {
            int[] arr = q.remove();
            int rCell = arr[0];
            int cCell = arr[1];
            maze[rCell][cCell].setVisited(true);
            if (rCell == endRow && cCell == maze[0].length - 1) {
                break;
            }
            if (rCell - 1 >= 0) {
                if (!maze[rCell - 1][cCell].getVisited() && !maze[rCell - 1][cCell].getBottom()) {
                    q.add(new int[]{rCell - 1, cCell});
                }
            }
            if (rCell + 1 <= maze.length - 1) {
                if (!maze[rCell + 1][cCell].getVisited() && !maze[rCell][cCell].getBottom()) {
                    q.add(new int[]{rCell + 1, cCell});
                }
            }
            if (cCell - 1 >= 0) {
                if (!maze[rCell][cCell - 1].getVisited() && !maze[rCell][cCell - 1].getRight()) {
                    q.add(new int[]{rCell, cCell - 1});
                }
            }
            if (cCell + 1 <= maze[0].length - 1) {
                if (!maze[rCell][cCell + 1].getVisited() && !maze[rCell][cCell].getRight()) {
                    q.add(new int[]{rCell, cCell + 1});
                }
            }
        }
        printMaze();
    }

    public static void main (String[]args){
        /*Make and solve maze */
        MyMaze newMaze;
        newMaze = makeMaze();
        newMaze.solveMaze();
    }
}
