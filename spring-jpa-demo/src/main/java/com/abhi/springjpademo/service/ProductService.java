package com.abhi.springjpademo.service;

import com.abhi.springjpademo.entity.Product;
import com.abhi.springjpademo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderService orderService;

    @Autowired
    public ProductService(ProductRepository productRepository, OrderService orderService) {
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    public void createProductWithoutException() {
        Product p = new Product();
        p.setTitle("laptop");
        p.setDescription("apple laptop is awesome");
        p.setPrice(7002.6);

        this.productRepository.save(p);
    }

    public void createProductWithException() {
        Product p = new Product();
        p.setTitle("laptop");
        p.setDescription("apple laptop is awesome");
        p.setPrice(7002.6);

        this.productRepository.save(p);
        throw new RuntimeException("exception in product creation");
    }

    @Transactional
    public void createProductWithRuntimeException() {
        Product p = new Product();
        p.setTitle("laptop");
        p.setDescription("apple laptop is awesome with exception");
        p.setPrice(7002.6);

        this.productRepository.save(p);
        throw new RuntimeException("run time exception is thrown");
    }

    @Transactional
    public void createProductWithHandleRuntimeException() {
        Product p = new Product();
        p.setTitle("laptop");
        p.setDescription("apple laptop is awesome with exception");
        p.setPrice(7002.6);

        try {
            this.productRepository.save(p);
            throw new RuntimeException("run time exception is thrown");
        } catch (Exception ex) {
            System.out.println("Runtime exception is caught");
        }

    }

    @Transactional
    public void createProductWithCheckedException() throws SQLException {
        Product p = new Product();
        p.setTitle("laptop");
        p.setDescription("apple laptop is awesome with exception");
        p.setPrice(7002.6);

        this.productRepository.save(p);
        throw new SQLException("checked exception is thrown");
    }

    @Transactional(rollbackFor = SQLException.class)
    public void createProductWithCheckedExceptionHandle() throws SQLException {
        Product p = new Product();
        p.setTitle("laptop");
        p.setDescription("apple laptop is awesome with exception");
        p.setPrice(7002.6);

        this.productRepository.save(p);
        throw new SQLException("checked exception is thrown");
    }

    public void createProductWithOrderWithoutEx() {
        createProductWithoutException();
        this.orderService.createOrderWithoutEx();
    }

    @Transactional
    public void createProductWithOrderEx() {
        createProductWithoutException();
        this.orderService.createOrderWithEx();
    }

    @Transactional
    public void createProductWithTryCatchHandleOrderEx() {
        createProductWithoutException();
        try {
            this.orderService.createOrderWithEx();
        } catch (Exception ex) {
            System.out.println("exception is handled for create order");
        }
    }

    @Transactional
    public void createProductWithTryCatchHandleOrderRuntimeEx() {
        createProductWithoutException();
        try {
            this.orderService.createOrderWithRunTimeEx();
        } catch (Exception ex) {
            System.out.println("exception is handled for create order run time");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createProductWithTryCatchHandleOrderRuntimeExNewPropagation() {
        createProductWithoutException();
        try {
            this.orderService.createOrderWithRunTimeEx();
        } catch (Exception ex) {
            System.out.println("exception is handled for create order run time new propagation");
        }
    }

    @Transactional()
    public void createProductWithNewPropagationHandleOrderEx() {
        createProductWithoutException();
        try {
            this.orderService.createOrderWithNewPropagationEx();
        } catch (Exception ex) {
            System.out.println("exception is handled for create order with new propagation");
        }

    }
}
