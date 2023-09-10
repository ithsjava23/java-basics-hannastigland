package org.example;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String val;

        int[] priser = new int[24];

        do {
            System.out.print("Elpriser\n");
            System.out.print("========\n");
            System.out.print("1. Inmatning\n");
            System.out.print("2. Min, Max och Medel\n");
            System.out.print("3. Sortera\n");
            System.out.print("4. Bästa Laddningstid (4h)\n");
            System.out.print("e. Avsluta\n");
            val = scanner.nextLine();

            switch (val.toLowerCase()) {
                case "1" -> {
                    for (int timme = 0; timme < priser.length; timme++) {
                        System.out.print("Skriv in priset för " + (timme + 1) + ":a timmen i hela ören. \n");
                        priser[timme] = scanner.nextInt();
                    }
                }
                case "2" -> {
                    int min = priser[0];
                    int max = priser[0];
                    int summa = priser[0];
                    int timmeMin = 0;
                    int timmeMax = 0;
                    for (int timme = 1; timme < priser.length; timme++) {
                        int aktuelltPris = priser[timme];

                        if (aktuelltPris < min) {
                            min = aktuelltPris;
                            timmeMin = timme;
                        }
                        if (aktuelltPris > max) {
                            max = aktuelltPris;
                            timmeMax = timme;
                        }
                        summa += aktuelltPris;
                    }
                    int medel = summa / priser.length;
                    System.out.print("Lägsta pris: " + formatTimme(timmeMin) + ", " + min + " öre per kW/h. \n");
                    System.out.print("Högsta pris: " + formatTimme(timmeMax) + ", " + max + " öre per kW/h. \n");
                    System.out.print("Genomsnittligt pris: " + medel + " öre per kW/h. \n");
                }
                case "e" -> System.out.print("Programmet avslutas.");
                default -> System.out.print("Ej giltigt, försök igen.\n");
            }

        } while (!val.equalsIgnoreCase("e"));
    }
    public static String formatTimme(int timme) {
        return String.format("%02d-%02d", timme, (timme + 1) % 24);
    }
}
