package ru.springlearning.learning_proj.repo;

import org.springframework.data.repository.CrudRepository;
import ru.springlearning.learning_proj.model.Book;

public interface BookRepo extends CrudRepository<Book, Long> {

}
