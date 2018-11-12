package loginApp;

import admin.adminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import students.studentController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    // instance of loginModel class
        LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbstatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<option> comboBox;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginStatus;


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param url  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param rb The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

            if (this.loginModel.isDatabaseConnected()){

                this.dbstatus.setText("Connected to database");
            } else {
                this.dbstatus.setText("Not Connected to database");
            }

            this.comboBox.setItems(FXCollections.observableArrayList(option.values()));
    }

    /**
     * checks whether the login info is admin or student.
     * @param event
     */
    @FXML
    public void Login(ActionEvent event){

        try {
            if (this.loginModel.isLogin(this.username.getText(),this.password.getText(),((option)this.comboBox.getValue()).toString())){
                    Stage stage = (Stage)this.loginButton.getScene().getWindow();
                    stage.close();
                    switch (((option) this.comboBox.getValue()).toString()){
                        case "Admin":
                                      AdminLogin();
                                      break;

                        case  "Student":
                                        studentLogin();
                                        break;
                    }

            }else {
                this.loginStatus.setText("Wrong credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
        public void studentLogin(){

        try {
            Stage userStage  = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/students/studentsFXML.fxml").openStream());

            studentController studentController = (students.studentController)loader.getController();

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Student Dashboard");
            userStage.setResizable(false);
            userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AdminLogin(){
        try {
            Stage adminStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane adminroot = (Pane) loader.load(getClass().getResource("/admin/adminFxml.fxml").openStream());

            adminController adminController = (adminController) loader.getController();

            Scene scene = new Scene(adminroot);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
