// 4b. You are provided with balanced binary tree with the target value k. return x number of values that are
// closest to the given target k. provide solution in O(n)
// Note: You have only one set of unique values x in binary search tree that are closest to the target.
// Input:
// K=3.8
// x=2
// Output: 3,4


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package Question4;

// Algorithm Steps:
// 1.Initialize a class Node to represent nodes in the binary search tree (BST).
// 2.Define the closestValues method to find the x closest values to the target in the BST.
// 3.Check if the root node is null or if the number of closest values required (x) is zero.
// 4.Implement a method to find x the closest values to a given target in a binary search tree.
// 5.Traverse the binary search tree in in-order fashion using a stack.
// 6.Maintain a list to store the x closest values.
// 7.Compare each node's value with the target and update the list accordingly.
// 8.Return the list of x closest values.
// 9.Test the method with a sample binary search tree and target value.

import java.util.*;

public class Qst_4b {

    static class Node {
        int val;
        Node left, right;

        public Node(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }

    public static List<Integer> closestValues(Node root, double target, int x) {
        List<Integer> closest = new ArrayList<>();
        // Check if the tree is empty or x is zero
        if (root == null || x == 0) {
            return closest;
        }

        Deque<Node> stack = new LinkedList<>();
        Node curr = root;

        // Perform in-order traversal to traverse the BST
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop();

            if (closest.size() < x) {
                closest.add(curr.val);
            } else {
                // If current value is closer to target
                if (Math.abs(curr.val - target) < Math.abs(closest.get(0) - target)) {
                    closest.remove(0);
                    closest.add(curr.val);
                } else {
                    break; // Stop traversal if further nodes cannot provide closer values
                }
            }

            curr = curr.right;
        }

        // Return the closest list
        return closest;
    }

    public static void main(String[] args) {
        Node node = new Node(4);
        node.left = new Node(2);
        node.right = new Node(5);
        node.left.left = new Node(1);
        node.left.right = new Node(3);

        double target = 3.8;
        int x = 2;
        List<Integer> closestValues = closestValues(node, target, x);
        System.out.println("Closest values to target: " + closestValues);
    }
}

// output: 3, 4

// note:
// x = represents the number of values that are closest to the given target k
// k = represents target value i.e. input given

// Steps Explained:
// 1. Initialize a class `Node` to represent nodes in the binary search tree.
// 2. Define the `closestValues` method to find the x closest values to the target in the BST.
// 3. Create an empty list `closest` to store the closest values.
// 4. Check if the root node is null or if the number of closest values required (x) is zero. If so,
//    return the empty list.
// 5. Initialize a stack `stack` to perform an in-order traversal of the BST.
// 6. Start traversal with the current node as the root node.
// 7. While the current node is not null or the stack is not empty:
//  - Push all left child nodes of the current node onto the stack.
//  - Pop the top node from the stack and set it as the current node.
//  - If the size of the `closest` list is less than x, add the value of the current node to the list.
//  - Otherwise, compare the absolute difference between the value of the current node and the target with
//    the absolute difference between.
//  - If the absolute difference of the current node is smaller, remove the first element from the `closest`
//    list and add the value of the current node to the list.
//  - Otherwise, break the traversal loop.
//  - Set the current node to its right child.
// 8. Return the `closest` list containing the x closest values to the target in the BST.
// 9. In the main method:
//  - Call the `closestValues` method with the root of the tree, the target value, and the number of
//    closest values required (x).
//  - Print the list of closest values obtained from the `closestValues` method.
