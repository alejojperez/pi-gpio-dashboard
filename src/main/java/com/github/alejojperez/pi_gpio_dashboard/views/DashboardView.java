package com.github.alejojperez.pi_gpio_dashboard.views;

import com.github.alejojperez.pi_gpio_dashboard.Application;
import com.github.alejojperez.pi_gpio_dashboard.message_center.Manager;
import com.github.alejojperez.pi_gpio_dashboard.view_models.DashboardViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardView implements FxmlView<DashboardViewModel>, Initializable
{
    @InjectViewModel
    private DashboardViewModel viewModel;

    @FXML
    private TableView tvPins;

    public void initialize(URL location, ResourceBundle resources)
    {
        try {
            this.viewModel.loadPinsIntoController("2 and 3");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Manager.showMessage(Application.getPrimaryStage(), "message");
    }
}
