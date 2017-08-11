package com.github.alejojperez.pi_gpio_dashboard.message_center;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

public class Manager
{
    public static void showMessage(String title, String text, Node graphic, Duration duration, Pos position, EventHandler<ActionEvent> onAction)
    {
        Notifications.create()
            .title(title)
            .text(text)
            .graphic(graphic)
            .hideAfter(duration)
            .position(position)
            .onAction(onAction)
            .show();
    }

    private static Notifications buildNotification(String title, String text, Node graphic, Duration duration, Pos position, EventHandler<ActionEvent> onAction)
    {
        return Notifications.create()
                .title(title)
                .text(text)
                .graphic(graphic)
                .hideAfter(duration)
                .position(position)
                .onAction(onAction);
    }
}
