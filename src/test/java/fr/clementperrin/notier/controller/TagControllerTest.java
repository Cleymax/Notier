package fr.clementperrin.notier.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.clementperrin.notier.model.Tag;
import fr.clementperrin.notier.service.TagService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TagController.class)
class TagControllerTest {

    @Autowired
    private ObjectMapper mapper;

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
        mockMvc.perform(get("/tags").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(3)))
               .andDo(print())
               .andReturn();
    }

    @Test
    void searchTag() throws Exception {
        mockMvc.perform(get("/tags/search").param("q", "prog").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].name", is("PROG")))
               .andExpect(jsonPath("$[0].id", is(1)))
               .andDo(print())
               .andReturn();
    }

    @Test
    void getTagById() throws Exception {
        mockMvc.perform(get("/tags/2"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name", is("CUISINE")))
               .andExpect(jsonPath("$.id", is(2)))
               .andDo(print())
               .andReturn();
    }

    @Test
    void createTag() throws Exception {
        Tag tag = new Tag(null, "IUT Annecy");

        when(tagService.saveTag(tag)).thenReturn(tag);

        mockMvc.perform(post("/tags").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(tag)))
               .andExpect(status().isCreated())
               .andDo(print())
               .andReturn();
    }

    @Test
    void deleteTag() throws Exception {
        mockMvc.perform(delete("/tags/1"))
               .andExpect(status().isNoContent())
               .andDo(print())
               .andReturn();
    }

    @Test
    void updateTag() throws Exception {

        Tag t2 = new Tag(2L, "KOUIZINE");

        when(tagService.getTag(t2.getId())).thenReturn(Optional.of(t2));
        when(tagService.saveTag(t2)).thenReturn(t2);

        mockMvc.perform(put("/tags/2").content(this.mapper.writeValueAsString(t2)).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name", is("KOUIZINE")))
               .andDo(print())
               .andReturn();
    }
}
