package com.abevilacqua.techstore;

import com.abevilacqua.techstore.model.Product;
import com.abevilacqua.techstore.repository.ProductRepoPessimisticLocking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Executors;

import static com.abevilacqua.techstore.TestUtils.createProduct;
import static java.util.concurrent.TimeUnit.SECONDS;

@SpringBootTest
public class ProductRepoPessimistLockingTest {

    @Autowired
    private ProductRepoPessimisticLocking repo;

    @Test
    void test1() throws InterruptedException {
        repo.save(createProduct());
        var executorService = Executors.newFixedThreadPool(2);

        // user 1, reader
        executorService.execute(runUser1Transaction);

        // user 2, writer
        executorService.execute(runUser2Transaction);
        executorService.awaitTermination(10, SECONDS);
    }

    private final Runnable runUser1Transaction = () -> {
        System.out.println(" -- User 1 reading Product entity --");
        long start = System.currentTimeMillis();
        Product product1;
        try {
            product1 = repo.findProductForRead(1L);
        } catch (Exception e) {
            System.out.println("User 1 got exception while acquiring the database lock:\n " + e);
            return;
        }
        System.out.println("User 1 got the lock, block time was: " + (System.currentTimeMillis() - start));
        // Delay for 3 secs
        threadSleep(3000);
        System.out.println("User 1 read product: " + product1);
    };

    private final Runnable runUser2Transaction = () -> {
        threadSleep(500);
        System.out.println(" -- User 2 writing Product entity --");
        long start = System.currentTimeMillis();
        Product product2;
        try {
            product2 = repo.findProductForWrite(1L);
        } catch (Exception e) {
            System.out.println("User 2 got exception while acquiring the database lock:\n " + e);
            return;
        }
        System.out.println("User 2 got the lock, block time was: " + (System.currentTimeMillis() - start));
        product2.setDescription("Updated product description by user 2.");
        repo.save(product2);
        System.out.println("User 2 updated product: " + product2);
    };

    private void threadSleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
