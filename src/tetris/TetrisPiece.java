package tetris;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Zain on 8/18/2015.
 */
public class TetrisPiece {

    /*NOTE: Class variables should be private so only this class can directly modify them.
    Outside classes should use modifier methods.*/

    //keep track of the 4-block pieces in 2D
    private int[] pieceX = new int[4];
    private int[] pieceY = new int[4];
    private int pieceNum = 0;
    private Color pieceColor;
    //keep track of pivot point for all 7 pieces in 2D

    private boolean genNewPiece = true;

    public TetrisPiece() {
    }

    public void newPieceNum() {
        //random generator to select piece
        Random rand = new Random();
        pieceNum = (rand.nextInt(7)) + 1;
        setPiece(pieceNum);
    }

    public void setPiece(int i) {
        switch (i) {
            case 1:
                //L Piece
                pieceX[0] = 5;
                pieceX[1] = 5;
                pieceX[2] = 5;
                pieceX[3] = 6;
                pieceY[0] = 0;
                pieceY[1] = 1;
                pieceY[2] = 2;
                pieceY[3] = 2;
                pieceColor = new Color(255, 87, 1);
                break;
            case 2:
                //Backwards L piece
                pieceX[0] = 6;
                pieceX[1] = 6;
                pieceX[2] = 6;
                pieceX[3] = 5;
                pieceY[0] = 0;
                pieceY[1] = 1;
                pieceY[2] = 2;
                pieceY[3] = 2;
                pieceColor = new Color(22, 76, 174);
                break;
            case 3:
                //straight
                Arrays.fill(pieceX, 5); //fill all X with same position
                pieceY[0] = 0;
                pieceY[1] = 1;
                pieceY[2] = 2;
                pieceY[3] = 3;
                pieceColor = new Color(9, 179, 235);
                break;
            case 4:
                //Z piece
                pieceX[0] = 4;
                pieceX[1] = 5;
                pieceX[2] = 5;
                pieceX[3] = 6;
                pieceY[0] = 0;
                pieceY[1] = 0;
                pieceY[2] = 1;
                pieceY[3] = 1;
                pieceColor = new Color(254, 0, 6);
                break;
            case 5:
                ////backwards Z
                pieceX[0] = 5;
                pieceX[1] = 5;
                pieceX[2] = 6;
                pieceX[3] = 6;
                pieceY[0] = 0;
                pieceY[1] = 1;
                pieceY[2] = 1;
                pieceY[3] = 2;
                pieceColor = new Color(74, 198, 0);
                break;
            case 6:
                //arrow keys
                pieceX[0] = 5;
                pieceX[1] = 6;
                pieceX[2] = 6;
                pieceX[3] = 7;
                pieceY[0] = 1;
                pieceY[1] = 1;
                pieceY[2] = 0;
                pieceY[3] = 1;
                pieceColor = new Color(122, 3, 178);
                break;
            case 7:
                //square
                pieceX[0] = 5;
                pieceX[1] = 5;
                pieceX[2] = 6;
                pieceX[3] = 6;
                pieceY[0] = 0;
                pieceY[1] = 1;
                pieceY[2] = 0;
                pieceY[3] = 1;
                pieceColor = new Color(255, 176, 0);
                break;
        }

        genNewPiece = false;
    }

    public boolean getGenNewPiece() {
        return genNewPiece;
    }

    public void setGenNewPiece(boolean genNewPiece) {
        this.genNewPiece = genNewPiece;
    }

    public int[] getPieceX() {
        return pieceX;
    }

    public int[] getPieceY() {
        return pieceY;
    }

    public Color getPieceColor() {
        return pieceColor;
    }

    public int getPieceNum() {
        return pieceNum;
    }

    public void setPieceX(int[] pieceX) {
        this.pieceX = pieceX;
    }

    public void setPieceY(int[] pieceY) {
        this.pieceY = pieceY;
    }
}
