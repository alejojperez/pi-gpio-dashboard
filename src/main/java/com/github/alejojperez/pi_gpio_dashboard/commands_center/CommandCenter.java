package com.github.alejojperez.pi_gpio_dashboard.commands_center;

import com.github.alejojperez.pi_gpio_dashboard.commands_center.commands.TogglePinDirectionCommand;
import com.github.alejojperez.pi_gpio_dashboard.commands_center.commands.TogglePinInitializationCommand;
import com.github.alejojperez.pi_gpio_dashboard.commands_center.commands.TogglePinOnOffCommand;
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
            CommandCenter.onOffCommand.put(pinNumber, command);

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

        TogglePinInitializationCommand command = new TogglePinInitializationCommand(pinNumber, true);
        CommandCenter.onOffCommand.put(pinNumber, command);

        return CommandCenter.initializeCommand.get(pinNumber);
    }
}
