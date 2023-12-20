package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

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
import twitter.ITwitter;
import blog_news.crawl.HandleBlognewsCrawler;
import blog_news.crawl.ICrawler;
import twitter.handle.HandleTwitter;
import ui.marketplace.CollectionController;


public class LoadingController implements Initializable{
	@FXML
	private ProgressBar loadingProgressBar;
	
	@FXML
    private ImageView gifLoading;
	
	@FXML
	private Label percentLabel;
	
	@FXML
	private Label loadingLabel;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    private Timeline timeline;
    
	public void increaseProgress() {
		double increment = 0.1;
		loadingProgressBar.setProgress(Math.min(1.0, loadingProgressBar.getProgress() + increment));
        percentLabel.setText(String.format("%d%%", (int) Math.round(loadingProgressBar.getProgress() * 100)));
        if (loadingProgressBar.getProgress() >= 1.0) {
            timeline.stop();
            loadingLabel.setText("Done");
        }
	}
	
	public void clickBtnCrawl(ActionEvent event) {
		ICrawlerManager crawlDataMarket = new CrawlerManager();
		ITwitter crawlDataTwitter = new HandleTwitter();
		ICrawler crawlDataBlogAndNews = new HandleBlognewsCrawler();
		
		// startAutoIncrease
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> increaseProgress())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

//		 try {
//		        // Crawl blog and news data
//		        System.out.println("------------------- Blog and News -------------------");
//		        System.out.println("Crawling blog and news data...");
//		        crawlDataBlogAndNews.crawl();
//		        System.out.println("Blog and news data crawling completed.");
//		        System.out.println("-----------------------------------------------------");
//		    } catch (IOException | RuntimeException e) {
//		        System.out.println("Error while crawling blog and news data:");
//		        e.printStackTrace();
//		    }
//
//		 try {
//		        // Refresh Twitter data
//		        System.out.println("------------------- Twwitter -------------------");
//		        System.out.println("Crawling Twitter data...");
//		        crawlDataTwitter.refreshData();
//		        System.out.println("Twitter data refresh completed.");
//		        System.out.println("------------------------------------------------");
//
//		    } catch (InterruptedException e) {
//		        System.out.println("Error while refreshing Twitter data:");
//		        e.printStackTrace();
//		    }
//
//		 try {
//		        // Clear existing data and crawl market data
//		        System.out.println("------------------- Marketplace -------------------");
//		        System.out.println("Crawling market data...");
//		        crawlDataMarket.crawlAllTrending();
//		        System.out.println("Market data crawling completed.");
//		        System.out.println("---------------------------------------------------");
//		        loadingLabel.setText("Done");
//
//		    } catch (CrawlTimeoutException e) {
//		        System.out.println("Error while crawling market data: " + e.getMessage());
//		    } catch (InternetConnectionException e) {
//		        System.out.println("Internet connection error: " + e.getMessage());
//		    } catch (Exception e) {
//		        System.out.println("Unexpected error: " + e.getMessage());
//		    }
	}
	
	public void switchToMarketplace(ActionEvent event) throws IOException {
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/marketplace/Collection.fxml"));
		  root = loader.load();
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  stage.setScene(scene);
		  stage.setTitle("Marrketplace");
		  stage.show();
		 }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadingProgressBar.setStyle("-fx-accent: rgba(31, 248, 153, 1);");
	}
}
