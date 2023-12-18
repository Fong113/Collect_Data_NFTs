package ui.blogandtwitter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import blog_news.Article;
import blog_news.handle.HandleArticleManager;
import blog_news.handle.IArticleManager;
import blog_news.helper.JsonIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import twitter.ITwitter;
import twitter.handle.Tweet;
import twitter.handle.AHandle.TimePeriodType;
import twitter.handle.HandleTwitter;

public class BlogAndTwitterController implements Initializable {
	@FXML
	private TextField searchField;
//	@FXML
//	private Label authorLabel;
	@FXML
	private Button searchButton;
	@FXML
	private TabPane resultPane;
	@FXML
	private Tab blogTab, twitterTab;
	@FXML
	private VBox articlesBlog, articlesTwitter, tagsBox;
	@FXML
	private Pagination blogPagination, tweetPagination;
	@FXML
	private ChoiceBox<String> hourChoice, typeTagChoice;
	@FXML 
	private ScrollPane tagsPane;

	private IArticleManager articleManager = new HandleArticleManager();
    private List<Article> currentArticles = articleManager.getAllArticles();
    
    private ITwitter twitterData = new HandleTwitter();
    private List<Tweet> currentTweets = twitterData.getTweetsAboutNFTs();
    
    private final int itemsPerPage = 5; 
    
    private Stage stage;
	private Scene scene;
	private Parent root;
    
    
    public void switchToHome(ActionEvent event) throws IOException {
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Loading.fxml"));
		  root = loader.load();
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  scene.getStylesheets().add(getClass().getResource("Collection.css").toExternalForm());
		  stage.setScene(scene);
		  stage.show();
	}
	
	public void switchToSceneMarketplace(ActionEvent event) throws IOException {
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/marketplace/Collection.fxml"));
		  root = loader.load();
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  scene.getStylesheets().add(getClass().getResource("/ui/marketplace/Collection.css").toExternalForm());
		  stage.setTitle("Markertplace");
		  stage.setScene(scene);
		  stage.show();
	}
	
	public void switchToSceneConTrast(ActionEvent event) throws IOException {
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/contrast/Contrast.fxml"));
		  root = loader.load();
		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		  scene = new Scene(root);
		  scene.getStylesheets().add(getClass().getResource("/ui/contrast/Contrast.css").toExternalForm());
		  stage.setTitle("Contrast");
		  stage.setScene(scene);
		  stage.show();
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	Type aListType = new TypeToken<List<Article>>() {}.getType();
        JsonIO<Article> jsonIO = new JsonIO<>(aListType);
        currentArticles = jsonIO.loadJson("data/blog_news.json");
        

        setChoiceBox();
        typeTagChoice.getSelectionModel().selectedItemProperty()
        .addListener((v, oldValue, newValue) -> updateDisplayTags());
        
        hourChoice.getSelectionModel().selectedItemProperty()
        		.addListener((v, oldValue, newValue) -> updateDisplayTags());
        
        updateDisplayTags();
    }
    
    public void setChoiceBox() {
    	hourChoice.getItems().addAll("1 day", "1 week", "1 month");
    	hourChoice.setValue("1 day");
    	
    	typeTagChoice.getItems().addAll("Blog", "Twitter");
    	typeTagChoice.setValue("Blog");
    }
   
    public void search(ActionEvent e) throws IOException {
    	String searchText = searchField.getText().toLowerCase();
    	Tab selectedTab = resultPane.getSelectionModel().getSelectedItem();
        
        if (selectedTab == blogTab) {
        	if (!searchText.isEmpty()) {
            	currentArticles = (List<Article>) articleManager.filterArticlesByTags(new String[]{searchText});
            	setupPagination(currentArticles);
            }
        } else if (selectedTab == twitterTab) {
        	if (!searchText.isEmpty()) {
            	currentTweets = twitterData.getTweetsByTag(searchText);
            	setupPagination(currentTweets);
            }
        }
    }

