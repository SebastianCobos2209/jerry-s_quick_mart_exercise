package models.Customer;

import models.Inventory.Product;

public class Member extends Customer {
    public  Member(String name){
        super(name);
    }

    @Override
    public double getPrice(Product p){
        return p.getMembersPrice();
    }
}
