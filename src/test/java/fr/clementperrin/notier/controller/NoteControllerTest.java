package fr.clementperrin.notier.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.clementperrin.notier.model.Note;
import fr.clementperrin.notier.model.Tag;
import fr.clementperrin.notier.service.NoteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    private final Note n1 = new Note(1L, "Java mon ami", "Java != Javascript");
    private final Note n2 = new Note(2L, "Un bain parfumé", "Ne pas oublié de mettre de l'eau dans le bain");
    private final Note n3 = new Note(3L, null, "Tableau start at: Ruby = 1, Java = 0");

    @BeforeEach
    void setUp() {
        when(noteService.getNotes()).thenReturn(new HashSet<>(Arrays.asList(n1, n2, n3)));
        when(noteService.getNote(2L)).thenReturn(Optional.of(n2));
    }

    @AfterEach
    void setDown() {
        Mockito.framework().clearInlineMocks();
    }

    @Test
    void getAllNotes() throws Exception {
        mockMvc.perform(get("/notes"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(3)))
               .andDo(print())
               .andReturn();
    }

    @Test
    void getNoteById() throws Exception {
        mockMvc.perform(get("/notes/2"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content", is("Ne pas oublié de mettre de l'eau dans le bain")))
               .andExpect(jsonPath("$.id", is(2)))
               .andDo(print())
               .andReturn();
    }

    @Test
    void createNote() throws Exception {
        Note note = new Note(null, "Test", "Test Content");

        when(noteService.saveNote(note)).thenReturn(note);

        mockMvc.perform(post("/notes").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(note)))
               .andExpect(status().isCreated())
               .andDo(print())
               .andReturn();
    }

    @Test
    void deleteNote() throws Exception {
        mockMvc.perform(delete("/notes/1")).andExpect(status().isNoContent()).andDo(print()).andReturn();

        Mockito.verify(noteService).deleteNote(1L);

        //verify that the note is deleted
        mockMvc.perform(get("/notes/1")).andExpect(status().isBadRequest()).andDo(print()).andReturn();
    }

    @Test
    void updateNote() throws Exception {
        Note note = new Note(2L, "Test", "Test Content");

        when(noteService.getNote(2L)).thenReturn(Optional.of(note));
        when(noteService.saveNote(note)).thenReturn(note);

        mockMvc.perform(put("/notes/2").contentType(MediaType.APPLICATION_JSON)
                                       .content(mapper.writeValueAsString(note)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content", is("Test Content")))
               .andExpect(jsonPath("$.id", is(2)))
               .andDo(print())
               .andReturn();
    }

    @Test
    void getTagOfNote() throws Exception {
        // Get Tag of note  with id=2
        mockMvc.perform(get("/notes/2/tags"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", empty()))
               .andDo(print())
               .andReturn();
    }

    @Test
    void addTagOffNote() throws Exception {
        Tag tag = new Tag(null, "tag");

        when(noteService.getNote(2L)).thenReturn(Optional.of(n2));
        when(noteService.saveTag(tag)).thenReturn(tag);
        when(noteService.saveNote(n2)).thenReturn(n2);

        mockMvc.perform(post("/notes/2/tags").contentType(MediaType.APPLICATION_JSON)
                                             .content(mapper.writeValueAsString(tag)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is(2)))
               .andExpect(jsonPath("$.tags", hasSize(1)))
               .andDo(print())
               .andReturn();
    }
}
