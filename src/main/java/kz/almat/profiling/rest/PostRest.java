package kz.almat.profiling.rest;

import kz.almat.profiling.model.Post;
import kz.almat.profiling.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Almat on 27.03.2020
 */

@RestController
@RequestMapping("/")
public class PostRest {

    @Autowired
    private PostRepo postRepo;

    @GetMapping
    public List<Post> all() {
        List<Post> posts = postRepo.findAll();
        return posts;
    }

    @GetMapping("{id}")
    public Optional<Post> getById(@PathVariable Long id) {
        return postRepo.findById(id);
    }

    @PostMapping
    public Post add(@RequestBody Post post) {
        return postRepo.save(post);
    }

    @PutMapping("{id}")
    public void update(@PathVariable Long id,
                       @RequestBody Post post) {
        Optional<Post> postFromDb = postRepo.findById(id);
        postFromDb.ifPresent(post1 -> {
            post1.setTopic(post.getTopic());
            post1.setMessage(post.getMessage());
            postRepo.save(post1);
        });
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        Optional<Post> post = postRepo.findById(id);
        postRepo.delete(post.get());
    }

}
