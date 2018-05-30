package com.development.espakio.appespakio.view;

import com.development.espakio.appespakio.model.Usuario;

import java.util.Vector;

/**
 * Created by Spectre 13-4107la on 08/03/2018.
 */

public interface IGetUsersView {
    void chargeUsers(Vector<Usuario> users);
    void goToGameMenu();
    void goToConfiguration();
    void goToNewUser();

}
