package com.example.minor.project.service;

import com.example.minor.project.Entity.Book;
import com.example.minor.project.Entity.User;
import com.example.minor.project.exception.BookAlreadyExistException;
import com.example.minor.project.exception.BookNotFoundException;
import com.example.minor.project.repository.BookRepository;
import com.example.minor.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("No Book found with given Id "+id));

    }

    public Book save(Book book) throws BookAlreadyExistException {
        Optional<Book> isBookExist = bookRepository.findById(book.getId());
        if(isBookExist.isPresent()){
            throw new BookAlreadyExistException("Book already  exist with id "+book.getId());
        }
        return bookRepository.save(book);
    }

    public void deleteById(Long id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException("No Book found with given Id "+id);
        }

        bookRepository.deleteById(id);
    }

    public Book borrowBook(Long bookId, Long userId) throws BookNotFoundException, BookAlreadyExistException {
        Book book = findById(bookId);
        User user = userRepository.findById(userId).orElse(null);

        if (book != null && !book.isBorrowed() && user != null) {
            book.setBorrowedBy(user);
            book.setBorrowed(true);
            return save(book);
        }
        // Handle errors (e.g., book not found, book already borrowed, user not found)
        return null;
    }

    public Book returnBook(Long bookId) throws BookNotFoundException, BookAlreadyExistException {
        Book book = findById(bookId);
        if (book != null && book.isBorrowed()) {
            book.setBorrowedBy(null);
            book.setBorrowed(false);
            return save(book);
        }
        // Handle errors (e.g., book not found, book not borrowed)
        return null;
    }
}
