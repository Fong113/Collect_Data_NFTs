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
import blog_news.crawl.Todaynftnews_crawler;
import blog_news.handle.HandleArticleManager;
import blog_news.helper.JsonIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Controller implements Initializable {
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
	private ChoiceBox<String> hourChoice;
	@FXML
	private ChoiceBox<String> typeTagChoice;
	@FXML 
	private ScrollPane tagsPane;
	@FXML
	private VBox tagsBox;

	private HandleArticleManager articleManager;
    private List<Article> currentArticles = new ArrayList<Article>();
    private final int itemsPerPage = 5; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	Type aListType = new TypeToken<List<Article>>() {}.getType();
        JsonIO<Article> jsonIO = new JsonIO<>(aListType);
        currentArticles = jsonIO.loadJson("data/blog_news.json");
        articleManager = new HandleArticleManager(currentArticles);

        
        
        setChoiceBox();
        displayAllTags(tagsBox);
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
            	currentArticles = articleManager.filterArticlesByTags(new String[]{searchText});
            	setupPagination();
            }
        } else if (selectedTab == twitterTab) {
            searchTwitter();
        }
    }

    private void setupPagination() {
        int pageCount = (int) Math.ceil((double) currentArticles.size() / itemsPerPage);
        blogPagination.setPageCount(pageCount);
        blogPagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        VBox box = new VBox(5);
        int start = pageIndex * itemsPerPage;
        int end = Math.min(start + itemsPerPage, currentArticles.size());
        for (int i = start; i < end; i++) {
            Article article = currentArticles.get(i);
            box.getChildren().add(createArticleNode(article));
            
            if (i < end - 1) {
                box.getChildren().add(new Separator());
            }
        }
        return new ScrollPane(box);
    }

    private Node createArticleNode(Article article) {
        VBox articleBox = new VBox(10);
        articleBox.getStyleClass().add("article-box");

        Label titleLabel = new Label(article.getTitle());
        titleLabel.getStyleClass().add("article-title");
        titleLabel.setOnMouseClicked(event -> showArticleInWebView(article.getAbsoluteURL()));

        Label dateLabel = new Label(article.getPublishDate());
        dateLabel.getStyleClass().add("article-date");

        Label contentLabel = new Label(article.getFullContent());
        contentLabel.getStyleClass().add("article-content");
        contentLabel.setWrapText(true);
        
        articleBox.getChildren().addAll(titleLabel, contentLabel, dateLabel);

        return articleBox;
    }
    private void showArticleInWebView(String url) {
        Stage stage = new Stage();
        
        WebView webView = new WebView();
        webView.getEngine().load(url);
        
        Scene scene = new Scene(webView);
        stage.setScene(scene);
        
        stage.show();
    }
    
    private void displayAllTags(VBox tagsContainer) {
        tagsContainer.getChildren().clear();
        TagTest test = new TagTest();

        Set<String> allTags = test.getAllUniqueTags(currentArticles);

        Iterator<String> iterator = allTags.iterator();
        while (iterator.hasNext()) {
            String tag = iterator.next();

            Label tagLabel = new Label(tag);
            tagLabel.getStyleClass().add("tag-label");
            tagsContainer.getChildren().add(tagLabel);

            if (iterator.hasNext()) {
                tagsContainer.getChildren().add(new Separator());
            }
        }
    }

    private void searchTwitter() {
    
    }
}
