package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.image.Image;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import marketplace.IMarketplace;
import marketplace.crawl.CrawlerManager;
import marketplace.crawl.ICrawlerManager;
import marketplace.crawl.exception.CrawlTimeoutException;
import marketplace.crawl.exception.InternetConnectionException;
import marketplace.handler.MarketplaceHandler;

public class LoadingController implements Initializable{
//	@FXML
//	private ProgressBar myProgressBar;
	
	@FXML
    private ImageView gifLoading;
	
	@FXML
	private Label percentLabel;
	
	@FXML
	private Label loadingLabel;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
//    private Timeline timeline;
    
	IMarketplace m = new MarketplaceHandler();

//	public void increaseProgress() {
//		double increment = 0.1;
//        myProgressBar.setProgress(Math.min(1.0, myProgressBar.getProgress() + increment));
//        percentLabel.setText(String.format("%d%%", (int) Math.round(myProgressBar.getProgress() * 100)));
//        if (myProgressBar.getProgress() >= 1.0) {
//            timeline.stop();
//            loadingLabel.setText("Done");
//        }
//	}
	
	public void clickBtnCrawl(ActionEvent event) {
		ICrawlerManager crawlData = new CrawlerManager();

		// startAutoIncrease
//        timeline = new Timeline(
//                new KeyFrame(Duration.seconds(0.5), e -> increaseProgress())
//        );
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
		

		try {
			m.clearData();
			crawlData.crawlAllTrending();
//			Image image = new Image(getClass().getResourceAsStream("/img/done.jpg"));
//	        gifLoading.setImage(image);
			
			loadingLabel.setText("Done");
		} catch (CrawlTimeoutException e) {
			System.out.println(e.getMessage());
		} catch (InternetConnectionException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void switchToMarketplace(ActionEvent event) throws IOException {
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("Collection.fxml"));
		  root = loader.load();
		  CollectionController collectionController  = loader.getController();
		  collectionController.createIMarketplace(m);
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.show();
		 }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//	    myProgressBar.setStyle("-fx-accent: #00FF00;");
	}
}
