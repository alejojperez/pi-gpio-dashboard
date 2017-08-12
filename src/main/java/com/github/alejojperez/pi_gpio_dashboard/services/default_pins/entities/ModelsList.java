package com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "models")
public class ModelsList
{
    List<Model> models;

    public List<Model> getModels()
    {
        return this.models;
    }

    @XmlElement(name = "model")
    public void setModels(List<Model> models)
    {
        this.models = models;
    }
}
