package amplify.us.amplify.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ImageView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import amplify.us.amplify.R;
//import amplify.us.amplify.details.DetailEstablishmentActivity;
import amplify.us.amplify.details.DetailSongActivity;
import amplify.us.amplify.entities.SongEntity;

public class VotedSongsAdapter extends RecyclerView.Adapter<VotedSongsAdapter.VotedSongsViewHolder> {

    class VotedSongsViewHolder extends RecyclerView.ViewHolder {

        TextView name_song;
        ImageView image_song;
        CardView parentLayout;

        public VotedSongsViewHolder (View itemView){
            super(itemView);
            name_song = (TextView) itemView.findViewById(R.id.name_fav_song);
            image_song = (ImageView) itemView.findViewById(R.id.img_song);
            parentLayout = (CardView) itemView.findViewById(R.id.card_votedSongs);
        }

    }

    private final ArrayList<SongEntity> data;

    public VotedSongsAdapter(ArrayList<SongEntity> data){
        this.data = data;
    }

    @Override
    public VotedSongsAdapter.VotedSongsViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        return new VotedSongsAdapter.VotedSongsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_most_voted_songs,parent,false));
    }

    @Override
    public void onBindViewHolder(VotedSongsAdapter.VotedSongsViewHolder holder, int position){
        SongEntity songEntity = data.get(position);
        holder.name_song.setText(songEntity.getName());
        Log.d("RIPARNAU", songEntity.getUrl_image());
        Picasso.get()
                .load(songEntity.getUrl_image())
                .centerCrop()
                .fit()
                .into(holder.image_song);

        holder.parentLayout.setOnClickListener((View v) -> {
            Intent intent = new Intent(v.getContext(),DetailSongActivity.class);
            intent.putExtra("song",data.get(position).getName());
            intent.putExtra("artist",data.get(position).getAlbum());
            intent.putExtra("album",data.get(position).getArtist());
            //IMAGE -> intent.putExtra("info_title",data.get(position).getInfo());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public  int getItemCount(){
        return data.size();
    }
}
