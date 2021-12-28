package fr.clementperrin.notier.service;

import fr.clementperrin.notier.model.Note;
import fr.clementperrin.notier.model.Tag;
import fr.clementperrin.notier.repository.NoteRepository;
import fr.clementperrin.notier.repository.TagRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Class NoteServiceImpl
 * Created on 28/12/2021
 *
 * @author Clément PERRIN <contact@clementperrin.fr>
 */
@Service
@Data
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private TagRepository tagRepository;

    public Optional<Note> getNote(final Long id) {
        return this.noteRepository.findById(id);
    }

    public Set<Note> getNotes() {
        return StreamSupport.stream(this.noteRepository.findAll().spliterator(), false).collect(Collectors.toSet());
    }

    public Optional<Tag> getTag(final Long id) {
        return this.tagRepository.findById(id);
    }

    public Note saveNote(Note note) {
        return this.noteRepository.save(note);
    }

    public void deleteNote(final Long id) {
        this.noteRepository.deleteById(id);
    }
}
