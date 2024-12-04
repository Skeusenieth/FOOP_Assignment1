package blocks;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    private static final Map<String, byte[]> soundDataCache = new HashMap<>();
    private static final Map<String, AudioFormat> audioFormatCache = new HashMap<>();

    // Preload sounds into memory
    public static void preloadSounds(String... soundFiles) {
        for (String soundFile : soundFiles) {
            try {
                InputStream audioSrc = SoundPlayer.class.getClassLoader().getResourceAsStream(soundFile);
                if (audioSrc == null) {
                    throw new IllegalArgumentException("Sound file not found: " + soundFile);
                }

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);
                AudioFormat format = audioStream.getFormat();

                // Read the entire audio stream into a byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = audioStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }

                soundDataCache.put(soundFile, byteArrayOutputStream.toByteArray());
                audioFormatCache.put(soundFile, format);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Play a sound from memory
    public static void playSound(String soundFile) {
        byte[] soundData = soundDataCache.get(soundFile);
        AudioFormat format = audioFormatCache.get(soundFile);

        if (soundData == null || format == null) {
            System.err.println("Sound not preloaded or found in cache: " + soundFile);
            return;
        }

        try {
            // Create a Clip and load it with audio data
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(soundData);
            AudioInputStream audioStream = new AudioInputStream(byteInputStream, format, soundData.length / format.getFrameSize());
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Close the clip after playback
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}