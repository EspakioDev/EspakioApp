package com.development.espakio.appespakio.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.model.GameInfo;

import java.util.List;

public class HorizontalHabilidadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GameInfo.Juego> mDataList;
    private int mColum = -1;
    private OnItemClickListenerRV listener;

    public interface OnItemClickListenerRV {
        void onItemClick(int childPosition, int parentPosition);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private ImageView imgJuego;

        public ItemViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.txtNomJuego);
            imgJuego = (ImageView) itemView.findViewById(R.id.imgGridView);
        }
    }

    public HorizontalHabilidadAdapter() { }

    public void setData(List<GameInfo.Juego> data) {
        if (mDataList != data) {
            mDataList = data;
            notifyDataSetChanged();
        }
    }

    public void setRowIndex(int index) {
        mColum = index;
    }

    public void setOnClick(OnItemClickListenerRV listener){
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_menujuegos, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rawHolder, final int position) {
        ItemViewHolder holder = (ItemViewHolder) rawHolder;
        holder.text.setText(mDataList.get(position).getNombre());
        holder.imgJuego.setImageResource(mDataList.get(position).getIdImagen());
        holder.imgJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position, mColum);
            }
        });
        //holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}


