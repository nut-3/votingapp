package com.github.nut3.votingapp.web.restaurant.menu;

import com.github.nut3.votingapp.MatcherFactory;
import com.github.nut3.votingapp.model.Dish;
import com.github.nut3.votingapp.model.LunchMenu;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class MenuTestData {
    public static final MatcherFactory.Matcher<LunchMenu> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(LunchMenu.class, "id", "date", "restaurant", "dishes.id", "dishes.menu", "$$_hibernate_interceptor");

    public static final LunchMenu pushkinLunchMenu1 = new LunchMenu(1, LocalDate.of(2021, 8, 22));
    public static final LunchMenu pushkinLunchMenu2 = new LunchMenu(2, LocalDate.of(2021, 8, 30));
    public static final LunchMenu pushkinLunchMenu3 = new LunchMenu(7, LocalDate.now());
    public static final LunchMenu mcdonaldsLunchMenu1 = new LunchMenu(3, LocalDate.of(2021, 8, 22));
    public static final LunchMenu mcdonaldsLunchMenu2 = new LunchMenu(4, LocalDate.of(2021, 8, 30));
    public static final LunchMenu mcdonaldsLunchMenu3 = new LunchMenu(8, LocalDate.now());
    public static final LunchMenu kebabLunchMenu1 = new LunchMenu(5, LocalDate.of(2021, 8, 22));
    public static final LunchMenu kebabLunchMenu2 = new LunchMenu(6, LocalDate.of(2021, 8, 30));
    public static final LunchMenu kebabLunchMenu3 = new LunchMenu(9, LocalDate.now());

    public static final Dish pushkinDish1 = new Dish("Bouillabaisse", 1500);
    public static final Dish pushkinDish2 = new Dish("Poached Egg", 2300);
    public static final Dish pushkinDish3 = new Dish("Ratatouille", 1850);
    public static final Dish pushkinDish4 = new Dish("Nicoise", 1300);
    public static final Dish pushkinDish5 = new Dish("Fish with a Creamy Sauce", 2270);
    public static final Dish pushkinDish6 = new Dish("Tart Taten", 2050);
    public static final Dish pushkinDish7 = new Dish("Duck breast", 5000);
    public static final Dish pushkinDish8 = new Dish("Ricotta gnocchi", 1700);
    public static final Dish pushkinDish9 = new Dish("Rostbif", 6200);

    public static final Dish mcdonaldsDish1 = new Dish("Big Mac", 250);
    public static final Dish mcdonaldsDish2 = new Dish("Cherry Pie", 170);
    public static final Dish mcdonaldsDish3 = new Dish("McFlurry", 90);
    public static final Dish mcdonaldsDish4 = new Dish("BigTasty", 320);
    public static final Dish mcdonaldsDish5 = new Dish("Nuggets", 120);
    public static final Dish mcdonaldsDish6 = new Dish("French Fries", 60);
    public static final Dish mcdonaldsDish7 = new Dish("French fries", 99);
    public static final Dish mcdonaldsDish8 = new Dish("Cheeseburger", 120);
    public static final Dish mcdonaldsDish9 = new Dish("Shrimp roll", 150);

    public static final Dish kebabDish1 = new Dish("Shawarma", 100);
    public static final Dish kebabDish2 = new Dish("Shawerma", 70);
    public static final Dish kebabDish3 = new Dish("Shavurma", 20);
    public static final Dish kebabDish4 = new Dish("Chicken in pita bread", 120);
    public static final Dish kebabDish5 = new Dish("Meat in pita", 100);
    public static final Dish kebabDish6 = new Dish("Flatbread", 150);
    public static final Dish kebabDish7 = new Dish("Kharcho", 120);
    public static final Dish kebabDish8 = new Dish("Khachapuri", 100);
    public static final Dish kebabDish9 = new Dish("Veg salad", 150);

    static {
        pushkinLunchMenu1.addDishes(pushkinDish1, pushkinDish2, pushkinDish3);
        pushkinLunchMenu2.addDishes(pushkinDish4, pushkinDish5, pushkinDish6);
        mcdonaldsLunchMenu1.addDishes(mcdonaldsDish1, mcdonaldsDish2, mcdonaldsDish3);
        mcdonaldsLunchMenu2.addDishes(mcdonaldsDish4, mcdonaldsDish5, mcdonaldsDish6);
        kebabLunchMenu1.addDishes(kebabDish1, kebabDish2, kebabDish3);
        kebabLunchMenu2.addDishes(kebabDish4, kebabDish5, kebabDish6);
        pushkinLunchMenu3.addDishes(pushkinDish7, pushkinDish8, pushkinDish9);
        mcdonaldsLunchMenu3.addDishes(mcdonaldsDish7, mcdonaldsDish8, mcdonaldsDish9);
        kebabLunchMenu3.addDishes(kebabDish7, kebabDish8, kebabDish9);
    }

    public static LunchMenu getNew() {
        LunchMenu newLunchMenu = new LunchMenu(null, LocalDate.of(2021, 8, 27));
        newLunchMenu.addDishes(new Dish("New dish1", 150),
                new Dish("New dish2", 750),
                new Dish("New dish3", 300));
        return newLunchMenu;
    }

    public static LunchMenu getUpdated() {
        LunchMenu newLunchMenu = new LunchMenu(pushkinLunchMenu2);
        newLunchMenu.setDishes(new Dish("Updated dish1", 2500),
                new Dish("Updated dish2", 7500),
                new Dish("Updated dish3", 3000));
        return newLunchMenu;
    }
}
