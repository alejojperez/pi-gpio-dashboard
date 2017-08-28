package com.github.alejojperez.pi_gpio_dashboard.commands_center.commands;

import com.alejojperez.pi_gpio.core.config.Configuration;
import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import com.github.alejojperez.pi_gpio_dashboard.Application;
import com.github.alejojperez.pi_gpio_dashboard.message_center.Manager;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.Repository;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.ModelsList;
import de.saxsys.mvvmfx.utils.commands.Action;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.function.Supplier;

public class SaveDefaultPinsCommand extends AbstractCommand
{
    public SaveDefaultPinsCommand(ModelsList modelsList)
    {
        super(buildActionSupplier(modelsList));
    }

    public SaveDefaultPinsCommand(ModelsList modelsList, boolean inBackground)
    {
        super(buildActionSupplier(modelsList), inBackground);
    }

    public SaveDefaultPinsCommand(ModelsList modelsList, ObservableValue<Boolean> executableObservable)
    {
        super(buildActionSupplier(modelsList), executableObservable);
    }

    public SaveDefaultPinsCommand(ModelsList modelsList, ObservableValue<Boolean> executableObservable, boolean inBackground)
    {
        super(buildActionSupplier(modelsList), executableObservable, inBackground);
    }

    private static Supplier<Action> buildActionSupplier(ModelsList modelsList)
    {
        return () -> new Action() {
            @Override
            protected void action() throws Exception
            {
                if(modelsList != null)
                    Repository.modelsList = modelsList;

                boolean result = Repository.saveCurrentModelList();

                if(!result)
                Manager.buildMessage("Default Pins", "Error Saving pins", "We could not save the pins details to the file.", Alert.AlertType.ERROR, null, "");
            }
        };
    }
}
