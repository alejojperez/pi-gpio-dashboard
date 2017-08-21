package com.github.alejojperez.pi_gpio_dashboard.views;

import com.github.alejojperez.pi_gpio_dashboard.view_models.ConfigurationViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurationView implements FxmlView<ConfigurationViewModel>, Initializable
{
    @InjectViewModel
    private ConfigurationViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
