import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameManagment {
    public static void saveGame(Checkers checkers){
        PrintWriter outputStream = null;
        try {
             outputStream = new PrintWriter(new FileOutputStream("save.txt"));
        } catch(FileNotFoundException e) {
             System.out.println("Error opening the file save.txt.");
             System.exit(0);
        }
        outputStream.println(checkers.toString());
        outputStream.close();
    }

    public static Checkers loadGame(){
        Scanner inputStream = null;
         try {
             inputStream = new Scanner(new FileInputStream("save.txt"));
             } catch(FileNotFoundException e) {
             System.out.println("File save.txt was not found");
             System.out.println("or could not be opened.");
             System.exit(0);
             }
         String n1 = inputStream.nextLine();
         return new Checkers(n1);
    }
}
