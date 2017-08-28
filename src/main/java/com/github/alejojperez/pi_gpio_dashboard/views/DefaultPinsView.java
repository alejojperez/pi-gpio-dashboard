package com.github.alejojperez.pi_gpio_dashboard.views;

import com.github.alejojperez.pi_gpio_dashboard.message_center.Manager;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.Repository;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.Model;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.ModelsList;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.Pin;
import com.github.alejojperez.pi_gpio_dashboard.services.default_pins.entities.PinsList;
import com.github.alejojperez.pi_gpio_dashboard.view_models.DefaultPinsViewModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DefaultPinsView implements FxmlView<DefaultPinsViewModel>, Initializable
{
    @InjectViewModel
    private DefaultPinsViewModel viewModel;

    @FXML
    private ChoiceBox cbModels;
    @FXML
    private TableView tvDefaultPins;
    @FXML
    public TableColumn<Pin, String> tcAlias;
    @FXML
    public TableColumn<Pin, String> tcPinNumber;
    @FXML
    public TableColumn<Pin, String> tcGpio;
    @FXML
    private TableColumn<Pin, String> tcActions;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.initializeModelsChoiceBox();
        this.initializeDefaultPinsTable();
    }

    private void initializeModelsChoiceBox()
    {
        ObservableList options = FXCollections.observableArrayList();

        for(Model m: this.viewModel.getModels().getModels())
            options.add(m.getDescription());

        this.cbModels.setItems(options);
    }

    private void initializeDefaultPinsTable()
    {
        /**
         * Table Column Alias
         */
        this.tcAlias.setCellFactory(
                new Callback<TableColumn<Pin, String>, TableCell<Pin, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Pin, String> param )
                    {
                        return new TableCell<Pin, String>()
                        {
                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                this.setText( null );

                                if ( empty ) {
                                    this.setGraphic( null );
                                } else {
                                    this.setGraphic( this.generateContent() );
                                }
                            }

                            private TextField generateContent()
                            {
                                Pin record = this.getTableView().getItems().get(this.getIndex());
                                TextField textField = new TextField(record.getAlias());

                                textField.textProperty().addListener((observable, oldValue, newValue) -> record.setAlias( newValue));

                                return textField;
                            }
                        };
                    }
                }
        );

        /**
         * Table Column Pin Number
         */
        this.tcPinNumber.setCellFactory(
                new Callback<TableColumn<Pin, String>, TableCell<Pin, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Pin, String> param )
                    {
                        return new TableCell<Pin, String>()
                        {
                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                this.setText( null );

                                if ( empty ) {
                                    this.setGraphic( null );
                                } else {
                                    this.setGraphic( this.generateContent() );
                                }
                            }

                            private TextField generateContent()
                            {
                                Pin record = this.getTableView().getItems().get(this.getIndex());
                                TextField textField = new TextField(record.getNumber().toString());

                                textField.textProperty().addListener((observable, oldValue, newValue) -> record.setNumber( Integer.parseInt(newValue)));

                                return textField;
                            }
                        };
                    }
                }
        );

        /**
         * Table Column GPIO
         */
        this.tcGpio.setCellFactory(
                new Callback<TableColumn<Pin, String>, TableCell<Pin, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Pin, String> param )
                    {
                        return new TableCell<Pin, String>()
                        {
                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                this.setText( null );

                                if ( empty ) {
                                    this.setGraphic( null );
                                } else {
                                    this.setGraphic( this.generateContent() );
                                }
                            }

                            private TextField generateContent()
                            {
                                Pin record = this.getTableView().getItems().get(this.getIndex());
                                TextField textField = new TextField(record.getGpio().toString());

                                textField.textProperty().addListener((observable, oldValue, newValue) -> record.setGpio( Integer.parseInt(newValue)));

                                return textField;
                            }
                        };
                    }
                }
        );

        /**
         * Table Column GPIO
         */
        this.tcActions.setCellFactory(
                new Callback<TableColumn<Pin, String>, TableCell<Pin, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Pin, String> param )
                    {
                        return new TableCell<Pin, String>()
                        {
                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                this.setText( null );

                                if ( empty ) {
                                    this.setGraphic( null );
                                } else {
                                    this.setGraphic( this.generateContent() );
                                }
                            }

                            private Button generateContent()
                            {
                                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                icon.setGlyphStyle("-fx-fill: #fff");

                                Button btn = new Button();
                                btn.setGraphic(icon);
                                btn.getStyleClass().addAll("btn", "btn-danger", "btn-xs");

                                btn.setOnAction(event -> {
                                    Model model = viewModel.getModel(cbModels.getValue().toString());

                                    if(model == null)
                                    {
                                        Manager.error("Default Pins", "The pin can not be deleted because the model ["+cbModels.getValue().toString()+"] is not valid.");
                                        return;
                                    }

                                    model.getPinsList().getPins().remove(this.getIndex());
                                    tvDefaultPins.getItems().remove(this.getIndex());
                                });

                                return btn;
                            }
                        };
                    }
                }
        );

        this.cbModels.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if(oldValue != null && oldValue.equals(newValue)) return;

            if(newValue == null)
            {
                Manager.error("Default Pins", "Select a raspberry pi model.");
                this.tvDefaultPins.setItems(null);
                return;
            }

            Model model = this.viewModel.getModel(newValue.toString());

            if(model == null)
            {
                Manager.error("Default Pins", "The selected model is not valid.");
                this.tvDefaultPins.setItems(null);
                return;
            }

            this.tvDefaultPins.setItems(FXCollections.observableArrayList(model.getPinsList().getPins()));
        });
    }

    @FXML
    private void closeWindow(ActionEvent actionEvent)
    {
        ((Stage) ( (Button)actionEvent.getSource() ).getScene().getWindow() ).close();
    }

    @FXML
    private void save(ActionEvent actionEvent)
    {
        this.viewModel.saveDefaultPins(null);
    }
}
