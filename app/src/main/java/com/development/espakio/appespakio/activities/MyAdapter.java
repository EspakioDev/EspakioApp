package com.development.espakio.appespakio.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.development.espakio.appespakio.R;


/**
 * Created by marcuscrespo on 07/03/18.
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int[] imgUsuario;
    private int layout;

    public  MyAdapter(Context context, int layout, int[] imgUsuario){

        this.context = context;
        this.layout = layout;
        this.imgUsuario = imgUsuario;
    }

    @Override
    public int getCount() {
        return this.imgUsuario.length;
    }

    @Override
    public Object getItem(int i) {
        return this.imgUsuario[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.gridview, viewGroup, false);

        }

        ImageView imagenCoche = (ImageView) view.findViewById(R.id.MenUsuGrid_imgUsuario);



        Glide.with(imagenCoche.getContext())
                .load(imgUsuario[i])
                .into(imagenCoche);



        return view;
    }
}
