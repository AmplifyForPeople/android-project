package amplify.us.amplify.adapters;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import amplify.us.amplify.details.DetailEstablishmentActivity;
import amplify.us.amplify.R;
import amplify.us.amplify.entities.EstablishmentEntity;
import amplify.us.amplify.entities.GenreEntity;

public class EstablishmentAdapter extends RecyclerView.Adapter<EstablishmentAdapter.EstablishmentViewHolder>{

    class EstablishmentViewHolder extends RecyclerView.ViewHolder {

        TextView name_establishment;
        TextView info_establishment;
        ImageView image_establishment;
        CardView parentLayout;

        public EstablishmentViewHolder (View itemView){
            super(itemView);
            name_establishment = (TextView) itemView.findViewById(R.id.name_establishment);
            info_establishment = (TextView) itemView.findViewById(R.id.info_establishment);
            image_establishment = (ImageView) itemView.findViewById(R.id.img_establishment);
            parentLayout = (CardView) itemView.findViewById(R.id.card_establishment);
        }

    }

    private final ArrayList<EstablishmentEntity> data;

    public EstablishmentAdapter(ArrayList<EstablishmentEntity> data){
        this.data = data;
    }

    @Override
    public EstablishmentViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        return new EstablishmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_establishments_nearby,parent,false));
    }
    @Override
    public void onBindViewHolder(EstablishmentViewHolder holder, int position){
        EstablishmentEntity establishmentEntity = data.get(position);
        holder.name_establishment.setText(establishmentEntity.getName());
        holder.info_establishment.setText(establishmentEntity.getInfo());
        Picasso.get()
                .load(establishmentEntity.getUrl_image())
                .centerCrop()
                .fit()
                .into(holder.image_establishment);
        holder.parentLayout.setOnClickListener((View v) -> {
                Intent intent = new Intent(v.getContext(),DetailEstablishmentActivity.class);
                intent.putExtra("establishment_title",data.get(position).getName());
                intent.putExtra("info_title",data.get(position).getInfo());
                intent.putExtra("image",data.get(position).getUrl_image());
                intent.putExtra("genres",(ArrayList<GenreEntity>)data.get(position).getGenres());
                intent.putExtra("id",data.get(position).getId());
                v.getContext().startActivity(intent);
        });
    }

    @Override
    public  int getItemCount(){
        return data.size();
    }




}
