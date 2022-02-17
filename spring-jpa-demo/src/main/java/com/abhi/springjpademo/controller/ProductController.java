package com.abhi.springjpademo.controller;

import com.abhi.springjpademo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
    private final ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/create")
    public void createProductWithoutException() {
        this.productService.createProductWithoutException();//record will be inserted
    }

    @GetMapping(value = "/create/ex")
    public void createProductWithException() {
        this.productService.createProductWithException();//record will be inserted
    }

    @GetMapping(value = "/create/ex/handle")
    public void createProductWithHandleRuntimeException() {
        this.productService.createProductWithHandleRuntimeException();//record will be inserted
    }

    @GetMapping(value = "/create/ex/run")
    public void createProductWithRuntimeException() {
        this.productService.createProductWithRuntimeException();//record will be roll-backed
    }

    @GetMapping(value = "/create/ex/checked")
    public void createProductWithCheckedException() throws SQLException {
        this.productService.createProductWithCheckedException();//record will be inserted
    }

    @GetMapping(value = "/create/ex/checked/handle")
    public void createProductWithCheckedExceptionHandle() throws SQLException {
        this.productService.createProductWithCheckedExceptionHandle();// record will be roll backed
    }

    @GetMapping(value = "/create/order")
    public void createProductWithOrderWithoutEx() {
        this.productService.createProductWithOrderWithoutEx();// product, order will be inserted
    }

    @GetMapping(value = "/create/order/ex")
    public void createProductWithOrderEx() {
        this.productService.createProductWithOrderEx();// product, order will be roll-backed
    }

    @GetMapping(value = "/create/order/runtime/ex")
    public void createProductWithTryCatchHandleOrderRuntimeEx() {
        this.productService.createProductWithTryCatchHandleOrderRuntimeEx();//product will be inserted, order will be also inserted as there is no @Transactional on Order
    }

    @GetMapping(value = "/create/order/runtime/ex/newpropagation")
    public void createProductWithTryCatchHandleOrderRuntimeExNewPropagation() {
        this.productService.createProductWithTryCatchHandleOrderRuntimeExNewPropagation();//product will be inserted, order will be also inserted as there is no @Transactional on Order
    }

    @GetMapping(value = "/create/order/ex/handle/try")
    public void createProductWithTryCatchHandleOrderEx() {
        this.productService.createProductWithTryCatchHandleOrderEx();// order will be roll-backed , product will also be roll-backed due to UnexpectedRollbackException
    }

    @GetMapping(value = "/create/order/ex/handle/newpropagation")
    public void createProductWithNewPropagationHandleOrderEx() {
        this.productService.createProductWithNewPropagationHandleOrderEx();//order will be roll-backed, but product will be inserted
    }
}
