package engine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;


public class SoundManager {

    public enum Sound {
        bullet,
        background,
        attack,
        gameOver,
        hit
    }

    String background = "Bgm/background.wav";

    Clip backgroundClip = null;
    String bullet = "Bgm/ball.wav";
    String attack = "Bgm/attack.wav";
    String gameOver = "Bgm/gameOver.wav";
    String hit = "Bgm/hit.wav";

    public void startMusic(Sound what) {
        String bgm = null;
        switch (what) {
            case background:
                bgm = background;
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(bgm).getAbsoluteFile());
                    backgroundClip = AudioSystem.getClip();
                    backgroundClip.open(audioInputStream);
                    backgroundClip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case bullet:
                bgm = bullet;
                break;
            case attack:
                bgm = attack;
                break;
            case gameOver:
                bgm = gameOver;
                break;
            case hit:
                bgm = hit;
                break;
            default:
                break;
        }
        if (bgm != null && !bgm.equals(background)) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(bgm).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stopMusic(String what) {
        switch (what) {
            case "background":
                if (backgroundClip != null) {
                    backgroundClip.stop();
                    backgroundClip = null;
                }
                break;

            default:
                break;
        }
    }
}
