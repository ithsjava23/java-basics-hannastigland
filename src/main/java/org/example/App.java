package org.example;
import java.util.Locale;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Locale swedishLocale = Locale.of("sv", "SE");
        Locale.setDefault(swedishLocale);
        String val;

        int[] priser = new int[24];
        int[] timmar = new int[24];

        do {
            skrivUtMeny();
            val = scanner.nextLine();

            switch (val.toLowerCase()) {
                case "1" -> inmatningAvPriser(priser, timmar, scanner);
                case "2" -> räknaUtMinMaxMedel(priser, timmar);
                case "3" -> sorteraPriser(priser, timmar);
                case "4" -> bästaLaddningsTid(priser, timmar);
                case "e" -> System.out.print("Programmet avslutas.");
                default -> System.out.print("Ej giltigt, försök igen.\n");
            }
        } while (!val.equalsIgnoreCase("e"));

    }

    public static String formatTimme(int timme, boolean medIntervall) {
        int slutTimme = (timme + 1) % 24;
        if (slutTimme == 0) {
            slutTimme = 24;
            return String.format("%02d-%02d", timme, slutTimme);
        }
        if (medIntervall) {
            return String.format("%02d-%02d", timme, (timme + 1) % 24);
        } else {
            return String.format("%02d", timme);
        }
    }

    public static void skrivUtMeny() {
        System.out.print("Elpriser\n");
        System.out.print("========\n");
        System.out.print("1. Inmatning\n");
        System.out.print("2. Min, Max och Medel\n");
        System.out.print("3. Sortera\n");
        System.out.print("4. Bästa Laddningstid (4h)\n");
        System.out.print("e. Avsluta\n");
    }

    public static void inmatningAvPriser(int[] priser, int[] timmar, Scanner scanner) {
        for (int timme = 0; timme < priser.length; timme++) {
            System.out.print("Skriv in priset för " + (timme + 1) + ":a timmen i hela ören.\n");
            try {
                priser[timme] = Integer.parseInt(scanner.nextLine());
                timmar[timme] = timme;
            } catch (NumberFormatException e) {
                System.out.print("Felaktig inmatning, ange ett heltal.\n");
                timme--;
            }
        }
    }

    public static void räknaUtMinMaxMedel(int[] priser, int[] timmar) {
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
        System.out.print("Lägsta pris: " + formatTimme(timmar[timmeMin], true) + ", " + min + " öre/kWh\n");
        System.out.print("Högsta pris: " + formatTimme(timmar[timmeMax], true) + ", " + max + " öre/kWh\n");
        System.out.print("Medelpris: " + String.format("%.2f", medel) + " öre/kWh\n");
    }

    public static void sorteraPriser(int[] priser, int[] timmar) {
        System.out.print("Inmatade elpriser sorterade från dyrast till billigast: \n");
        for (int i = 0; i < priser.length - 1; i++) {
            for (int j = 0; j < priser.length - i - 1; j++) {
                if (priser[j] < priser[j + 1]) {
                    int tempPris = priser[j];
                    priser[j] = priser[j + 1];
                    priser[j + 1] = tempPris;
                    int tempTimme = timmar[j];
                    timmar[j] = timmar[j + 1];
                    timmar[j + 1] = tempTimme;
                }
            }
        }
        for (int i = 0; i < priser.length; i++) {
            int pris = priser[i];
            String tidsspann = formatTimme(timmar[i], true);
            System.out.print(tidsspann + " " + pris + " öre\n");
        }
    }
    public static void bästaLaddningsTid(int[] priser, int[] timmar) {
        double billigasteTotalPris = Integer.MAX_VALUE;
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
        double medelprisFör4Timmar = billigasteTotalPris / 4;
        String startTid = formatTimme(timmar[startTimme], false);
        System.out.print("Påbörja laddning klockan " + startTid + "\n");
        System.out.printf(("Medelpris 4h: " + medelprisFör4Timmar + " öre/kWh\n").replace('.', ','));
    }
}

