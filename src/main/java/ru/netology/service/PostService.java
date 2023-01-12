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
        if(post.getRemoved()){
            throw new NotFoundException();
        }
        return post;
    }

    public Post save(Post post) {
        if (post.getId() <= all().size()) {
            return repository.save(post);
        } else {
            return new Post(0, "Введено не корректное ID. Измените ID");
        }
    }

    public void removeById(long id) {
        repository.getById(id).orElseThrow(NotFoundException::new).setRemoved(true);
    }
}