package androidcourse.softuni.bg.spotifyrecycleview.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidcourse.softuni.bg.spotifyrecycleview.R;
import androidcourse.softuni.bg.spotifyrecycleview.models.Song;
import androidcourse.softuni.bg.spotifyrecycleview.services.MusicService;

public class AdapterPlaylist extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = AdapterPlaylist.class.getSimpleName();

    private List<Song> songs;
    private static MusicService musicService;

    public AdapterPlaylist(List<Song> songs){
        this.songs = songs;
    }

    public static void setMusicService(MusicService mService){
        musicService = mService;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView author;
        private TextView subTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.tv_title);
            author = (TextView)itemView.findViewById(R.id.tv_author);
            subTitle = (TextView)itemView.findViewById(R.id.tv_subTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(musicService!= null) {
                        musicService.setSong(new Song(title.getText().toString(), author.getText().toString(), 0));
                        musicService.playSong();
                    }else{
                        Log.d(TAG, "onClick: music service is null");
                    }
                }
            });
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Song currentSong = songs.get(position);

        if(holder!=null){
            ViewHolder viewHolder= (ViewHolder)holder;

            viewHolder.title.setText(currentSong.getTitle());
            viewHolder.author.setText(currentSong.getAuthor());
            viewHolder.subTitle.setText(currentSong.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
