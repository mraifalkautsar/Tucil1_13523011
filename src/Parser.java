import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Parser - sebuah class untuk mengolah input.
public class Parser {

    private static char getFirstNonSpace(String line) {
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (!Character.isWhitespace(ch)) {
                return ch;
            }
        }
        return ' ';
    }

    // Konversi dari teks ke blok.
    public static List<Block> textToBlocks(int P, List<String> allLines) {
        List<List<String>> blockGroups = new ArrayList<>();
        if (allLines.isEmpty()) {
            System.err.println("Tidak ada baris blok.");
            System.exit(1);
        }
        // Mulai dari baris pertama.
        List<String> currentGroup = new ArrayList<>();
        currentGroup.add(allLines.get(0));
        char currentKey = getFirstNonSpace(allLines.get(0));

        // Memroses baris-baris yang tersisa.
        for (int i = 1; i < allLines.size(); i++) {
            String line = allLines.get(i);
            if (line.isEmpty()) continue; // Melewati baris kosong.
            char lineKey = getFirstNonSpace(line);
            if (lineKey != currentKey) {
                blockGroups.add(currentGroup);
                currentGroup = new ArrayList<>();
                currentKey = lineKey;
            }
            currentGroup.add(line);
        }
        if (!currentGroup.isEmpty()) {
            blockGroups.add(currentGroup);
        }
        if (blockGroups.size() != P) {
            System.err.println("Jumlah blok yang seharusnya: " + P + ". Jumlah blok yang ditemukan: " + blockGroups.size() + ".");
            System.exit(1);
        }
        List<Block> blocks = new ArrayList<>();
        for (List<String> group : blockGroups) {
            char id = getFirstNonSpace(group.get(0));
            Set<Ball> balls = new HashSet<>();
            for (int r = 0; r < group.size(); r++) {
                String row = group.get(r);
                for (int c = 0; c < row.length(); c++) {
                    if (row.charAt(c) == id) {
                        balls.add(new Ball(r, c, id));
                    }
                }
            }
            blocks.add(new Block(balls));
        }
        return blocks;
    }

    // Mengolah seluruh input.
    public static ParsedData parse(File inputFile) {
        ParsedData data = new ParsedData();
        try (Scanner scanner = new Scanner(inputFile)) {
            // Kasus membaca input kosong.
            if (!scanner.hasNextLine()) {
                System.err.println("File input kosong!");
                System.exit(1);
            }

            // Membaca ukuran papan dan jumlah blok.
            String headerLine = scanner.nextLine().trim();
            String[] headerParts = headerLine.split(" ");

            // Kasus baris pertama salah.
            if (headerParts.length != 3) {
                System.err.println("Baris N, M, dan P salah!");
                System.exit(1);
            }
            data.N = Integer.parseInt(headerParts[0]);
            data.M = Integer.parseInt(headerParts[1]);
            data.P = Integer.parseInt(headerParts[2]);

            // Membaca baris mode.
            data.mode = scanner.nextLine().trim();
            if (data.mode.equalsIgnoreCase("DEFAULT")) {

            }
            else if (data.mode.equalsIgnoreCase("CUSTOM")) {
                data.boardConfig = new ArrayList<>();
                // Membaca sebanyak N baris untuk konfigurasi papan.
                for (int i = 0; i < data.N; i++) {
                    if (scanner.hasNextLine()) {
                        data.boardConfig.add(scanner.nextLine());
                    }
                }
            }
            else {
                System.err.println("Mode tidak ada atau tidak valid!");
                System.exit(1);
            }

            List<String> blockLines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    blockLines.add(line);
                }
            }
            data.blocks = textToBlocks(data.P, blockLines);
        } catch (FileNotFoundException e) {
            System.err.println("File tidak ditemukan!");
            System.exit(1);
        }
        return data;
    }
}
