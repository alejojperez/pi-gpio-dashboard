package com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class PinsList
{
    List<Pin> pins;

    public List<Pin> getPins()
    {
        return pins;
    }

    @XmlElement(name = "pin")
    public void setPins(List<Pin> pins)
    {
        this.pins = pins;
    }
}
