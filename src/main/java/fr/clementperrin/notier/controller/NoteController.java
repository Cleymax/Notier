package fr.clementperrin.notier.controller;

import fr.clementperrin.notier.error.NoteNotFoundException;
import fr.clementperrin.notier.model.Note;
import fr.clementperrin.notier.model.Tag;
import fr.clementperrin.notier.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * Class NoteController
 * Created on 25/12/2021
 *
 * @author Clément PERRIN <contact@clementperrin.fr>
 */
@RestController
@RequestMapping(path = "/notes", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping()
    public Set<Note> getAllNotes() {
        return this.noteService.getNotes();
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return this.noteService.getNote(id).orElseThrow(() -> new NoteNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note createNote(@Valid @RequestBody Note note) {
        return this.noteService.saveNote(note);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable Long id) {
        this.noteService.deleteNote(id);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable Long id, @Valid @RequestBody Note newNote) {
        var note = this.noteService.getNote(id).orElseThrow(() -> new NoteNotFoundException(id));

        if (newNote.getTitle() != null) note.setTitle(newNote.getTitle());
        if (newNote.getContent() != null) note.setContent(newNote.getContent());
        if (newNote.getTags() != null && newNote.getTags().isEmpty()) note.setTags(newNote.getTags());

        return this.noteService.saveNote(note);
    }

    @GetMapping("/{id}/tags")
    public Set<Tag> getTagOfNote(@PathVariable Long id) {
        return this.noteService.getNote(id).orElseThrow(() -> new NoteNotFoundException(id)).getTags();
    }

    @PostMapping("/{id}/tags")
    public Note addTagOffNote(@PathVariable Long id, @Valid @RequestBody Tag tag) {
        var note = this.noteService.getNote(id).orElseThrow(() -> new NoteNotFoundException(id));
        this.noteService.saveTag(tag);
        note.getTags().add(tag);

        return this.noteService.saveNote(note);
    }

    @PostMapping("/{id}/tags/{tagId}")
    public Note addTagWithIdOfNote(@PathVariable Long id, @PathVariable Long tagId) {
        var note = this.noteService.getNote(id).orElseThrow(() -> new NoteNotFoundException(id));
        var tag = this.noteService.getTag(tagId).orElseThrow(() -> new NoteNotFoundException(id));

        note.getTags().add(tag);

        return this.noteService.saveNote(note);
    }

    @DeleteMapping("/{id}/tags/{tagId}")
    public Note removeTagWithIdOfNote(@PathVariable("id") Long id, @PathVariable("tagId") Long tagId) {
        var note = this.noteService.getNote(id).orElseThrow(() -> new NoteNotFoundException(id));

        note.getTags()
            .stream()
            .filter(tag -> tag.getId().equals(tagId))
            .findFirst()
            .ifPresent(tag -> note.getTags().remove(tag));

        return this.noteService.saveNote(note);
    }
}
