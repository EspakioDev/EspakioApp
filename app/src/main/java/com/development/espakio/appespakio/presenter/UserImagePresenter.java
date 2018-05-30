package com.development.espakio.appespakio.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.development.espakio.appespakio.view.IUserImageView;

public class UserImagePresenter implements IUserImagePresenter{

    private Context context;
    private SharedPreferences preferences;
    private IUserImageView userImageView;

    public UserImagePresenter(IUserImageView userImageView, Context context) {
        //this.userImageView = userImageView;
        this.context = context;
        this.preferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    @Override
    public void setImageUser(int idImage) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ImageUser", idImage);
        editor.apply();
    }
}
