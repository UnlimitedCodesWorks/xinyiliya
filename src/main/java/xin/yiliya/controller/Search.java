package xin.yiliya.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;



public class Search {
    @FXML
    TextField search_text;

    @FXML
    FlowPane searchFlow;

    @FXML
    public void searchP(MouseEvent mouseEvent) {
        searchFlow.getChildren().removeAll(searchFlow.getChildren());
        if (search_text.getText().length() == 0) {
            System.out.println("请输入搜索信息");
        } else {
            System.out.println(search_text.getText());
        }
    }
}

