package BinarySearchTreeApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TreeRepository treeRepository;

    // Test that the "Enter Numbers" page loads successfully
    @Test
    public void testEnterNumbersPageLoads() throws Exception {
        mockMvc.perform(get("/enter-numbers"))
                .andExpect(status().isOk());
    }

    // Test that a tree can be created and stored in the database
    @Test
    public void testCreateTreeStoresInDatabase() throws Exception {
        mockMvc.perform(post("/create-tree")
                        .param("numbers", "5,3,7"))
                .andExpect(status().isOk());

        List<TreeEntity> trees = treeRepository.findAll();
        assertThat(trees.size()).isGreaterThan(0);
        assertThat(trees.get(trees.size() - 1).getInputNumbers()).isEqualTo("5,3,7");
    }

    // Test that the page showing previous trees loads successfully
    @Test
    public void testViewSubmissionsPageLoads() throws Exception {
        mockMvc.perform(get("/previous-trees"))
                .andExpect(status().isOk());
    }

    // Test that the convertBSTToJson method produces correct JSON
    @Test
    public void testConvertBSTToJson() {
        TreeNode node = new TreeNode();
        node.value = 5;

        TreeNode leftNode = new TreeNode();
        leftNode.value = 3;

        TreeNode rightNode = new TreeNode();
        rightNode.value = 7;

        node.left = leftNode;
        node.right = rightNode;

        TreeController controller = new TreeController();
        String json = controller.convertBSTToJson(node);

        assertThat(json).contains("\"value\":5");
        assertThat(json).contains("\"value\":3");
        assertThat(json).contains("\"value\":7");
    }
}


