package fr.clementperrin.notier.service;

import fr.clementperrin.notier.model.Tag;
import fr.clementperrin.notier.repository.TagRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Class TagServiceImpl
 * Created on 25/12/2021
 *
 * @author Clément PERRIN <contact@clementperrin.fr>
 */
@Service
@Data
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Optional<Tag> getTag(final Long id) {
        return this.tagRepository.findById(id);
    }

    public Set<Tag> getTags() {
        return StreamSupport.stream(this.tagRepository.findAll().spliterator(), false).collect(Collectors.toSet());
    }

    public void deleteTag(final Long id){
        this.tagRepository.deleteById(id);
    }

    public Tag saveTag(Tag tag) {
        return this.tagRepository.save(tag);
    }
}
