package tree.bst.medium;

import tree.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yiyun On 2020/6/13 06:24
 *
 * 230. Kth Smallest Element in a BST
 */
public class KthSmallestElementInABST {

    // BST : inorder traversal of BST is an array sorted in the ascending order

    public int kthSmallest(TreeNode root, int k) {
        return kthSmallest_iterative(root, k);
    }

    public int kthSmallest_recursive(TreeNode root, int k) {
        List<Integer> res = new LinkedList<>();
        inOrder(res, root, k);
        return res.get(k-1);
    }

    private void inOrder(List<Integer> res, TreeNode root, int k) {
        if (root == null || res.size() == k)
            return;

        inOrder(res, root.left, k);
        res.add(root.val);
        inOrder(res, root.right, k);
    }

    // Iteration is more efficient than recursion
    public int kthSmallest_iterative(TreeNode root, int k) {
        LinkedList<TreeNode> stack = new LinkedList<>();

        while (true) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (--k == 0)
                return root.val;
            root = root.right;
        }
    }

}
