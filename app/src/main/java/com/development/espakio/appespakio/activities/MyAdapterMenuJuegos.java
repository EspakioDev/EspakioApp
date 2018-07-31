package com.development.espakio.appespakio.activities;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.model.GameInfo;

import java.util.ArrayList;
import java.util.List;



public class MyAdapterMenuJuegos extends RecyclerView.Adapter<MyAdapterMenuJuegos.SimpleViewHolder> {

    private final Context mContext;
    private static List<GameInfo> mData;
    private static RecyclerView horizontalList;
    private HorizontalHabilidadAdapter.OnItemClickListenerRV listener;

    public class SimpleViewHolder extends RecyclerView.ViewHolder implements HorizontalHabilidadAdapter.OnItemClickListenerRV{

        public TextView txtHabilidad;
        private HorizontalHabilidadAdapter horizontalAdapter;
        private int childPosition;

        public SimpleViewHolder(View view) {
            super(view);
            Context itemContext = itemView.getContext();
            txtHabilidad = (TextView) view.findViewById(R.id.txtHabilidadHor);
            horizontalList = (RecyclerView) itemView.findViewById(R.id.listJuegos);
            horizontalList.setLayoutManager(new LinearLayoutManager(itemContext, LinearLayoutManager.HORIZONTAL, false));
            horizontalAdapter = new HorizontalHabilidadAdapter();
            horizontalAdapter.setOnClick(this);
            horizontalList.setAdapter(horizontalAdapter);
        }

        @Override
        public void onItemClick(int childPosition, int parentPosition) {
            listener.onItemClick(childPosition, parentPosition);
            //(getAdapterPosition()
        }
    }

    public MyAdapterMenuJuegos(Context context, List<GameInfo> data) {
        mContext = context;
        if (data != null)
            mData = new ArrayList<>(data);
        else mData = new ArrayList<>();
    }

    public void setOnClick(HorizontalHabilidadAdapter.OnItemClickListenerRV listener){
        this.listener = listener;
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.juegoshorizontal, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        holder.txtHabilidad.setText(mData.get(position).getHabilidad());
        holder.horizontalAdapter.setData(mData.get(position).getNombreJuegos()); // List of Strings
        horizontalList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.childPosition, position);
            }
        });
        holder.horizontalAdapter.setRowIndex(position);
    }
    //int pos=Integer.parseInt(v.getTag().toString());
    @Override
    public int getItemCount() {
        return mData.size();
    }
}


