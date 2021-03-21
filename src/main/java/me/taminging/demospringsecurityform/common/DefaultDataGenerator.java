package me.taminging.demospringsecurityform.common;

import me.taminging.demospringsecurityform.account.Account;
import me.taminging.demospringsecurityform.account.AccountService;
import me.taminging.demospringsecurityform.book.Book;
import me.taminging.demospringsecurityform.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataGenerator implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Autowired
    BookRepository bookRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //taemin -- spring
        //taemin2 -- hibernate
        Account taemin = createUser("taemin");
        Account taemin2 = createUser("taemin2");
        accountService.createNew(taemin);
        accountService.createNew(taemin2);

        createBook("spring" ,taemin);
    }

    private void createBook(String title, Account account) {
        Book book = new Book();
        book.setAccount(account);
        book.setTitle(title);
        bookRepository.save(book);
    }

    private Account createUser(String username) {

        Account account = new Account();
        account.setUsername(username);
        account.setPassword("123");
        account.setRole("USER");
        return account;
    }
}
