package com.github.alejojperez.pi_gpio_dashboard.commands_center.commands;

import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.value.ObservableValue;

import java.util.function.Supplier;

public abstract class AbstractCommand extends DelegateCommand implements Command
{
    public AbstractCommand(Supplier<Action> actionSupplier)
    {
        super(actionSupplier);
    }

    public AbstractCommand(Supplier<Action> actionSupplier, boolean inBackground)
    {
        super(actionSupplier, inBackground);
    }

    public AbstractCommand(Supplier<Action> actionSupplier, ObservableValue<Boolean> executableObservable)
    {
        super(actionSupplier, executableObservable);
    }

    public AbstractCommand(Supplier<Action> actionSupplier, ObservableValue<Boolean> executableObservable, boolean inBackground)
    {
        super(actionSupplier, executableObservable, inBackground);
    }
}
