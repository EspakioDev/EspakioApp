package com.development.espakio.appespakio.view;

/**
 * Created by Spectre 13-4107la on 08/03/2018.
 */

public interface IRegisterView {
    void registerFormatValidation();
    void emailDuplicate();
    void passwordNotMatch();
    void registerEmptyFields();
    void registerSuccess();
    void registerError();
    void registerFailed();
    void idCliente(int id);
}
