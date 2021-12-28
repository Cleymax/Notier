package fr.clementperrin.notier.controller;

import fr.clementperrin.notier.model.Tag;
import fr.clementperrin.notier.service.TagService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TagController.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    private final Tag t1 = new Tag(1L, "PROG");
    private final Tag t2 = new Tag(2L, "CUISINE");
    private final Tag t3 = new Tag(3L, "JAVA");

    @BeforeEach
    void setUp() {
        when(tagService.getTags()).thenReturn(new HashSet<>(Arrays.asList(t1, t2, t3)));
        when(tagService.getTag(2L)).thenReturn(Optional.of(t2));
    }

    @AfterEach
    void setDown() {
        Mockito.framework().clearInlineMocks();
    }

    @Test
    void getAllTags() throws Exception {
        mockMvc.perform(get("/tags")).andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    void searchTag() throws Exception {
        mockMvc.perform(get("/tags/search").param("q", "prog")).andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    void getTagById() {
    }

    @Test
    void createTag() {
    }

    @Test
    void deleteTag() {
    }

    @Test
    void updateTag() {
    }
}
