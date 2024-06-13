package Utils;

import Components.ScoreDisplay;

import javax.swing.*;
import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class ScoreKeeper {
    /*
     * FINAL PURPOSE OF THIS CLASS: WRITE THE JLIST DESC ORDER INTO HIGHSCORES.TXT
     * THIS SHOULD ALSO DISPLAY THE CONTENT OF THAT FILE
     * IOEXCEPTIONS SHOULD BE HANDLED IN THE GUI COMPONENT (E.G SEND AN ERROR MSG)
     */


    //TODO: redo --> singleton pattern
    private static ScoreKeeper instance;
    private static boolean tracksScore = false; //
    public static int currScore; //consider static
    public static int currTimeInSeconds;
    public static String scoreStr; //monitor
    public static boolean isDouble = false;
    private static Thread tracker;
    private final Object lock = new Object();

    private ScoreKeeper() {
        tracksScore = true;
        currScore = 0;
        currTimeInSeconds = 0;
        updateScoreStr();
        track();
    }

    public static ScoreKeeper getInstance() {
        if (instance == null) {
            synchronized (ScoreKeeper.class) {
                if (instance == null) {
                    instance = new ScoreKeeper();
                }
            }
        }
        return instance;
    }


    //Score increment Thread
    //Used to be static
    private static void track() {
        synchronized (scoreStr) {
            tracker = new Thread(() -> {
                try {
                    while(tracksScore && currScore < Integer.MAX_VALUE) {
                        Thread.sleep(1000);
                        if(!isDouble) {
                            currScore++;
                        } else {
                            currScore += 2;
                        }
                        currTimeInSeconds++;
                        updateScoreStr();
                        System.out.println("Score keeping");
                        if (Thread.currentThread().isInterrupted()) {
                            // Handle interruption if needed, or just exit the loop
                            break;
                        }

                    }
                    if(currScore == Integer.MAX_VALUE) {
                        JOptionPane.showMessageDialog(null,"Congratulations, you've reached the maximum score!\nYou can keep on playing if you wish to, but the score won't be increasing anymore.\nPress ESC to exit the game");
                        tracksScore = false;
                    }
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                }
            });
            tracker.start();
        }
    }

    public static void reset() {
        currScore = 0;
        currTimeInSeconds = 0;
        updateScoreStr();
    }

    public synchronized void startTracking() {
        if (!tracksScore) {
            tracksScore = true;
            if (tracker == null || !tracker.isAlive()) {
                track();
            }
        }
    }

    private static void updateScoreStr() {
        StringBuilder sb = new StringBuilder();
        sb.append(currScore);
        scoreStr = sb.toString();
    }

    public static void stop() {
        tracksScore = false;
    }

    public static synchronized void start() {
        if (!tracksScore) {
            tracksScore = true;
            if (tracker == null || !tracker.isAlive()) {
                tracksScore = true;
                track();
            }
        }
    }

    public static int getCurrScore() {
        return currScore;
    }

    public boolean isTracking() {
        return this.tracksScore;
    }

    public static void newScore(String score, String nick) throws IOException {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File("src/highscores.txt"), true));
            if(nick == null) nick = "Unknown";
            bw.write(score + " - " + nick);
            bw.newLine();
        } catch(Exception exc) {
            JOptionPane.showMessageDialog(null, "An Error has occured.\nYour score was not saved");
            exc.printStackTrace();
        } finally {
            if (bw != null)
                bw.close();
        }
    }

    public static String getScoreStr() {
        return scoreStr;
    }

    public static int getCurrTimeInSeconds() {
        return currTimeInSeconds;
    }

    //TODO: to rename

    public static void setCurrScore(int points) {
        if(Integer.MAX_VALUE - points > currScore) {
            currScore += points;
            updateScoreStr();
        }
    }

    //Read scores
    public static Vector<String> getScores() throws IOException {
        Map<Integer, String> scores = new TreeMap<>(Collections.reverseOrder());

        try (BufferedReader br = new BufferedReader(new FileReader("src/highscores.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                int delimIdx = line.indexOf('-');
                if (delimIdx != -1) {
                    String scoreStr = line.substring(0, delimIdx).trim();
                    String nick = line.substring(delimIdx + 1).trim();
                    try {
                        scores.put(Integer.parseInt(scoreStr), nick);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid score entry: " + line);
                    }
                }
            }
        } catch (FileNotFoundException exc) {
            System.out.println("File not found");
            JOptionPane.showMessageDialog(null, "The Highscores file doesn't exist.\nIt might have been deleted or moved");
        } catch (IOException exc) {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, "Resource error. Try again later");
        }

        Vector<String> sortedScores = new Vector<>();
        for (Map.Entry<Integer, String> entry : scores.entrySet()) {
            sortedScores.add(entry.getKey() + " - " + entry.getValue());
        }
        return sortedScores;
    }


}