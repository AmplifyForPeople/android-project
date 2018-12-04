package amplify.us.amplify.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import amplify.us.amplify.R;
import amplify.us.amplify.bottom_menu.AmplifySiteFragment;
import amplify.us.amplify.entities.SongEntity;

public class FavouriteSongsAdapter extends BaseAdapter implements Filterable {

    private Context mContext;

    public FavouriteSongsAdapter(Context mContext, ArrayList<SongEntity> mSongList) {
        super();
        this.mContext = mContext;
        this.mSongList = mSongList;
    }

    private ArrayList<SongEntity> mSongList;
    private ArrayList<SongEntity> songs;


    @Override
    public int getCount() {
        return mSongList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSongList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext,R.layout.item_fav_song,null);

        TextView tvSong = (TextView) view.findViewById(R.id.name_song_unique);
        TextView tvArtist = (TextView) view.findViewById(R.id.artist_name_unique);
        TextView tvAlbum = (TextView) view.findViewById(R.id.album_name_unique);

        tvSong.setText(mSongList.get(position).getName());
        tvArtist.setText(mSongList.get(position).getArtist());
        tvAlbum.setText(mSongList.get(position).getAlbum());

        view.setTag(mSongList.get(position).getName());

        return view;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<SongEntity> results = new ArrayList<>();
                if (songs == null)
                    songs = mSongList;
                if (constraint != null) {
                    if (songs != null && songs.size() > 0) {
                        for (final SongEntity g : songs) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                mSongList = (ArrayList<SongEntity>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
