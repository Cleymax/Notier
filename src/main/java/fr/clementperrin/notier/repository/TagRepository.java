package fr.clementperrin.notier.repository;

import fr.clementperrin.notier.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class TagRepository
 * Created on 25/12/2021
 *
 * @author Clément PERRIN <contact@clementperrin.fr>
 */
@Repository
public interface TagRepository extends CrudRepository<Tag,Long> {

}
