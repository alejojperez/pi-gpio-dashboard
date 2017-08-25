package com.github.alejojperez.pi_gpio_dashboard.views;

import com.github.alejojperez.pi_gpio_dashboard.view_models.ConfigurationViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.beans.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurationView implements FxmlView<ConfigurationViewModel>, Initializable
{
    @InjectViewModel
    private ConfigurationViewModel viewModel;

    @FXML
    private TextField txtPathPlaceholder;
    @FXML
    private TextField txtDirectionPath;
    @FXML
    private TextField txtExportPath;
    @FXML
    private TextField txtUnexportPath;
    @FXML
    private TextField txtValuePath;
    @FXML
    private TextField txtIsInitializedPath;
    @FXML
    private TextField txtGeneralPath;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.bindViewModel();
    }

    private void bindViewModel()
    {
        this.txtPathPlaceholder.textProperty().bindBidirectional(this.viewModel.config.gpioPlaceholder);
        this.txtDirectionPath.textProperty().bindBidirectional(this.viewModel.config.gpioPathDirection);
        this.txtExportPath.textProperty().bindBidirectional(this.viewModel.config.gpioPathExport);
        this.txtUnexportPath.textProperty().bindBidirectional(this.viewModel.config.gpioPathUnexport);
        this.txtValuePath.textProperty().bindBidirectional(this.viewModel.config.gpioPathValue);
        this.txtIsInitializedPath.textProperty().bindBidirectional(this.viewModel.config.gpioPathIsInitialized);
        this.txtGeneralPath.textProperty().bindBidirectional(this.viewModel.config.gpioPathGeneralPath);
    }

    @FXML
    private void closeWindow(ActionEvent actionEvent)
    {
        ((Stage) ( (Button)actionEvent.getSource() ).getScene().getWindow() ).close();
    }
}
