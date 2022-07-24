module com.gregorgott.githubupdatechecker {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.gregorgott.githubupdatechecker to javafx.fxml;
    exports com.gregorgott.githubupdatechecker;
}