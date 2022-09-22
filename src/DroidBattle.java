import com.droid.Droid;
import java.io.*;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

import com.droid.PrintFromFile;
import org.apache.commons.io.output.TeeOutputStream;

public class DroidBattle {
    BattleArena arena;
    List<Droid> fstTeam = new ArrayList<>();
    List<Droid> sndTeam = new ArrayList<>();
    List<Droid> droids = createTypeOfDroids();
    List<Droid> userDroids = new ArrayList<>();    // ліст для зберігання дроїдів введених користувачем

    public List<Droid> createTypeOfDroids(){   // створення кастомних дроїдів

        List<Droid> droids = new ArrayList<>();
        droids.add(new Droid("Tank", 150, 60));
        droids.add(new Droid("Doctor", 70, 90));
        droids.add(new Droid("Warrior", 120,85));
        droids.add(new Droid("Pancer", 60, 130));
        droids.add(new Droid("Ninja", 200, 35));
        droids.add(new Droid("Lovkash", 70, 135));

        return droids;
    }

    /* Функція проведення битви */
    public void battle() throws InterruptedException{

        Scanner in = new Scanner(System.in);
        int choice;

        while(true) {
            // меню гри
            System.out.print("""
                                    
                    Menu \s
                    Enter 1 for |1 vs 1|
                    Enter 2 for |team vs team|
                    Enter 3 for printing user droids\s
                    Enter 4 for printing battle from file\s
                    Enter 5 for exiting from game\s""".indent(1));

            choice = in.nextInt();

            if (choice == 1) {
                create1vs1();
            }
            else if (choice == 2) {
                int half = createTeams();
                takeFighters(half);
            }
            else if (choice == 3) {
                System.out.println(userDroids);
            }
            else if (choice == 4){
                PrintFromFile fileData = new PrintFromFile();
                fileData.PrintFight();
            }
            else if (choice == 5){
                System.out.print("\n\t The Game is over!");
                break;
            }

        }

    }

    /* Функція створення бою 1 на 1 */
    private void create1vs1() throws InterruptedException {
        int choice = createOrChose(); // якщо функція повертає 1, то користувач створює дроїдів,
                                        // якщо 2, то вибирає з наявних
        Droid first, second;

        if (choice == 1) {
            System.out.println(" 1st player: ");
            first = inputDroidInfo();
            userDroids.add(first);

            System.out.println(" 2nd player: ");
            second = inputDroidInfo();
            userDroids.add(second);

            droids.addAll(userDroids);

        } else {
            System.out.println(droids);
            System.out.println(" 1st player: ");
            first = choseCharacter();
            droids.remove(first);

            System.out.println(" 2nd player: ");
            second = choseCharacter();
            droids.remove(second);
        }
        arena = new BattleArena(first, second); // створення арени бою

        ByteArrayOutputStream buffer = StartPrintToFile();

        Droid loser = arena.startFight(); // запуск бою
        System.out.println("\n-----------------------------------");
        System.out.println(" The loser is " + loser.getName()+"\n");

        EndPrintToFile(buffer);
    }
    private int createOrChose() { // функція що повертає вибір користувача
        Scanner in = new Scanner(System.in);
        int choice;
        do {
            System.out.print("""
                    
                    What you want to do?\s
                    1 - to create droids
                    2 - to choose droids
                    \s""".indent(1));
            choice = in.nextInt();
        } while (choice != 2 && choice != 1);
        return choice;
    }
    private Droid inputDroidInfo() {  // введення інформації дроїда,
                                        // якщо він створюється користувачем
        Scanner in = new Scanner(System.in);
        Droid dr = new Droid();

        System.out.println(" Enter name: ");
        dr.setName(in.nextLine());
        System.out.println(" Enter health: ");
        dr.setHealth(in.nextInt());
        System.out.println(" Enter damage: ");
        dr.setDamage(in.nextInt());
        return dr;
    }
    private Droid choseCharacter() { // вибір дроїда
        Scanner in = new Scanner(System.in);

        System.out.print(" Enter name for droid: ");
        return findDroid(in.nextLine()); // повернення дроїда якого обрав користувач
    }
    public Droid findDroid(String name) { // пошук і повернення обраного дроїда

        String droidName;

        for (Droid droid : droids) {
            droidName = droid.getName().toLowerCase();
            if (droidName.equals(name.toLowerCase())) { // порівняння
                return droid;
            }
        }
        return null;
    }
    private int createTeams(){  // створення команд
        Scanner in = new Scanner(System.in);

        System.out.print(" Enter number of droids: ");
        int amount = in.nextInt();
        int half = amount / 2;  // змінна для к-сті дроїдів для одної команди

        int choice = createOrChose();

        if(choice == 1){  // створення дроїдів для команд

            System.out.println("\n Input players of 1st team: ");
            createTeam(half, fstTeam);

            half = amount / 2;
            System.out.println(" Input players of 2nd team: ");
            createTeam(half, sndTeam);

            System.out.println("\n First team: " + fstTeam);
            System.out.println("\n Second team: " + sndTeam);

        }

        else if(choice == 2){   // вибір дроїдів для команд

            System.out.println("\n Input players of 1st team: ");
            choseTeam(half, fstTeam);

            half = amount / 2;
            System.out.println("\n Input players of 2nd team: ");
            choseTeam(half, sndTeam);

            System.out.println("\n First team: " + fstTeam);
            System.out.println("\n Second team: " + sndTeam);
        }

        return amount / 2;
    }

