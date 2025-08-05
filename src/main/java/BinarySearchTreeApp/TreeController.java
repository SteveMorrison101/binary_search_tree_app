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

    // Load form at home page
    @GetMapping("/")
    public String home() {
        return "EnterNumbers";
    }

    @GetMapping("/enterNumbers")
    public String enterNumbers() {
        return "EnterNumbers";
    }

    @PostMapping("/createTree")
    public String createTree(@RequestParam("numbers") String numbers, Model model) {
        BinarySearchTree bst = new BinarySearchTree();
        String[] numArray = numbers.split(",");
        for (String num : numArray) {
            bst.insert(Integer.parseInt(num.trim()));
        }
        String treeJson = convertBSTToJson(bst.root);
        TreeEntity entity = new TreeEntity();
        entity.setInputNumbers(numbers);
        entity.setTreeJson(treeJson);
        treeRepository.save(entity);
        model.addAttribute("treeJson", treeJson);
        return "ViewTree";
    }

    @GetMapping("/viewSubmissions")
    public String viewSubmissions(Model model) {
        model.addAttribute("submissions", treeRepository.findAll());
        return "ViewSubmissions";
    }

    private String convertBSTToJson(TreeNode node) {
        if (node == null) {
            return "null";
        }
        return "{\"value\":" + node.value + ",\"left\":" + convertBSTToJson(node.left) + ",\"right\":" + convertBSTToJson(node.right) + "}";
    }
}




