import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.*;
import javafx.scene.text.Font;

public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("");

        Button btnSelectFile = new Button("Select Puzzle File");
        GridPane boardPane = new GridPane();
        boardPane.setHgap(2);
        boardPane.setVgap(2);
        boardPane.setAlignment(Pos.CENTER);

        Label lblIterations = new Label("Iterations: ");
        Label lblTime = new Label("Time Elapsed: ");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        Image logo = new Image("file:logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(320);
        logoView.setPreserveRatio(true);
        VBox.setMargin(logoView, new Insets(0, 0, 20, 0));
        root.getChildren().add(0, logoView);
        final Board[] board = {null};
        final String[] fileName = {null};

        Button btnSaveResultToText = new Button("Save Result to Text");
        btnSaveResultToText.setDisable(true);
        Button btnSaveResultToImage = new Button("Save Result to Image");
        btnSaveResultToImage.setDisable(true);

        btnSelectFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Puzzle File");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                if (selectedFile != null) {
                    fileName[0] = selectedFile.getName();
                    ParsedData data = Parser.parse(selectedFile);
                    board[0] = new Board(data.N, data.M);
                    long startTime = System.nanoTime();
                    boolean solved;
                    if (data.mode.equalsIgnoreCase("CUSTOM")) {
                        solved = board[0].solveCustom(data.blocks, data.boardConfig, 0);
                    } else {
                        solved = board[0].solveDefault(data.blocks, 0);
                    }
                    long endTime = System.nanoTime();
                    double elapsedTime = (endTime - startTime) / 1_000_000.0;
                    lblTime.setText("Time Elapsed: " + elapsedTime + " ms");
                    lblIterations.setText("Iterations: " + board[0].getIteration());

                    if (solved) {
                        boardPane.getChildren().clear();
                        char[][] grid = board[0].getGrid();

                        Map<Character, Color> colorMap = new HashMap<>();

                        Color[] fixedColors = {
                                Color.RED,
                                Color.GREEN,
                                Color.YELLOW,
                                Color.BLUE,
                                Color.MAGENTA,
                                Color.CYAN,
                                Color.ORANGERED,
                                Color.LIGHTGREEN,
                                Color.LIGHTYELLOW,
                                Color.LIGHTBLUE,
                                Color.MAGENTA,
                                Color.LIGHTCYAN
                        };

                        for (int i = 0; i < 12; i++) {
                            char letter = (char) ('A' + i);
                            colorMap.put(letter, fixedColors[i]);
                        }

                        int remaining = 14;
                        for (int i = 0; i < remaining; i++) {
                            char letter = (char) ('A' + 12 + i);
                            double hue = (i * 360.0) / remaining;
                            Color generated = Color.hsb(hue, 0.8, 0.8);
                            colorMap.put(letter, generated);
                        }

                        for (int i = 0; i < data.N; i++) {
                            for (int j = 0; j < data.M; j++) {
                                char cell = grid[i][j];
                                Label lbl = new Label(String.valueOf(cell));
                                lbl.setMinSize(30, 30);
                                lbl.setAlignment(Pos.CENTER);
                                lbl.setFont(Font.font(14));
                                if (cell == '0') {
                                    lbl.setStyle("-fx-background-color: white; -fx-border-color: black;");
                                } else if (cell == '.') {
                                    lbl.setStyle("-fx-background-color: gray; -fx-border-color: black;");
                                } else {
                                    Color col = colorMap.getOrDefault(cell, Color.LIGHTGRAY);
                                    String hex = String.format("#%02X%02X%02X",
                                            (int)(col.getRed()*255),
                                            (int)(col.getGreen()*255),
                                            (int)(col.getBlue()*255));
                                    lbl.setStyle("-fx-background-color: " + hex + "; -fx-border-color: black;");
                                }
                                boardPane.add(lbl, j, i);
                            }
                            btnSaveResultToText.setDisable(false);
                            btnSaveResultToImage.setDisable(false);
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("No Solution");
                        alert.setHeaderText(null);
                        alert.setContentText("Tidak ada solusi yang ditemukan!");
                        alert.showAndWait();
                    }
                }
            }
        });

        btnSaveResultToText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                board[0].saveBoardToFile("../test/result_" + fileName[0]);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Saved");
                alert.setContentText("Solution text saved in test.");
                alert.showAndWait();
            }
        });

        btnSaveResultToImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                board[0].saveBoardtoImage("../test/image_" + fileName[0].replace(".txt", ".jpg"));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Saved");
                alert.setContentText("Image saved in test.");
                alert.showAndWait();
            }
        });

        root.getChildren().addAll(btnSelectFile, boardPane, lblIterations, lblTime, btnSaveResultToText, btnSaveResultToImage);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
