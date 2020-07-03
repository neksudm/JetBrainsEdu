package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Machine machine = new Machine(400,540,120,9,550);
        while (machine.state != Machine.State.EXIT) {
            if (machine.state == Machine.State.MENU)
            {
                System.out.println("Write action (buy, fill, take, remaining, exit): ");
            }
            machine.Process(sc.nextLine());
        }
    }
}

class Machine {
    int availableWater;
    int availableMilk;
    int availableBeans;
    int availableMoney;
    int availableCups;
    State state;
    enum State { MENU, CHOOSE_TYPE, FILL_WATER, FILL_MILK, FILL_BEANS, FILL_CUPS, EXIT };

    public Machine (int water, int milk, int beans, int cups, int money) {
        this.availableWater = water;
        this.availableMilk = milk;
        this.availableBeans = beans;
        this.availableCups = cups;
        this.availableMoney = money;
        this.state = State.MENU;
    }

    public void makeCoffee(int water, int milk, int beans, int money) {
        boolean fail = false;
        if (availableWater - water < 0) {
            System.out.println("Sorry, not enough water!");
            fail = true;
        }
        if (availableMilk - milk < 0) {
            System.out.println("Sorry, not enough milk!");
            fail = true;
        }
        if (availableBeans - beans < 0) {
            System.out.println("Sorry, not enough coffee beans!");
            fail = true;
        }
        if (availableCups - 1 < 0) {
            System.out.println("Sorry, not enough disposable cups!");
            fail = true;
        }

        if (!fail) {
            System.out.println("I have enough resources, making you a coffee!");
            this.availableWater -= water;
            this.availableMilk -= milk;
            this.availableBeans -= beans;
            this.availableCups--;
            this.availableMoney += money;
        }
        System.out.println();
        state = State.MENU;
    }

    public void Process(String command) {
        switch (this.state) {
            case MENU:
                switch (command) {
                    case "buy":
                        System.out.println();
                        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
                        state = State.CHOOSE_TYPE;
                        break;
                    case "take":
                        System.out.println();
                        take();
                        System.out.println();
                        break;
                    case "fill":
                        System.out.println();
                        System.out.println("Write how many ml of water do you want to add:");
                        this.state = State.FILL_WATER;
                        break;
                    case "remaining":
                        System.out.println();
                        printState();
                        System.out.println();
                        break;
                    case "exit":
                        this.state = State.EXIT;
                        break;
                } break;
            case CHOOSE_TYPE: {
                switch (command) {
                    case "1":
                        makeCoffee(250, 0, 16, 4);
                        break;
                    case "2":
                        makeCoffee(350, 75, 20, 7);
                        break;
                    case "3":
                        makeCoffee(200, 100, 12, 6);
                        break;
                    case "back":
                        this.state = State.MENU;
                        System.out.println();
                        break;
                }
            } break;
            case FILL_WATER:
                availableWater += Integer.parseInt(command);
                System.out.println("Write how many ml of milk do you want to add:");
                state = State.FILL_MILK;
                break;
            case FILL_MILK:
                availableMilk += Integer.parseInt(command);
                System.out.println("Write how many grams of coffee beans do you want to add:");
                state = State.FILL_BEANS;
                break;
            case FILL_BEANS:
                availableBeans += Integer.parseInt(command);
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                state = State.FILL_CUPS;
                break;
            case FILL_CUPS:
                availableCups += Integer.parseInt(command);
                System.out.println();
                state = State.MENU;
                break;
            case EXIT:
                state = State.EXIT;
                break;
        }
    }

    public void take() {
        System.out.println("I gave you $" + availableMoney);
        availableMoney = 0;
    }

    public void printState() {
        System.out.println("The coffee machine has:\n" +
                availableWater + " of water\n" +
                availableMilk + " of milk\n" +
                availableBeans + " of coffee beans\n" +
                availableCups + " of disposable cups\n" +
                "$" + availableMoney + " of money");
    }
}