    /* Функція вибору бійців з кожної команди */
    private void takeFighters(int half) throws InterruptedException {

        Random random = new Random();
        half = half - 1 ;
        int i = random.nextInt(0,(half)); // для рандомного вибору перших бійців
        Droid first = fstTeam.get(i), second = sndTeam.get(i);

        ByteArrayOutputStream buffer = StartPrintToFile();

        while (true) {  // цикл поки в якоїсь команди не залишиться бійців

            arena = new BattleArena(first, second);
            Droid dead = arena.startFight();
            System.out.println( "\n" + dead.getName() + " is dead...\n");

            if (fstTeam.contains(dead)) { // якщо боєць, що загинув належав 1 команді

                fstTeam.remove(dead);  // видалення його
               // second.setHealth(second.getHealth()) ; // записування здоров'я бійця, що бився
                if (!fstTeam.isEmpty())
                    first = fstTeam.get(0); // бійцем стає найперший дроїд в 1 команді

            }
            else { // якщо боєць, що загинув належав 2 команді

                sndTeam.remove(dead);
               // first.setHealth(first.getHealth());
                if (!sndTeam.isEmpty())
                    second = sndTeam.get(0);
            }

            if (fstTeam.isEmpty()) { // якщо 1 команда пуста, відповідно виграла 2

                System.out.println("\n Second team win the game!");
                break; // вихід з циклу, бо є переможець
            }
            else if (sndTeam.isEmpty()){ // якщо 2 команда пуста, відповідно виграла 1

                System.out.println("\n First team win the game!");
                break;
            }
        }
        EndPrintToFile(buffer);
    }

    public void createTeam(int half, List<Droid> Team){ // створення команди
        do {

            Droid temp = inputDroidInfo();
            Team.add(temp);
            userDroids.add(temp);
            half--;
        }while (half != 0 );

        droids.addAll(userDroids);
    }

    public void choseTeam(int half, List<Droid> Team){ // вибір команди

        System.out.println(droids);

        while(half != 0){
            Droid temp = choseCharacter();
            Team.add(temp);
            droids.remove(temp);
            half--;
        }
    }

    public ByteArrayOutputStream StartPrintToFile(){
        // для запису консолі у текстовий документ
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        OutputStream teeStream = new TeeOutputStream(System.out, buffer);
        System.setOut(new PrintStream(teeStream));

        return buffer;
    }
    public void EndPrintToFile(ByteArrayOutputStream buffer){
        // Збереження буферу в текстовий документ
        try(OutputStream fileStream = new FileOutputStream("console.txt")){
            buffer.writeTo(fileStream);
            System.out.println(" \n\n The Game is successfully written in file! ");
        } catch(IOException e){
            System.out.println("error");
        }
    }
}
