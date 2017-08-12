package com.github.alejojperez.pi_gpio_dashboard.commands;

import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import com.alejojperez.pi_gpio.core.implementations.Pin;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import java.util.HashMap;

public class CommandCenter
{
    ////////////////////////// ON/OFF //////////////////////////
    private static HashMap<Integer, Command> onOffCommand;
    public static Command getOnOffCommand(Integer pinNumber)
    {
        if(CommandCenter.onOffCommand == null)
            CommandCenter.onOffCommand = new HashMap<>();

        if(!CommandCenter.onOffCommand.containsKey(pinNumber))
        {
            Command command = new DelegateCommand(() -> new Action() {
                @Override
                protected void action() throws Exception
                {
                    IPin pin = GPIOController.getInstance().get(pinNumber);
                    String value = pin.getValue().equals(Pin.GPIO_ON) ? Pin.GPIO_OFF : Pin.GPIO_ON;

                    pin.setValue(value);
                }
            },
            true);

            CommandCenter.onOffCommand.put(pinNumber, command);
        }

        return CommandCenter.onOffCommand.get(pinNumber);
    }

    ////////////////////////// Direction //////////////////////////
    private static HashMap<Integer, Command> directionCommand;
    public static Command getDirectionCommand(Integer pinNumber)
    {
        if(CommandCenter.directionCommand == null)
            CommandCenter.directionCommand = new HashMap<>();

        if(!CommandCenter.directionCommand.containsKey(pinNumber))
        {
            Command command = new DelegateCommand(() -> new Action() {
                @Override
                protected void action() throws Exception
                {
                    IPin pin = GPIOController.getInstance().get(pinNumber);
                    String value = pin.getDirection().equals(Pin.GPIO_IN) ? Pin.GPIO_OUT : Pin.GPIO_IN;

                    pin.setDirection(value);
                }
            },
            true);

            CommandCenter.directionCommand.put(pinNumber, command);
        }

        return CommandCenter.directionCommand.get(pinNumber);
    }

    ////////////////////////// Initialize //////////////////////////
    private static HashMap<Integer, Command> initializeCommand;
    public static Command getInitializeCommand(Integer pinNumber)
    {
        if(CommandCenter.initializeCommand == null)
            CommandCenter.initializeCommand = new HashMap<>();

        if(!CommandCenter.initializeCommand.containsKey(pinNumber))
        {
            Command command = new DelegateCommand(() -> new Action() {
                @Override
                protected void action() throws Exception
                {
                    IPin pin = GPIOController.getInstance().get(pinNumber);

                    if(pin.isInitialized())
                        pin.destroy();
                    else
                        pin.initialize();
                }
            },
            true);

            CommandCenter.initializeCommand.put(pinNumber, command);
        }

        return CommandCenter.initializeCommand.get(pinNumber);
    }
}
