package models.Inventory;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Inventory {
    private final List<Product> products = new ArrayList<Product>();
    private String route;

    public void UploadProductsData(String route) throws IOException{
        products.clear();
        this.route = route;

        List<String> lines = Files.readAllLines(Paths.get(route));
        for(String line : lines){
            if(line.trim().isEmpty()) continue;
            Product p = Product.addProductByLine(line);
            products.add(p);
        }
    }


    public List<Product> getProducts() {return products;}

    public Product searchProduct(String name) {
        return products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void EditProductsFromFile(String route) throws IOException{
        try (PrintWriter writer = new PrintWriter(new FileWriter(route))) {
            for (Product p : products) {
                if (p.getStock() > 0) {
                    writer.printf("%s: %d, $%.2f, $%.2f, %s%n",
                            p.getName(),
                            p.getStock(),
                            p.getRegularPrice(),
                            p.getMembersPrice(),
                            p.getTaxable());
                }
            }
        }
    }

    public String getRoute() {
        return route;
    }
}
