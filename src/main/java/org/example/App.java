package org.example;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Locale swedishLocale = new Locale("sv", "SE"); Locale.setDefault(swedishLocale);
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
                        System.out.print("Skriv in priset för " + (timme + 1) + ":a timmen i hela ören.\n");
                        try {
                            priser[timme] = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.print("Felaktig inmatning, ange ett heltal.\n");
                            timme--;
                        }
                    }
                }
                case "2" -> {
                    int min = Integer.MAX_VALUE;
                    int max = Integer.MIN_VALUE;
                    int summa = 0;
                    int timmeMin = 0;
                    int timmeMax = 0;
                    for (int timme = 0; timme < priser.length; timme++) {
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
                    double medel = (double) summa / priser.length;
                    System.out.print("Lägsta pris: " + formatTimme(timmeMin) + ", " + min + " öre/kWh\n");
                    System.out.print("Högsta pris: " + formatTimme(timmeMax) + ", " + max + " öre/kWh\n");
                    System.out.print("Medelpris: " + String.format("%.2f", medel) + " öre/kWh\n");
                }
                case "3" -> {
                    Arrays.sort(priser);
                    System.out.print("Inmatade elpriser sorterade från dyrast till billigast:\n");
                    for (int timme = priser.length - 1; timme >= 0; timme--) {
                        int pris = priser[timme];
                        String tidsspann = formatTimme(timme);
                        System.out.print(tidsspann + " " + pris + " öre \n");
                    }
                }
                case "4" -> {
                    int billigasteTotalPris = Integer.MAX_VALUE;
                    int startTimme = 0;
                    for (int i = 0; i <= priser.length - 4; i++) {
                        int totalPris = 0;
                        for (int j = i; j < i + 4; j++) {
                            totalPris += priser[j];
                        }
                        if (totalPris < billigasteTotalPris) {
                            billigasteTotalPris = totalPris;
                            startTimme = i;
                        }
                    }
                    int medelprisFör4Timmar = billigasteTotalPris / 4;
                    String startTid = formatTimme(startTimme);
                    System.out.print("Påbörja laddning klockan " + startTid + "\n");
                    System.out.print("Lägsta totalpris för dessa 4h: " + billigasteTotalPris + " öre\n");
                    System.out.print("Medelpris 4h: " + medelprisFör4Timmar + " öre/kWh\n");
                }
                case "e" -> System.out.print("Programmet avslutas.");
                default -> System.out.print("Ej giltigt, försök igen.\n");
            }

        } while (!val.equalsIgnoreCase("e"));
    }

    public static String formatTimme(int timme) {
        if (timme == 23) {
            return "23-24";
        } else {
            return String.format("%02d-%02d", timme, (timme + 1) % 24);
        }
    }
}
