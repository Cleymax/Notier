package fr.clementperrin.notier.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class NoteNotFoundException
 * Created on 25/12/2021
 *
 * @author Clément PERRIN <contact@clementperrin.fr>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoteNotFoundException extends NotFoundException {

    public NoteNotFoundException(Long id) {
        super("Note not found with ID: " + id);
    }
}
