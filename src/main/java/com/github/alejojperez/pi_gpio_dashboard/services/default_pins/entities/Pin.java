package com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities;

import javax.xml.bind.annotation.XmlAttribute;

public class Pin
{
    Integer gpio;
    Integer number;
    String alias;
    boolean editable;
    boolean fiveVolts;
    boolean threeVolts;
    boolean ground;

    public Integer getGpio()
    {
        return gpio;
    }

    @XmlAttribute
    public void setGpio(Integer gpio)
    {
        this.gpio = gpio;
    }

    public Integer getNumber()
    {
        return number;
    }

    @XmlAttribute
    public void setNumber(Integer number)
    {
        this.number = number;
    }

    public String getAlias()
    {
        return alias;
    }

    @XmlAttribute
    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public boolean getEditable()
    {
        return editable;
    }

    @XmlAttribute
    public void setEditable(boolean editable)
    {
        this.editable = editable;
    }

    public boolean getFiveVolts()
    {
        return fiveVolts;
    }

    @XmlAttribute(name = "five-volts")
    public void setFiveVolts(boolean fiveVolts)
    {
        this.fiveVolts = fiveVolts;
    }

    public boolean getThreeVolts()
    {
        return threeVolts;
    }

    @XmlAttribute(name = "three-volts")
    public void setThreeVolts(boolean threeVolts)
    {
        this.threeVolts = threeVolts;
    }

    public boolean getGround()
    {
        return ground;
    }

    @XmlAttribute
    public void setGround(boolean ground)
    {
        this.ground = ground;
    }
}
