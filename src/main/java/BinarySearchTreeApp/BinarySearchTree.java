package BinarySearchTreeApp;

public class BinarySearchTree {
    TreeNode root;

    // Constructor
    public BinarySearchTree() {
        root = null;
    }

    // Insert value into BST (recursive)
    private TreeNode insert(TreeNode currentNode, int value) {
        if (currentNode == null) {
            TreeNode newNode = new TreeNode();
            newNode.value = value;
            return newNode;
        } else if (value <= currentNode.value) {
            currentNode.left = insert(currentNode.left, value);
        } else {
            currentNode.right = insert(currentNode.right, value);
        }
        return currentNode;
    }

    // Public method to insert a value
    void insert(int value) {
        root = insert(root, value);
    }

    // In-order traversal
    public void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.print(node.value + " ");
        inOrder(node.right);
    }

    // Get root node
    public TreeNode getRoot() {
        return root;
    }
}

