package models.Customer.models.Inventory;

public class Product {
    private String name;
    private int Stock;
    private double RegularPrice;
    private double MembersPrice;
    private String Taxable;


    public Product(String name, int Stock, double RegularPrice, double MembersPrice, String Taxable) {
        this.name = name;
        this.Stock = Stock;
        this.RegularPrice = RegularPrice;
        this.MembersPrice = MembersPrice;
        this.Taxable = Taxable;
    }

    public static Product addProductByLine(String line){
        String[] parts = line.split(":");
        String name = parts[0].trim();
        String[] data = parts[1].split(",");
        int Stock =  Integer.parseInt(data[0].trim());
        String RegularPriceStr =data[1].trim().replace("$","");
        String MembersPriceStr = data[2].trim().replace("$","");
        double RegularPrice = Double.parseDouble(RegularPriceStr);
        double MembersPrice = Double.parseDouble(MembersPriceStr);
        String Taxable = data[3].trim();

        return new Product(name, Stock, RegularPrice, MembersPrice, Taxable);
    }

    @Override
    public String toString() {
        return name + " | Stock: " + Stock +
                " | Reg: $" + RegularPrice +
                " | Memb: $" + MembersPrice +
                " | Tax: " + Taxable;
    }

    public String getName() { return name; }
    public int getStock() { return Stock; }
    public double getRegularPrice() { return RegularPrice; }
    public double getMembersPrice() { return MembersPrice; }
    public String getTaxable() { return Taxable; }
}
