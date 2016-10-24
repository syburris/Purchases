package com.YoungMoney;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by stevenburris on 10/24/16.
 */

@Controller
public class PurchasesRestController {

    static final String PURCHASES = "purchases.csv";
    static final String CUSTOMERS = "customers.csv";

    @Autowired
    PurchasesRepo purchases;

    @Autowired
    CustomerRepo customers;

    @PostConstruct
    public void init() {
        if(customers.count() == 0) {
            File file = new File(CUSTOMERS);
            Scanner fileScanner = null;
            try {
                fileScanner = new Scanner(file);
                while (fileScanner.hasNext()) {
                    String line = fileScanner.nextLine();
                    String[] columns = line.split("\\,");
                    Customer customer = new Customer(columns[0],columns[1]);
                    customers.save(customer);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (purchases.count() == 0) {
            File file2 = new File(PURCHASES);
            Scanner fileScanner2 = null;
            try {
                fileScanner2 = new Scanner(file2);
                while (fileScanner2.hasNext()) {
                    String line = fileScanner2.nextLine();
                    String[] columns = line.split("\\,");
                    String date = columns[1];
                    String creditCard = columns[2];
                    int cvv = Integer.valueOf(columns[3]);
                    String category = columns[4];
                    Purchase purchase = new Purchase(date,creditCard,cvv,category,customers.findOne(Integer.valueOf(columns[0])));
                    purchases.save(purchase);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, String category) {
        List<Purchase> purchaseList;
        if(category != null) {
            purchaseList = purchases.findByCategory(category);
        }
        else {
            purchaseList = (List<Purchase>) purchases.findAll();
        }
        model.addAttribute("purchases", purchaseList);
        return "home";
    }

}
