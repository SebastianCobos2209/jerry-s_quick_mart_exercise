package models.Inventory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Inventory {
    private final List<Product> products = new ArrayList<Product>();

    public void UploadProductsData(String route) throws IOException{
        products.clear();

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
}
