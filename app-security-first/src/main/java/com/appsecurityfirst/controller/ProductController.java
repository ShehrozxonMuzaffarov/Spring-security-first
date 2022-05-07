package com.appsecurityfirst.controller;

import com.appsecurityfirst.entity.Product;
import com.appsecurityfirst.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    //DIRECTOR
//    role based
//    @PreAuthorize(value = "hasRole('DIRECTOR')")  //@EnableGlobalMethodSecurity(prePostEnabled = true) config classda bolishi kk ishlashi uchun

    //permission based uchun
    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping("")
    public Product addProduct(@RequestBody Product product){

        // UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=director1, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ADD_PRODUCT, DELETE_PRODUCT, EDIT_PRODUCT, READ_ALL_PRODUCT, READ_ONE_PRODUCT]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null], Granted Authorities=[ADD_PRODUCT, DELETE_PRODUCT, EDIT_PRODUCT, READ_ALL_PRODUCT, READ_ONE_PRODUCT]]
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication);

        return productRepository.save(product);
    }

    //DIRECTOR
    //role based uchun bu
//    @PreAuthorize(value = "hasRole('DIRECTOR')")

    //permission based uchun
    @PreAuthorize(value = "hasAuthority('EDIT_PRODUCT')")
    @PutMapping("/{id}")
    public Product editProduct(@PathVariable Long id, @RequestBody Product product){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            return null;
        Product product1 = optionalProduct.get();
        product1.setName(product.getName());
        return  productRepository.save(product1);
    }

    //DIRECTOR
    //role based uchun bu
//    @PreAuthorize(value = "hasRole('DIRECTOR')")

    //permission based uchun
    @PreAuthorize(value = "hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            return null;
        productRepository.deleteById(id);
        return "deleted success";
    }

    //DIRECTOR,MANAGER,USER
    //role based uchun bu
//    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER','USER')")

    //permission based uchun
    @PreAuthorize(value = "hasAuthority('READ_ONE_PRODUCT')")
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            return null;
        return optionalProduct.get();
    }


    //DIRECTOR,MANAGER
    //role based uchun bu
//    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")

    //permission based uchun
    @PreAuthorize(value = "hasAuthority('READ_ALL_PRODUCT')")
    @GetMapping("")
    public List<Product> getProduct(){
        return productRepository.findAll();
    }


}
