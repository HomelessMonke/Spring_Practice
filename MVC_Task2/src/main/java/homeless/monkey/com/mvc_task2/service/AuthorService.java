package homeless.monkey.com.mvc_task2.service;

import homeless.monkey.com.mvc_task2.model.AuthorEntity;
import homeless.monkey.com.mvc_task2.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorEntity getOrCreate(String name){
        return authorRepository.findByName(name)
                .orElseGet(()-> createAuthor(name));
    }

    private AuthorEntity createAuthor(String name){
        var author = new AuthorEntity();
        author.setName(name);
        return authorRepository.save(author);
    }
}
