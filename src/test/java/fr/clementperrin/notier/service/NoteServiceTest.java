package fr.clementperrin.notier.service;

import fr.clementperrin.notier.model.Note;
import fr.clementperrin.notier.repository.NoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private final Note n1 = new Note(1L, "Java mon ami", "Java != Javascript");
    private final Note n2 = new Note(2L, "Un bain parfumé", "Ne pas oublié de mettre de l'eau dans le bain");
    private final Note n3 = new Note(3L, null, "Tableau start at: Ruby = 1, Java = 0");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(noteRepository.findAll()).thenReturn(Arrays.asList(n1, n2, n3));
        when(noteRepository.findById(1L)).thenReturn(Optional.of(n1));
    }

    @AfterEach
    void tearDown() {
        Mockito.framework().clearInlineMocks();
    }

    @Test
    void getNote() {
    }

    @Test
    void getNotes() {
    }

    @Test
    void getTag() {
    }

    @Test
    void saveNote() {
    }

    @Test
    void deleteNote() {
    }
}
