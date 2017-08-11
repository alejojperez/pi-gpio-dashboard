package com.github.alejojperez.pi_gpio_dashboard;

import javafx.stage.Stage;

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
        Presenter.show(stage);
    }
}
