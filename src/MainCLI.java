import java.io.File;
import java.util.*;

public class MainCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName;

        File inputFile;
        while (true) {
            System.out.print("Masukkan nama file input: ");
            fileName = scanner.nextLine();
            inputFile = new File(fileName);

            if (inputFile.exists() && inputFile.isFile()) {
                break;
            }
            System.err.println("File '" + fileName + "' tidak ditemukan atau tidak valid.");
        }

        ParsedData data = Parser.parse(inputFile);
        Board board = new Board(data.N, data.M);
        boolean solved;
        long startTime = System.nanoTime();

        if (data.mode.equalsIgnoreCase("CUSTOM")) {
            solved = board.solveCustom(data.blocks, data.boardConfig, 0);
        } else {
            solved = board.solveDefault(data.blocks, 0);
        }
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1_000_000.0;

        if (solved) {
            board.printBoard();
        } else {
            System.out.println("Tidak ada solusi.");
        }
        System.out.println("Waktu pencarian: " + elapsedTime + " ms");
        System.out.println("Jumlah iterasi: " + board.getIteration());

        System.out.print("Simpan hasil ke teks? (y/n)");
        String choice1 = scanner.nextLine().trim().toLowerCase();
        if (choice1.equals("y")) {
            board.saveBoardToFile("../test/result_" + fileName);
        }

        System.out.print("Simpan hasil sebagai gambar? (y/n)");
        String choice2 = scanner.nextLine().trim().toLowerCase();
        if (choice2.equals("y")) {
            board.saveBoardtoImage("../test/image_" + fileName);
        }


        scanner.close();
    }
}
