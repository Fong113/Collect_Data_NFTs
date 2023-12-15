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
import marketplace.crawl.type.MarketplaceType;
import marketplace.handler.MarketplaceHandler;
import marketplace.crawl.CrawlerManager;
import marketplace.crawl.ICrawlerManager;
import marketplace.crawl.binance.BinanceChainType;
import marketplace.crawl.binance.BinancePeriodType;
import marketplace.crawl.exception.CrawlTimeoutException;
import marketplace.crawl.exception.InternetConnectionException;
import marketplace.crawl.niftygateway.NiftygatewayChainType;
import marketplace.crawl.niftygateway.NiftygatewayPeriodType;
import marketplace.crawl.opensea.OpenseaChainType;
import marketplace.crawl.opensea.OpenseaPeriodType;
import marketplace.crawl.rarible.RaribleChainType;
import marketplace.crawl.rarible.RariblePeriodType;
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

	ObservableList<String> listMarket = FXCollections.observableArrayList("Opensea", "Binance", "Rarible",
			"Niftygateway");
	ObservableList<String> listChain = FXCollections.observableArrayList();
	ObservableList<String> listPeriod = FXCollections.observableArrayList();
	
	ArrayList<Collection> list = new ArrayList<Collection>();
	
	String marketValueCurrent;
	String chainValueCurrent;
	String periodValueCurrent;
	String currency;
	public void switchToSceneBlogAndTwitter(ActionEvent event) throws IOException {
		  root = FXMLLoader.load(getClass().getResource("Tag.fxml"));
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
		 }
	
	public void marketComboBoxChanged(ActionEvent e) {
		String marketValue = marketComboBox.getValue();
		if (marketComboBox.getValue() != null) {
			listChain.clear();
			listPeriod.clear();
			if (marketValue.equals("Opensea")) {
//				listChain.setAll("ARBITRUM", "AVALANCHE", "BNB", "BASE", "ETH", "KLAYTN", "OPTIMISM", "POLYGON",
//						"SOLANA", "ZORA");
//				listPeriod.setAll("ONEHOUR", "SIXHOURS", "ONEDAY", "ONEWEEK", "ONEMONTH");
				listChain.setAll("BNB","ETH");
				listPeriod.setAll("ONEDAY", "ONEWEEK", "ONEMONTH");
			} else if (marketValue.equals("Binance")) {
//				listChain.setAll("BNB", "ETH", "BTC");
//				listPeriod.setAll("ONEHOUR", "SIXHOURS", "ONEDAY", "ONEWEEK");
				listChain.setAll("BNB", "ETH");
				listPeriod.setAll("ONEHOUR", "ONEDAY", "ONEWEEK");
			} else if (marketValue.equals("Rarible")) {
//				listChain.setAll("ETH", "POLYGON", "TEZOS", "IMMUTABLEX");
//				listPeriod.setAll("ONEHOUR", "ONEDAY", "ONEWEEK", "ONEMONTH");
				listChain.setAll("ETH", "POLYGON");
				listPeriod.setAll("ONEHOUR", "ONEDAY", "ONEWEEK", "ONEMONTH");
			} else if (marketValue.equals("Niftygateway")) {
				listChain.setAll("ETH", "USD");
				listPeriod.setAll("ONEDAY", "ONEWEEK", "ONEMONTH");
			}

			marketValueCurrent = marketValue;
		}
	}
	public void chainComboBoxChanged(ActionEvent e) {
		if (chainComboBox.getValue() != null) {
			chainValueCurrent = chainComboBox.getValue();
		}
	}
	public void periodComboBoxChanged(ActionEvent e) {
		if (periodComboBox.getValue() != null) {
			periodValueCurrent = periodComboBox.getValue();
		}
	}

	public void clickBtnShow(ActionEvent e) throws Exception {
		if (marketValueCurrent != null && chainValueCurrent != null && periodValueCurrent != null) {
			IMarketplace m = new MarketplaceHandler();
			Trending trend = null;
			if(marketValueCurrent.toUpperCase().equals("OPENSEA")) {
				trend = m.getTrending(
						MarketplaceType.OPENSEA,
						OpenseaChainType.valueOf(chainValueCurrent), 
						OpenseaPeriodType.valueOf(periodValueCurrent));
			} else if(marketValueCurrent.toUpperCase().equals("BINANCE")) {
				trend = m.getTrending(
						MarketplaceType.BINANCE,
						BinanceChainType.valueOf(chainValueCurrent), 
						BinancePeriodType.valueOf(periodValueCurrent));
			} else if(marketValueCurrent.toUpperCase().equals("RARIBLE")) {
				trend = m.getTrending(
						MarketplaceType.RARIBLE,
						RaribleChainType.valueOf(chainValueCurrent), 
						RariblePeriodType.valueOf(periodValueCurrent));
			} else if(marketValueCurrent.toUpperCase().equals("NIFTYGATEWAY")){
				trend = m.getTrending(
						MarketplaceType.NIFTYGATEWAY,
						NiftygatewayChainType.valueOf(chainValueCurrent), 
						NiftygatewayPeriodType.valueOf(periodValueCurrent));
			}
			

			if (table.getItems() != null) {
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
			System.out.println(collectionList);
			System.out.println(marketValueCurrent + " - " + chainValueCurrent + " - " + periodValueCurrent);
		} else {
			System.out.println("nullll");
		}
	}

	public void clickBtnCrawl(ActionEvent event) {
//		m.clearData();
		ICrawlerManager crawlData = new CrawlerManager();
		
        long startTime = System.currentTimeMillis();
		try {
			crawlData.crawlAllTrending();
		} catch (CrawlTimeoutException e) {
			System.out.println(e.getMessage());
		} catch (InternetConnectionException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

		System.out.println(elapsedTime);
//        Date date = new Date(elapsedTime);
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        String formattedElapsedTime = sdf.format(date);
//
//        System.out.println("Elapsed Time: " + formattedElapsedTime);
        
//		LocalDateTime currentTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedTime = currentTime.format(formatter);
//        labelTimeCrawl.setText(formattedTime);
        
//        System.out.println("Crawling data at: " + formattedTime);
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
