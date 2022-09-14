import com.droid.Droid;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BattleArena {
    private final Droid firstDroid;
    private final Droid secondDroid;
    private Droid attacker;
    private Droid defender;


    private int currentRound = 0;

    public BattleArena(Droid firstDroid, Droid secondDroid) {
        this.firstDroid = firstDroid;
        this.secondDroid = secondDroid;
    }
    private void initFighters() {  // визначення хто атакує, а хто приймає удар
        Random random = new Random();
        if (random.nextBoolean()) {
            attacker = secondDroid;
            defender = firstDroid;
        } else {
            attacker = firstDroid;
            defender = secondDroid;
        }
    }
    public Droid startFight() throws InterruptedException {

        System.out.println("\n\t\t  "+ firstDroid.getName() + " vs " + secondDroid.getName());
        do {
            prepareRound();
            int actualDamage = doFight(); // визначення актуальної атаки
            printRoundInfo(actualDamage);

            TimeUnit.SECONDS.sleep(2);
        } while (defender.isAlive());

        return defender;
    }

    private void prepareRound() {
        initFighters();
        currentRound++;
        System.out.print("\n--------------Fighting--------------\t Round " + currentRound + "\n");
    }

    // функція повертає к-сть атаки, яку отримав defender
    private int doFight() { return defender.getHit(attacker.getDamage()); }

    private void printRoundInfo(int actualDamage) { // виведення інформації про раунд битви
        System.out.println(attacker.getName() + " -- making--damage -- " + defender.getName());
        System.out.println(defender.getName() + " get " + actualDamage + " damage ");
        System.out.println("\n| Name: " + defender.getName() + " \t " + defender.getHealth() + " HP ");
        System.out.println("| Name: " + attacker.getName() + " \t " + attacker.getHealth() + "  HP ");
    }

}