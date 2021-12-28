package fr.clementperrin.notier.controller;

import fr.clementperrin.notier.model.Note;
import fr.clementperrin.notier.service.NoteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

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
        when(noteService.getNote(3L)).thenReturn(Optional.of(n3));
    }

    @AfterEach
    void setDown() {
        Mockito.framework().clearInlineMocks();
    }

    @Test
    void getAllNotes() throws Exception {
        mockMvc.perform(get("/notes")).andExpect(status().isOk()).andDo(print()).andReturn();
    }

    @Test
    void getNoteById() {
    }

    @Test
    void createNote() {
    }

    @Test
    void deleteNote() {
    }

    @Test
    void updateNote() {
    }

    @Test
    void getTagOfNote() {
    }

    @Test
    void addTagOffNote() {
    }

    @Test
    void addTagWithIdOfNote() {
    }

    @Test
    void removeTagWithIdOfNote() {
    }
}
