package crud.view;

import crud.MainApp;
import crud.dao.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class RootLayoutController {
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleClose() {
        System.exit(0);
    }

    @FXML
    private void handleDeleteAll() {
        mainApp.getUserData().clear();

        UserDao dao = new UserDao();

        dao.deleteAllUsers();
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CRUD User Information");
        alert.setHeaderText("About");
        alert.setContentText("Author: Ł.Ś.");

        alert.showAndWait();
    }

}
