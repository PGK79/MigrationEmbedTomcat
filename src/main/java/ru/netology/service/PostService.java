package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        List<Post> postsFromRepo = repository.all();
        postsFromRepo.removeIf(Post::getRemoved);
        return postsFromRepo;
    }

    public Post getById(long id) {
        Post post = repository.getById(id).orElseThrow(NotFoundException::new);
        if(post.getRemoved()) {
            throw new NotFoundException();
        }
        return post;
    }

    public Post save(Post post) {
        if (repository.all().size() != 0 && post.getId() != 0) {
            getById(post.getId()); // здесь же и NotFoundException если удален или не существует id
        }
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.getById(id).orElseThrow(NotFoundException::new).setRemoved(true);
    }
}