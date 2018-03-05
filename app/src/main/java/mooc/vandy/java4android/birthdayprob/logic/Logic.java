package mooc.vandy.java4android.birthdayprob.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import mooc.vandy.java4android.birthdayprob.ui.OutputInterface;

/**
 * This is where the logic of this App is centralized for this assignment.
 * <p>
 * The assignments are designed this way to simplify your early Android interactions.
 * Designing the assignments this way allows you to first learn key 'Java' features without
 * having to beforehand learn the complexities of Android.
 *
 */
public class Logic 
       implements LogicInterface {
    /**
     * This is a String to be used in Logging (if/when you decide you
     * need it for debugging).
     */
    public static final String TAG =
        Logic.class.getName();

    /**
     * This is the variable that stores our OutputInterface instance.
     * <p>
     * This is how we will interact with the User Interface
     * [MainActivity.java].
     * <p>
     * It is called 'mOut' because it is where we 'out-put' our
     * results. (It is also the 'in-put' from where we get values
     * from, but it only needs 1 name, and 'mOut' is good enough).
    */
    OutputInterface mOut;

    /**
     * This is the constructor of this class.
     * <p>
     * It assigns the passed in [MainActivity] instance
     * (which implements [OutputInterface]) to 'out'
     */
    public Logic(OutputInterface out){
        mOut = out;
    }

    /**
     * This is the method that will (eventually) get called when the
     * on-screen button labelled 'Process...' is pressed.
     */
    public void process() {
        int groupSize = mOut.getSize();
        int simulationCount = mOut.getCount();

        if (groupSize < 2 || groupSize > 365) {
            mOut.makeAlertToast("Group Size must be in the range 2-365.");
            return;
        }
        if (simulationCount <= 0) {
            mOut.makeAlertToast("Simulation Count must be positive.");
            return;
        }

        double percent = calculate(groupSize, simulationCount);

        // report results
        mOut.println("For a group of " + groupSize + " people, the percentage");
        mOut.println("of times that two people share the same birthday is");
        mOut.println(String.format("%.2f%% of the time.", percent));

    }

    /**
     * This is the method that actually does the calculations.
     * <p>
     * We provide you this method that way we can test it with unit testing.
     */
    public double calculate(int size, int count) {

        // count of matching birthday
        int collision = 0;

        // performing simulate
        for (int i = 0; i < count; i++) {

            // create random object
            Random rand = new Random();

            // set seed of random
            rand.setSeed(i + 1);

            // count pair of birthday
            if  (generateBirthdayPerson(size, rand)) {
                collision++;
            }
        }

        // return double percent of prop.
        return (collision * 100.0) / count;
    }

    /**
     *
     * @param size of group people
     * @return boolean pair of matching birthday
     */
    private boolean generateBirthdayPerson(int size, Random rand) {

        int DAYOFYEAR = 365;
        Set<Integer> persons = new HashSet<>();

        // find matching a group of person
        for (int i = 0; i < size; i++) {

            // random birthday
            Integer birthday = rand.nextInt(DAYOFYEAR);

            // check pair of birthday
            if (persons.contains(birthday)) {
                return true;
            } else {
                persons.add(birthday);
            }
        }

        return false;
    }
}
