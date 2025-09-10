package wol;

import java.io.Console;
import java.io.PrintStream;
import java.util.Scanner;

public class MS1Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }

        Scanner scanner = new Scanner(System.in);
        PrintStream out = System.out;


        ConsoleInterface consoleInterface = new ConsoleInterface(scanner,out);

        consoleInterface.askForMaximumMisses();
    }
}