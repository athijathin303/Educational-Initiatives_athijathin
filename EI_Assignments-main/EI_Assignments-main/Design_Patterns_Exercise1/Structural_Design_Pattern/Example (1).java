// Existing system
class OldPaymentSystem {
    public void pay(int amount) {
        System.out.println("Paid $" + amount + " using OldPaymentSystem");
    }
}

// New system interface
interface NewPaymentGateway {
    void makePayment(double amount);
}

// Adapter
class PaymentAdapter implements NewPaymentGateway {
    private OldPaymentSystem oldSystem;

    public PaymentAdapter(OldPaymentSystem oldSystem) { this.oldSystem = oldSystem; }

    @Override
    public void makePayment(double amount) {
        oldSystem.pay((int) amount);
    }
}

// Demo
public class AdapterDemo {
    public static void main(String[] args) {
        OldPaymentSystem oldSystem = new OldPaymentSystem();
        NewPaymentGateway gateway = new PaymentAdapter(oldSystem);

        gateway.makePayment(150.75);
    }
}
