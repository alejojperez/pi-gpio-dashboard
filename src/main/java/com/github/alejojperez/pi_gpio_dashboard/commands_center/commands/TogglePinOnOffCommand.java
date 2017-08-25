package com.github.alejojperez.pi_gpio_dashboard.commands_center.commands;

import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import com.alejojperez.pi_gpio.core.implementations.Pin;
import de.saxsys.mvvmfx.utils.commands.Action;
import javafx.beans.value.ObservableValue;

import java.util.function.Supplier;

public class TogglePinOnOffCommand extends AbstractCommand
{
    public TogglePinOnOffCommand(Integer pinNumber)
    {
        super(buildActionSupplier(pinNumber));
    }

    public TogglePinOnOffCommand(Integer pinNumber, boolean inBackground)
    {
        super(buildActionSupplier(pinNumber), inBackground);
    }

    public TogglePinOnOffCommand(Integer pinNumber, ObservableValue<Boolean> executableObservable)
    {
        super(buildActionSupplier(pinNumber), executableObservable);
    }

    public TogglePinOnOffCommand(Integer pinNumber, ObservableValue<Boolean> executableObservable, boolean inBackground)
    {
        super(buildActionSupplier(pinNumber), executableObservable, inBackground);
    }

    private static Supplier<Action> buildActionSupplier(Integer pinNumber)
    {
        return () -> new Action() {
            @Override
            protected void action() throws Exception
            {
                IPin pin = GPIOController.getInstance().get(pinNumber);
                String value = pin.getValue().equals(Pin.GPIO_ON) ? Pin.GPIO_OFF : Pin.GPIO_ON;

                pin.setValue(value);
            }
        };
    }
}
