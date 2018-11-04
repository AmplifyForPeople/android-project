package amplify.us.amplify.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import amplify.us.amplify.R;
import amplify.us.amplify.entities.SongEntity;

public class ListFavSongsAdapter extends BaseAdapter {

    private Context mContext;

    public ListFavSongsAdapter(Context mContext, List<SongEntity> mSongList) {
        this.mContext = mContext;
        this.mSongList = mSongList;
    }

    private List<SongEntity> mSongList;

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
        View v = View.inflate(mContext,R.layout.item_fav_song,null);

        TextView tvSong = (TextView) v.findViewById(R.id.name_song_unique);
        TextView tvArtist = (TextView) v.findViewById(R.id.artist_name_unique);
        TextView tvAlbum = (TextView) v.findViewById(R.id.album_name_unique);

        tvSong.setText(mSongList.get(position).getName());
        tvArtist.setText(mSongList.get(position).getArtist());
        tvAlbum.setText(mSongList.get(position).getAlbum());

        v.setTag(mSongList.get(position).getName());

        return v;
    }
}
