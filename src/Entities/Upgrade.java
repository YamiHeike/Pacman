package Entities;

import Utils.ImageScaler;
import Utils.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Upgrade extends JLabel {
    //TODO: THIS ENTIRE CLASS SHOULD BE TESTED
    private ImageIcon upgradeImg;
    private UpgradeType type;
    private int ageInSec = 0;
    /*
    * Character upgrade class
    * Upgrades for all Characters: SPEED, INVINCIBLE of non-playable characters negates INVINCIBLE of Pacman
    * Upgrades for Pacman: LIVES, PREDATOR, SCORE_SPEEDER
    */

    //Method overflow

    public Upgrade(UpgradeType type) {
        this.type = type;

        switch(type) {
            case SPEED -> upgradeImg = ImageScaler.adjustImg(new ImageIcon("src/assets/speed.png"), 25);
            case LIVES -> upgradeImg = ImageScaler.adjustImg(new ImageIcon("src/assets/heart_icon.png"), 25);
            case INVINCIBLE -> upgradeImg = ImageScaler.adjustImg(new ImageIcon("src/assets/invincible_icon.png"), 25);
            case PREDATOR -> upgradeImg = ImageScaler.adjustImg(new ImageIcon("src/assets/killer_icon.png"), 25);
            case SCORE_SPEEDER -> upgradeImg = ImageScaler.adjustImg(new ImageIcon("src/assets/double_icon.png"), 25);

        }

        setIcon(upgradeImg);
        aging();
    }

    public void upgrade(Character character, UpgradeType type) {
        switch(type) {
            case SPEED:
                //movement calculates time between moves with the expression: 700 - getSpeed() - (getInitialSpeed * 100)
                //I set 6 as my inital speed

                if(character.getSpeed() < 12) {
                    character.speed++;
                }
                break;
            case INVINCIBLE:
                makeInvincible(character);
                break;
            default:
                break;
        }
    }

    public void upgrade(Pacman pac, UpgradeType type) {

        this.type = type;
        switch(type) {
            case LIVES:
                pac.addLives(1);
                break;
            case PREDATOR:
                allowMurder(pac);
                break;
            case SCORE_SPEEDER:
                //MAY ADD CAN WALK THROUGH WALLS INSTEAD OF SCORE_SPEEDER IF IT CAUSES PROBLEMS
                //TODO: define where the score speed will be kept
                doubleScore();
                break;
            case SPEED:
                if(pac.getSpeed() < 12) {
                    pac.setSpeed(pac.getSpeed() + 1);
                }
            case INVINCIBLE:
                makeInvincible(pac);
                break;
            default:
                break;
        }
    }

    private void aging() {
        Object monitor = new Object();
        synchronized (monitor) {
            Thread age = new Thread(() -> {
                while (true) {

                    try {
                        Thread.sleep(1000);
                        ageInSec++;
                    } catch(InterruptedException exc) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            age.start();
        }

    }

    private synchronized void doubleScore() {
        Thread scoreUpgrader = new Thread(() -> {
           try {
               ScoreKeeper.isDouble = true;
               Thread.sleep(10000);
               ScoreKeeper.isDouble = false;
           } catch(InterruptedException exc) {
               Thread.currentThread().interrupt();
               exc.printStackTrace();
           }
        });
        scoreUpgrader.start();
    }

    private synchronized<T extends Character>  void makeInvincible(T entity) {
        Thread invincible = new Thread(() -> {
            try {
                entity.isInvincible = true;
                Thread.sleep(10000);
                entity.isInvincible = false;
            } catch(InterruptedException exc) {
                Thread.currentThread().interrupt();
                exc.toString();
            }
        });
        invincible.start();
    }

    private synchronized void allowMurder(Pacman pac) {
        Thread letPacmanKill = new Thread(() -> {
           try {
               pac.canKill = true;
               Thread.sleep(10000);
               pac.canKill = false;
           } catch(InterruptedException exc) {
               Thread.currentThread().interrupt();
               exc.printStackTrace();
           }
        });
        letPacmanKill.start();
    }


    public UpgradeType getType() {
        return type;
    }

    public static UpgradeType typeLottery() {
        Random rand = new Random();
        int picked = rand.nextInt(UpgradeType.values().length);
        return UpgradeType.values()[picked];
    }

    public int getAgeInSec() {
        return ageInSec;
    }

    //Upgrade types

    enum UpgradeType {
        SPEED,
        LIVES,
        INVINCIBLE,
        PREDATOR, //This one - ability to kill ghosts
        SCORE_SPEEDER
    }
}
