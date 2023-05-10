package cmu.ediss.bookservice.service;


import cmu.ediss.bookservice.entity.Book;
import cmu.ediss.bookservice.mapper.TBookMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class BookService extends ServiceImpl<TBookMapper, Book> {
    private final RestTemplate restTemplate;
    private final CircuitBreaker circuitBreaker;
    private final TimeLimiter timeLimiter;

    @Autowired
    public BookService(RestTemplate restTemplate,
                               CircuitBreakerRegistry circuitBreakerRegistry,
                               TimeLimiterRegistry timeLimiterRegistry) {
        this.restTemplate = restTemplate;
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("relatedBooks");
        this.timeLimiter = timeLimiterRegistry.timeLimiter("relatedBooks");
    }



    public Book getByIsbn(String isbn) {
        LambdaQueryWrapper<Book> wrapper =
                new LambdaQueryWrapper<Book>().eq(Book::getISBN, isbn);
        return getOne(wrapper);
    }

    // to change
    public boolean updateByISBN(Book book, String ISBN) {
        UpdateWrapper<Book> wrapper = new UpdateWrapper<Book>()
                .set("ISBN", book.getISBN())
                .set("title", book.getTitle())
                .set("Author", book.getAuthor())
                .set("description", book.getDescription())
                .set("genre", book.getGenre())
                .set("price", book.getPrice())
                .set("quantity", book.getQuantity())
                .eq("ISBN", ISBN);
        int rows = baseMapper.update(null, wrapper);
        return rows > 0;
    }

    public boolean createBook(Book book) {

        return save(book);
    }

    public void deleteAll() {
        LambdaQueryWrapper<Book> wrapper =
                new LambdaQueryWrapper<Book>();
        remove(wrapper);
    }

    public ResponseEntity<?> getRelatedBooks(String isbn) {
        ResponseEntity<?> response;
        CircuitBreaker.State state = CircuitBreaker.State.CLOSED;
        try {
            state = circuitBreaker.getState();
            // Execute the external service call within the circuit breaker
            response = circuitBreaker.executeCallable(() ->
                    restTemplate.getForEntity("http://44.214.218.139/recommended-titles/isbn/" + isbn, String.class));

        } catch (CallNotPermittedException ex) {
            // Circuit breaker is open, return HTTP 503 Service Unavailable
            response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        } catch (Exception ex) {
            // Circuit breaker is closed and external service call threw an exception, treat it as a success and return the exception message
            var status = state.equals(CircuitBreaker.State.CLOSED) ? HttpStatus.GATEWAY_TIMEOUT : HttpStatus.SERVICE_UNAVAILABLE;
            response = ResponseEntity.status(status).body(ex.getMessage());
            circuitBreaker.transitionToOpenState();
        }
        return response;
    }
}


