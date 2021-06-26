package suncodes.hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import suncodes.hibernate.dao.BookRepository;
import suncodes.hibernate.pojo.po.Book;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> list() {
        return bookRepository.findAll();
    }

    public void insert() {
        Book book = new Book("The Tartar Steppe");
        Book book1 = new Book("Poem Strip");
        Book book2 = new Book("Restless Nights: Selected Stories of Dino Buzzati");
        bookRepository.save(book);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
