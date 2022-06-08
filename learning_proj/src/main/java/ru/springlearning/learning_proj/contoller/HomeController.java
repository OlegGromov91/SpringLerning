package ru.springlearning.learning_proj.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.springlearning.learning_proj.model.Book;
import ru.springlearning.learning_proj.repo.BookRepo;

@Controller
@RequestMapping("/")
public class HomeController {

    private BookRepo bookRepo;

    public HomeController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping
    public String getIndex(Model model){
        model.addAttribute("books", bookRepo.findAll());
        model.addAttribute("newBook", new Book());
        return "index";
    }

    @PostMapping
    public String createBook(Book book){
        bookRepo.save(book);
        return "redirect:/";
    }

}
