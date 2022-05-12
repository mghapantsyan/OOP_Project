package am.aua.Checkers.IO;
import am.aua.Checkers.core.Checkers;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameManagement {
    /**
     * This method save the game into a file
     * @param checkers is our board that we want to save
     */
    public static void saveGame(Checkers checkers) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream("save.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file save.txt.");
            System.exit(0);
        }
        outputStream.println(checkers.toString());
        outputStream.close();
    }

    /**
     * This method loads the game from save file
     * @return our board from save file
     * @throws Exception if loading file is damaged
     */
    public static Checkers loadGame() throws Exception {
        Scanner inputStream = null;
        inputStream = new Scanner(new FileInputStream("save.txt"));
        String n1 = inputStream.nextLine();
        GameManagement.validation(n1);
        return new Checkers(n1);
    }

    private static void validation(String n) throws Exception {

        if (n.length() == 65) {
            switch (n.charAt(64)) {
                case 'B':
                case 'W':
                    break;
                default:
                    throw new Exception("Invalid loading file");
            }

            for(int i = 0;i<64;i++){
                if ((i/8+ i%8) % 2 == 0 && n.charAt(i) != '-') {
                    throw new Exception("Invalid loading file");
                } else {
                    switch (n.charAt(i)) {
                        case 'K':
                        case 'k':
                        case 'P':
                        case 'p':
                        case '-':
                            continue;
                        default:
                            throw new Exception("Invalid loading file");
                    }
                }
            }

        } else {
            throw new Exception("Invalid load file");
        }
    }
}
