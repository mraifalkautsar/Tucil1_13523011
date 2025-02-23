import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Board {
    private final int N, M;
    private final char[][] grid;
    private int iteration;

    public Board(int N, int M) {
        this.N = N;
        this.M = M;
        this.grid = new char[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++ ) {
                grid[i][j] = '0';
            }
        }
    }

    public int getIteration() {
        return iteration;
    }

    public char[][] getGrid() {
        return grid;
    }

    public boolean placeBlock(Set<Ball> block, int x, int y, int index) {
        for (Ball ball : block) {
            int newX = x + ball.x;
            int newY = y + ball.y;
            if (newX < 0 || newX >= N || newY < 0 || newY >= M || grid[newX][newY] != '0') {
                return false;
            }
        }
        for (Ball ball : block) {
            grid[x + ball.x][y + ball.y] = ball.id;
        }
        return true;
    }

    public void removeBlock(Set<Ball> block, int x, int y) {
        for (Ball ball : block) {
            grid[x + ball.x][y + ball.y] = '0';
        }
    }

    public boolean isBoardFilled() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] == '0') return false;
            }
        }
        return true;
    }

    // Melakukan pemecahan dalam mode default.
    public boolean solveDefault(List<Block> blocks, int index) {
        if (index == blocks.size()) return isBoardFilled();
        Block block = blocks.get(index);
        for (Set<Ball> rotation : block.getRotations()) {
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < M; y++) {
                    iteration++;
                    if (placeBlock(rotation, x, y, index + 1)) {
                        if (solveDefault(blocks, index + 1)) return true;
                        removeBlock(rotation, x, y);
                    }
                }
            }
        }
        return false;
    }

    // Menginisalisasi papan custom dengan '.' sebagai sel yang tak bisa ditempati.
    public void initializeCustom(List<String> config) {
        for (int i = 0; i < N; i++) {
            String row = config.get(i);
            for (int j = 0; j < M; j++) {
                char ch = row.charAt(j);
                if (ch == '.') {
                    grid[i][j] = '.'; // Sel yang terblokir.
                } else {
                    grid[i][j] = '0'; // Sel yang bebas.
                }
            }
        }
    }

    // Melakukan pemecahan dalam mode custom.
    public boolean solveCustom(List<Block> blocks, List<String> config, int index) {
        if (index == 0) {
            initializeCustom(config);
        }
        if (index == blocks.size()) return isBoardFilled();
        Block block = blocks.get(index);
        for (Set<Ball> rotation : block.getRotations()) {
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < M; y++) {
                    iteration++;
                    if (placeBlock(rotation, x, y, index + 1)) {
                        if (solveCustom(blocks, config, index + 1)) return true;
                        removeBlock(rotation, x, y);
                    }
                }
            }
        }
        return false;
    }

    public void printBoard() {
        Map<Character, String> colorMap = new HashMap<>();
        String[] colors = {
                "\u001B[31m", // Red
                "\u001B[32m", // Green
                "\u001B[33m", // Yellow
                "\u001B[34m", // Blue
                "\u001B[35m", // Magenta
                "\u001B[36m", // Cyan
                "\u001B[91m", // Bright Red
                "\u001B[92m", // Bright Green
                "\u001B[93m", // Bright Yellow
                "\u001B[94m", // Bright Blue
                "\u001B[95m", // Bright Magenta
                "\u001B[96m", // Bright Cyan
        };

        int colorIndex = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            colorMap.put(c, colors[colorIndex % colors.length]);
            colorIndex++;
        }

        final String RESET = "\u001B[0m";

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                char cell = grid[i][j];
                if (colorMap.containsKey(cell)) {
                    System.out.print(colorMap.get(cell) + cell + RESET);
                } else {
                    System.out.print(cell);
                }
            }
            System.out.println();
        }
    }

    public void saveBoardToFile(String fileName) {
        File outputFile = new File(fileName);
        outputFile.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(outputFile)) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    writer.write(grid[i][j]);
                }
                writer.write("\n");
            }
            System.out.println("Solusi disimpan ke: " + fileName);
        } catch (IOException e) {
            System.err.println("Error menyimpan ke file: " + e.getMessage());
        }
    }

    public void saveBoardtoImage(String fileName) {
        int cellSize = 30;
        int width = M * cellSize;
        int height = N * cellSize;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        Map<Object, Color> colorMap = new HashMap<>();
        Color[] colors = {
                Color.RED,
                Color.GREEN,
                Color.YELLOW,
                Color.BLUE,
                Color.MAGENTA,
                Color.CYAN,
                Color.ORANGE,
                new Color(144, 238, 144),
                new Color(255, 255, 224),
                new Color(173, 216, 230),
                Color.MAGENTA,
                new Color(224, 255, 255)
        };

        int colorIndex = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            colorMap.put(c, colors[colorIndex % colors.length]);
            colorIndex++;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                char cell = grid[i][j];
                Color bg;
                if (cell == '0') {
                    bg = Color.WHITE;
                } else if (cell == '.') {
                    bg = Color.GRAY;
                } else {
                    bg = colorMap.getOrDefault(cell, Color.LIGHT_GRAY);
                }

                g2d.setColor(bg);
                g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);

                g2d.setColor(Color.BLACK);
                g2d.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);

                if (cell != '0' && cell != '.') {
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(String.valueOf(cell));
                    int textHeight = fm.getAscent();
                    int x = j * cellSize + (cellSize - textWidth) / 2;
                    int y = i * cellSize + (cellSize + textHeight) / 2 - fm.getDescent();
                    g2d.drawString(String.valueOf(cell), x, y);
                }
            }
        }
        g2d.dispose();

        try {
            fileName = fileName.substring(0, fileName.length()-4) + ".jpg";
            ImageIO.write(image, "png", new File(fileName));
            System.out.println("Gambar papan disimpan ke " + fileName);
        } catch (IOException e) {
            System.err.println("Error menyimpan gambar papan: " + e.getMessage());
        }
    }
}