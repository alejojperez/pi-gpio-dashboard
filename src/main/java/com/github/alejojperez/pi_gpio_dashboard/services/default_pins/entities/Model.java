package com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Model
{
    String description;
    PinsList pinsList;

    public String getDescription()
    {
        return description;
    }

    @XmlAttribute
    public void setDescription(String description)
    {
        this.description = description;
    }

    public PinsList getPinsList()
    {
        return pinsList;
    }

    @XmlElement(name = "pins")
    public void setPinsList(PinsList pinsList)
    {
        this.pinsList = pinsList;
    }
}
