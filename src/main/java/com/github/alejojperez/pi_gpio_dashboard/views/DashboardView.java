package com.github.alejojperez.pi_gpio_dashboard.views;

import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio.core.implementations.Pin;
import com.github.alejojperez.pi_gpio_dashboard.Application;
import com.github.alejojperez.pi_gpio_dashboard.message_center.Manager;
import com.github.alejojperez.pi_gpio_dashboard.view_models.DashboardViewModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
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
    private TableColumn<Map.Entry<Integer, IPin>, String> tcPinNumber;
    @FXML
    private TableColumn<Map.Entry<Integer, IPin>, String> tcInitialized;
    @FXML
    private TableColumn<Map.Entry<Integer, IPin>, String> tcDirection;
    @FXML
    private TableColumn<Map.Entry<Integer, IPin>, String> tcOnOff;

    public void initialize(URL location, ResourceBundle resources)
    {
        this.initializeViewModel();
        this.bindToViewModel();
        this.initializeTableView();
    }

    private void bindToViewModel()
    {
        this.viewModel.getGPIOController().getPins().addListener((MapChangeListener<Integer, IPin>) change -> refreshPins());
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
         * Table Column Pin Number
         */
        this.tcPinNumber.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Integer, IPin>, String> p) -> new SimpleStringProperty( Integer.toString(p.getValue().getValue().getPinNumber()) )
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
        try {
            this.viewModel.loadPinsIntoController();
        } catch (Exception e) {
            Manager.error("Load Pins", "Error loading the pins from the default file.");
        }
    }

    private void refreshPins()
    {
        this.tvPins.refresh();
    }
}
