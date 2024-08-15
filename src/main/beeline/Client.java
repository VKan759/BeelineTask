public class Client {
    private String name;
    private double balance;
    private double valueLimit;
    private String comparePhrase;
    private double internetTraffic;

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", internetTraffic=" + internetTraffic +
                '}';
    }

    public Client(String name, double balance, double internetTraffic, double valueLimit, String compareSign) {
        this.name = name;
        this.balance = balance;
        this.internetTraffic = internetTraffic;
        this.valueLimit = valueLimit;
        this.comparePhrase = compareSign;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public double getInternetTraffic() {
        return internetTraffic;
    }

    public double getValueLimit() {
        return valueLimit;
    }

    public String getComparePhrase() {
        return comparePhrase;
    }
}
