package com.github.alejojperez.pi_gpio_dashboard.view_models;

import com.alejojperez.pi_gpio.core.Utils;
import com.alejojperez.pi_gpio.core.contracts.IFileLogger;
import com.alejojperez.pi_gpio.core.contracts.IGPIOController;
import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio.core.implementations.FileLogger;
import com.alejojperez.pi_gpio.core.implementations.FolderWatcher;
import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import com.alejojperez.pi_gpio.core.implementations.Pin;
import de.saxsys.mvvmfx.ViewModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DashboardViewModel implements ViewModel
{
    /**
     * The GPIO controller
     */
    private IGPIOController gpioController;

    /**
     * The default pins file path
     */
    private String defaultPinsFileLocation = "./target/classes/com/github/alejojperez/pi_gpio_dashboard/default-pins.xml";

    /**
     * Constructor
     */
    public DashboardViewModel()
    {
        this.initializeGPIOController();
    }

    /**
     * Get the GPIO controller instance
     *
     * @return the GPIO controller
     */
    public IGPIOController getGPIOController()
    {
        if(this.gpioController == null)
            this.gpioController = GPIOController.getInstance();

        return gpioController;
    }

    /**
     * Setup the GPIO controller
     */
    private void initializeGPIOController()
    {
        // Set the default configuration file location
        Utils.configPath = "./target/classes/com/github/alejojperez/pi_gpio_dashboard/configuration.xml";

        // Tell the folder watcher to log events
        FolderWatcher.log = true;

        // Register logger to the GPIO controller
        IFileLogger logger = new FileLogger();
        this.getGPIOController().registerLogger(logger);
    }

    /**
     * Load all the pins into the GPIO controller
     *
     * @param model the GPIO version model
     */
    public void loadPinsIntoController(String model) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(this.defaultPinsFileLocation);
        doc.normalizeDocument();

        NodeList nList = doc.getElementsByTagName("model");
        Element xmlModel = null;

        for (int i = 0; i < nList.getLength(); i++)
        {
            Element tmp = (Element) nList.item(i);

            if (tmp.hasAttribute("description") && tmp.getAttribute("description").equals(model)) {
                xmlModel = tmp;
                break;
            }
        }

        if (xmlModel == null)
            throw new IllegalArgumentException("The model [" + model + "] is not a defined model in the default pins file. Consider updating or changing the default pins file.");

        NodeList pinsList = xmlModel.getElementsByTagName("pin");

        if(pinsList.getLength() == 0)
            throw new Exception("The model ["+model+"] does not have any pin defined.");

        for (int i = 0; i < pinsList.getLength(); i++)
        {
            Element xmlPin = (Element) pinsList.item(i);

            IPin pin = new Pin(Integer.parseInt(xmlPin.getAttribute("number")));
            pin
                .setAlias(xmlPin.getAttribute("alias"))
                .setEditable(Boolean.parseBoolean(xmlPin.getAttribute("editable")))
                .setFiveVolts(Boolean.parseBoolean(xmlPin.getAttribute("five-volts")))
                .setThreeVolts(Boolean.parseBoolean(xmlPin.getAttribute("three-volts")))
                .setGround(Boolean.parseBoolean(xmlPin.getAttribute("ground")));

            this.getGPIOController().addPin(pin);
        }
    }
}
