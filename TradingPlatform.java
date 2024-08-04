package project;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Stock {
    private String name;
    private String symbol;
    private double price;

    public Stock(String name, String symbol, double price) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Portfolio {
    private Map<String, Integer> holdings;
    private double cash;

    public Portfolio(double initialCash) {
        this.holdings = new HashMap<>();
        this.cash = initialCash;
    }

    public double getCash() {
        return cash;
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cash >= cost) {
            cash -= cost;
            holdings.put(stock.getSymbol(), holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
            System.out.println("Bought " + quantity + " shares of " + stock.getName() + " at $" + stock.getPrice() + " each.");
        } else {
            System.out.println("Insufficient funds to buy " + quantity + " shares of " + stock.getName() + ".");
        }
    }

    public void sellStock(Stock stock, int quantity) {
        int currentHoldings = holdings.getOrDefault(stock.getSymbol(), 0);
        if (currentHoldings >= quantity) {
            double revenue = stock.getPrice() * quantity;
            cash += revenue;
            holdings.put(stock.getSymbol(), currentHoldings - quantity);
            System.out.println("Sold " + quantity + " shares of " + stock.getName() + " at $" + stock.getPrice() + " each.");
        } else {
            System.out.println("Insufficient shares to sell.");
        }
    }

    public void printPortfolio() {
        System.out.println("Portfolio holdings:");
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " shares");
        }
        System.out.println("Cash: $" + cash);
    }
}

public class TradingPlatform {
    private static List<Stock> marketData;
    private static Portfolio portfolio;

    public static void main(String[] args) {
        marketData = new ArrayList<>();
        marketData.add(new Stock("Apple", "AAPL", 150.00));
        marketData.add(new Stock("Google", "GOOGL", 2800.00));
        marketData.add(new Stock("Amazon", "AMZN", 3400.00));
        marketData.add(new Stock("Tata Steel", "TASL", 120.00));
        marketData.add(new Stock("IRCTC", "IRCT", 100.00));
        marketData.add(new Stock("HDFC Bank", "HDFC", 80.00));
        marketData.add(new Stock("LIC", "LIC", 90.00));
        portfolio = new Portfolio(100000.00);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n1. View market data");
            System.out.println("2. Buy stock");
            System.out.println("3. Sell stock");
            System.out.println("4. View portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewMarketData();
                    break;
                case 2:
                    buyStock(scanner);
                    break;
                case 3:
                    sellStock(scanner);
                    break;
                case 4:
                    portfolio.printPortfolio();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void viewMarketData() {
        System.out.println("Market data:");
        for (Stock stock : marketData) {
            System.out.println(stock.getName() + " (" + stock.getSymbol() + "): $" + stock.getPrice());
        }
    }

    private static void buyStock(Scanner scanner) {
        System.out.print("Enter the stock symbol to buy: ");
        String symbol = scanner.next().toUpperCase();
        Stock stock = getStockBySymbol(symbol);

        if (stock != null) {
            System.out.print("Enter the quantity to buy: ");
            int quantity = scanner.nextInt();
            portfolio.buyStock(stock, quantity);
        } else {
            System.out.println("Stock symbol not found.");
        }
    }

    private static void sellStock(Scanner scanner) {
        System.out.print("Enter the stock symbol to sell: ");
        String symbol = scanner.next().toUpperCase();
        Stock stock = getStockBySymbol(symbol);

        if (stock != null) {
            System.out.print("Enter the quantity to sell: ");
            int quantity = scanner.nextInt();
            portfolio.sellStock(stock, quantity);
        } else {
            System.out.println("Stock symbol not found.");
        }
    }

    private static Stock getStockBySymbol(String symbol) {
        for (Stock stock : marketData) {
            if (stock.getSymbol().equals(symbol)) {
                return stock;
            }
        }
        return null;
    }
}
