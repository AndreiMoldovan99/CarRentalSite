package com.example.demo.controller;


import com.example.demo.entity.Car;
import com.example.demo.entity.CarRental;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Park;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
@RequestMapping("/renting")
public class HomeController {
    CarRental carRental = new CarRental("Cluj-Napoca Car Park", "074562314", "Romania");
    @PostConstruct
    public void addCustomer(){
        carRental.add_item(new Car("Chevrolet", 2, 300.00));
        carRental.add_item(new Car("BMW", 3, 500.00));
        carRental.add_item(new Car("Audi", 2, 450.00));
        carRental.add_item(new Car("Toyota", 5, 200.00));
        carRental.add_item(new Car("Mercedes", 3, 550.00));
        carRental.add_item(new Car("Volkswagen", 7, 200.00));
        carRental.add_item(new Car("Skoda", 2, 150.00));
        carRental.add_cust(new Customer("Andrei", "Moraru", "email@email.com", "0745155784", "Strada dincluj nr 3", new Park()));
        carRental.add_cust(new Customer("Andrei", "Moldovan", "mail@email.com", "0713546784", "Strada strazilor nr 25", new Park()));
        carRental.add_cust(new Customer("Marean", "Marinescu", "mailmail@email.com", "0754123964", "Aleea Viorelelor nr 25", new Park()));
    }

    @GetMapping("/init")
    public String homepage(Model model) {
        model.addAttribute("customers", carRental.getCustomers());
        return "home";
    }

