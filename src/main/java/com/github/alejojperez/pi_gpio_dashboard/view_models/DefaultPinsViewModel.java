package com.github.alejojperez.pi_gpio_dashboard.view_models;

import com.github.alejojperez.pi_gpio_dashboard.commands_center.CommandCenter;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.Repository;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.Model;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.ModelsList;
import de.saxsys.mvvmfx.ViewModel;

public class DefaultPinsViewModel implements ViewModel
{
    public ModelsList getModels()
    {
        return Repository.getModels();
    }

    public Model getModel(String model)
    {
        return Repository.getModel(model);
    }

    public void saveDefaultPins(ModelsList modelsList)
    {
        CommandCenter.getSaveDefaultPinsCommand(modelsList, true).execute();
    }
}
