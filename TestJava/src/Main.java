import java.util.ArrayList;
import java.util.List;

// ==========================================
// 1. ПАТТЕРН OBSERVER (Наглядач)
// ==========================================
interface Observer {
    void update(String message);
}

class User implements Observer {
    private String name;
    public User(String name) { this.name = name; }
    @Override
    public void update(String message) {
        System.out.println("Сповіщення для " + name + ": " + message);
    }
}

// ==========================================
// 2. ПАТТЕРН BRIDGE (Міст)
// ==========================================
interface DeviceAction {
    void performAction();
}

class TurnOnAction implements DeviceAction {
    public void performAction() { System.out.print("Увімкнено "); }
}

abstract class SmartDevice {
    protected DeviceAction action;
    protected List<Observer> observers = new ArrayList<>();

    protected SmartDevice(DeviceAction action) { this.action = action; }

    public void addObserver(Observer o) { observers.add(o); }
    public abstract void execute();

    protected void notifyObservers(String deviceName) {
        for (Observer o : observers) o.update(deviceName + " активовано!");
    }
}

// ==========================================
// 3. ПАТТЕРН ABSTRACT FACTORY (Абстрактна фабрика)
// ==========================================
class Light extends SmartDevice {
    public Light(DeviceAction action) { super(action); }
    public void execute() {
        action.performAction();
        System.out.println("Світло.");
        notifyObservers("Світло");
    }
}

class Socket extends SmartDevice {
    public Socket(DeviceAction action) { super(action); }
    public void execute() {
        action.performAction();
        System.out.println("Розетка.");
        notifyObservers("Розетка");
    }
}

interface SmartHomeFactory {
    SmartDevice createPrimaryDevice(DeviceAction action);
}

class KitchenFactory implements SmartHomeFactory {
    public SmartDevice createPrimaryDevice(DeviceAction action) { return new Socket(action); }
}

class LivingRoomFactory implements SmartHomeFactory {
    public SmartDevice createPrimaryDevice(DeviceAction action) { return new Light(action); }
}

// ==========================================
// ГОЛОВНИЙ КЛАС
// ==========================================
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Лабораторна робота №3 (Варіант 23) ---");

        // Створюємо спостерігача (User)
        User admin = new User("Адміністратор");

        // 1. Використання Abstract Factory для створення пристроїв
        SmartHomeFactory kitchen = new KitchenFactory();
        SmartHomeFactory livingRoom = new LivingRoomFactory();

        // 2. Використання Bridge (передаємо дію "TurnOnAction" у пристрій)
        DeviceAction on = new TurnOnAction();

        SmartDevice device1 = kitchen.createPrimaryDevice(on);
        SmartDevice device2 = livingRoom.createPrimaryDevice(on);

        // 3. Використання Observer
        device1.addObserver(admin);
        device2.addObserver(admin);

        // Запуск пристроїв
        System.out.println("Активація системи:");
        device1.execute();
        device2.execute();

        System.out.println("------------------------------------------");
    }
}