package com.github.alejojperez.pi_gpio_dashboard.commands_center.commands;

import com.alejojperez.pi_gpio.core.config.Configuration;
import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import com.github.alejojperez.pi_gpio_dashboard.Application;
import com.github.alejojperez.pi_gpio_dashboard.message_center.Manager;
import de.saxsys.mvvmfx.utils.commands.Action;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.function.Supplier;

public class SaveConfigurationCommand extends AbstractCommand
{
    public SaveConfigurationCommand(Configuration configuration)
    {
        super(buildActionSupplier(configuration));
    }

    public SaveConfigurationCommand(Configuration configuration, boolean inBackground)
    {
        super(buildActionSupplier(configuration), inBackground);
    }

    public SaveConfigurationCommand(Configuration configuration, ObservableValue<Boolean> executableObservable)
    {
        super(buildActionSupplier(configuration), executableObservable);
    }

    public SaveConfigurationCommand(Configuration configuration, ObservableValue<Boolean> executableObservable, boolean inBackground)
    {
        super(buildActionSupplier(configuration), executableObservable, inBackground);
    }

    private static Supplier<Action> buildActionSupplier(Configuration configuration)
    {
        return () -> new Action() {
            @Override
            protected void action() throws Exception
            {
                try {
                    File file = new File(Application.class.getResource("configuration.xml").getPath());
                    JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                    // output pretty printed
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                    jaxbMarshaller.marshal(configuration, file);

                    // Once the configuration is saved, we have to reload the GPIO watcher
                    // if it was loaded
                    if(GPIOController.getInstance().isFolderWatcherRunning())
                    {
                        GPIOController.getInstance().stopFolderWatcher();
                        GPIOController.getInstance().loadGeneralPath();
                        GPIOController.getInstance().startFolderWatcher();
                    }
                } catch (JAXBException e) {
                    Manager.buildMessage("Configuration", "Error Saving Configuration", "We could not save the configuration details to the file.", Alert.AlertType.ERROR, null, "");
                }
            }
        };
    }
}
