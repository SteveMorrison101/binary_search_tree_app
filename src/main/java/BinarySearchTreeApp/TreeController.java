package BinarySearchTreeApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class TreeController {

    @Autowired
    private TreeRepository treeRepository;

    @GetMapping("/")
    public String home() {
        return "enter-numbers";
    }

    @GetMapping("/enter-numbers")
    public String enterNumbers() {
        return "enter-numbers";
    }

    // Show all previous trees from the database
    @GetMapping("/previous-trees")
    public String viewSubmissions(Model model) {
        model.addAttribute("trees", treeRepository.findAll());
        return "previous-trees";
    }

    // Create BST from entered numbers and save to database
    @PostMapping("/create-tree")
    public String createTree(@RequestParam("numbers") String numbers, Model model) {
        BinarySearchTree bst = new BinarySearchTree();

        // Insert numbers into BST
        String[] numArray = numbers.split(",");
        try {
            for (String num : numArray) {
                String trimmed = num.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                bst.insert(Integer.parseInt(trimmed));
            }
        } catch (NumberFormatException ex) {
            // Error handling for invalid inputs
            model.addAttribute("errorMessage", "Invalid input. Please enter only whole numbers separated by commas (e.g., 5,3,7).");
            model.addAttribute("previousInput", numbers);
            return "enter-numbers";
        }

        // Convert BST to JSON
        String treeJson = convertBSTToJson(bst.root);

        // Save to database
        TreeEntity entity = new TreeEntity();
        entity.setInputNumbers(numbers);
        entity.setTreeJson(treeJson);
        treeRepository.save(entity);

        // Pass data to view
        model.addAttribute("inputNumbers", numbers);
        model.addAttribute("treeJson", treeJson);

        return "view-tree";
    }

    // Convert BST to JSON format
    public String convertBSTToJson(TreeNode node) {
        if (node == null) {
            return "null";
        }
        return "{\"value\":" + node.value
                + ",\"left\":" + convertBSTToJson(node.left)
                + ",\"right\":" + convertBSTToJson(node.right) + "}";
    }
}

