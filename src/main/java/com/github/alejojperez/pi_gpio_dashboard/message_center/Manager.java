package com.github.alejojperez.pi_gpio_dashboard.message_center;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Manager
{
    public static void alert(String title, String header, String message, Alert.AlertType alertType, String details, String detailsHeader)
    {
        Manager.buildMessage(title, header, message, alertType, details, detailsHeader).showAndWait();
    }

    public static String getExceptionMessage(Exception e)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    //region Message

    public static void message(String title, String text)
    {
        Manager.buildMessage(title, text, Alert.AlertType.NONE).showAndWait();
    }

    public static void message(String title, String header, String message)
    {
        Manager.buildMessage(title, header, message, Alert.AlertType.NONE).showAndWait();
    }

    //endregion

    //region Info

    public static void info(String title, String text)
    {
        Manager.buildMessage(title, text, Alert.AlertType.INFORMATION).showAndWait();
    }

    public static void info(String title, String header, String message)
    {
        Manager.buildMessage(title, header, message, Alert.AlertType.INFORMATION).showAndWait();
    }

    //endregion

    //region Confirm

    public static void confirm(String title, String text)
    {
        Manager.buildMessage(title, text, Alert.AlertType.CONFIRMATION).showAndWait();
    }

    public static void confirm(String title, String header, String message)
    {
        Manager.buildMessage(title, header, message, Alert.AlertType.CONFIRMATION).showAndWait();
    }

    //endregion

    //region Error

    public static void error(String title, String text)
    {
        Manager.buildMessage(title, text, Alert.AlertType.ERROR).showAndWait();
    }

    public static void error(String title, String header, String message)
    {
        Manager.buildMessage(title, header, message, Alert.AlertType.ERROR).showAndWait();
    }

    //endregion

    //region Warning

    public static void warning(String title, String text)
    {
        Manager.buildMessage(title, text, Alert.AlertType.WARNING).showAndWait();
    }

    public static void warning(String title, String header, String message)
    {
        Manager.buildMessage(title, header, message, Alert.AlertType.WARNING).showAndWait();
    }

    //endregion

    //region Builders

    private static Alert buildMessage(String title, String message)
    {
        return Manager.buildMessage(title, null, message, Alert.AlertType.NONE, null, null);
    }

    private static Alert buildMessage(String title, String header, String message)
    {
        return Manager.buildMessage(title, header, message, Alert.AlertType.NONE, null, null);
    }

    private static Alert buildMessage(String title, String message, Alert.AlertType alertType)
    {
        return Manager.buildMessage(title, null, message, alertType, null, null);
    }

    private static Alert buildMessage(String title, String header, String message, Alert.AlertType alertType)
    {
        return Manager.buildMessage(title, header, message, alertType, null, null);
    }

    public static Alert buildMessage(String title, String header, String message, Alert.AlertType alertType, String details, String detailsHeader)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        if(details != null && !details.isEmpty())
        {
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);

            if(detailsHeader != null && !detailsHeader.isEmpty())
            {
                Label label = new Label(detailsHeader);
                expContent.add(label, 0, 0);
            }

            TextArea textArea = new TextArea(details);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);
            expContent.add(textArea, 0, 1);

            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);
        }

        return alert;
    }

    //endregion
}
