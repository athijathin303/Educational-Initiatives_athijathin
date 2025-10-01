interface Drone {
    void deliver(String packageInfo);
}

class CargoDrone implements Drone {
    @Override
    public void deliver(String packageInfo) {
        System.out.println("Cargo drone delivering: " + packageInfo);
    }
}

class SurveyDrone implements Drone {
    @Override
    public void deliver(String packageInfo) {
        System.out.println("Survey drone inspecting: " + packageInfo);
    }
}

class DroneFactory {
    public static Drone getDrone(String type) {
        switch (type) {
            case "cargo": return new CargoDrone();
            case "survey": return new SurveyDrone();
            default: throw new IllegalArgumentException("Unknown drone type");
        }
    }
}

// Demo
public class FactoryDemo {
    public static void main(String[] args) {
        Drone cargo = DroneFactory.getDrone("cargo");
        Drone survey = DroneFactory.getDrone("survey");

        cargo.deliver("Electronics");
        survey.deliver("Construction Site");
    }
}
