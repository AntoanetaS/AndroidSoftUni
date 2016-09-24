package androidcourse.softuni.bg.spotifyrecycleview.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.List;

import androidcourse.softuni.bg.spotifyrecycleview.models.Song;

/**
 * Created by Sansarova on 22.9.2016 г..
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {
    private final IBinder musicPlayerBinder = new MusicBinder();

    private MediaPlayer mediaPlayer;
    private List<Song> songs;
    private int songPosition;

    private double startTime = 0;
    private double finalTime = 0;
    private int forwardTime = 5000;
    private int backwardTime = 5000;

    private Handler myHandler = new Handler();;

    public void setSongs(List<Song> songs) {
        if (songs != null) {
            this.songs = songs;
        }
    }

    public void setSong(Song song) {
        songPosition = songs.indexOf(song);
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicPlayerBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        pauseSong();
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        songPosition = 0;
        mediaPlayer = new MediaPlayer();

        initMusicPlayer();
    }

    public void initMusicPlayer() {
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playNextSong();

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    public void playSong() {
        mediaPlayer.reset();

        Song currentSong = songs.get(songPosition);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), currentSong.getSondRawFileId());
        // mediaPlayer.prepareAsync();
        mediaPlayer.start();

        updateSongTime();
    }

    public void pauseSong() {
        mediaPlayer.pause();
    }

    public boolean isMusicPlayerPlay() {
        return mediaPlayer.isPlaying();
    }

    public void playNextSong() {
        int val = songPosition;
        Song track;
        val = val + 1;
        if (val < songs.size()) {
            track = songs.get(val);

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), track.getSondRawFileId());
            mediaPlayer.start();
            songPosition = val;

            updateSongTime();
        }
    }

    public void playPreviousSong() {
        int val = songPosition;
        Song track;
        val = val - 1;
        if (val >= 0) {
            track = songs.get(val);

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), track.getSondRawFileId());
            mediaPlayer.start();
            songPosition = val;

            updateSongTime();
        }
    }

    private void updateSongTime() {
        finalTime = mediaPlayer.getDuration();
        myHandler.postDelayed(UpdateSongTime,100);
    }

    public void fastForward (){
        int temp = (int)startTime;
        if((temp+forwardTime)<=finalTime){
            startTime = startTime + forwardTime;
            mediaPlayer.seekTo((int) startTime);
        }
    }

    public void fastRewind(){
        int temp = (int)startTime;

        if((temp-backwardTime)>0){
            startTime = startTime - backwardTime;
            mediaPlayer.seekTo((int) startTime);
        }
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
        }
    };
}
