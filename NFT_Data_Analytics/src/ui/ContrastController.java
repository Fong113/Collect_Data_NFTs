package ui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Callback;
import marketplace.model.CollectionFilter;
import marketplace.handler.MarketplaceHandler;
import javafx.scene.image.Image;

import java.util.Set;

public class ContrastController {

    @FXML
    private TableView<CollectionFilter> tableView;

    @FXML
    private TableColumn<CollectionFilter, Integer> columnNumber;

    @FXML
    private TableColumn<CollectionFilter, String> columnLogo;

    @FXML
    private TableColumn<CollectionFilter, String> columnName;

    @FXML
    private TableColumn<CollectionFilter, Double> columnVolume;

    @FXML
    private TableColumn<CollectionFilter, Double> columnVolumeChange;

    @FXML
    private TableColumn<CollectionFilter, Double> columnFloorPrice;

    @FXML
    private TableColumn<CollectionFilter, Double> columnFloorPriceChange;
    
    @FXML
    private TableColumn<CollectionFilter, Integer> columnItems;
    
    @FXML
    private TableColumn<CollectionFilter, Integer> columnOwners;

    @FXML
    private TableColumn<CollectionFilter, String> columnCurrency;

    @FXML
    private TableColumn<CollectionFilter, String> columnChain;

    @FXML
    private TableColumn<CollectionFilter, String> columnPeriod;

    @FXML
    private TableColumn<CollectionFilter, String> columnMarketplaceName;

    @FXML
    private TextField searchTextField; 

    @FXML
    private Label noResultsLabel;

    @FXML
    private Button searchButton;
    
    private MarketplaceHandler handler = new MarketplaceHandler();
//    private ObservableList<CollectionFilter> collectionList;
    Set<CollectionFilter> collectionList;
    

