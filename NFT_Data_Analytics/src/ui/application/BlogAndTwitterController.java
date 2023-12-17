package ui.application;

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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import twitter.ITwitter;
import twitter.handle.Tweet;
import twitter.handle.HandleTwitter;
import twitter.handle.HandleTwitter.TimePeriodType;

public class BlogAndTwitterController implements Initializable {
	@FXML
	private TextField searchField;
	@FXML
	private Label authorLabel;
	@FXML
	private Button searchButton;
	@FXML
	private TabPane resultPane;
	@FXML
	private Tab blogTab;
	@FXML
	private Tab twitterTab;
	@FXML
	private VBox articlesBlog;
	@FXML
	private VBox articlesTwitter;
	@FXML
	private Pagination blogPagination;
	@FXML
	private Pagination tweetPagination;
	@FXML
	private ChoiceBox<String> hourChoice;
	@FXML
	private ChoiceBox<String> typeTagChoice;
	@FXML 
	private ScrollPane tagsPane;
	@FXML
	private VBox tagsBox;

	private IArticleManager articleManager = new HandleArticleManager();
    private List<Article> currentArticles = new ArrayList<Article>();
    
    private ITwitter twitterData = new HandleTwitter();
    private List<Tweet> currentTweets = twitterData.getTweetsAboutNFTs();
    private List<String> currentTwitterTags = new ArrayList<String>();
    
    private final int itemsPerPage = 5; 

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
    	hourChoice.getItems().addAll("1 hour", "1 day", "1 week", "1 month");
    	hourChoice.setValue("1 hour");
    	
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
            case "1 hour":
                return TimePeriodType.DAILY;
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
        List<String> tags;
        Set<String> blogTags = articleManager.extractUniqueTags();
        
        if (typeTag.equals("Twitter")){
            tags = twitterData.getHotTags(timePeriodType);
        } else {
            tags =  new ArrayList<>(blogTags);
        }
        
        for (String tag : tags) {
            Label tagLabel = new Label(tag);
            tagLabel.getStyleClass().add("tag-label");
            tagsContainer.getChildren().add(tagLabel);
            tagsContainer.getChildren().add(new Separator());
        }
    }
}