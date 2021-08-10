package com.example.demo.entity;

import java.util.*;

public class CarRental {
    private String name;
    private String phone;
    private String area;
    private List<Car> items;
    private List<Customer> customers;

    private HashMap<Date,Customer> invoice;

    public CarRental(String name, String phone, String area) {
        this.name = name;
        this.phone = phone;
        this.area = area;
        this.customers = new ArrayList<>();
        this.items = new ArrayList<>();
        this.invoice = new HashMap<>();
    }

    //check if items exists before adding
    public int check_item(String item)
    {
        for(int i=0; i<items.size();i++)
        {
            if(items.get(i).getName().trim().toLowerCase().equals(item.trim().toLowerCase()))
            {
                return i;
            }
        }
        return -1;
    }

    //check if customer exists before adding
    public int check_customer(String customer)
    {
        for(int i=0; i<customers.size();i++)
        {
            if(customers.get(i).getLastname().toLowerCase().equals(customer.toLowerCase()))
            {
                return i;
            }
        }
        return -1;
    }

    //add customer to list
    public boolean add_cust(Customer cust) {
        int pos = check_customer(cust.getLastname());
        if(pos >= 0)
        {
            //customer already exists
            return false;
        }
        else
        {
            this.customers.add(cust);

        }
        return true;
    }

    //add car to list
    public boolean add_item(Car car) {
        int pos = check_customer(car.getName());
        if(pos >= 0)
        {
            //customer already exists
            return false;
        }
        else
        {
            this.items.add(car);
        }
        return true;
    }

    //add car to a specific customer
    public boolean add_item_cust(String cust, String item, int quantity) {
        int a = check_customer(cust);
        int b = check_item(item);
        if(a>=0 && b>=0) {
            Customer find = this.customers.get(a);
            Car find_item = this.items.get(b);
            HashMap<Car, Integer> cust_park = find.getPark().getPark();

            if(cust_park.containsKey(find_item)) {
                cust_park.put(find_item, cust_park.get(find_item) + quantity);
            }
            else
            {
                cust_park.put(find_item, quantity);
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean remove_item_cust(String cust, String item, int quantity) {
        int a = check_customer(cust);
        int b = check_item(item);
        if(a>=0 && b>=0) {
            Customer find = this.customers.get(a);
            Car find_item = this.items.get(b);
            HashMap<Car, Integer> cust_park = find.getPark().getPark();

            if(cust_park.containsKey(find_item)) {
                cust_park.remove(find_item);
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    public void print_bill(String customername) {
        int pos = check_customer(customername);

        if(pos >= 0) {
            Double totalbill = 0.0;
            Customer find = this.customers.get(pos);
            System.out.println("Customer name: " + find.getFirstname() + " " + find.getLastname() + "; Contact" + find.getPhone() + " " + find.getEmail());

            for(Map.Entry<Car,Integer> e:find.getPark().getPark().entrySet()) {
                System.out.println("Car name => " + e.getKey().getName() + ", Price: " + e.getKey().getValue() + ", Quantity => " + e.getValue() + ", Amount => " + e.getKey().getValue() * e.getValue());
                totalbill += e.getKey().getValue() * e.getValue();
            }

            if(totalbill != 0)
            {
                System.out.print("Total bill to pay => " + totalbill);
                find.setTotalbill(totalbill);
                this.invoice.put(new Date(), find);
            }
        }
    }

    public void print_invoice(String customer) {
        for(Map.Entry<Date, Customer> b:this.invoice.entrySet()) {
            if(b.getValue().getLastname().toLowerCase().equals(customer)) {
                Customer find = b.getValue();

                System.out.println("Date and Time: " + b.getValue().toString() + ", Customer name: " + find.getFirstname() + " " + find.getLastname() + "; Contact" + find.getPhone() + " " + find.getEmail());

                for(Map.Entry<Car,Integer> e:find.getPark().getPark().entrySet()) {
                    System.out.println("Car name => " + e.getKey().getName() + ", Price: " + e.getKey().getValue() + ", Quantity => " + e.getValue() + ", Amount => " + e.getKey().getValue() * e.getValue());
                    //totalbill += e.getKey().getValue() * e.getValue();
                }

                System.out.print("Total bill to pay => " + find.getTotalbill());
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getArea() {
        return area;
    }

    public List<Car> getItems() {
        return items;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public HashMap<Date, Customer> getInvoice() {
        return invoice;
    }
}
