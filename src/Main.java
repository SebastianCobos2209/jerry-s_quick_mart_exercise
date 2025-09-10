import models.Cart.CartItem;
import models.CheckOut.CheckOut;
import models.Customer.Customer;
import models.Customer.Member;
import models.Customer.Regular;
import models.Inventory.Inventory;
import models.Inventory.Product;
import models.Menus.MainMenu;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        MainMenu mainMenu = new MainMenu(scanner);
        mainMenu.runMainMenu();
    }

}