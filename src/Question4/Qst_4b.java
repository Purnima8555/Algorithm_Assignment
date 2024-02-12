package Question4;

import java.util.*;

public class Qst_4b {

    static class TreeNode {
        double val;
        TreeNode left, right;

        public TreeNode(double val) {
            this.val = val;
            this.left = this.right = null;
        }
    }

    public static List<Double> closestValues(TreeNode root, double target, int x) {
        List<Double> closest = new ArrayList<>();
        if (root == null || x == 0) {
            return closest;
        }

        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode curr = root;

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
                // If current value is closer to target than farthest value in closest list
                if (Math.abs(curr.val - target) < Math.abs(closest.get(0) - target)) {
                    closest.remove(0);
                    closest.add(curr.val);
                } else {
                    break; // Stop traversal if further nodes cannot provide closer values
                }
            }

            curr = curr.right;
        }

        return closest;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        double target = 3.8;
        int x = 2;
        List<Double> closestValues = closestValues(root, target, x);
        System.out.println("Closest values to target: " + closestValues); // Output: [3.0, 4.0]
    }
}
