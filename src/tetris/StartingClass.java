package tetris;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Zain on 8/17/2015.
 */
public class StartingClass extends Applet implements Runnable, KeyListener {

    //initialize all Objects
    TetrisPiece piece;
    GameGrid grid;
    Thread gameLoop;

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
        gameLoop.start();
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
        while (true) {
            //code to drop piece down once
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

        //paint the pieces
        int gridTemp[][] = grid.getGridArray();
        for (int i = 0; i < grid.getCol(); i++)
            for (int j = 0; j < grid.getRow(); j++) {
                if (gridTemp[i][j] == piece.getPieceNum()) {
                    g2d.setColor(piece.getPieceColor());
                    int startingX = (grid.getPieceSize() + grid.getSpacing()) * i + (grid.getStartX() + (grid.getSpacing()));
                    int startingY = (grid.getStartY() + grid.getSpacing()) + (grid.getPieceSize() + grid.getSpacing()) * j;
                    g2d.fillRect(startingX, startingY, grid.getPieceSize(), grid.getPieceSize());
                }

                if (gridTemp[i][j] != piece.getPieceNum() && gridTemp[i][j] != 0) {
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

        g2d.setColor(Color.black);
        for (int i = 0; i < grid.getCol(); i++)
            for (int j = 0; j < grid.getRow(); j++)
                g2d.drawString("" + gridTemp[i][j], (grid.getPieceSize() + grid.getSpacing()) * i + (grid.getStartX() + (4 * grid.getSpacing())),
                        (grid.getStartY() + 7 * grid.getSpacing()) + (grid.getPieceSize() + grid.getSpacing()) * j);

        g2d.setFont(new Font("Roboto Light", Font.PLAIN, 23));
        g2d.drawString("Lines cleared: " + grid.getLineScore(), 157, 25);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                System.out.println("Rotate");
                grid.keyUp();
                break;

            case KeyEvent.VK_DOWN:
                System.out.println("Move down");
                    grid.keyDown();
                break;

            case KeyEvent.VK_LEFT:
                System.out.println("Move left");
                    grid.keyLeft();
                break;

            case KeyEvent.VK_RIGHT:
                System.out.println("Move right");
                    grid.keyRight();
                break;

            case KeyEvent.VK_SPACE:
                System.out.println("Drop");
                grid.keySpace();
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
