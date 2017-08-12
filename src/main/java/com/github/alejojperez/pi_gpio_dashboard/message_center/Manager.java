package com.github.alejojperez.pi_gpio_dashboard.message_center;

import com.github.alejojperez.pi_gpio_dashboard.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;
import javafx.stage.Popup;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

public class Manager
{
    private static int defaultDuration = 5;
    private static Pos defaultPosition = Pos.BOTTOM_RIGHT;

    //region Message

    public static void message(String title, String text)
    {
        Manager.buildMessage(title, text).show();
    }

    public static void message(String title, String text, Duration duration)
    {
        Manager.buildMessage(title, text, duration).show();
    }

    public static void message(String title, String text, Pos position)
    {
        Manager.buildMessage(title, text, position).show();
    }

    public static void message(String title, String text, EventHandler<ActionEvent> onAction)
    {
        Manager.buildMessage(title, text, onAction).show();
    }

    //endregion

    //region Info

    public static void info(String title, String text)
    {
        Manager.buildMessage(title, text).showInformation();
    }

    public static void info(String title, String text, Duration duration)
    {
        Manager.buildMessage(title, text, duration).showInformation();
    }

    public static void info(String title, String text, Pos position)
    {
        Manager.buildMessage(title, text, position).showInformation();
    }

    public static void info(String title, String text, EventHandler<ActionEvent> onAction)
    {
        Manager.buildMessage(title, text, onAction).showInformation();
    }

    //endregion

    //region Confirm

    public static void confirm(String title, String text)
    {
        Manager.buildMessage(title, text).showConfirm();
    }

    public static void confirm(String title, String text, Duration duration)
    {
        Manager.buildMessage(title, text, duration).showConfirm();
    }

    public static void confirm(String title, String text, Pos position)
    {
        Manager.buildMessage(title, text, position).showConfirm();
    }

    public static void confirm(String title, String text, EventHandler<ActionEvent> onAction)
    {
        Manager.buildMessage(title, text, onAction).showConfirm();
    }

    //endregion

    //region Error

    public static void error(String title, String text)
    {
        Manager.buildMessage(title, text).showError();
    }

    public static void error(String title, String text, Duration duration)
    {
        Manager.buildMessage(title, text, duration).showError();
    }

    public static void error(String title, String text, Pos position)
    {
        Manager.buildMessage(title, text, position).showError();
    }

    public static void error(String title, String text, EventHandler<ActionEvent> onAction)
    {
        Manager.buildMessage(title, text, onAction).showError();
    }

    //endregion

    //region Warning

    public static void warning(String title, String text)
    {
        Manager.buildMessage(title, text).showWarning();
    }

    public static void warning(String title, String text, Duration duration)
    {
        Manager.buildMessage(title, text, duration).showWarning();
    }

    public static void warning(String title, String text, Pos position)
    {
        Manager.buildMessage(title, text, position).showWarning();
    }

    public static void warning(String title, String text, EventHandler<ActionEvent> onAction)
    {
        Manager.buildMessage(title, text, onAction).showWarning();
    }

    //endregion

    //region Builders

    private static Notifications buildMessage(String title, String text)
    {
        return Manager.buildMessage(title, text, null, Duration.seconds(Manager.defaultDuration), Manager.defaultPosition, null);
    }

    private static Notifications buildMessage(String title, String text, Duration duration)
    {
        return Manager.buildMessage(title, text, null, duration, Manager.defaultPosition, null);
    }

    private static Notifications buildMessage(String title, String text, Pos position)
    {
        return Manager.buildMessage(title, text, null, Duration.seconds(Manager.defaultDuration), position, null);
    }

    private static Notifications buildMessage(String title, String text, EventHandler<ActionEvent> onAction)
    {
        return Manager.buildMessage(title, text, null, Duration.seconds(Manager.defaultDuration), Manager.defaultPosition, onAction);
    }

    public static Notifications buildMessage(String title, String text, Node graphic, Duration duration, Pos position, EventHandler<ActionEvent> onAction)
    {
        return Notifications.create()
                .title(title)
                .text(text)
                .graphic(graphic)
                .hideAfter(duration)
                .position(position)
                .onAction(onAction)
                .darkStyle();
    }

    //endregion
}
