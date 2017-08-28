package com.github.alejojperez.pi_gpio_dashboard.commands_center;

import com.alejojperez.pi_gpio.core.config.Configuration;
import com.github.alejojperez.pi_gpio_dashboard.commands_center.commands.*;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.ModelsList;
import de.saxsys.mvvmfx.utils.commands.Command;
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
            TogglePinOnOffCommand command = new TogglePinOnOffCommand(pinNumber, true);
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
            TogglePinDirectionCommand command = new TogglePinDirectionCommand(pinNumber, true);
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
            TogglePinInitializationCommand command = new TogglePinInitializationCommand(pinNumber, true);
            CommandCenter.initializeCommand.put(pinNumber, command);
        }

        return CommandCenter.initializeCommand.get(pinNumber);
    }

    ////////////////////////// Save Configuration //////////////////////////
    private static Command saveConfigurationCommand;
    public static Command getSaveConfigurationCommand(Configuration configuration, boolean reload)
    {
        if(reload || CommandCenter.saveConfigurationCommand == null)
            CommandCenter.saveConfigurationCommand = new SaveConfigurationCommand(configuration, true);

        return CommandCenter.saveConfigurationCommand;
    }

    ////////////////////////// Save Default Pins //////////////////////////
    private static Command saveDefaultPinsCommand;
    public static Command getSaveDefaultPinsCommand(ModelsList modelsList, boolean reload)
    {
        if(reload || CommandCenter.saveDefaultPinsCommand == null)
            CommandCenter.saveDefaultPinsCommand = new SaveDefaultPinsCommand(modelsList, true);

        return CommandCenter.saveDefaultPinsCommand;
    }
}
