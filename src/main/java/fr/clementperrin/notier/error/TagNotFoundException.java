package fr.clementperrin.notier.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class TagNotFoundException
 * Created on 25/12/2021
 *
 * @author Clément PERRIN <contact@clementperrin.fr>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TagNotFoundException extends NotFoundException {

    public TagNotFoundException(Long id) {
        super("Tag not found with ID: " + id);
    }
}
