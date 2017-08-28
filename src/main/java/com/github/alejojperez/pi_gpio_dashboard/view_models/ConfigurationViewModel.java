package com.github.alejojperez.pi_gpio_dashboard.view_models;

import com.alejojperez.pi_gpio.core.Utils;
import com.alejojperez.pi_gpio.core.config.Configuration;
import com.alejojperez.pi_gpio.core.config.GPIO;
import com.alejojperez.pi_gpio.core.config.Paths;
import com.github.alejojperez.pi_gpio_dashboard.commands_center.CommandCenter;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.SimpleStringProperty;

public class ConfigurationViewModel implements ViewModel
{
    public Config config = new Config();

    public ConfigurationViewModel()
    {
        this.initializeConfig();
    }

    private void initializeConfig()
    {
        GPIO gpio = Utils.configuration().getGpio();
        this.config.gpioPlaceholder.setValue(gpio.getPlaceholder());
        this.config.gpioPathDirection.setValue(gpio.getPaths().getValue());
        this.config.gpioPathExport.setValue(gpio.getPaths().getExport());
        this.config.gpioPathUnexport.setValue(gpio.getPaths().getUnexport());
        this.config.gpioPathValue.setValue(gpio.getPaths().getValue());
        this.config.gpioPathIsInitialized.setValue(gpio.getPaths().getIsInitialized());
        this.config.gpioPathGeneralPath.setValue(gpio.getPaths().getGeneralPath());
    }

    public class Config
    {
        public SimpleStringProperty gpioPlaceholder = new SimpleStringProperty("");
        public SimpleStringProperty gpioPathDirection = new SimpleStringProperty("");
        public SimpleStringProperty gpioPathExport = new SimpleStringProperty("");
        public SimpleStringProperty gpioPathUnexport = new SimpleStringProperty("");
        public SimpleStringProperty gpioPathValue = new SimpleStringProperty("");
        public SimpleStringProperty gpioPathIsInitialized = new SimpleStringProperty("");
        public SimpleStringProperty gpioPathGeneralPath = new SimpleStringProperty("");
    }

    public void saveConfiguration()
    {
        Paths paths = new Paths();
        paths.setDirection(this.config.gpioPathDirection.getValue());
        paths.setExport(this.config.gpioPathExport.getValue());
        paths.setUnexport(this.config.gpioPathUnexport.getValue());
        paths.setValue(this.config.gpioPathValue.getValue());
        paths.setIsInitialized(this.config.gpioPathIsInitialized.getValue());
        paths.setGeneralPath(this.config.gpioPathGeneralPath.getValue());

        GPIO gpio = new GPIO();
        gpio.setPlaceholder(this.config.gpioPlaceholder.getValue());
        gpio.setPaths(paths);

        Configuration configuration = new Configuration();
        configuration.setGpio(gpio);

        CommandCenter.getSaveConfigurationCommand(configuration, true).execute();
    }
}
