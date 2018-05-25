package com.development.espakio.appespakio.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.development.espakio.appespakio.R;

import java.util.ArrayList;

/**
 * Created by marcuscrespo on 23/05/18.
 */

public class MyAdapterMenuJuegos extends RecyclerView.Adapter<MyAdapterMenuJuegos.HorizontalViewHolder> implements View.OnClickListener{

    private int[] imgJuegos;
    private ArrayList<String> nombresJuegos;
    private Context context;
    private Dialog playgame;
    private int habilidad;
    private int position;

    public MyAdapterMenuJuegos(Context context, int[] imgJuegos, ArrayList<String> nombresJuegos, int habilidad) {
        this.imgJuegos = imgJuegos;
        this.nombresJuegos = nombresJuegos;
        this.context = context;
        this.habilidad = habilidad;
        playgame = new Dialog(context);
    }


    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_menujuegos, parent,false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, final int position) {

        Glide.with(context).load(imgJuegos[position]).into(holder.img);
        holder.txtNomJuego.setText(nombresJuegos.get(position));

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (habilidad == 1){
                    switch (position){
                        case 0: //primera lunita
                            context.startActivity( new Intent(context, JuegoUno.class));
                            break;
                        case 1: //segunda lunita
                            break;
                        case 2: //tercera lunita
                            break;
                    }
                }
            }
        });
    }

    /*public  void showpopup(final Context context, int position){
        Button btnplay, btnCancelar;
        TextView txtNombre, txtDescripcion;

        playgame.setContentView(R.layout.descripcionjuego_popup);

        txtNombre = (TextView)  playgame.findViewById(R.id.txtDescripcion);
        txtDescripcion = (TextView) playgame.findViewById(R.id.txtNombreJuego);
        btnplay = (Button) playgame.findViewById(R.id.btnPlay);

        txtNombre.setText(nombresJuegos.get(position));

        btnplay.setOnClickListener(this);
        playgame.show();

    }*/

    @Override
    public int getItemCount() {
        return nombresJuegos.size();
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId()){
            case R.id.btnPlay:
                playgame.hide();
                context.startActivity(new Intent(context,JuegoUno.class));
                break;
            case R.id.imgGridView:
                //view.
                break;
        }*/

    }

    public  class HorizontalViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txtNomJuego;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgGridView);
            txtNomJuego =itemView.findViewById(R.id.txtNomJuego);
        }
    }
}
