//  3a. You are developing a student score tracking system that keeps track of scores from different assignments. The
//  ScoreTracker class will be used to calculate the median score from the stream of assignment scores. The class
//  should have the following methods:
//  • ScoreTracker() initializes a new ScoreTracker object.
//  • void addScore(double score) adds a new assignment score to the data stream.
//  • double getMedianScore() returns the median of all the assignment scores in the data stream. If the number
//  of scores is even, the median should be the average of the two middle scores.
//  Input:
//  ScoreTracker scoreTracker = new ScoreTracker();
//  scoreTracker.addScore(85.5);    // Stream: [85.5]
//  scoreTracker.addScore(92.3);    // Stream: [85.5, 92.3]
//  scoreTracker.addScore(77.8);    // Stream: [85.5, 92.3, 77.8]
//  scoreTracker.addScore(90.1);    // Stream: [85.5, 92.3, 77.8, 90.1]
//  double median1 = scoreTracker.getMedianScore(); // Output: 87.8  (average of 90.1 and 85.5)
//  scoreTracker.addScore(81.2);    // Stream: [85.5, 92.3, 77.8, 90.1, 81.2]
//  scoreTracker.addScore(88.7);    // Stream: [85.5, 92.3, 77.8, 90.1, 81.2, 88.7]
//  double median2 = scoreTracker.getMedianScore(); // Output: 87.1 (average of 88.7 and 85.5)


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package Question3;

// Algorithm Steps:
// 1.Create a class to handle score tracking and median calculation.
// 2.Maintain an ArrayList to store assignment scores.
// 3.Implement methods to add scores and calculate the median.
// 4.Sort the scores and find the median based on the size of the list.
// 5.Instantiate the class, add scores, and calculate the median in the main method.

import java.util.ArrayList;
import java.util.Collections;

public class Qst_3a {
    private ArrayList<Double> scores;

    // Constructor to initialize the ArrayList
    public Qst_3a() {
        scores = new ArrayList<>();
    }

    // Method to add a score to the ArrayList
    public void addScore(double score) {
        scores.add(score);
    }

    // Method to calculate the median score
    public double getMedianScore() {
        // Sort the ArrayList
        Collections.sort(scores);

        int size = scores.size();
        int middle = size / 2;

        if (size % 2 == 0) {
            // If the number of scores is even, return the average of the two middle scores
            return (scores.get(middle - 1) + scores.get(middle)) / 2.0;
        } else {
            // If the number of scores is odd, return the middle score
            return scores.get(middle);
        }
    }

    public static void main(String[] args) {
        Qst_3a scoreTracker = new Qst_3a();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore();
        // Calculate and display the median
        System.out.println("Median 1: " + median1);

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore();
        // Calculate and display the new median
        System.out.println("Median 2: " + median2);
    }
}

// output: m1=87.8, m2=87.1

// Steps Explained:
// 1. Define a public class to encapsulate the functionality for tracking scores and calculating the median.
// 2. Create a private ArrayList named scores to store the assignment scores.
// 3. Define a constructor method Qst_3a() to initialize the ArrayList scores.
// 4. Create a method addScore(double score) to add a new assignment score to the ArrayList.
// 5. Create a method getMedianScore() to calculate the median score.
// 6. Sort the scores ArrayList in ascending order using a sorting algorithm.
// 7. Calculate the size of the scores ArrayList.
// 8. Determine the index of the middle element in the sorted ArrayList.
// 9. Check if the size of the scores ArrayList is even or odd.
// 10. If the size is even, calculate the median by averaging the two middle scores.
// 11. If the size is odd, the median is the middle score.
// 12. In the main method:
// a. Create an instance of Qst_3a class to create a new instance of the score tracker.
// b. Add assignment scores using the addScore() method to populate the scores ArrayList.
// c. Retrieve the median score using the getMedianScore() method and print it.
// d. Add more assignment scores and recalculate the median to verify the functionality.