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

    // Show form at homepage
    @GetMapping("/")
    public String home() {
        return "enter-numbers";
    }

    // Show page to enter numbers
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
        for (String num : numArray) {
            bst.insert(Integer.parseInt(num.trim()));
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
        return "{\"value\":" + node.value + ",\"left\":" + convertBSTToJson(node.left) + ",\"right\":" + convertBSTToJson(node.right) + "}";
    }
}
