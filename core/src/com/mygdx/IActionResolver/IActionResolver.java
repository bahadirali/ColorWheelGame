package com.mygdx.IActionResolver;

/**
 * Created by bahadirali on 20.06.2015.
 */

//Source:
//https://code.google.com/p/libgdx-users/wiki/IntegratingAndroidNativeUiElements3TierProjectSetup

public interface IActionResolver {
    void showShortToast(CharSequence toastMessage);
    void showLongToast(CharSequence toastMessage);
    void showAlertBox(String alertBoxTitle, String alertBoxMessage, String alertBoxButtonText);
    void openUri(String uri);
    void showMyList();
}
