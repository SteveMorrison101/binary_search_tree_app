package BinarySearchTreeApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TreeController {

    // Display the form to enter numbers
    @GetMapping("/enter-numbers")
    public String showForm() {
        return "enterNumbers";
    }

}
