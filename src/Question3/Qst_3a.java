package Question3;

import java.util.Collections;
import java.util.PriorityQueue;

public class Qst_3a {
    private PriorityQueue<Double> minHeap; // Store the larger half of scores
    private PriorityQueue<Double> maxHeap; // Store the smaller half of scores

    public Qst_3a() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    }

    public void addScore(double score) {
        // Add score to appropriate heap
        if (maxHeap.isEmpty() || score <= maxHeap.peek()) {
            maxHeap.offer(score);
        } else {
            minHeap.offer(score);
        }

        // Balance the heaps
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double getMedianScore() {
        // If the total number of scores is even, return the average of the two middle scores
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else { // If the total number of scores is odd, return the middle score
            return maxHeap.peek();
        }
    }

    public static void main(String[] args) {
        Qst_3a scoreTracker = new Qst_3a();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore();
        System.out.println("Median 1: " + median1); // Output: 88.9

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore();
        System.out.println("Median 2: " + median2); // Output: 86.95
    }
}

// m1=87.8
// m2=87.1
