package ru.gb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.dto.ProductDto;
import ru.gb.market.exceptions.ResourceNotFoundException;
import ru.gb.market.model.Category;
import ru.gb.market.model.Product;
import ru.gb.market.services.CategoryService;
import ru.gb.market.services.ProductService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public Page<ProductDto> findAll(@RequestParam(name = "p", defaultValue = "1") int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return productService.findAll(pageIndex - 1, 10).map(ProductDto::new);
    }


//    @GetMapping("/products/{id}")
//    public ResponseEntity<?> findById(@PathVariable Long id) {
//        Optional<Product> product = productService.findById(id);
//        if (product.isEmpty()) {
//            return new ResponseEntity<>(new MarketError("Product id = "+ id +" not found"), HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(new ProductDto(product.get()), HttpStatus.OK);
//    }

    @GetMapping("/products/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return new ProductDto(productService
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product id = "+ id +" not found")));
    }
    @DeleteMapping("/products/{id}")
    public void deleteById(@PathVariable Long id) {
        try {
            productService.deleteById(id);
        } catch(Exception e){
            throw new ResourceNotFoundException("Product id = "+ id +" not found");
        }
    }

    @DeleteMapping("/products")
    public void deleteAll() {
        try {
            productService.deleteAll();
        } catch(Exception e){
            throw new ResourceNotFoundException("Error durinf data deleting: "+ e.getMessage());
        }
    }


    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto save(@RequestBody ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Category category = categoryService.findByTitle(productDto
                .getCategoryTitle())
                .orElseThrow(()-> new ResourceNotFoundException("Category title = "+ productDto.getCategoryTitle() +" not found"));
        product.setCategory(category);
        productService.save(product);
        return new ProductDto(product);
    }

    @PutMapping("/products")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductDto update(@RequestBody ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Category category = categoryService.findByTitle(productDto
                .getCategoryTitle())
                .orElseThrow(()-> new ResourceNotFoundException("Category title = "+
                        productDto.getCategoryTitle() +" not found"));
        product.setCategory(category);
        Long id = product.getId();

        Product productFinded = productService.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product id = "+ id +" not found"));

        productService.save(product);
        return new ProductDto(product);
    }
}
