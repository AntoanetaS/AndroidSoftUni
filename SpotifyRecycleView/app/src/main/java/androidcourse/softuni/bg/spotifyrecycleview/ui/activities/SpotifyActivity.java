package androidcourse.softuni.bg.spotifyrecycleview.ui.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidcourse.softuni.bg.spotifyrecycleview.R;
import androidcourse.softuni.bg.spotifyrecycleview.models.Song;
import androidcourse.softuni.bg.spotifyrecycleview.services.MusicService;
import androidcourse.softuni.bg.spotifyrecycleview.ui.activities.decorations.PlaylistCustomDecoration;
import androidcourse.softuni.bg.spotifyrecycleview.ui.adapters.AdapterPlaylist;

public class SpotifyActivity extends AppCompatActivity {

    private RecyclerView rvPlaylist;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterPlaylist adapter;
    private List<Song> songs;

    private MusicService musicService;
    private Intent musicPlayerIntent;
    private boolean isMusicBound = false;

    private ImageView ivPlay;
    private ImageView ivNext;
    private ImageView ivPrevious;
    private ImageView ivFastFolard;
    private ImageView ivFastRewind;

    private ServiceConnection musicPlayerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder musicBinder = (MusicService.MusicBinder)service;
            musicService = musicBinder.getService();
            musicService.setSongs(songs);
            AdapterPlaylist.setMusicService(musicService);
            isMusicBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isMusicBound = false;
        }
    };

    private View.OnClickListener onPlayOptionsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemId = v.getId();

            switch (itemId){
                case R.id.iv_play:{
                    if(musicService.isMusicPlayerPlay()){
                        musicService.pauseSong();
                        ivPlay.setImageResource(R.drawable.ic_pause);
                    }else{
                        musicService.playSong();
                        ivPlay.setImageResource(R.drawable.ic_play);
                    }
                    break;
                }
                case R.id.iv_skipNext:{
                    musicService.playNextSong();
                    break;
                }
                case R.id.iv_skipPrevious:{
                    musicService.playPreviousSong();
                    break;
                }
                case R.id.iv_fastForward:{
                    musicService.fastForward();
                    break;
                }
                case R.id.iv_fastRewind:{
                    musicService.fastRewind();
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify);

        initSongslist();

        rvPlaylist = (RecyclerView)findViewById(R.id.rv_playlist);
        layoutManager = new LinearLayoutManager(this);
        adapter = new AdapterPlaylist(songs);

        rvPlaylist.setLayoutManager(layoutManager);
        rvPlaylist.setAdapter(adapter);
        rvPlaylist.addItemDecoration(new PlaylistCustomDecoration(this));

        ivPlay = (ImageView)findViewById(R.id.iv_play);
        ivPlay.setOnClickListener(onPlayOptionsClickListener);
        ivPlay.setImageResource(R.drawable.ic_pause);

        ivNext = (ImageView)findViewById(R.id.iv_skipNext);
        ivNext.setOnClickListener(onPlayOptionsClickListener);

        ivFastFolard = (ImageView)findViewById(R.id.iv_fastForward);
        ivFastFolard.setOnClickListener(onPlayOptionsClickListener);

        ivFastRewind = (ImageView)findViewById(R.id.iv_fastRewind);
        ivFastRewind.setOnClickListener(onPlayOptionsClickListener);

        ivPrevious = (ImageView)findViewById(R.id.iv_skipPrevious);
        ivPrevious.setOnClickListener(onPlayOptionsClickListener);
    }

    private void initSongslist() {
        songs = new ArrayList<>();

        songs.add(new Song("Faces (ft. Meighan Nealon)", "Andy Moor & Ashley Wallbridge", R.raw.andy_moor));
        songs.add(new Song("Miss You Paradise (Shogun Remix)","Emma Hewitt", R.raw.emma_hewitt));
        songs.add(new Song("Silent Sun (vocal mix)","Tom Cloud",R.raw.tom_cloud));
        songs.add(new Song("Headache In A Bottle","D.O.D", R.raw.headache));
        songs.add(new Song("Roll The Drums (Felix Cartel Remix)","Marissa Jack", R.raw.roll));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(musicPlayerIntent == null){
            musicPlayerIntent = new Intent(this, MusicService.class);
            bindService(musicPlayerIntent, musicPlayerConnection, BIND_AUTO_CREATE);
            startService(musicPlayerIntent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(musicPlayerConnection);
        stopService(musicPlayerIntent);
        musicService=null;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
