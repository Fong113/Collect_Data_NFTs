//package ui;
//
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.GridPane;
//import javafx.stage.Stage;
//import marketplace.IMarketplace;
//import marketplace.handle.CollectionFilter;
//import marketplace.handle.Handler;
//
//import java.util.Set;
//
//public class NFTApp extends Application {
//
//    private final IMarketplace marketplaceHandler = new Handler();
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("NFT App");
//
//        GridPane grid = new GridPane();
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(20, 20, 20, 20));
//
//        Label nameLabel = new Label("Collection Name:");
//        TextField nameField = new TextField();
//
//        Button searchButton = new Button("Search");
//
//        TableView<CollectionFilter> tableView = new TableView<>();
//        TableColumn<CollectionFilter, String> marketplaceColumn = new TableColumn<>("Marketplace");
//        TableColumn<CollectionFilter, String> idColumn = new TableColumn<>("Id");
//        TableColumn<CollectionFilter, String> logoColumn = new TableColumn<>("Logo");
//        TableColumn<CollectionFilter, String> nameColumn = new TableColumn<>("Name");
//        TableColumn<CollectionFilter, Integer> ownersColumn = new TableColumn<>("Owners");
//        TableColumn<CollectionFilter, Integer> itemsColumn = new TableColumn<>("Items");
//        TableColumn<CollectionFilter, Double> volumeColumn = new TableColumn<>("Volume");
//        TableColumn<CollectionFilter, Double> volumeChangeColumn = new TableColumn<>("Volume Change");
//        TableColumn<CollectionFilter, Double> floorPriceColumn = new TableColumn<>("Floor Price");
//        TableColumn<CollectionFilter, Double> floorPriceChangeColumn = new TableColumn<>("Floor Price Change");
//        TableColumn<CollectionFilter, String> currencyColumn = new TableColumn<>("Currency");
//        TableColumn<CollectionFilter, String> chainColumn = new TableColumn<>("Chain");
//        TableColumn<CollectionFilter, String> periodColumn = new TableColumn<>("Period");
//
//        tableView.getColumns().addAll(marketplaceColumn, idColumn, logoColumn, nameColumn, ownersColumn, itemsColumn,
//                volumeColumn, volumeChangeColumn, floorPriceColumn, floorPriceChangeColumn, currencyColumn, chainColumn, periodColumn);
//
//        searchButton.setOnAction(event -> {
//            String collectionName = nameField.getText();
//            if (!collectionName.isEmpty()) {
//                Set<CollectionFilter> collectionFilters = marketplaceHandler.getCollectionList(collectionName);
//
//                ObservableList<CollectionFilter> data = FXCollections.observableArrayList(collectionFilters);
//                tableView.setItems(data);
//            } else {
//                showAlert("!!!!!!!!!Sai roi", "Hay nhap collection name dii");
//            }
//        });
//
//        marketplaceColumn.setCellValueFactory(new PropertyValueFactory<>("marketplaceName"));
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        logoColumn.setCellValueFactory(new PropertyValueFactory<>("logo"));
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        ownersColumn.setCellValueFactory(new PropertyValueFactory<>("owners"));
//        itemsColumn.setCellValueFactory(new PropertyValueFactory<>("items"));
//        volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
//        volumeChangeColumn.setCellValueFactory(new PropertyValueFactory<>("volumeChange"));
//        floorPriceColumn.setCellValueFactory(new PropertyValueFactory<>("floorPrice"));
//        floorPriceChangeColumn.setCellValueFactory(new PropertyValueFactory<>("floorPriceChange"));
//        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
//        chainColumn.setCellValueFactory(new PropertyValueFactory<>("chain"));
//        periodColumn.setCellValueFactory(new PropertyValueFactory<>("period"));
//
//        GridPane.setConstraints(nameLabel, 0, 1);
//        GridPane.setConstraints(nameField, 1, 1);
//        GridPane.setConstraints(searchButton, 1, 2);
//        GridPane.setConstraints(tableView, 0, 3, 2, 1);
//
//        grid.getChildren().addAll(nameLabel, nameField, searchButton, tableView);
//
//        Scene scene = new Scene(grid, 1000, 600);
//        primaryStage.setScene(scene);
//
//        primaryStage.show();
//    }
//
//    private void showAlert(String title, String content) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle(title);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
//    
//    
//    
//}

package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NFTApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("NFTs APP");
		Parent root = FXMLLoader.load(getClass().getResource("Collection.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
  }
}

