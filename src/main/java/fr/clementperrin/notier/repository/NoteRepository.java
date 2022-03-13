package fr.clementperrin.notier.repository;

import fr.clementperrin.notier.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class NoteRepository
 * Created on 25/12/2021
 *
 * @author Clément PERRIN <contact@clementperrin.fr>
 */
@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

}
