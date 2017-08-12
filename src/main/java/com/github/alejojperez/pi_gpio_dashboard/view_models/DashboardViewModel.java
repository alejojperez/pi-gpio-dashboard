package com.github.alejojperez.pi_gpio_dashboard.view_models;

import com.alejojperez.pi_gpio.core.contracts.IGPIOController;
import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import com.github.alejojperez.pi_gpio_dashboard.message_center.Manager;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.Repository;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.Pin;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.PinsList;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;

public class DashboardViewModel implements ViewModel
{
    /**
     * The GPIO controller
     */
    private IGPIOController gpioController;

    /**
     * The Raspberry PI model
     */
    private SimpleStringProperty model = new SimpleStringProperty();

    /**
     * Constructor
     */
    public DashboardViewModel() { }

    /**
     * Get the GPIO controller instance
     *
     * @return the GPIO controller
     */
    public IGPIOController getGPIOController()
    {
        if(this.gpioController == null)
            this.gpioController = GPIOController.getInstance();

        return gpioController;
    }

    /**
     * The Raspberry Pi model
     *
     * @return
     */
    public String getModel()
    {
        return this.model.getValue();
    }

    /**
     * Get the model property
     * @return
     */
    public SimpleStringProperty getModelProperty()
    {
        return this.model;
    }

    /**
     * Load all the pins into the GPIO controller
     */
    public void loadPinsIntoController()
    {
        boolean errors = false;
        PinsList pins = Repository.getPinsByModel(this.model.getValue());

        if(pins == null)
        {
            errors = true;
        }
        else
        {
            for(Pin pinEntity : pins.getPins())
            {
                IPin pin;

                try {
                    pin = new com.alejojperez.pi_gpio.core.implementations.Pin(pinEntity.getNumber());
                } catch(Exception e) {
                    errors = true;
                    continue;
                }

                pin
                    .setAlias(pinEntity.getAlias())
                    .setEditable(pinEntity.getEditable())
                    .setFiveVolts(pinEntity.getFiveVolts())
                    .setThreeVolts(pinEntity.getThreeVolts())
                    .setGround(pinEntity.getGround());

                this.getGPIOController().addPin(pin);
            }
        }

        if(errors)
            Manager.error("Loading Error", "We could not all the pins from the default pins file.");
    }
}
