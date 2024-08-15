import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.StringJoiner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Menu {
    private Client client;
    private String[] menuItems;
    private String specialOffers;


    public Client getClient() {
        return client;
    }

    public String[] getMenuItems() {
        return menuItems;
    }

    public String getSpecialOffers() {
        return specialOffers;
    }

    public Menu(Client client, String[] menuItems, String specialOffers) {
        this.client = client;
        this.menuItems = menuItems;
        this.specialOffers = specialOffers;
    }

    public static void mainMenuRunning(Client client, Menu menu) {
        Scanner scanner = new Scanner(System.in);
        printMenu(menu);
        String answer = scanner.nextLine();
        switch (answer) {
            case "1" -> printBalanceInfo(client, client.getComparePhrase());
            case "2" -> printInternetInfo(client);
            case "3" -> specialOffers(client, menu);
            case "*", "#", "9" -> {
            }
        }
    }

    public static void printMenu(Menu menu) {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        for (String string : menu.getMenuItems()) {
            joiner.add(string);
        }
        System.out.println(joiner);
    }

    public static void printBalanceInfo(Client client, String conditionForComparing) {
        String phrase = conditionForComparing.toLowerCase();
        boolean isBelowLimit = client.getBalance() < client.getValueLimit();
        boolean isAtLimit = client.getBalance() == client.getValueLimit();

        switch (phrase) {
            case "меньше" -> {
                if (isBelowLimit) {
                    System.out.println("У Вас на счету меньше " + client.getValueLimit() + " тенге. Рекомендуем пополнить баланс.\n");
                } else {
                    System.out.println("У Вас на счету " + client.getBalance() + " тенге.\n");
                }
            }
            case "меньше или равно" -> {
                if (isBelowLimit || isAtLimit) {
                    System.out.println("У Вас на счету " + (isBelowLimit ? "меньше или равно " : "") + client.getValueLimit() + " тенге. Рекомендуем пополнить баланс.\n");
                } else {
                    System.out.println("У Вас на счету " + client.getBalance() + " тенге.\n");
                }
            }
            case "больше", "больше или равно" -> {
                if (!isBelowLimit) {
                    System.out.println("У Вас на счету " + (isAtLimit ? "больше или равно " : "больше ") + client.getValueLimit() + " тенге.\n");
                } else {
                    System.out.println("У Вас на счету " + client.getBalance() + " тенге.\n");
                }
            }
            default -> System.out.println("У Вас на счету " + client.getBalance() + " тенге.\n");
        }
    }

    public static void printInternetInfo(Client client) {
        double value = client.getInternetTraffic();
        int kb = (int) (value % Math.pow(1024, 2) / 1024);
        int mb = (int) (value / Math.pow(1024, 2) % 1024);
        int gb = (int) (value / Math.pow(1024, 3));
        System.out.printf("Ваш интернет трафик: %d ГБ %d МБ %d КБ%n", gb, mb, kb);
    }

    public static void specialOffers(Client client, Menu menu) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(menu.getSpecialOffers());
            String answer = scanner.nextLine();
            switch (answer) {
                case "1" -> System.out.println("Успейте подключить до 1 июня и получите безлимитный интернет.\n");
                case "2" -> System.out.println("Получите скидку 50% на Абонентскую плату на 3 месяца.\n");
                case "9" -> {
                    return;
                }
                case "#" -> {
                }
                case "*" -> {
                    mainMenuRunning(client, menu);
                    return;
                }
                default -> System.out.println("Неверный ввод, попробуйте еще раз.");
            }
        }
    }

    public static void greeting(Client client) {
        LocalTime localTime = LocalTime.now();

        if (localTime.isBefore(LocalTime.parse("06:00"))) {
            System.out.println("Доброй ночи, " + client.getName() + "!");
        } else if (localTime.isBefore(LocalTime.parse("16:00"))) {
            System.out.println("Добрый день, " + client.getName() + "!");
        } else if (localTime.isBefore(LocalTime.parse("21:00"))) {
            System.out.println("Добрый вечер, " + client.getName() + "!");
        } else {
            System.out.println("Доброй ночи, " + client.getName() + "!");
        }
    }

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();
        try {
            Menu menu = gson.fromJson(new BufferedReader(new FileReader("src/main/beeline/json.json")), Menu.class);
            Client client = menu.getClient();
            greeting(client);
            while (true) {
                mainMenuRunning(client, menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