    private void setupPagination(List<?> items) {
        int pageCount = (int) Math.ceil((double) items.size() / itemsPerPage);
        Pagination targetPagination = null;
        
        if (items.get(0) instanceof Article) {
        	targetPagination = blogPagination;
        } else if (items.get(0) instanceof Tweet) {
        	targetPagination = tweetPagination;
        }
        targetPagination.setPageCount(pageCount);
        targetPagination.setPageFactory(pageIndex -> createPage(items, pageIndex));
    }

    private Node createPage(List<?> items, int pageIndex) {
        if (items.isEmpty()) {
            return new ScrollPane();
        }

        VBox targetBox = null;
        if (items.get(0) instanceof Article) {
            articlesBlog.getChildren().clear();
            targetBox = articlesBlog;
        } else if (items.get(0) instanceof Tweet) {
            articlesTwitter.getChildren().clear();
            targetBox = articlesTwitter;
        }

        int start = pageIndex * itemsPerPage;
        int end = Math.min(start + itemsPerPage, items.size());
        for (int i = start; i < end; i++) {
            Object item = items.get(i);
            Node node = (item instanceof Article) ? 
            		createArticleNode((Article) item) : createTweetNode((Tweet) item);
            targetBox.getChildren().add(node);
            if (i < end - 1) {
                targetBox.getChildren().add(new Separator());
            }
   
        }
        return new ScrollPane(targetBox);
    }

    private Node createArticleNode(Article article) {
        VBox articleBox = new VBox(10);
        articleBox.getStyleClass().add("article-box");

        Label titleLabel = new Label(article.getTitle());
        titleLabel.getStyleClass().add("article-title");
        titleLabel.setWrapText(true);

        Label dateLabel = new Label(article.getPublishDate());
        dateLabel.getStyleClass().add("article-date");

        Label contentLabel = new Label(article.getFullContent());
        contentLabel.getStyleClass().add("article-content");
        contentLabel.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
        
        articleBox.getChildren().addAll(titleLabel, contentLabel, dateLabel);
        return articleBox;
    }
    private Node createTweetNode(Tweet tweet) {
        VBox tweetBox = new VBox(10);
        tweetBox.getStyleClass().add("tweet-box");

        Label authorLabel = new Label(tweet.getAuthor());
        authorLabel.getStyleClass().add("tweet-author");

        Label contentLabel = new Label(tweet.getContent());
        contentLabel.getStyleClass().add("tweet-content");
        contentLabel.setWrapText(true);

        tweetBox.getChildren().addAll(authorLabel, contentLabel);
        return tweetBox;
    }
    
    private void updateDisplayTags() {
        String selectedType = typeTagChoice.getValue();
        TimePeriodType timePeriod = getTimePeriodFromChoice(hourChoice.getValue());
        if (selectedType.equals("Twitter")) {
            displayAllTags(tagsBox, timePeriod, "Twitter");
        } else {
            displayAllTags(tagsBox, timePeriod, "Blog");
        }
    }
    private TimePeriodType getTimePeriodFromChoice(String timeChoice) {
        switch (timeChoice) {
        	case "1 day":
        		return TimePeriodType.DAILY;
        	case "1 week":
        		return TimePeriodType.WEEKLY;
        	case "1 month":
        		return TimePeriodType.MONTHLY;
        	default:
        		return TimePeriodType.DAILY;
        }
                	
    }
    
    private void displayAllTags(VBox tagsContainer, TimePeriodType timePeriodType, String typeTag) {
        tagsContainer.getChildren().clear();
        List<String> tags = new ArrayList<>();

        if (typeTag.equals("Twitter")) {
            tags = twitterData.getHotTags(timePeriodType);
        } else if (typeTag.equals("Blog")) {
            switch (timePeriodType) {
                case DAILY:
                    tags = articleManager.findHotTagsForDay();
                    break;
                case WEEKLY:
                    tags = articleManager.findHotTagsForWeek();
                    break;
                case MONTHLY:
                    tags = articleManager.findHotTagsForMonth();
                    break;
                default:
                    tags = new ArrayList<>(articleManager.extractUniqueTags());
                    break;
            }
        }

        for (String tag : tags) {
            Label tagLabel = new Label(tag);
            tagLabel.getStyleClass().add("tag-label");
            tagsContainer.getChildren().add(tagLabel);
            tagsContainer.getChildren().add(new Separator());
        }
    }

}
