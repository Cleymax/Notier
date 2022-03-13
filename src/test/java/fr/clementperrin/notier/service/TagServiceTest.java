package fr.clementperrin.notier.service;

import fr.clementperrin.notier.model.Tag;
import fr.clementperrin.notier.repository.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    private Tag t1 = new Tag(1L, "PROG");
    private Tag t2 = new Tag(2L, "CUISINE");
    private Tag t3 = new Tag(3L, "JAVA");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(tagRepository.findAll()).thenReturn(Arrays.asList(t1, t2, t3));
    }

    @AfterEach
    void tearDown() {
        Mockito.framework().clearInlineMocks();
    }

    @Test
    void getTag() {
        given(tagRepository.findById(1L)).willReturn(Optional.of(t1));

        Optional<Tag> result = tagService.getTag(1L);

        assertTrue(result.isPresent());
        assertEquals("PROG", result.get().getName());

        verify(tagRepository, times(1)).findById(1L);
    }

    @Test
    void getTags() {
        Set<Tag> result = tagService.getTags();
        assertEquals(3, result.size());

        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void deleteTag() {
        tagService.deleteTag(2L);

        verify(tagRepository, times(1)).deleteById(2L);
    }

    @Test
    void saveTag() {
        given(tagRepository.save(t3)).willReturn(t3);

        Tag result = tagService.saveTag(t3);

        assertNotNull(result);

        verify(tagRepository, times(1)).save(t3);
    }
}
