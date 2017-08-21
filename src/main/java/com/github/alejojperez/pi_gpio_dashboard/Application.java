package com.github.alejojperez.pi_gpio_dashboard;

import com.alejojperez.pi_gpio.core.Utils;
import com.alejojperez.pi_gpio.core.config.Configuration;
import com.alejojperez.pi_gpio.core.contracts.IFileLogger;
import com.alejojperez.pi_gpio.core.implementations.FileLogger;
import com.alejojperez.pi_gpio.core.implementations.FolderWatcher;
import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import javafx.stage.Stage;

import java.net.URL;

public class Application extends javafx.application.Application
{
    private static Stage primaryStage;

    public static Stage getPrimaryStage()
    {
        return Application.primaryStage;
    }

    public static void main(String... args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        Application.primaryStage = stage;
        this.configGPIOController();
        Presenter.show(stage);
    }

    private void configGPIOController()
    {
        // Set the default configuration file location
        Utils.callback = () -> Application.class.getResourceAsStream("configuration.xml");

        // Tell the folder watcher to log events
        FolderWatcher.log = true;

        // Register logger to the GPIO controller
        IFileLogger logger = new FileLogger();
        GPIOController.getInstance().registerLogger(logger);

        // Start listening for the folder watcher
        GPIOController.getInstance().startFolderWatcher();

        // Stop listening for the folder watcher
        if(Application.getPrimaryStage() != null)
            Application.getPrimaryStage().setOnCloseRequest(event -> GPIOController.getInstance().stopFolderWatcher());
    }
}
