package amplify.us.amplify.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import amplify.us.amplify.DetailEstablishmentActivity;
import amplify.us.amplify.DiscoverFragment;
import amplify.us.amplify.MainActivity;
import amplify.us.amplify.R;
import amplify.us.amplify.entities.EstablishmentEntity;

public class EstablishmentAdapter extends RecyclerView.Adapter<EstablishmentAdapter.EstablishmentViewHolder>{

    class EstablishmentViewHolder extends RecyclerView.ViewHolder {

        TextView name_establishment;
        TextView info_establishment;
        CardView parentLayout;

        public EstablishmentViewHolder (View itemView){
            super(itemView);
            name_establishment = (TextView) itemView.findViewById(R.id.name_establishment);
            info_establishment = (TextView) itemView.findViewById(R.id.info_establishment);
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

        holder.parentLayout.setOnClickListener((View v) -> {
                Intent intent = new Intent(v.getContext(),DetailEstablishmentActivity.class);
                intent.putExtra("establishment_title",data.get(position).getName());
                intent.putExtra("info_title",data.get(position).getInfo());
                v.getContext().startActivity(intent);
        });
    }

    @Override
    public  int getItemCount(){
        return data.size();
    }


}
