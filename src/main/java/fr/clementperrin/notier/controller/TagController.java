package fr.clementperrin.notier.controller;

import fr.clementperrin.notier.error.TagNotFoundException;
import fr.clementperrin.notier.model.Tag;
import fr.clementperrin.notier.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class TagController
 * Created on 25/12/2021
 *
 * @author Clément PERRIN <contact@clementperrin.fr>
 */
@RestController
@RequestMapping(path = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping()
    public Set<Tag> getAllTags() {
        return this.tagService.getTags();
    }

    @GetMapping("/search")
    public Set<Tag> searchTag(@RequestParam(value = "q") String q) {
        var tags = this.tagService.getTags();

        return tags.stream()
                   .filter(tag -> tag.getName().toLowerCase().strip().contains(q.toLowerCase().strip()))
                   .collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable("id") Long id) {
        return this.tagService.getTag(id).orElseThrow(() -> new TagNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tag createTag(@Valid @RequestBody Tag tag) {
        return this.tagService.saveTag(tag);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable("id") Long id) {
        this.tagService.deleteTag(id);
    }

    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable("id") Long id, Tag newTag) {
        var tag = this.tagService.getTag(id).orElseThrow(() -> new TagNotFoundException(id));

        if (newTag.getName() != null) tag.setName(newTag.getName());

        return this.tagService.saveTag(tag);
    }

}
