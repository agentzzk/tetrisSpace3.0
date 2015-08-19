package tetris;

/**
 * Created by Zain on 8/18/2015.
 */
public class GameGrid implements Runnable{

    //set the game grid
    private static final int col = 12;
    private static final int row = 20;
    private static final int startX = 11;
    private static final int startY = 35;
    private static final int pieceSize = 35;
    private static final int spacing = 3;
    private int[][] gridArray = new int[col][row];

    private TetrisPiece piece;
    private Thread dropPiece;

    //variables necessary for grid organization
    private int[] staticCode = {11, 22, 33, 44, 55, 66, 77};
    private int[] pieceX;
    private int[] pieceY;
    private int[] lines = new int[row];
    private int difficulty = 1300;
    private int lineScore = 0;

    public GameGrid(TetrisPiece piece) {
        this.piece = piece;
        for (int i = 0; i < col; i++)
            for (int j = 0; j < row; j++)
                gridArray[i][j] = 0;        //empty first time grid setup

        dropPiece = new Thread(this);
    }

    public void update() {
        //constantly check for full grid lines, remove them, and award point
        clearLines();

        //code to update the grid array with the piece codes.
        if (piece.getGenNewPiece()) {
            piece.newPieceNum();
            pieceX = piece.getPieceX();
            pieceY = piece.getPieceY();
            for (int i = 0; i < 4; i++) {
                gridArray[pieceX[i]][pieceY[i]] = piece.getPieceNum();
            }
        }

        //after game is set, start dropping pieces simultaneously: launch Thread
        if(!dropPiece.isAlive())
            dropPiece.start();

        //Changing drop time based on lines cleared: difficulty
        if (difficulty > 500)
        {
            difficulty = 1300 - (lineScore / 5) * 100;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(difficulty);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            keyDown();
        }
    }

    public void keyLeft() {
        if (checkBounds("left")) {
            clearOld();
            for (int i = 0; i < 4; i++) {
                pieceX[i]--;
                gridArray[pieceX[i]][pieceY[i]] = piece.getPieceNum();
            }
        }
    }

    public void keyRight() {
        if (checkBounds("right")) {
            clearOld();
            for (int i = 0; i < 4; i++) {
                pieceX[i]++;
                gridArray[pieceX[i]][pieceY[i]] = piece.getPieceNum();
            }
        }
    }

    public void keySpace() {
        while (checkBounds("down")) {
            clearOld();
            for (int i = 0; i < 4; i++) {
                pieceY[i]++;
                gridArray[pieceX[i]][pieceY[i]] = piece.getPieceNum();
            }
        }
    }

    public void keyDown() {
        if (checkBounds("down")) {
            for (int i = 0; i < 4; i++)
                gridArray[pieceX[i]][pieceY[i]] = 0;
            for (int i = 0; i < 4; i++) {
                pieceY[i]++;
                gridArray[pieceX[i]][pieceY[i]] = piece.getPieceNum();
            }
        }
    }

