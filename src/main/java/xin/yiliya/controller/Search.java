package xin.yiliya.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;



public class Search {
    @FXML
    TextField search_text;

    @FXML
    FlowPane searchFlow;

    @FXML
    Button search_button;

    @FXML
    public void searchP(MouseEvent mouseEvent) {
        if (search_text.getText().length() == 0) {
            System.out.println("请输入搜索信息");
        } else {
            System.out.println(search_text.getText());
            searchFlow.getChildren().removeAll(searchFlow.getChildren());
        }
    }

    public void mouseEnter(MouseEvent mouseEvent) {
        search_button.setStyle("-fx-background-color: #3498ff; -fx-font-size: 15");
    }

    public void mouseExited(MouseEvent mouseEvent) {
        search_button.setStyle("-fx-background-color: #3498db; -fx-font-size: 15");
    }
}

