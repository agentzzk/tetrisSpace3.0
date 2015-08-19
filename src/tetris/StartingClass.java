/**
 * Created by Zain on 8/17/2015.
 * Simple Tetris project started July 2015
 */

package tetris;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartingClass extends Applet implements Runnable, KeyListener {

    //initialize all Objects
    TetrisPiece piece;
    GameGrid grid;
    Thread gameLoop;
    boolean start = false;

    //initialize game settings
    @Override
    public void init() {
        super.init();
        setSize(480, 800);

        //TODO: update this with the setting the user chooses: night mode (black) v. regular mode (white).

        setBackground(Color.white);
        setFocusable(true);
        addKeyListener(this);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("Simple Tetris");
    }

    //first start
    @Override
    public void start() {
        super.start();
        piece = new TetrisPiece();
        grid = new GameGrid(piece);
        gameLoop = new Thread(this);
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    //thread execution
    @Override
    public void run() {
        while (!grid.lose()) {
            //start updating grid values
            grid.update();
            repaint();
        }
    }

    @Override
    public void update(Graphics g) {
        //code for double buffering entire screen for smoother graphics.
        Graphics offgc;
        Image offscreen = null;
        Dimension d = getSize();

        // create the offscreen buffer and associated Graphics
        offscreen = createImage(d.width, d.height);
        offgc = offscreen.getGraphics();
        // clear the exposed area
        offgc.setColor(getBackground());
        offgc.fillRect(0, 0, d.width, d.height);
        offgc.setColor(getForeground());
        // do redraw all components back on
        paint(offgc);
        // transfer offscreen to window
        g.drawImage(offscreen, 0, 0, this);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //main menu screen
        if (!start) {
            g2d.setColor(Color.black);
            g2d.setColor(Color.white);
            g2d.fillRect(20, 250, 440, 300);
            g2d.setColor(Color.black);
            g2d.drawRect(20, 250, 440, 300);
            g2d.setFont(new Font("Arial", Font.BOLD, 26));
            g2d.drawString("Simple Tetris", 160, 400);
            g2d.setFont(new Font("Arial", Font.PLAIN, 19));
            g2d.drawString("Press Enter to start", 160, 430);
        }

        //game screen
        else if (start) {
            //paint the grey grid border
            g2d.setColor(Color.lightGray);
            //left grid line
            g2d.fillRect(grid.getStartX(), grid.getStartY(), grid.getSpacing(), (grid.getPieceSize() * grid.getRow()) + (grid.getSpacing() * grid.getRow()));
            //right grid line
            g2d.fillRect(grid.getStartX() + (grid.getPieceSize() * grid.getCol()) + (grid.getSpacing() * (grid.getCol())), grid.getStartY(), grid.getSpacing(),
                    (grid.getPieceSize() * grid.getRow()) + (grid.getSpacing() * grid.getRow()));
            //bottom grid line
            g2d.fillRect(grid.getStartX(), grid.getStartY() + (grid.getPieceSize() * grid.getRow()) + (grid.getSpacing() * grid.getRow()),
                    grid.getStartX() + (grid.getPieceSize() * grid.getCol()) + (grid.getSpacing() * (grid.getCol())) - grid.getStartX() + grid.getSpacing(), grid.getSpacing());

            int gridTemp[][] = grid.getGridArray();
            for (int i = 0; i < grid.getCol(); i++)
                for (int j = 0; j < grid.getRow(); j++) {

                    //paint current piece
                    if (gridTemp[i][j] == piece.getPieceNum()) {
                        g2d.setColor(piece.getPieceColor());
                        int startingX = (grid.getPieceSize() + grid.getSpacing()) * i + (grid.getStartX() + (grid.getSpacing()));
                        int startingY = (grid.getStartY() + grid.getSpacing()) + (grid.getPieceSize() + grid.getSpacing()) * j;
                        g2d.fillRect(startingX, startingY, grid.getPieceSize(), grid.getPieceSize());
                    }

                    //paint old piece
                    if (gridTemp[i][j] != piece.getPieceNum() && gridTemp[i][j] != 0 && gridTemp[i][j] != 99) {
                        Color staticColor = Color.white;
                        switch (gridTemp[i][j]) {
                            case 11:
                                staticColor = new Color(255, 87, 1);
                                break;
                            case 22:
                                staticColor = new Color(22, 76, 174);
                                break;
                            case 33:
                                staticColor = new Color(9, 179, 235);
                                break;
                            case 44:
                                staticColor = new Color(254, 0, 6);
                                break;
                            case 55:
                                staticColor = new Color(74, 198, 0);
                                break;
                            case 66:
                                staticColor = new Color(122, 3, 178);
                                break;
                            case 77:
                                staticColor = new Color(255, 176, 0);
                                break;
                        }
                        g2d.setColor(staticColor);
                        int startingX = (grid.getPieceSize() + grid.getSpacing()) * i + (grid.getStartX() + (grid.getSpacing()));
                        int startingY = (grid.getStartY() + grid.getSpacing()) + (grid.getPieceSize() + grid.getSpacing()) * j;
                        g2d.fillRect(startingX, startingY, grid.getPieceSize(), grid.getPieceSize());
                    }
                }

            //debugging - print grid values on screen
            /*for (int i = 0; i < grid.getCol(); i++)
                for (int j = 0; j < grid.getRow(); j++)
                    g2d.drawString("" + gridTemp[i][j], (grid.getPieceSize() + grid.getSpacing()) * i + (grid.getStartX() + (4 * grid.getSpacing())),
                          (grid.getStartY() + 7 * grid.getSpacing()) + (grid.getPieceSize() + grid.getSpacing()) * j);*/

            //display score
            g2d.setColor(Color.black);
            g2d.setFont(new Font("Arial", Font.PLAIN, 23));
            g2d.drawString("Lines cleared: " + grid.getLineScore(), 157, 25);

            //if game lost, bring game over screen
            if (grid.lose()) {
                g2d.setColor(Color.black);
                g2d.setColor(Color.white);
                g2d.fillRect(20, 250, 440, 300);
                g2d.setColor(Color.black);
                g2d.drawRect(20, 250, 440, 300);
                g2d.setFont(new Font("Arial", Font.PLAIN, 26));
                g2d.drawString("Game Over!", 170, 400);
                g2d.setFont(new Font("Arial", Font.PLAIN, 19));
                g2d.drawString("Final score: " + grid.getLineScore(), 180, 430);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        //start the game
        if (!start && e.getKeyCode() == e.VK_ENTER) {
            start = true;
            gameLoop.start();
        }

        //during gameplay, controls
        else if (start)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    grid.keyUp();
                    break;

                case KeyEvent.VK_DOWN:
                    grid.keyDown();
                    break;

                case KeyEvent.VK_LEFT:
                    grid.keyLeft();
                    break;

                case KeyEvent.VK_RIGHT:
                    grid.keyRight();
                    break;

                case KeyEvent.VK_SPACE:
                    grid.keySpace();
                    break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