    public void keyUp() {

        int pieceXCopy[] = new int[4];
        int pieceYCopy[] = new int[4];
        int gridCopy[][] = new int[col][row];

        for (int i = 0; i < col; i++)
            for (int j = 0; j < row; j++) {
                gridCopy[i][j] = gridArray[i][j];
            }

        for (int i = 0; i < pieceXCopy.length; i++) {
            pieceXCopy[i] = pieceX[i];
            pieceYCopy[i] = pieceY[i];
        }

        //rotation algorithm
        if (piece.getPieceNum() == 3)          //special case for straight piece
        {

        } else if (piece.getPieceNum() != 7) {
            gridArray[pieceX[1]][(pieceY[1]) - 1] = gridCopy[pieceXCopy[1] + 1][pieceYCopy[1]];
            for (int i = 0; i < 4; i++) {
                if ((pieceXCopy[i] == pieceXCopy[1] + 1) && (pieceYCopy[i] == pieceYCopy[1])) {
                    pieceX[i] = pieceXCopy[1];
                    pieceY[i] = pieceYCopy[1] - 1;
                }
            }
            gridArray[pieceX[1]][(pieceY[1]) + 1] = gridCopy[pieceXCopy[1] - 1][pieceYCopy[1]];
            for (int i = 0; i < 4; i++) {
                if ((pieceXCopy[i] == pieceXCopy[1] - 1) && (pieceYCopy[i] == pieceYCopy[1])) {
                    pieceX[i] = pieceXCopy[1];
                    pieceY[i] = pieceYCopy[1] + 1;
                }
            }
            gridArray[(pieceX[1]) + 1][pieceY[1]] = gridCopy[pieceXCopy[1]][pieceYCopy[1] + 1];
            for (int i = 0; i < 4; i++) {
                if ((pieceXCopy[i] == pieceXCopy[1]) && (pieceYCopy[i] == pieceYCopy[1] + 1)) {
                    pieceX[i] = pieceXCopy[1] + 1;
                    pieceY[i] = pieceYCopy[1];
                }
            }
            gridArray[(pieceX[1]) - 1][pieceY[1]] = gridCopy[pieceXCopy[1]][pieceYCopy[1] - 1];
            for (int i = 0; i < 4; i++) {
                if ((pieceXCopy[i] == pieceXCopy[1]) && (pieceYCopy[i] == pieceYCopy[1] - 1)) {
                    pieceX[i] = pieceXCopy[1] - 1;
                    pieceY[i] = pieceYCopy[1];
                }
            }
            gridArray[(pieceX[1]) - 1][(pieceY[1]) - 1] = gridCopy[pieceXCopy[1] + 1][pieceYCopy[1] - 1];
            for (int i = 0; i < 4; i++) {
                if ((pieceXCopy[i] == pieceXCopy[1] + 1) && (pieceYCopy[i] == pieceYCopy[1] - 1)) {
                    pieceX[i] = pieceXCopy[1] - 1;
                    pieceY[i] = pieceYCopy[1] - 1;
                }
            }
            gridArray[(pieceX[1]) - 1][(pieceY[1]) + 1] = gridCopy[pieceXCopy[1] - 1][pieceYCopy[1] - 1];
            for (int i = 0; i < 4; i++) {
                if ((pieceXCopy[i] == pieceXCopy[1] - 1) && (pieceYCopy[i] == pieceYCopy[1] - 1)) {
                    pieceX[i] = pieceXCopy[1] - 1;
                    pieceY[i] = pieceYCopy[1] + 1;
                }
            }
            gridArray[(pieceX[1]) + 1][(pieceY[1]) - 1] = gridCopy[pieceXCopy[1] + 1][pieceYCopy[1] + 1];
            for (int i = 0; i < 4; i++) {
                if ((pieceXCopy[i] == pieceXCopy[1] + 1) && (pieceYCopy[i] == pieceYCopy[1] + 1)) {
                    pieceX[i] = pieceXCopy[1] + 1;
                    pieceY[i] = pieceYCopy[1] - 1;
                }
            }
            gridArray[(pieceX[1]) + 1][(pieceY[1]) + 1] = gridCopy[pieceXCopy[1] - 1][pieceYCopy[1] + 1];
            for (int i = 0; i < 4; i++) {
                if ((pieceXCopy[i] == pieceXCopy[1] - 1) && (pieceYCopy[i] == pieceYCopy[1] + 1)) {
                    pieceX[i] = pieceXCopy[1] + 1;
                    pieceY[i] = pieceYCopy[1] + 1;
                }
            }
        }
    }

    public boolean checkBounds(String key) {

        if (key.equals("left")) {
            for (int i = 0; i < 4; i++)
                for (int k = 0; k < staticCode.length; k++)
                    if (pieceX[i] == 0 || gridArray[pieceX[i] - 1][(pieceY[i])] == staticCode[k])
                        return false;
        } else if (key.equals("right")) {
            for (int i = 0; i < 4; i++)
                for (int k = 0; k < staticCode.length; k++)
                    if (pieceX[i] == (col - 1) || gridArray[pieceX[i] + 1][(pieceY[i])] == staticCode[k])
                        return false;
        } else if (key.equals("down")) {
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < staticCode.length; j++)
                    if (pieceY[i] == (row - 1) || gridArray[pieceX[i]][(pieceY[i]) + 1] == staticCode[j]) {
                        for (int k = 0; k < 4; k++)
                            gridArray[pieceX[k]][pieceY[k]] = staticCode[piece.getPieceNum() - 1];
                        piece.setGenNewPiece(true);
                        return false;
                    }
        }

        return true;
    }

    public void clearOld() {
        for (int i = 0; i < 4; i++)
            gridArray[pieceX[i]][pieceY[i]] = 0;
    }

    public void clearLines() {
        int blocks = 0;

        for (int i = 0; i < lines.length; i++)
            lines[i] = 0;

        //this time the loop is reversed in order to count the rows to remove first
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int k = 0; k < staticCode.length; k++) {
                    if (gridArray[j][i] == staticCode[k]) {
                        blocks++;

                        if (blocks == col) {
                            lines[i] = 1;
                        }
                    }
                }
            }
            blocks = 0;
        }

        for (int f = 0; f < row; f++) {
            if (lines[f] != 0) {
                for (int i = 0; i < col; i++) {
                    gridArray[i][f] = 0;
                    for (int j = f; j > 0; j--) {
                        gridArray[i][j] = gridArray[i][j - 1];
                    }
                }
                lineScore++;
            }
        }
    }

    public static int getCol() {
        return col;
    }

    public static int getPieceSize() {
        return pieceSize;
    }

    public static int getRow() {
        return row;
    }

    public static int getSpacing() {
        return spacing;
    }

    public static int getStartX() {
        return startX;
    }

    public static int getStartY() {
        return startY;
    }

    public int[][] getGridArray() {
        return gridArray;
    }

    public int getLineScore() {
        return lineScore;
    }
}
