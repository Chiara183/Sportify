package sportify;

import sportify.controller.graphic.LoginGraphicController;

public class AdapterLogin implements Login{

    private LoginGraphicController controller;
    private boolean google;

    public void setController(LoginGraphicController controller) {
        this.controller = controller;
    }

    public void setGoogle(boolean google) {
        this.google = google;
    }

    public LoginGraphicController getController() {
        return controller;
    }

    public boolean isGoogle() {
        return google;
    }

    public AdapterLogin(){}

    public AdapterLogin(LoginGraphicController controller, boolean google){
        setGoogle(google);
        setController(controller);
    }

    @Override
    public void doLogin() {
        if(isGoogle()){
            getController().loginWithGoogle();
        }
        else{
            getController().submitActionLogin();
        }

    }
}
