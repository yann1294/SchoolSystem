package admin;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class adminController implements Initializable {

    @FXML
    private TextField ID;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private DatePicker DOB;

    @FXML
    private TableView<studentData> studenttable;

    @FXML
    private TableColumn<studentData,String> idcolumn;
    @FXML
    private TableColumn<studentData,String> firstnamecolumn;
    @FXML
    private TableColumn<studentData,String> lastnamecolumn;
    @FXML
    private TableColumn<studentData,String> emailcolumn;
    @FXML
    private TableColumn<studentData,String> dobcolumn;


    private dbConnection dc;

    private ObservableList<studentData> data;

    private String sql = "SELECT * FROM students";

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param url  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param  rb resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.dc = new dbConnection();
    }

    // method to load data to our columns
    @FXML
    private void loadStudentData(ActionEvent event){

        try {

            Connection conn = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()){
                //checks whether there's anything inside the table
                this.data.add(new studentData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        // displaying the data
        this.idcolumn.setCellValueFactory(new PropertyValueFactory<studentData,String>("ID"));
        this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<studentData,String>("firstName"));
        this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<studentData,String>("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<studentData,String>("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<studentData,String>("DOB"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.data);
    }

    @FXML
    private void addStudent(ActionEvent event){
         String sqlInsert = "INSERT INTO students (id,fname,lname,email,DOB) VALUES (?,?,?,?,?)";

            try {
                Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlInsert);
                stmt.setString(1,this.ID.getText());
                stmt.setString(2,this.firstName.getText());
                stmt.setString(3,this.lastName.getText());
                stmt.setString(4,this.email.getText());
                stmt.setString(5,this.DOB.getEditor().getText());

                //executing the query
                stmt.execute();
                conn.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @FXML
    private void clearForm(ActionEvent event){
            this.ID.setText("");
            this.firstName.setText("");
            this.lastName.setText("");
            this.email.setText("");
            this.DOB.setValue(null);
    }

}
