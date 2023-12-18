package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import marketplace.IMarketplace;
import marketplace.crawl.MarketplaceType;


import marketplace.model.Collection;
import marketplace.model.Trending;

public class CollectionController implements Initializable {

	@FXML
	private TableView<Collection> table;

	@FXML
	private TableColumn<Collection, Integer> numberCol;

	@FXML
	private TableColumn<Collection, String> logoCol;

	@FXML
	private TableColumn<Collection, String> nameCol;

	@FXML
	private TableColumn<Collection, Double> volumeCol;

	@FXML
	private TableColumn<Collection, Double> volumeChangeCol;

	@FXML
	private TableColumn<Collection, Double> floorPriceCol;

	@FXML
	private TableColumn<Collection, Double> floorPriceChangeCol;

	@FXML
	private TableColumn<Collection, Integer> itemsCol;

	@FXML
	private TableColumn<Collection, Integer> ownersCol;

	@FXML
	private ComboBox<String> marketComboBox;

	@FXML
	private ComboBox<String> chainComboBox;

	@FXML
	private ComboBox<String> periodComboBox;

	@FXML
	private Label labelTimeCrawl;
	
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private ObservableList<Collection> collectionList;
	ArrayList<Collection> list = new ArrayList<Collection>();

	ObservableList<String> listMarket = FXCollections.observableArrayList(MarketplaceType.getListMarktplaceName());
	ObservableList<String> listChain = FXCollections.observableArrayList();
	ObservableList<String> listPeriod = FXCollections.observableArrayList();
	
	IMarketplace m;
	String currency;
	
	public void marketComboBoxChanged(ActionEvent e) {
			listChain.clear();
			listPeriod.clear();
			
			listChain.setAll(MarketplaceType.valueOf(marketComboBox.getValue()).getListChains());
			listPeriod.setAll(MarketplaceType.valueOf(marketComboBox.getValue()).getListPeriods());
	}
	
	public void createIMarketplace(IMarketplace iMarketplace) {
		m = iMarketplace;
	}
	
	public void displayChanged(ActionEvent e) {
		if (marketComboBox.getValue() != null && chainComboBox.getValue() != null && periodComboBox.getValue() != null) {
			Trending trend = null;
			
			try {
				trend = m.getTrending(
						MarketplaceType.valueOf(marketComboBox.getValue()),
						chainComboBox.getValue(), 
						periodComboBox.getValue());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(table.getItems() != null) {
				table.getItems().clear();
		        collectionList.clear();
		        list.clear();
			}
			for (Collection c : trend.getData()) {
				list.add(c);
			}
			currency = trend.getCurrency();
			
			collectionList = FXCollections.observableArrayList(list);
			table.setItems(collectionList);
			
			System.out.println(marketComboBox.getValue() + " - " + chainComboBox.getValue() + " - " + periodComboBox.getValue());
			System.out.println(collectionList);
		} else {
			System.out.println("nullll");
		}
	}
	
	public void switchToSceneBlogAndTwitter(ActionEvent event) throws IOException {
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/application/BlogAndTwitter.fxml"));
		  root = loader.load();
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  scene.getStylesheets().add(getClass().getResource("/ui/application/BlogAndTwitter.css").toExternalForm());
		  stage.setTitle("Hastag");
		  stage.setScene(scene);
		  stage.show();
	}
	
	public void switchToHome(ActionEvent event) throws IOException {
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("Loading.fxml"));
		  root = loader.load();
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		marketComboBox.setItems(listMarket);
		chainComboBox.setItems(listChain);
		periodComboBox.setItems(listPeriod);

		numberCol.setCellValueFactory(new Callback<CellDataFeatures<Collection, Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Collection, Integer> c) {
				int rowIndex = c.getValue() != null ? table.getItems().indexOf(c.getValue()) + 1 : 0;
				return new ReadOnlyObjectWrapper<>(rowIndex);
			}
		});
		numberCol.setCellFactory(new Callback<TableColumn<Collection, Integer>, TableCell<Collection, Integer>>() {
			@Override
			public TableCell<Collection, Integer> call(TableColumn<Collection, Integer> param) {
				return new TableCell<Collection, Integer>() {
					@Override
					protected void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null && item != null) {
							setText(String.valueOf(this.getTableRow().getIndex() + 1));
						} else {
							setText("");
						}
					}
				};
			}
		});
		numberCol.setSortable(false);

		logoCol.setCellValueFactory(new PropertyValueFactory<Collection, String>("logo"));
		logoCol.setCellFactory(param -> new TableCell<Collection, String>() {
			private final ImageView imageView = new ImageView();

			@Override
			protected void updateItem(String logoUrl, boolean empty) {
				super.updateItem(logoUrl, empty);

				if (empty || logoUrl == null || logoUrl.isEmpty()) {
					setGraphic(null);
				} else {
					Image image = new Image(logoUrl, true);
					imageView.setImage(image);
					imageView.setFitWidth(75);
					imageView.setFitHeight(75);
					setGraphic(imageView);
				}
			}
		});

		nameCol.setCellValueFactory(new PropertyValueFactory<Collection, String>("name"));
		volumeCol.setCellValueFactory(new PropertyValueFactory<Collection, Double>("volume"));
		volumeCol.setCellFactory(column -> {
		    return new TableCell<Collection, Double>() {
		        @Override
		        protected void updateItem(Double item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item != null && !empty) {
		                setText(String.format("%.3f", item) + " " + currency);
		            } else {
		                setText("");
		            }
		        }
		    };
		});
		volumeChangeCol.setCellValueFactory(new PropertyValueFactory<Collection, Double>("volumeChange"));
		volumeChangeCol.setCellFactory(column -> {
		    return new TableCell<Collection, Double>() {
		        @Override
		        protected void updateItem(Double item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item != null && !empty) {
		                setText(String.format("%.3f", item) + " " + currency);
		            } else {
		                setText("");
		            }
		        }
		    };
		});
		floorPriceCol.setCellValueFactory(new PropertyValueFactory<Collection, Double>("floorPrice"));
		floorPriceCol.setCellFactory(column -> {
		    return new TableCell<Collection, Double>() {
		        @Override
		        protected void updateItem(Double item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item != null && !empty) {
		                setText(String.format("%.3f", item) + " " + currency);
		            } else {
		                setText("");
		            }
		        }
		    };
		});
		floorPriceChangeCol.setCellValueFactory(new PropertyValueFactory<Collection, Double>("floorPriceChange"));
		floorPriceChangeCol.setCellFactory(column -> {
		    return new TableCell<Collection, Double>() {
		        @Override
		        protected void updateItem(Double item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item != null && !empty) {
		                setText(String.format("%.3f", item) + " " + currency);
		            } else {
		                setText("");
		            }
		        }
		    };
		});
		itemsCol.setCellValueFactory(new PropertyValueFactory<Collection, Integer>("items"));
		ownersCol.setCellValueFactory(new PropertyValueFactory<Collection, Integer>("owners"));

		table.setItems(collectionList);

	}

}
