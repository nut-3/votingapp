package ru.topjava.votingapp.web.restaurant.menu;

import lombok.experimental.UtilityClass;
import ru.topjava.votingapp.MatcherFactory;
import ru.topjava.votingapp.model.Dish;
import ru.topjava.votingapp.model.LunchMenu;

import java.time.LocalDate;

@UtilityClass
public class MenuTestData {
    public static final MatcherFactory.Matcher<LunchMenu> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(LunchMenu.class, "id", "restaurant", "dishes.id", "dishes.menu");

    public static final LunchMenu pushkinLunchMenu1 = new LunchMenu(1, LocalDate.of(2021, 8, 22));
    public static final LunchMenu pushkinLunchMenu2 = new LunchMenu(2, LocalDate.of(2021, 8, 30));
    public static final LunchMenu mcdonaldsLunchMenu1 = new LunchMenu(3, LocalDate.of(2021, 8, 22));
    public static final LunchMenu mcdonaldsLunchMenu2 = new LunchMenu(4, LocalDate.of(2021, 8, 30));
    public static final LunchMenu kebabLunchMenu1 = new LunchMenu(5, LocalDate.of(2021, 8, 22));
    public static final LunchMenu kebabLunchMenu2 = new LunchMenu(6, LocalDate.of(2021, 8, 30));

//    public static final Dish pushkinDish1 = new Dish(1, "Bouillabaisse", 1500);
//    public static final Dish pushkinDish2 = new Dish(2, "Poached Egg", 2300);
//    public static final Dish pushkinDish3 = new Dish(3, "Ratatouille", 1850);
//    public static final Dish pushkinDish4 = new Dish(4, "Nicoise", 1300);
//    public static final Dish pushkinDish5 = new Dish(5, "Fish with a Creamy Sauce", 2270.3);
//    public static final Dish pushkinDish6 = new Dish(6, "Tart Taten", 2050.21);
//
//    public static final Dish mcdonaldsDish1 = new Dish(7, "Big Mac", 250.11);
//    public static final Dish mcdonaldsDish2 = new Dish(8, "Cherry Pie", 170.04);
//    public static final Dish mcdonaldsDish3 = new Dish(9, "McFlurry", 90.09);
//    public static final Dish mcdonaldsDish4 = new Dish(10, "BigTasty", 320.34);
//    public static final Dish mcdonaldsDish5 = new Dish(11, "Nuggets", 120.00);
//    public static final Dish mcdonaldsDish6 = new Dish(12, "French Fries", 60);
//
//    public static final Dish kebabDish1 = new Dish(13, "Shawarma", 100);
//    public static final Dish kebabDish2 = new Dish(14, "Shawerma", 70);
//    public static final Dish kebabDish3 = new Dish(15, "Shavurma", 20);
//    public static final Dish kebabDish4 = new Dish(16, "Chicken in pita bread", 120);
//    public static final Dish kebabDish5 = new Dish(17, "Meat in pita", 100);
//    public static final Dish kebabDish6 = new Dish(18, "Flatbread", 150);

    public static final Dish pushkinDish1 = new Dish("Bouillabaisse", 1500);
    public static final Dish pushkinDish2 = new Dish("Poached Egg", 2300);
    public static final Dish pushkinDish3 = new Dish("Ratatouille", 1850);
    public static final Dish pushkinDish4 = new Dish("Nicoise", 1300);
    public static final Dish pushkinDish5 = new Dish("Fish with a Creamy Sauce", 2270.3);
    public static final Dish pushkinDish6 = new Dish("Tart Taten", 2050.21);

    public static final Dish mcdonaldsDish1 = new Dish("Big Mac", 250.11);
    public static final Dish mcdonaldsDish2 = new Dish("Cherry Pie", 170.04);
    public static final Dish mcdonaldsDish3 = new Dish("McFlurry", 90.09);
    public static final Dish mcdonaldsDish4 = new Dish("BigTasty", 320.34);
    public static final Dish mcdonaldsDish5 = new Dish("Nuggets", 120.00);
    public static final Dish mcdonaldsDish6 = new Dish("French Fries", 60);

    public static final Dish kebabDish1 = new Dish("Shawarma", 100);
    public static final Dish kebabDish2 = new Dish("Shawerma", 70);
    public static final Dish kebabDish3 = new Dish("Shavurma", 20);
    public static final Dish kebabDish4 = new Dish("Chicken in pita bread", 120);
    public static final Dish kebabDish5 = new Dish("Meat in pita", 100);
    public static final Dish kebabDish6 = new Dish("Flatbread", 150);

    static {
        pushkinLunchMenu1.addDishes(pushkinDish1, pushkinDish2, pushkinDish3);
        pushkinLunchMenu2.addDishes(pushkinDish4, pushkinDish5, pushkinDish6);
        mcdonaldsLunchMenu1.addDishes(mcdonaldsDish1, mcdonaldsDish2, mcdonaldsDish3);
        mcdonaldsLunchMenu2.addDishes(mcdonaldsDish4, mcdonaldsDish5, mcdonaldsDish6);
        kebabLunchMenu1.addDishes(kebabDish1, kebabDish2, kebabDish3);
        kebabLunchMenu2.addDishes(kebabDish4, kebabDish5, kebabDish6);
    }

    public static LunchMenu getNewMenu() {
        LunchMenu newLunchMenu = new LunchMenu(null, LocalDate.of(2021, 8, 27));
        newLunchMenu.addDishes(new Dish("New dish1", 150.25),
                new Dish("New dish2", 750.9),
                new Dish("New dish3", 300));
        return newLunchMenu;
    }
}
