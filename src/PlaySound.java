
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



public class PlaySound 
{
    Clip clip = null;
   
    // For shorter sounds, you can directly use this approach.
    public void playOof() {        
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/death_sound.wav"));
        clip.play();
    }
    
    public void playBullet()
    {
    	AudioClip clip = Applet.newAudioClip(getClass().getResource("/bullet_sound.wav"));
        clip.play();
    }
    
    public void playCrate()
    {
    	AudioClip clip = Applet.newAudioClip(getClass().getResource("/crate_sound.wav"));
        clip.play();
    }
    
    public void playEnemy()
    {
    	AudioClip clip = Applet.newAudioClip(getClass().getResource("/enemy_sound.wav"));
        clip.play();
    }
    
    public void playCheckmate()
    {
    	AudioClip clip = Applet.newAudioClip(getClass().getResource("/checkmate_sound.wav"));
        clip.play();
    }
    
    // For longer sounds, use this approach
    public void playSad()
    {
    	File soundFile = new File("res/opening_theme.wav");
    	AudioInputStream audioIn = null;

		try {
			audioIn = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// Open audio clip and load samples from the audio input stream.
	         clip.open(audioIn);
	         clip.start();
	         clip.loop(Clip.LOOP_CONTINUOUSLY);  // repeat forever
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
    }
   
   
}


