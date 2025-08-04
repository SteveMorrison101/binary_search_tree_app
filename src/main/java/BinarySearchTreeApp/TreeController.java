package BinarySearchTreeApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class TreeController {

    @Autowired
    private TreeRepository treeRepository;

    // Route to show the EnterNumbers.html page
    @GetMapping("/enter-numbers")
    public String enterNumbers() {
        return "EnterNumbers"; // This will load templates/EnterNumbers.html
    }

    // Route to process the numbers entered by the user
    @PostMapping("/process-numbers")
    public Map<String, Object> processNumbers(@RequestParam("numbers") String numbers) {
        // Create a new BST
        BinarySearchTree bst = new BinarySearchTree();

        // Split the string and insert numbers into the BST
        String[] numberArray = numbers.split(",");
        for (int i = 0; i < numberArray.length; i++) {
            try {
                int value = Integer.parseInt(numberArray[i].trim());
                bst.insert(value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number skipped: " + numberArray[i]);
            }
        }

        // Convert BST to JSON-like Map
        Map<String, Object> treeJson = new HashMap<>();
        treeJson.put("root", nodeToMap(bst.root));

        // Save input and tree structure to database
        TreeEntity treeEntity = new TreeEntity();
        treeEntity.setInputNumbers(numbers);
        treeEntity.setTreeJson(treeJson.toString());
        treeRepository.save(treeEntity);

        // Return tree JSON to the user
        return treeJson;
    }

    // Convert a BinaryNode to a Map (recursively)
    private Map<String, Object> nodeToMap(TreeNode node) {
        if (node == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("value", node.value);
        map.put("left", nodeToMap(node.left));
        map.put("right", nodeToMap(node.right));
        return map;
    }
}

