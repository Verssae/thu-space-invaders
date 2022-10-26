package engine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.net.URL;


public class Sound {


    public void backroundmusic(){
        try{
            String bgm = "Bgm/background.wav";

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(bgm).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bulletsound() {
        try {
            String bgm = "Bgm/ball.wav";

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(bgm).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void explosionsound() {
        try {
            String bgm = "Bgm/bomb.wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(bgm).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
