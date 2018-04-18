package com.development.espakio.appespakio.view;

/**
 * Created by Spectre 13-4107la on 05/03/2018.
 */

public interface ILoginView {
    void loginFormatValidation();
    void loginEmptyFields();
    void loginSuccess();
    void loginError();
    void loginFailed();
    void idCliente(int id);
}
