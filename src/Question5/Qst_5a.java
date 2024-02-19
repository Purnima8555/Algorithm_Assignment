// 5a.Implement ant colony algorithm solving travelling a salesman problem


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package Question5;

// Algorithm Steps:
// 1.Initialize parameters such as the number of nodes, number of ants, alpha, beta, evaporation rate, and
//   initial pheromone.
// 2.Initialize the pheromone matrix and distance matrix.
// 3.For each ant, construct a path from the source node to the destination node.
// 4.Select the next node for each ant based on pheromone levels and distances.
// 5.Update the pheromone levels on edges based on the paths constructed by the ants.
// 6.Determine the shortest path among all paths constructed by the ants.
// 7.Repeat the process for a certain number of iterations or until convergence criteria are met.
// 8.Print the shortest path found and its length.

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Qst_5a {
    private double[][] pheromoneMatrix;
    private int[][] distanceMatrix;
    private int numberOfNodes;
    private int numberOfAnts;
    private int sourceNode;
    private int destinationNode;
    private double alpha;
    private double beta;
    private double evaporationRate;
    private double initialPheromone;

    public Qst_5a(int numberOfNodes, int numberOfAnts, int sourceNode, int destinationNode,
                  double alpha, double beta, double evaporationRate, double initialPheromone) {
        this.numberOfNodes = numberOfNodes;
        this.numberOfAnts = numberOfAnts;
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        this.initialPheromone = initialPheromone;
        pheromoneMatrix = new double[numberOfNodes][numberOfNodes];
        distanceMatrix = new int[numberOfNodes][numberOfNodes];
    }

    // Initialize the pheromone matrix with initial values
    public void initializePheromoneMatrix() {
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                pheromoneMatrix[i][j] = initialPheromone;
            }
        }
    }

    // Initialize the distance matrix with given distances between nodes
    public void initializeDistanceMatrix(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    // Find the shortest path by iterating over ants
    public List<Integer> findShortestPath() {
        Random random = new Random();
        List<Integer> bestPath = null;
        int bestDistance = Integer.MAX_VALUE;
        for (int iteration = 0; iteration < numberOfAnts; iteration++) {
            List<Integer> antPath = constructAntPath(sourceNode, destinationNode, random);
            int antDistance = calculatePathDistance(antPath);
            if (antDistance < bestDistance) {
                bestDistance = antDistance;
                bestPath = antPath;
            }
            updatePheromoneTrail(antPath, antDistance);
        }
        return bestPath;
    }

    // Path for a single ant
    private List<Integer> constructAntPath(int source, int destination, Random random) {
        List<Integer> antPath = new ArrayList<>();
        boolean[] visitedNodes = new boolean[numberOfNodes];
        int currentNode = source;
        antPath.add(currentNode);
        visitedNodes[currentNode] = true;
        while (currentNode != destination) {
            int nextNode = selectNextNode(currentNode, visitedNodes, random);
            antPath.add(nextNode);
            visitedNodes[nextNode] = true;
            currentNode = nextNode;
        }
        return antPath;
    }

    // Select the next node for an ant based on pheromone levels and distances
    private int selectNextNode(int currentNode, boolean[] visitedNodes, Random random) {
        double[] probabilities = new double[numberOfNodes];
        double probabilitiesSum = 0.0;
        for (int node = 0; node < numberOfNodes; node++) {
            if (!visitedNodes[node]) {
                double pheromoneLevel = Math.pow(pheromoneMatrix[currentNode][node], alpha);
                double distance = 1.0 / Math.pow(distanceMatrix[currentNode][node], beta);
                probabilities[node] = pheromoneLevel * distance;
                probabilitiesSum += probabilities[node];
            }
        }
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;
        for (int node = 0; node < numberOfNodes; node++) {
            if (!visitedNodes[node]) {
                double probability = probabilities[node] / probabilitiesSum;
                cumulativeProbability += probability;
                if (randomValue <= cumulativeProbability) {
                    return node;
                }
            }
        }
        return -1;
    }

    // Calculate the total distance of a path
    private int calculatePathDistance(List<Integer> path) {
        int distance = 0;
        int pathSize = path.size();
        for (int i = 0; i < pathSize - 1; i++) {
            int currentNode = path.get(i);
            int nextNode = path.get(i + 1);
            distance += distanceMatrix[currentNode][nextNode];
        }
        return distance;
    }

    // Update the pheromone trail based on the distance of the path taken by the ant
    private void updatePheromoneTrail(List<Integer> path, int distance) {
        double pheromoneDeposit = 1.0 / distance;
        for (int i = 0; i < path.size() - 1; i++) {
            int currentNode = path.get(i);
            int nextNode = path.get(i + 1);
            pheromoneMatrix[currentNode][nextNode] = (1 - evaporationRate) *
                    pheromoneMatrix[currentNode][nextNode] + evaporationRate * pheromoneDeposit;
        }
    }

    public static void main(String[] args) {
        int[][] distanceMatrix = {
                {0, 2, 3, 4},
                {2, 0, 6, 1},
                {3, 6, 0, 2},
                {4, 1, 2, 0}
        };
        int numberOfNodes = 4;
        int numberOfAnts = 10;
        int sourceNode = 0;
        int destinationNode = 3;
        double alpha = 1.0;
        double beta = 2.0;
        double evaporationRate = 0.5;
        double initialPheromone = 0.1;
        Qst_5a TSP = new Qst_5a(numberOfNodes, numberOfAnts,
                sourceNode, destinationNode,
                alpha, beta, evaporationRate, initialPheromone);
        TSP.initializePheromoneMatrix();
        TSP.initializeDistanceMatrix(distanceMatrix);
        List<Integer> shortestPath = TSP.findShortestPath();
        System.out.println("Shortest path: " + shortestPath);
    }
}

// output = {0, 1, 3}

// Steps Explained:
// 1. Define a Java class for the Ant Colony Optimization algorithm.
// 2. Initialize private instance variables to store necessary data such as pheromone and distance matrices, number
//    of nodes, number of ants, source and destination nodes, alpha, beta, evaporation rate, and initial pheromone level.
// 3. Implement a constructor method to initialize the algorithm with provided parameters.
// 4. Create a method to initialize the pheromone matrix with the initial pheromone level.
// 5. Implement a method to initialize the distance matrix with provided data.
// 6. Define a method to find the shortest path using Ant Colony Optimization.
// 7. Inside this method, initialize a random number generator and variables to store the best path and its distance.
// 8. Iterate through each ant to construct paths from source to destination nodes.
// 9. Calculate the total distance of each ant's path and update the best path and its distance if the current
//    ant's path is shorter.
// 10. Update the pheromone trail based on the paths constructed by all ants.
// 11. Return the best shortest path found.
