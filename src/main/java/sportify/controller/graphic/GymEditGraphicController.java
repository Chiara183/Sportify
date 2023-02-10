package sportify.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import sportify.bean.GymEditBean;
import sportify.controller.Controller;
import sportify.controller.ControllerType;
import sportify.controller.GymEditController;
import sportify.model.domain.GymUser;

import java.util.Objects;

public class GymEditGraphicController extends EditGraphicController{
    protected static final String STYLE = "-fx-text-fill: #06B7C5;";
    protected static final String COLOR = "-fx-text-fill: black;";

    /** Reference to controller*/
    protected GymEditController gymEditController;

    /** Reference to bean*/
    protected final GymEditBean BEAN = new GymEditBean();


    public void setUser(GymUser user) {
        gymName.setText(user.getGymName());
        address.setText(user.getAddress());
        telephone.setText(user.getPhone());
        gymNameLabel.setText(user.getGymName());
        addressLabel.setText(user.getAddress());
        telephoneLabel.setText(user.getPhone());
    }

    public boolean checkSyntax(){
        return
        BEAN.checkUser(super.username.getText())
                &&
        BEAN.checkPass(super.password.getText())
                &&
        BEAN.checkEmail(super.email.getText())
                &&
        BEAN.checkGymName(super.gymName.getText())
                &&
        BEAN.checkTel(super.telephone.getText())
                &&
        BEAN.checkAddress(super.address.getText())
                &&
        BEAN.checkBday(super.dayOfBirth.getText(), super.birthMonth.getText(), super.birthYear.getText());
    }

    /** The action of the button.*/
    @Override
    @FXML
    protected void okAction(){
        if (this.checkSyntax()) {
            checkedOkAction();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error detected!");
            alert.setContentText("Wrong Syntax.");
            alert.showAndWait();
        }
    }

    private void checkedOkAction(){
        if (!Objects.equals(gymEditController.getUser().getUserName(), super.username.getText())) {
            gymEditController.getUser().setUserName(super.username.getText());
        }
        if (!Objects.equals(gymEditController.getUser().getPassword(), super.password.getText())) {
            gymEditController.getUser().setPassword(super.password.getText());
        }
        if (!Objects.equals(gymEditController.getUser().getFirstName(), super.firstName.getText())) {
            gymEditController.getUser().setFirstName(super.firstName.getText());
        }
        if (!Objects.equals(gymEditController.getUser().getLastName(), super.lastName.getText())) {
            gymEditController.getUser().setLastName(super.lastName.getText());
        }
        if (!Objects.equals(gymEditController.getUser().getEmail(), super.email.getText())) {
            gymEditController.getUser().setEmail(super.email.getText());
        }
        if (!Objects.equals(gymEditController.getUser().getGymName(), gymName.getText())) {
            gymEditController.getUser().setFirstName(gymName.getText());
        }
        if (!Objects.equals(gymEditController.getUser().getAddress(), address.getText())) {
            gymEditController.getUser().setLastName(address.getText());
        }
        if (!Objects.equals(gymEditController.getUser().getPhone(), telephone.getText())) {
            gymEditController.getUser().setEmail(telephone.getText());
        }
    }

    /** Is called to set controller*/
    @Override
    public void setController(Controller controller) {
        this.gymEditController = (GymEditController) controller;
        super.setController(controller);
    }

    /** Is called to get controller type*/
    @Override
    public ControllerType getGraphicType(){
        return gymEditController.getType();
    }
}