    @GetMapping("/rent")
    public String customer_items(@RequestParam("customer") String name, Model model) {
        int pos = carRental.check_customer(name);
        if(pos > 0)
        {
            model.addAttribute("customer", name);
            model.addAttribute("cars", carRental.getItems());
            return "cust_list";
        }
        return "redirect:init";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());
        return "cust_form";
    }

    @PostMapping("/savecust")
    public  String save_cust(@ModelAttribute("customer") Customer customer) {
        if(customer.getFirstname() != null && customer.getLastname() != null && customer.getPhone() != null && customer.getEmail() != null && customer.getAddress() != null)
        {
            int pos = carRental.check_customer(customer.getLastname());
            Customer tosave = null;
            if(pos >= 0)
            {
                tosave = carRental.getCustomers().get(pos);
                tosave.setFirstname(customer.getFirstname());
                tosave.setLastname(customer.getLastname());
                tosave.setPhone(customer.getPhone());
                tosave.setEmail(customer.getEmail());
                tosave.setAddress(customer.getAddress());
                return "redirect:init";
            }
            else
            {
                tosave=customer;
                carRental.add_cust(tosave);
            }
        }
        return "redirect:init";
    }

    @GetMapping("/additem")
    public String add_item(Model model) {
        model.addAttribute("Items",new Car());
        return "item_form";
    }

    @GetMapping("/items")
    public String listitems(Model model){
        model.addAttribute("Items",carRental.getItems());
        return "items";
    }

    @PostMapping("/saveitem")
    public String save_item(@ModelAttribute("Item") Car car) {
        if ((car.getName() != null && car.getValue() != 0) && (car.getStock() != 0)) {
            int pos = carRental.check_item(car.getName());

            if (pos >= 0) {
                Car cars = carRental.getItems().get(pos);
                cars.setName(car.getName().trim());
                cars.setValue(car.getValue());
                cars.setStock(car.getStock());
                return "redirect:items";
            } else {
                carRental.add_item(car);
            }
        }
        return "redirect:items";
    }

    @PostMapping("/updateitem")
    public String update_item(@RequestParam("name") String[] name,@ModelAttribute("item") Car car){

        if ((car.getName() != null && car.getValue() != 0) && (car.getStock() != 0)) {
            int pos = carRental.check_item(name[0]);

            if (pos >= 0) {
                Car cars = carRental.getItems().get(pos);
                cars.setName(name[1]);
                cars.setValue(car.getValue());
                cars.setStock(car.getStock());
                return "redirect:items";
            } else {
                carRental.add_item(car);
            }
        }
        return "redirect:items";
    }

    @GetMapping("/itemupd")
    public String upd_item(@RequestParam("name") String name, Model model){
        if(name!=null){
            int pos=carRental.check_item(name);
            if(pos>=0){
                Car item = carRental.getItems().get(pos);
                model.addAttribute("Items",item);
                return "item_form_update";
            }
        }
        return "redirect:items";
    }

    @GetMapping("/itemdel")
    public String del_item(@RequestParam("name") String name){
        if(name!=null){
            int pos=carRental.check_item(name);
            if(pos>=0){
                carRental.getItems().remove(pos);
            }
        }
        return "redirect:items";
    }

    @GetMapping("/updcust")
    public String upd_cust(@RequestParam("customer") String name, Model model){
        if(name!= null){
            int pos = carRental.check_customer(name);
            if(pos>=0){
                Customer customer = carRental.getCustomers().get(pos);
                model.addAttribute("customer",customer);

                return "cust_update";
            }
        }
        return "init";
    }

    @PostMapping("/updatecust")
    public String savecust(@RequestParam("lastname") String[] name,@ModelAttribute("customer") Customer customer){
        if(customer.getFirstname() !=null && customer.getLastname() !=null && customer.getPhone() !=null && customer.getEmail() !=null && customer.getAddress() !=null){
            int pos = carRental.check_customer(name[0]);
            if(pos>=0){
                Customer customer1 = carRental.getCustomers().get(pos);
                customer1.setLastname(name[1]);
                customer1.setFirstname(customer.getFirstname());
                customer1.setPhone(customer.getPhone());
                customer1.setEmail(customer.getEmail());
                customer1.setAddress(customer.getAddress());

                return "redirect:init";
            }
        }

        return "redirect:init";
    }

    @GetMapping("/delcust")
    public String del_cust(@RequestParam("customer") String name){
        if(name!=null){
            int pos=carRental.check_customer(name);
            if(pos>=0){
                carRental.getCustomers().remove(pos);

            }
        }

        return "redirect:init";
    }

    @GetMapping("/additemcust")
    public String add_item_cust(@RequestParam("customer")String name,Model model){

        if(name!=null){

            int pos= carRental.check_customer(name);
            if(pos>=0){

                Customer customer=carRental.getCustomers().get(pos);
                model.addAttribute("customer",customer);
                model.addAttribute("cars",carRental.getItems());
            }

        }

        return "cust_items";
    }

    @GetMapping("/custitemupd")
    public String add_cust_item(@RequestParam("name")String item,@RequestParam("customer")String customer,Model model){

        if(item!=null && customer!=null){

            int pos=carRental.check_customer(customer);
            int pos1=carRental.check_item(item);
            if(pos>=0 && pos1>=0){

                model.addAttribute("Items",item);
                model.addAttribute("Customer",customer);
            }
        }

        return "add_cust_item";
    }

    @PostMapping("/savecustprod")
    public String add_cust_item(@RequestParam("item")String item,@RequestParam("customer")String customer,@RequestParam("quantity")String quantity,Model model){

        if(item!=null && customer!=null && quantity!=null){
            try{
                int pos=carRental.check_customer(customer);
                int pos1=carRental.check_item(item);
                if(pos>=0 && pos1>=0){

                    carRental.add_item_cust(customer,item,Integer.valueOf(quantity));
//                ModelAndView modelAndView=new ModelAndView();
                    model.addAttribute("name",item);
                    model.addAttribute("customer",customer);

                }
            }catch (Exception e){
                System.out.println("Exception occurs => "+e.getMessage());
            }finally {
                return "redirect:rent?customer="+customer;
            }

        }
        return "redirect:rent?customer="+customer;
    }

    @GetMapping("/custitemdel")
    public String del_item_cust(@RequestParam("name")String item,@RequestParam("customer")String customer,Model model){

        if(item!=null && customer!=null){

            int pos=carRental.check_customer(customer);
            int pos1=carRental.check_item(item);
            if(pos>=0 && pos1>=0){

                // model.addAttribute("Items",item);
                //  model.addAttribute("Customer",customer);
                carRental.getItems().remove(pos);
                carRental.getItems().remove(pos1);

            }

        }

        return "add_cust_item";

    }


    @GetMapping("/printbill")
    public String printbill(@RequestParam("customer") String customer, Model model){
        int pos = carRental.check_customer(customer);
        if(pos>=0){
            Double price=0.0;
            Customer customer1= carRental.getCustomers().get(pos);
            model.addAttribute("customer",customer1);
            model.addAttribute("park",customer1.getPark().getPark().entrySet());
            for(Map.Entry<Car,Integer>e:customer1.getPark().getPark().entrySet()){
                price += e.getKey().getValue()*e.getValue();
            }

            model.addAttribute("total",price);

        }
        return "printbill";
    }

    @GetMapping("/delitem")
    public String remove_item(@RequestParam("name") String name){
        if(name!=null){
            int pos=carRental.check_item(name);
            if(pos>=0){
                carRental.getItems().remove(pos);
            }
        }
        return "printbill";
    }

    @GetMapping("/pay")
    public String pay() {
        return "pay";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

}