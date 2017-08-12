package com.github.alejojperez.pi_gpio_dashboard.services.default_pins;

import com.github.alejojperez.pi_gpio_dashboard.message_center.Manager;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.Model;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.ModelsList;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.PinsList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Repository
{
    /**
     * Loaded xml file
     */
    private static ModelsList modelsList;

    /**
     * The default pins file path
     */
    public static String defaultPinsFileLocation = "./target/classes/com/github/alejojperez/pi_gpio_dashboard/default-pins.xml";

    /**
     * Load the pins file
     */
    public static void load()
    {
        try
        {
            File file = new File(Repository.defaultPinsFileLocation);
            JAXBContext jaxbContext = JAXBContext.newInstance(ModelsList.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Repository.modelsList = (ModelsList) jaxbUnmarshaller.unmarshal(file);

        }
        catch (JAXBException e)
        {
            Manager.error("Default Pins", "We could not load the default pins from the file ["+Repository.defaultPinsFileLocation+"]");
        }
    }

    /**
     * Load models list if it is null, and check that it loaded correctly
     *
     * @return
     */
    public static boolean loadIfNullAndGetSuccessStatus(boolean showError)
    {
        if(Repository.modelsList == null)
            Repository.load();

        boolean success = Repository.modelsList != null;

        if(!success && showError)
            Manager.error("Default Pins File", "The default pins document could not been loaded.");

        return success;
    }

    /**
     * Save the current models list to the xml file
     *
     * @return
     */
    public static boolean saveCurrentModelList()
    {
        if(Repository.modelsList == null)
        {
            Manager.error("Default Pins File", "we could not save the current pins file.");
            return false;
        }

        try {
            File file = new File(Repository.defaultPinsFileLocation);
            JAXBContext jaxbContext = JAXBContext.newInstance(ModelsList.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(modelsList, file);
        } catch (JAXBException e) {
            return false;
        }

        return true;
    }

    //region Queries

    /**
     * Get model by description
     *
     * @return
     */
    public static Model getModel(String description)
    {
        Model response = null;

        for(Model model: Repository.modelsList.getModels())
        {
            if(model.getDescription().equals(description))
            {
                response = model;
                break;
            }
        }

        if(response == null)
            Manager.error("Default Pins File", "The model [" + description + "] is not a defined model in the default pins file.");

        return response;
    }

    /**
     * Get models list
     *
     * @return
     */
    public static ModelsList getModels()
    {
        ModelsList response = null;

        if(Repository.loadIfNullAndGetSuccessStatus(true))
            response = Repository.modelsList;

        return response;
    }

    /**
     * Get all the available pins in the default pins file by model
     *
     * @param description the model description defined on the default pins file
     * @return
     */
    public static PinsList getPinsByModel(String description)
    {
        PinsList response = null;

        if(Repository.loadIfNullAndGetSuccessStatus(true))
        {
            Model model = Repository.getModel(description);

            if(model != null)
                response = model.getPinsList();
        }

        return response;
    }

    //endregion
}
