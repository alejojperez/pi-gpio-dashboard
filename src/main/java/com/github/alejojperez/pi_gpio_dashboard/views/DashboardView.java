package com.github.alejojperez.pi_gpio_dashboard.views;

import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio.core.implementations.Pin;
import com.github.alejojperez.pi_gpio_dashboard.commands_center.CommandCenter;
import com.github.alejojperez.pi_gpio_dashboard.message_center.Manager;
import com.github.alejojperez.pi_gpio_dashboard.view_models.DashboardViewModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardView implements FxmlView<DashboardViewModel>, Initializable
{
    @InjectViewModel
    private DashboardViewModel viewModel;

    @FXML
    private TableView tvPins;
    @FXML
    private TableColumn<Map.Entry<Integer, IPin>, String> tcName;
    @FXML
    private TableColumn<Map.Entry<Integer, IPin>, String> tcGPIONumber;
    @FXML
    private TableColumn<Map.Entry<Integer, IPin>, String> tcPinNumber;
    @FXML
    private TableColumn<Map.Entry<Integer, IPin>, String> tcInitialized;
    @FXML
    private TableColumn<Map.Entry<Integer, IPin>, String> tcDirection;
    @FXML
    private TableColumn<Map.Entry<Integer, IPin>, String> tcOnOff;
    @FXML
    private Button btnInitialize;
    @FXML
    private Button btnDirection;
    @FXML
    private Button btnOnOff;

    public void initialize(URL location, ResourceBundle resources)
    {
        // View model
        this.initializeViewModel();
        this.bindToViewModel();

        // View
        this.initializeTableView();
        this.initializeButtons();
    }

    private void bindToViewModel()
    {
        this.viewModel.getGPIOController().getPins().addListener((MapChangeListener<Integer, IPin>) change -> refreshPins());
    }

    private void initializeButtons()
    {
        this.btnInitialize.disableProperty().bind(this.tvPins.getSelectionModel().selectedItemProperty().isNull());

        this.tvPins.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null)
            {
                this.btnDirection.setDisable(true);
                this.btnOnOff.setDisable(true);
                return;
            }

            IPin pin = ((Map.Entry<Integer, IPin>) newValue).getValue();

            if(!pin.isInitialized())
            {
                this.btnDirection.setDisable(true);
                this.btnOnOff.setDisable(true);
                return;
            }

            this.btnDirection.setDisable(false);
            this.btnOnOff.setDisable(false);
        });
    }

    private void initializeTableView()
    {
        /**
         * Table Column Name
         */
        this.tcName.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Integer, IPin>, String> p) -> new SimpleStringProperty(p.getValue().getValue().getAlias())
        );

        /**
         * Table Column GPIO Number
         */
        this.tcGPIONumber.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Integer, IPin>, String> p) -> new SimpleStringProperty( Integer.toString(p.getValue().getValue().getGPIOPin()) )
        );
        
        /**
         * Table Column Pin Number
         */
        this.tcPinNumber.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Integer, IPin>, String> p) -> new SimpleStringProperty( Integer.toString(p.getValue().getValue().getPin()) )
        );

        /**
         * Table Column Initialized
         */
        this.tcInitialized.setCellFactory(
                new Callback<TableColumn<Map.Entry<Integer, IPin>, String>, TableCell<Map.Entry<Integer, IPin>, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Map.Entry<Integer, IPin>, String> param )
                    {
                        return new TableCell<Map.Entry<Integer, IPin>, String>()
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

                            private HBox generateContent()
                            {
                                Map.Entry<Integer, IPin> record = this.getTableView().getItems().get(this.getIndex());
                                IPin pin = record.getValue();
                                Integer pinKey = record.getKey();

                                HBox hBox = new HBox();

                                /**
                                 * Initialized Icon
                                 */
                                String color =  pin.isInitialized() ? "1b9957" : "d9493e";
                                FontAwesomeIcon initializedIcon = pin.isInitialized() ? FontAwesomeIcon.THUMBS_UP : FontAwesomeIcon.THUMBS_DOWN;
                                FontAwesomeIconView initializedIconView = new FontAwesomeIconView(initializedIcon);
                                initializedIconView.setGlyphStyle("-fx-fill: #"+color);

                                /**
                                 * Add all components
                                 */
                                hBox.setSpacing(5);
                                hBox.getChildren().addAll(initializedIconView);
                                hBox.setAlignment(Pos.CENTER);

                                return hBox;
                            }
                        };
                    }
                }
        );

        /**
         * Table Column Direction
         */
        this.tcDirection.setCellFactory(
                new Callback<TableColumn<Map.Entry<Integer, IPin>, String>, TableCell<Map.Entry<Integer, IPin>, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Map.Entry<Integer, IPin>, String> param )
                    {
                        return new TableCell<Map.Entry<Integer, IPin>, String>()
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

                            private HBox generateContent()
                            {
                                Map.Entry<Integer, IPin> record = this.getTableView().getItems().get(this.getIndex());
                                IPin pin = record.getValue();
                                Integer pinKey = record.getKey();

                                HBox hBox = new HBox();

                                if(pin.isInitialized()) {
                                    /**
                                     * Direction Icon
                                     */
                                    String color = pin.getDirection().equals(Pin.GPIO_IN) ? "1b9957" : "d9493e";
                                    FontAwesomeIcon directionIcon = pin.getDirection().equals(Pin.GPIO_IN) ? FontAwesomeIcon.SIGN_IN : FontAwesomeIcon.SIGN_OUT;
                                    FontAwesomeIconView directionIconView = new FontAwesomeIconView(directionIcon);
                                    directionIconView.setGlyphStyle("-fx-fill: #" + color);
                                    hBox.getChildren().addAll(directionIconView);
                                } else {
                                    Label text = new Label("N/A");
                                    text.getStyleClass().addAll("label-css", "label-default");
                                    hBox.getChildren().addAll(text);
                                }

                                /**
                                 * Add all components
                                 */
                                hBox.setSpacing(5);
                                hBox.setAlignment(Pos.CENTER);

                                return hBox;
                            }
                        };
                    }
                }
        );

        /**
         * Table Column OnOff
         */
        this.tcOnOff.setCellFactory(
                new Callback<TableColumn<Map.Entry<Integer, IPin>, String>, TableCell<Map.Entry<Integer, IPin>, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Map.Entry<Integer, IPin>, String> param )
                    {
                        return new TableCell<Map.Entry<Integer, IPin>, String>()
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

                            private HBox generateContent()
                            {
                                Map.Entry<Integer, IPin> record = this.getTableView().getItems().get(this.getIndex());
                                IPin pin = record.getValue();
                                Integer pinKey = record.getKey();

                                HBox hBox = new HBox();

                                if(pin.isInitialized()) {
                                    /**
                                     * Direction Icon
                                     */
                                    String color = pin.getValue().equals(Pin.GPIO_ON) ? "1b9957" : "d9493e";
                                    FontAwesomeIconView onOffIconView = new FontAwesomeIconView(FontAwesomeIcon.POWER_OFF);
                                    onOffIconView.setGlyphStyle("-fx-fill: #" + color);

                                    hBox.getChildren().addAll(onOffIconView);
                                } else {
                                    Label text = new Label("N/A");
                                    text.getStyleClass().addAll("label-css", "label-default");
                                    hBox.getChildren().addAll(text);
                                }

                                /**
                                 * Add all components
                                 */
                                hBox.setSpacing(5);
                                hBox.setAlignment(Pos.CENTER);

                                return hBox;
                            }
                        };
                    }
                }
        );

        this.tvPins.setItems(FXCollections.observableArrayList(this.viewModel.getGPIOController().getPins().entrySet()));
    }

    private void initializeViewModel()
    {
        this.viewModel.getModelProperty().setValue("2 and 3");
        this.viewModel.loadPinsIntoController();
    }

    @FXML
    private void closeWindow(ActionEvent actionEvent)
    {
        ((Stage) ( (Button)actionEvent.getSource() ).getScene().getWindow() ).close();
    }

    @FXML
    private void showDefaultPinsWindow()
    {
        Stage window = new Stage();
        window.setTitle("GPIO Pins: Default Pins");
        window.initModality(Modality.APPLICATION_MODAL);

        window.setOnHidden(event -> {
            this.initializeViewModel();
            this.initializeTableView();
        });

        ViewTuple viewTuple = FluentViewLoader.fxmlView(DefaultPinsView.class).load();
        Parent root = viewTuple.getView();

        window.setScene(new Scene(root));
        window.show();
    }

    @FXML
    private void showConfigurationWindow()
    {
        Stage window = new Stage();
        window.setTitle("GPIO Pins: Configuration");
        window.initModality(Modality.APPLICATION_MODAL);

        window.setOnHidden(event -> this.initializeViewModel());

        ViewTuple viewTuple = FluentViewLoader.fxmlView(ConfigurationView.class).load();
        Parent root = viewTuple.getView();

        window.setScene(new Scene(root));
        window.show();
    }

    @FXML
    private void toggleDirectionPin()
    {
        if(this.tvPins.getSelectionModel().selectedItemProperty().isNull().get())
        {
            Manager.error("Error: Direction", "Pin's direction cannot be changed. Select a pin first.");
            return;
        }

        Integer pinNumber = ((Map.Entry<Integer, IPin>)this.tvPins.getSelectionModel().selectedItemProperty().getValue()).getValue().getGPIOPin();

        CommandCenter.getDirectionCommand(pinNumber).execute();
    }

    @FXML
    private void toggleInitializePin()
    {
        if(this.tvPins.getSelectionModel().selectedItemProperty().isNull().get())
        {
            Manager.error("Error: Initialize", "Pin's initialization status cannot be changed. Select a pin first.");
            return;
        }

        Integer pinNumber = ((Map.Entry<Integer, IPin>)this.tvPins.getSelectionModel().selectedItemProperty().getValue()).getValue().getGPIOPin();

        CommandCenter.getInitializeCommand(pinNumber).execute();
    }

    @FXML
    private void toggleOnOffPin()
    {
        if(this.tvPins.getSelectionModel().selectedItemProperty().isNull().get())
        {
            Manager.error("Error: ON/OFF", "Pin's ON/OFF status cannot be changed. Select a pin first.");
            return;
        }

        Integer pinNumber = ((Map.Entry<Integer, IPin>)this.tvPins.getSelectionModel().selectedItemProperty().getValue()).getValue().getGPIOPin();

        CommandCenter.getOnOffCommand(pinNumber).execute();
    }

    @FXML
    private void refreshPins()
    {
        this.tvPins.refresh();
    }
}
