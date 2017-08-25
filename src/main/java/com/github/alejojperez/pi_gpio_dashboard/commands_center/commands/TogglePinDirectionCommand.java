package com.github.alejojperez.pi_gpio_dashboard.commands_center.commands;

import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import com.alejojperez.pi_gpio.core.implementations.Pin;
import de.saxsys.mvvmfx.utils.commands.Action;
import javafx.beans.value.ObservableValue;

import java.util.function.Supplier;

public class TogglePinDirectionCommand extends AbstractCommand
{
    public TogglePinDirectionCommand(Integer pinNumber)
    {
        super(buildActionSupplier(pinNumber));
    }

    public TogglePinDirectionCommand(Integer pinNumber, boolean inBackground)
    {
        super(buildActionSupplier(pinNumber), inBackground);
    }

    public TogglePinDirectionCommand(Integer pinNumber, ObservableValue<Boolean> executableObservable)
    {
        super(buildActionSupplier(pinNumber), executableObservable);
    }

    public TogglePinDirectionCommand(Integer pinNumber, ObservableValue<Boolean> executableObservable, boolean inBackground)
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
                String value = pin.getDirection().equals(Pin.GPIO_IN) ? Pin.GPIO_OUT : Pin.GPIO_IN;

                pin.setDirection(value);
            }
        };
    }
}
