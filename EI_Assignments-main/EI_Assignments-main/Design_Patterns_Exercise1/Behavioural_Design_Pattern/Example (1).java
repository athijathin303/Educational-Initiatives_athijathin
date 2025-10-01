import java.util.*;

interface Observer {
    void update(String item, int quantity);
}

interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}

class SmartFridge implements Subject {
    private Map<String, Integer> items = new HashMap<>();
    private List<Observer> observers = new ArrayList<>();

    public void addItem(String item, int quantity) {
        items.put(item, quantity);
        notifyObservers();
    }

    public void consumeItem(String item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) - quantity);
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer o) { observers.add(o); }

    @Override
    public void removeObserver(Observer o) { observers.remove(o); }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                if (entry.getValue() < 5) {
                    o.update(entry.getKey(), entry.getValue());
                }
            }
        }
    }
}

class User implements Observer {
    private String name;
    public User(String name) { this.name = name; }

    @Override
    public void update(String item, int quantity) {
        System.out.println("Hey " + name + "! Only " + quantity + " " + item + " left in your fridge!");
    }
}

// Demo
public class ObserverDemo {
    public static void main(String[] args) {
        SmartFridge fridge = new SmartFridge();
        User user = new User("Ananth");

        fridge.registerObserver(user);

        fridge.addItem("Milk", 10);
        fridge.consumeItem("Milk", 6);
        fridge.consumeItem("Eggs", 2); // Will notify if <5
    }
}
