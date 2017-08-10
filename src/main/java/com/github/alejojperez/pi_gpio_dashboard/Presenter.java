package com.github.alejojperez.pi_gpio_dashboard;

import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Presenter
{
    public static void show(Stage stage)
    {
        stage.setResizable(true);

        //set Stage boundaries to visible bounds of the main screen
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        stage.setTitle("GPIO Pins: Dashboard");
        //ViewTuple<DashboardView, DashboardViewModel> viewTuple = FluentViewLoader.fxmlView(DashboardView.class).load();

        //Parent root = viewTuple.getView();
        //stage.setScene(new Scene(root));

        //tage.setOnCloseRequest(event -> GPIOController.getInstance().finalize());

        stage.show();
    }
}
