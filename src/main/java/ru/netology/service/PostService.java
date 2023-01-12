package ru.netology.service;

import org.springframework.stereotype.Service;
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
        return repository.all();
    }

    public Post getById(long id) {
        //В условии задачи не сказано, что обязателен Exception. Временно заменен на более читаемое.
        return repository.getById(id).orElseGet(() -> new Post(0, "Пост не найден"));
    }

    public Post save(Post post) {
        if (post.getId() <= all().size()) {
            return repository.save(post);
        } else {
            return new Post(0, "Введено не корректное ID. Измените ID");
        }
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}