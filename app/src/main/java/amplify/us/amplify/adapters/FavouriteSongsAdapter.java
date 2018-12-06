package amplify.us.amplify.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import amplify.us.amplify.R;
import amplify.us.amplify.bottom_menu.AmplifySiteFragment;
import amplify.us.amplify.entities.SongEntity;

public class FavouriteSongsAdapter extends BaseAdapter  {

    private Context mContext;
    private List<SongEntity> mSongList = null;
    private ArrayList<SongEntity> arraylist;

    public FavouriteSongsAdapter(Context mContext, List<SongEntity> mSongList) {
        this.mContext = mContext;
        this.mSongList = mSongList;
        this.arraylist = new ArrayList<SongEntity>();
        this.arraylist.addAll(mSongList);
    }


    @Override
    public int getCount() {
        return mSongList.size();
    }

    @Override
    public SongEntity getItem(int position) {
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

    public void filter (String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        mSongList.clear();
        if(charText.length() == 0){
            mSongList.addAll(arraylist);
        } else {
            for (SongEntity wp : arraylist){
                if(wp.getName().toLowerCase(Locale.getDefault()).contains(charText)){
                    mSongList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
