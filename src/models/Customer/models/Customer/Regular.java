package models.Customer.models.Customer;

import models.Customer.models.Inventory.Product;

public class Regular extends Customer {
    public Regular(String name){
        super(name);
    }

    @Override
    public double getPrice(Product p){
        return p.getRegularPrice();
    }
}
