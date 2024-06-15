package Components;

import Entities.Cell;
import Entities.Enemy;
import Entities.Food;
import Entities.Character;
import Entities.Pacman;
import Utils.ColorScheme;
import Utils.ScoreKeeper;
import Windows.Game;
import Windows.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Maze extends JPanel implements KeyListener {
    /*
    * Windows.Game logic class
    * Generates a random maze with Depth-First Search Algorithm based on an entered size
    * The actual maze size is size * 2 + 1 to ensure the size isn't even
    * The Maze is filled with fully scalable cells
    * The methods pause(), resume(), gameOver() allow game state change
    * WSAD + Arrow Keys make Pacman move, Enter pauses the game, Esc exits the application
    *
    * */


    private final int side;
    private Color wallColor;
    private final Cell[][] grid;
    public boolean isGameOver;
    int enemyNum;
    private boolean moveInProgress = false;
    public final Object gameOverLock = new Object();
    public boolean running;
    public Pacman pac;

    public Maze(int side, int enemyNum, Color bgColor, Color wallColor) {
        this.side = side;
        this.enemyNum = enemyNum;
        this.wallColor = wallColor;
        Cell startCell = new Cell(2 * side, 2 * side);
        running = true;
        isGameOver = false;


        if (side < 6 || side > 12) {
            JOptionPane.showMessageDialog(null, "Side length must be between 6 and 12");
            throw new IllegalArgumentException("Side length must be between 6 and 12");
        }

        int gridSide = 2 * side + 1;
        grid = new Cell[gridSide][gridSide];

        setSize(800, 800);
        setLayout(new GridLayout(gridSide, gridSide));
        setBorder(BorderFactory.createLineBorder(ColorScheme.ACCENT_YELLOW,4,true));
        setBackground(ColorScheme.BG_DARK);

        for (int row = 0; row < gridSide; row++) {
            for (int col = 0; col < gridSide; col++) {
                grid[row][col] = new Cell(row, col);
                grid[row][col].setBackground(bgColor);
                add(grid[row][col]);
            }
        }

        createMaze(wallColor);
        setupGameObjects();

        pac.startEating();
        checkForGameOver();


        setVisible(true);
        setPreferredSize(new Dimension(800, 800));

        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();

    }

    private void setupGameObjects() {
        List<Cell> ghostPositions = findGhostStartingPoint();
        int ghostCounter = 1;
        int icn = 26;

        for (Cell cell : getNonWallCells()) {
            grid[cell.getXPos()][cell.getYPos()].setLayout(new BorderLayout());

            if (cell.getXPos() == 2 * side && cell.getYPos() == 0) {
                pac = new Pacman(6,  3, icn, grid);
                grid[cell.getXPos()][cell.getYPos()].add(pac, BorderLayout.CENTER);
                pac.setLocation(cell.getXPos(), cell.getYPos());

            } else if (ghostPositions.contains(cell)) {
                Enemy.Ghost type;
                if (ghostCounter % 3 == 0) {
                    type = Enemy.Ghost.BLUE;
                } else if (ghostCounter % 2 == 0) {
                    type = Enemy.Ghost.PURPLE;
                } else {
                    type = Enemy.Ghost.RED;
                }
                Enemy ghost = new Enemy(6, grid, type);
                grid[cell.getXPos()][cell.getYPos()].add(ghost, BorderLayout.CENTER);
                ghost.setLocation(cell.getXPos(), cell.getYPos());
                ghostCounter++;
            } else {
                int randFood = (int) (Math.random() * 3);
                switch (randFood) {
                    case 0:
                        grid[cell.getXPos()][cell.getYPos()].add(new Food(Food.FoodSize.SMALL), BorderLayout.CENTER);
                        break;
                    case 1:
                        grid[cell.getXPos()][cell.getYPos()].add(new Food(Food.FoodSize.MEDIUM), BorderLayout.CENTER);
                        break;
                    case 2:
                        grid[cell.getXPos()][cell.getYPos()].add(new Food(Food.FoodSize.LARGE), BorderLayout.CENTER);
                        break;
                }
            }
            repaint();
            revalidate();
        }
    }

    //Deep First Search Algorithm - adjusted to Java Swing Grid Layout
    private void createMaze(Color wallColor) {
        boolean[][] visited = new boolean[side][side];
        Deque<Cell> stack = new ArrayDeque<>();
        Cell start = new Cell(side - 1, 0);
        stack.push(start);
        visited[side - 1][0] = true;

        while (!stack.isEmpty()) {
            Cell curr = stack.pop();
            ArrayList<Cell> neighbors = findUnvisited(curr, visited);
            for (Cell neighbor : neighbors) {
                visited[neighbor.getXPos()][neighbor.getYPos()] = true;
                addWall(curr, neighbor);
                stack.push(neighbor);
            }
        }
        setCellBackground(side - 1, side - 1);
    }



    private ArrayList<Cell> findUnvisited(Cell cell, boolean[][] visited) {
        int x = cell.getXPos();
        int y = cell.getYPos();
        ArrayList<Cell> neighbors = new ArrayList<>();

        ArrayList<Cell> unvisitedNeighbors = new ArrayList<>();

        if (x > 0 && !visited[x - 1][y]) unvisitedNeighbors.add(new Cell(x-1,y));
        if (x < side - 1 && !visited[x + 1][y]) unvisitedNeighbors.add(new Cell(x+1, y));
        if (y > 0 && !visited[x][y - 1]) unvisitedNeighbors.add(new Cell(x, y-1));
        if (y < side - 1 && !visited[x][y + 1]) unvisitedNeighbors.add(new Cell(x,y+1));

        Collections.shuffle(unvisitedNeighbors);
        neighbors.addAll(unvisitedNeighbors);

        return neighbors;
    }

    private void addWall(Cell current, Cell next) {

        int xCurr = current.getXPos();
        int yCurr = current.getYPos();
        int xNext = next.getXPos();
        int yNext = next.getYPos();

        setCellBackground(xCurr, yCurr);
        setCellBackground(xNext, yNext);

        int xWall = xCurr + xNext + 1;
        int yWall = yCurr + yNext + 1;

        if (xCurr == xNext) {
            grid[xWall / 2][yCurr * 2 + 1].setBackground(wallColor);
            grid[xWall / 2][yCurr * 2 + 1].add(new Wall());
        } else {
            grid[xCurr * 2 + 1][yWall / 2].setBackground(wallColor);
            grid[xCurr * 2 + 1][yWall / 2].add(new Wall());
        }
    }

    private void setCellBackground(int row, int col) {
        grid[row * 2 + 1][col * 2 + 1].setBackground(wallColor);
        grid[row * 2 + 1][col * 2 + 1].add(new Wall());
    }

    public List<Cell> getNonWallCells() {
        List<Cell> nonWallCells = new ArrayList<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (!grid[row][col].getBackground().equals(wallColor)) {
                    nonWallCells.add(new Cell(row, col));
                }
            }
        }
        return nonWallCells;
    }

    public List<Cell> findGhostStartingPoint() {
        List<Cell> pos = new ArrayList<>();
        List<Cell> nwc = getNonWallCells();
        int max = nwc.size();
        int randIdx;
        Random random = new Random();
        for (int i = 0; i < enemyNum; i++) {
            randIdx = random.nextInt(max);
            pos.add(nwc.get(randIdx));
        }
        return pos;
    }

    public int getSide() {
        return side;
    }

    //Event delegation

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (moveInProgress) {
            return;
        }

        moveInProgress = true;

        int dx = 0;
        int dy = 0;
        int key = e.getKeyCode();
        switch(key) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                dx = -1;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                dx = 1;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                dy = -1;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                dy = 1;
                break;

            case KeyEvent.VK_ESCAPE:
                running = false;
                ScoreKeeper.stop();
                isGameOver = true;
                gameOver();
                Game.deleteInstance();
                SwingUtilities.invokeLater(() -> Game.openMenu());
                break;
            case KeyEvent.VK_ENTER:
                pause();
                int choice = JOptionPane.showConfirmDialog(null,"Game Paused. Do you wish to resume?","Pause",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
                if(choice == JOptionPane.YES_OPTION) {
                    resume();
                } else {
                    isGameOver = true;
                    gameOver();
                    Game.deleteInstance();
                    SwingUtilities.invokeLater(() -> Game.openMenu());
                }
                break;
        }

        // Calculate new position
        int newX = pac.getXPos() + dx;
        int newY = pac.getYPos() + dy;
        int[] coords = Character.edgeMove(newX, newY, grid);
        newX = coords[0];
        newY = coords[1];


        if (Character.isValidMove(newX, newY, grid)) {
            pac.move(dx, dy);
            pac.updatePosition(newX, newY);

            new Thread(() -> {
                try {
                    Thread.sleep(700 - ((pac.getSpeed() - pac.getInitialSpeed()) * 100));
                    moveInProgress = false;
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } else {
            moveInProgress = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void checkForGameOver() {
        Thread gameOverCheckerThread = new Thread(() -> {
            while (true) {
                    synchronized (gameOverLock) {
                        if(isGameOver) {
                            break;
                        }

                        try {
                            Thread.sleep(500);
                            int remainingLives = Pacman.getLives();
                            if (remainingLives == 0 || ScoreKeeper.getCurrTimeInSeconds() >= 600) {
                                isGameOver = true;
                                running = false;
                                gameOver();
                            }
                        } catch (InterruptedException exc) {
                            Thread.currentThread().interrupt();
                            exc.printStackTrace();
                        }

                }
            }
        });
        gameOverCheckerThread.start();
    }

    public void pause() {
        running = false;
        Enemy.setIsRunning(false);
        Enemy.setIsGeneratingUpgrades(false);
        ScoreKeeper.stop();
    }

    public void resume() {
        running = true;
        Enemy.setIsRunning(true);
        Enemy.setIsGeneratingUpgrades(true);
        ScoreKeeper.start();

        Container parent = getParent();
        for(Component c: parent.getComponents()){
            if(c instanceof ScoreDisplay) {
                ((ScoreDisplay) c).resumeTracking();
            }
        }
        for(Enemy ghost: Enemy.getAllGhostList()){
            ghost.resumeActivity();
        }
    }

    private void resetGame() {
        ScoreKeeper.reset();

        removeAll();
        revalidate();
        repaint();
        setupGameObjects();
        removeAll();
        checkForGameOver();
        requestFocusInWindow();
    }


    public void gameOver() {
        String nick = "unknown";
        try {
            pause();
            ScoreKeeper.stop();
            Enemy.setIsRunning(false);
            Enemy.setIsGeneratingUpgrades(false);
            Enemy.setAllGhostList(new ArrayList<Enemy>());
            nick = JOptionPane.showInputDialog(null, "Windows.Game over.\nYour score will be saved\nEnter your nickname","GAME OVER", JOptionPane.INFORMATION_MESSAGE);
            if(nick == null) {
                nick = "Unknown";
            }
            ScoreKeeper.newScore(nick);
            Game.openMenu();
            GamePanel.deleteGamePanel();


            ScoreKeeper.reset();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

}
