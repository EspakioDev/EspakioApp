package com.development.espakio.appespakio.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    int habilidad = 0;
    Dialog desJuego;


    public MyAdapterMenuJuegos(Context context, int[] imgJuegos, ArrayList<String> nombresJuegos, int habilidad) {
        this.imgJuegos = imgJuegos;
        this.nombresJuegos = nombresJuegos;
        this.context = context;
        this.habilidad = habilidad;
        desJuego = new Dialog(context);
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
                showpopup(view, habilidad, position);
            }
        });
    }

    public  void showpopup(View view, final int habilidad, int position){
        Button btnPlay;
        TextView txtCancelar, txtDescripcio;
        ImageView imgLogoJuego;
        desJuego.setContentView(R.layout.descripcionjuego_popup);
        btnPlay = (Button)desJuego.findViewById(R.id.btnPlay);
        txtCancelar = (TextView)desJuego.findViewById(R.id.txtCancelar);
        txtDescripcio = (TextView)desJuego.findViewById(R.id.txtDescJuego);
        imgLogoJuego = (ImageView) desJuego.findViewById(R.id.imglogoJuego);
        if (habilidad == 1){
            switch (position){
                case 0:

                    break;
                case 1:

                    break;
            }
        }else if(habilidad == 2){
            switch (position){
                case 0: //primera lunita
                    imgLogoJuego.setImageResource(R.drawable.img_juego_colores);
                    txtDescripcio.setText("Selecciona el color que dice en el texo, no su color ¿Podras hacerlo?.");
                    btnPlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,JuegoTres.class);
                            context.startActivities(new Intent[]{intent});
                            desJuego.hide();
                        }
                    });
                    break;
                case 1: //segunda lunita
                    break;
                case 2:
                    break;
            }
        }else if(habilidad == 3){
            switch (position){
                case 0: //primera lunita
                    imgLogoJuego.setImageResource(R.drawable.img_juego_uno);
                    txtDescripcio.setText("¿Cual número es mayor? Descubre tus habilidades con este juego que desafaria tu intelecto.");
                    btnPlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,JuegoUno.class);
                            context.startActivities(new Intent[]{intent});
                            desJuego.hide();
                        }
                    });
                    break;
                case 1: //segunda lunita
                    break;
            }
        }
        txtCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desJuego.cancel();
            }
        });
        desJuego.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        desJuego.show();

    }

    @Override
    public int getItemCount() {
        return nombresJuegos.size();
    }

    @Override
    public void onClick(View view) {

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