    public void initialize() {
        // Set up cell value factories for each column using PropertyValueFactory
    	
    	columnNumber.setCellValueFactory(new Callback<CellDataFeatures<CollectionFilter, Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<CollectionFilter, Integer> c) {
				int rowIndex = c.getValue() != null ? tableView.getItems().indexOf(c.getValue()) + 1 : 0;
				return new ReadOnlyObjectWrapper<>(rowIndex);
			}
		});
    	columnNumber.setCellFactory(new Callback<TableColumn<CollectionFilter, Integer>, TableCell<CollectionFilter, Integer>>() {
			@Override
			public TableCell<CollectionFilter, Integer> call(TableColumn<CollectionFilter, Integer> param) {
				return new TableCell<CollectionFilter, Integer>() {
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
    	columnNumber.setSortable(false);
    	
    	
//    	columnLogo.setCellValueFactory(new PropertyValueFactory<CollectionFilter, String>("logo"));
//		columnLogo.setCellFactory(param -> new TableCell<CollectionFilter, String>() {
//			private final ImageView imageView = new ImageView();
//
//			@Override
//			protected void updateItem(String logoUrl, boolean empty) {
//				super.updateItem(logoUrl, empty);
//
//				if (empty || logoUrl == null || logoUrl.isEmpty()) {
//					setGraphic(null);
//				} else {
//					Image image = new Image(logoUrl, true);
//					System.out.println(logoUrl);
//					imageView.setImage(image);
//					imageView.setFitWidth(75);
//					imageView.setFitHeight(75);
//					setGraphic(imageView);
//				}
//			}
//		});
    	columnLogo.setCellValueFactory(new PropertyValueFactory<CollectionFilter, String>("logo"));
    	columnLogo.setCellFactory(param -> new TableCell<CollectionFilter, String>() {
    		private final ImageView imageView = new ImageView();
    	    private final MediaView mediaView = new MediaView();
    	    private String lastUrl = "";

    	    @Override
    	    protected void updateItem(String logoUrl, boolean empty) {
    	        super.updateItem(logoUrl, empty);

    	        if (empty || logoUrl == null || logoUrl.isEmpty()) {
    	            setGraphic(null);
    	        } else {
    	        	if (isImage(logoUrl)) {
    	                if (!logoUrl.equals(lastUrl)) {
    	                    Image image = new Image(logoUrl, true);
    	                    System.out.println(logoUrl);
    	                    imageView.setImage(image);
    	                    imageView.setFitWidth(75);
    	                    imageView.setFitHeight(75);
    	                    setGraphic(imageView);
    	                }
    	            } else if (isVideo(logoUrl)) {
    	                if (!logoUrl.equals(lastUrl)) {
    	                    Media media = new Media(logoUrl);
    	                    MediaPlayer mediaPlayer = new MediaPlayer(media);
    	                    mediaView.setMediaPlayer(mediaPlayer);
    	                    mediaView.setFitWidth(75);
    	                    mediaView.setFitHeight(75);
    	                    setGraphic(mediaView);
    	                    mediaPlayer.play();
    	                }
    	            } else {
    	                System.out.println("Unsupported media type");
    	                setGraphic(null);
    	            }
    	        	lastUrl = logoUrl;
    	        }
    	    }
    	    private boolean isVideo(String url) {
    	        return url.toLowerCase().endsWith(".mp4");
    	    }

    	    private boolean isImage(String url) {
    	        return url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".jpeg") || url.toLowerCase().endsWith(".png");
    	    }
    	});

    	
        columnName.setCellValueFactory(new PropertyValueFactory<CollectionFilter, String>("name"));
        
        columnVolume.setCellValueFactory(new PropertyValueFactory<CollectionFilter, Double>("volume"));
        columnVolume.setCellFactory(column -> {
		    return new TableCell<CollectionFilter, Double>() {
		        @Override
		        protected void updateItem(Double item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item != null && !empty) {
		                setText(String.format("%.3f", item));
		            } else {
		                setText("");
		            }
		        }
		    };
		});
        
        columnVolumeChange.setCellValueFactory(new PropertyValueFactory<CollectionFilter, Double>("volumeChange"));
        columnVolumeChange.setCellFactory(column -> {
		    return new TableCell<CollectionFilter, Double>() {
		        @Override
		        protected void updateItem(Double item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item != null && !empty) {
		                setText(String.format("%.3f", item));
		            } else {
		                setText("");
		            }
		        }
		    };
		});
        
        columnFloorPrice.setCellValueFactory(new PropertyValueFactory<CollectionFilter, Double>("floorPrice"));
        columnFloorPrice.setCellFactory(column -> {
		    return new TableCell<CollectionFilter, Double>() {
		        @Override
		        protected void updateItem(Double item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item != null && !empty) {
		                setText(String.format("%.4f", item));
		            } else {
		                setText("");
		            }
		        }
		    };
		});
        
        columnFloorPriceChange.setCellValueFactory(new PropertyValueFactory<CollectionFilter, Double>("floorPriceChange"));
        columnFloorPriceChange.setCellFactory(column -> {
		    return new TableCell<CollectionFilter, Double>() {
		        @Override
		        protected void updateItem(Double item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item != null && !empty) {
		                setText(String.format("%.4f", item));
		            } else {
		                setText("");
		            }
		        }
		    };
		});
        
        columnItems.setCellValueFactory(new PropertyValueFactory<CollectionFilter, Integer>("items"));
		columnOwners.setCellValueFactory(new PropertyValueFactory<CollectionFilter, Integer>("owners"));
        columnCurrency.setCellValueFactory(new PropertyValueFactory<CollectionFilter, String>("currency"));
        columnChain.setCellValueFactory(new PropertyValueFactory<CollectionFilter, String>("chain"));
        columnPeriod.setCellValueFactory(new PropertyValueFactory<CollectionFilter, String>("period"));
        
        columnMarketplaceName.setCellValueFactory(new PropertyValueFactory<CollectionFilter, String>("marketplaceName"));

        noResultsLabel.setVisible(false);
    }
    
    public void handleSearchButton(ActionEvent event) {
        try {
            String searchTerm = searchTextField.getText().trim();
            System.out.println(searchTerm);
            collectionList = handler.filterCollectionListByName(searchTerm);
            System.out.println(collectionList);
            updateTableView(collectionList);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public void updateTableView(Set<CollectionFilter> collectionList) {
        ObservableList<CollectionFilter> observableList = FXCollections.observableArrayList(collectionList);
        tableView.setItems(observableList);

        // Show or hide the "No results found" label based on the search results
        noResultsLabel.setVisible(collectionList.isEmpty());
    }
}
