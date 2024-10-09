/*
 * HWScheduler.java
 *
 * Author: John Baldor
 * Submitted on: 9/25/24
 *
 * Academic Honesty Declaration:
 *
 * The following code represents my own work and I have neither received nor given assistance
 * that violates the collaboration policy posted with this assignment. I have not copied or modified code
 * from any other source other than the homework assignment, course textbook, or course lecture slides.
 * Any unauthorized collaboration or use of materials not permitted will be subjected to academic integrity policies of
 * WPI and CS 2223.
 *
 * I acknowledge that this homework assignment is based upon an assignment created by WPI and that any publishing or
 * posting of this code is prohibited unless I receive written permission from WPI.
 */
package wpialgs.hw03;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

/**
 * <p>
 * This is a scheduler that uses the "Earliest Deadline First" strategy to schedule activities.
 * </p>
 *
 * @version 1.0
 */
public class HWScheduler {

    /**
     * The main entry point to the program.
     *
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("USAGE: java HWScheduler <filename>");
        } else {

            String filename = args[0];
            int time = 0;
            int runTime = 0;
            In in = new In(filename);

            StdOut.println("Welcome to CS 2223 - Earliest Deadline First Scheduler!");
            StdOut.printf("\tRunning scheduler on file: %s.\n", filename);
            StdOut.println("-------------------------------------------------------");

            MinPQ<Activity> activities = new MinPQ<>();
            String line = in.readLine();


            while (line != null) {
                String[] array = line.split(" ");


                if (array[0].equals("schedule")) {
                    Activity activity = new Activity(array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3]));
                    activities.insert(activity);
                    StdOut.println("[ " + time + " ]: " + "adding " + activity.name + " with deadline " + activity.deadline + " and duration " + activity.duration);
                }


                // run the schedule
                if (array[0].equals("run")) {
                    runTime += Integer.parseInt(array[1]);

                    while (time <= runTime && !activities.isEmpty()) {
                        Activity current = activities.delMin();
                        StdOut.println("[ " + time + " ]: working on " + current.name + " with deadline " + current.deadline + " and duration " + current.duration);


                        if (time + current.duration <= runTime) {


                            if (time + current.duration <= current.deadline) {
                                StdOut.println("[ " + (time + current.duration) + " ]: done with " + current.name + " on time!");
                            }

                            else {
                                StdOut.println("[ " + (time + current.duration) + " ]: done with " + current.name + ", but completed it late!");
                            }
                            time += current.duration;

                        }


                        else {
                            StdOut.println("[ " + runTime + " ]: time is up! adding back " + current.name + " with deadline " + current.deadline + " and duration " + (current.duration - (runTime - time)));
                            activities.insert(new Activity(current.name, current.deadline, (current.duration - (runTime - time))));

                            time = runTime;
                            break;
                        }
                    }
                }
                line = in.readLine();
            }
            StdOut.println();

            if (activities.isEmpty()) {
                StdOut.println("Finished all jobs!");
            }

            else {
                StdOut.println("Never finished the following jobs:");
                int index = 1;
                while (!activities.isEmpty()) {
                    StdOut.println(index + ". " + activities.delMin().name);
                }
            }
        }
    }

    /**
     * <p>
     * This helper class stores an activity's information.
     * </p>
     */
    private static class Activity implements Comparable<Activity> {

        // Class attributes
        String name;
        int deadline;
        int duration;

        /**
         * This creates a new activity.
         *
         * @param name
         *            Activity's name
         * @param deadline
         *            Activity's deadline
         * @param duration
         *            Activity's duration
         */
        public Activity(String name, int deadline, int duration) {
            this.name = name;
            this.deadline = deadline;
            this.duration = duration;
        }

        /**
         * This implements the {@link Comparable#compareTo(Object)} method to check the ordering of two objects.
         *
         * @param o
         *            Object to be compared.
         *
         * @return {@code < 0} if this activity is less than {@code o}, {@code > 0} if this activity is greater than
         *         {@code o}, and {@code 0} if they are equal.
         */
        @Override
        public int compareTo(Activity o) {
            return Integer.compare(this.deadline, o.deadline); // compare using deadlines
        }

        /**
         * This overrides the default {@link Object#equals(Object)} method to perform an equality test on two objects.
         *
         * @param o
         *            Object to be compared.
         *
         * @return {@code true} if all the fields are equal, {@code false} otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Activity activity = (Activity) o;
            return deadline == activity.deadline && duration == activity.duration && name.equals(activity.name);
        }

        /**
         * This overrides the default {@link Object#hashCode()} method to produce a hash code for the node.
         *
         * @return The hash code associated with the object.
         */
        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + deadline;
            result = 31 * result + duration;
            return result;
        }

        /**
         * This overrides the default {@link Object#toString()} method to produce a string representation of a node.
         *
         * @return A string representation of the stack.
         */
        @Override
        public String toString() {
            return name + " with deadline " + deadline + " and duration " + duration;
        }
    }
}
