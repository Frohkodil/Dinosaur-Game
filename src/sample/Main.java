package sample;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main extends Application {
    boolean changed;
    private TextArea txtInput;
    private Stage primaryStage;
    private Button saveButton;
    private Button loadButton;
    private Button exitButton;
    private Button buzzwordChecker;
    private BuzzwordCounter buzzwordCounter;
    private ProgressBar bingoBar;

    public static void main (String[]args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Notpad--");
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);


        exitButton = createExitButton();
        saveButton = createSaveButton();
        loadButton = createLoadButton();
        buzzwordChecker = createBuzzwordChecker();
        VBox vbox = createMenu();


        txtInput = new TextArea("Beispieltext, \n lets goooooo. \n Füg irgendwas hier ein oder schreibe etwas.");
        txtInput.setPrefColumnCount(20);
        txtInput.setPrefRowCount(100);

        buzzwordCounter = createBuzzwordCounter();

        ProgressBar progressBar = new ProgressBar(0);

        Button countdownButton = new Button();
        countdownButton.setText("Countdown");
        countdownButton.setOnAction(e -> countdown(countdownButton, progressBar));

        bingoBar = new ProgressBar(0);
        Label bingoLabel = new Label("");
        Label counterLabel = new Label ("");

        gridpane.add(vbox, 0, 0);
        gridpane.add(txtInput, 0, 1, 6, 1);
        gridpane.add(exitButton, 0, 2);
        gridpane.add(loadButton, 2, 2);
        gridpane.add(saveButton, 4, 2);
        gridpane.add(countdownButton, 6, 2);
        gridpane.add(progressBar, 8, 2);
        gridpane.add(bingoBar, 4, 0);
        gridpane.add(bingoLabel, 3,0);
        gridpane.add(counterLabel, 2,0);
        gridpane.add(buzzwordChecker,3, 0);

        Image image = new Image("file:src/resources/Beispiel.jpg", 1024, 724, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gridpane.setBackground(new Background(backgroundImage));

        Scene scene = new Scene(gridpane, 1024, 724);
        txtInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                saveButton.disableProperty().set(!changed);
                if(! primaryStage.getTitle().contains("*")) {
                    String titel = primaryStage.getTitle();
                    primaryStage.setTitle(titel + "*");
                    changed = true;
                }
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void save() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("C://"));
        directoryChooser.setTitle("Datei speichern");
        Stage saveStage = new Stage();
        File saveFile = directoryChooser.showDialog(saveStage);
        Path path = Paths.get(saveFile.getAbsolutePath());
        String ausgabe = txtInput.getText();
        try {
            Files.writeString(path, ausgabe);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    private String load(){
        FileChooser fileChooser = createFileChooser();
        Stage loadStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(loadStage);
        if (selectedFile != null) {
            Path path = Paths.get(selectedFile.getAbsolutePath());
            try{
                return Files.readAllLines(path).get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        changed = true;
        saveButton.disableProperty().set(changed);
        return null;
    }
    private void countdown(Button countdownButton, ProgressBar progressBar){
        //countdownButton.setDisable(true);
        LangeTask langeTask = new LangeTask();
        progressBar.progressProperty().bind(langeTask.progressProperty());
        countdownButton.textProperty().bind(langeTask.messageProperty());
        new Thread(langeTask).start();
    }

    public VBox createMenu(){
        Menu optionen = new Menu("Optionen");
        MenuItem exitMenu = new MenuItem("Exit");
        exitMenu.setOnAction(e -> primaryStage.close());
        MenuItem loadMenu = new MenuItem("Load");
        loadMenu.setOnAction(e -> load());
        MenuItem saveMenu = new MenuItem("Save");
        saveMenu.setOnAction(e -> save());
        optionen.getItems().add(exitMenu);
        optionen.getItems().add(loadMenu);
        optionen.getItems().add(saveMenu);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(optionen);
        VBox vbox = new VBox(menuBar);
        return  vbox;
    }
    public FileChooser createFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\Siebo\\Documents"));
        fileChooser.setInitialFileName("Datei Laden");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        return fileChooser;
    }
    public Button createExitButton(){
        Button exitButton = new Button();
        exitButton.setText("Exit");
        exitButton.setOnAction(e -> primaryStage.close());
        return exitButton;
    }
    public Button createSaveButton(){
        Button saveButton = new Button();
        saveButton.setText("Save");
        saveButton.disableProperty().set(!changed);
        saveButton.setOnAction(e -> save());
        return saveButton;
    }
    public Button createLoadButton(){
        Button loadButton = new Button();
        loadButton.setText("Load");
        loadButton.setOnAction(e -> load());
        return loadButton;
    }
    public Button createBuzzwordChecker(){
        Button buzzwordChecker = new Button();
        buzzwordChecker.setText("Buzzwords Checken");
        buzzwordChecker.setOnAction(e -> buzzwordCounter.setCount(txtInput.textProperty()));
        return buzzwordChecker;
    }
    public BuzzwordCounter createBuzzwordCounter(){
        List<String> buzzwords = List.of("Model","View","Controller");
        BuzzwordCounter buzzwordCounter = new BuzzwordCounter( buzzwords, txtInput.getText());
        return buzzwordCounter;
    }
}
