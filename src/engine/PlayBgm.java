package engine;

import screen.GameScreen;
import screen.Screen;
import screen.ShopScreen;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;


public class PlayBgm {
    static Clip clip_hollow;

    static File bgm;
    static AudioInputStream stream;
    static AudioFormat format;
    static DataLine.Info info;
    static Clip clip;

    static File bgm2;
    static AudioInputStream stream2;
    static AudioFormat format2;
    static DataLine.Info info2;
    static Clip clip2;

    public static void play() {

        switch (ShopScreen.getApply_bgm()) {
            case 1:
                bgm = new File("Bgm\\Dummy-data-bgm.wav");

                try {

                    stream = AudioSystem.getAudioInputStream(bgm);
                    format = stream.getFormat();
                    info = new DataLine.Info(Clip.class, format);
                    clip = (Clip) AudioSystem.getLine(info);
                    clip.open(stream);
                    clip.start();

                } catch (Exception e) {
                    System.out.println("err : " + e);
                }
                break;
            case 2:
                bgm2 = new File("Bgm\\Dummy-data-bgm2.wav");
                try {

                    stream2 = AudioSystem.getAudioInputStream(bgm2);
                    format2 = stream2.getFormat();
                    info2 = new DataLine.Info(Clip.class, format2);
                    clip2 = (Clip) AudioSystem.getLine(info2);
                    clip2.open(stream2);
                    clip2.start();

                }  catch(Exception e){
                    System.out.println("err : " + e);
                }
                break;
        }



    }

    public static void BgmStop(Clip clip) {
        clip.stop();
    }

    public static Clip getClip() {
        if (ShopScreen.getApply_bgm() == 1) {
            return clip;
        }
        else if (ShopScreen.getApply_bgm() == 2) {
            return clip2;
        }
        return clip_hollow;
    }
}
