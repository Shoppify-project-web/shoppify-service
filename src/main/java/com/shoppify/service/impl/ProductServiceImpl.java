package com.shoppify.service.impl;

import com.shoppify.converter.ProductConverter;
import com.shoppify.dto.CommonResponse;
import com.shoppify.dto.payload.request.CategoryRequest;
import com.shoppify.dto.payload.request.ProductRequest;
import com.shoppify.dto.payload.response.CategoryResponse;
import com.shoppify.dto.payload.response.ProductResponse;
import com.shoppify.entity.Category;
import com.shoppify.entity.Product;
import com.shoppify.repository.ProductRepository;
import com.shoppify.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
   private final ProductRepository productRepository;
   private final ProductConverter productConverter;

   @Override
   public CommonResponse findAllProduct() {

      CommonResponse commonResponse = new CommonResponse();

      List<Product> productList = productRepository.findAll();

      if(productList.isEmpty()) {

         commonResponse.setData(null);
         commonResponse.setMessage("Products not found");
         commonResponse.setStatusCode(HttpStatus.NOT_FOUND);
      }
      else {

         List<ProductResponse> productResponseList = productConverter.toDtoProductList(productList);

         commonResponse.setData(productResponseList);
         commonResponse.setMessage("Accessed the products successfully");
         commonResponse.setStatusCode(HttpStatus.OK);
      }
      return commonResponse;
   }

   @Override
   public CommonResponse getProductById(Long productId) {
      try {
         Optional<Product> optionalProduct = productRepository.findById(productId);

         if (optionalProduct.isPresent()) {

            Product product = optionalProduct.get();
            ProductResponse productResponse = productConverter.toDtoProduct(product);

            return CommonResponse.builder()
                    .data(productResponse)
                    .message("Product retrieved successfully")
                    .statusCode(HttpStatus.OK).build();
         } else {

            return CommonResponse.builder()
                    .data(null)
                    .message("Product not found")
                    .statusCode(HttpStatus.NOT_FOUND)
                    .build();
         }
      } catch (Exception e) {

         return CommonResponse.builder()
                 .data(null)
                 .message("Error retrieving product: " + e.getMessage())
                 .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                 .build();
      }
   }

   @Override
   public CommonResponse getProductByCategoryId(Long categoryId) {
      CommonResponse commonResponse = new CommonResponse();
      List<Product> productList = productRepository.findProductByCategoryId(categoryId);

      if(productList.isEmpty()) {

         commonResponse.setData(null);
         commonResponse.setMessage("Products not found");
         commonResponse.setStatusCode(HttpStatus.NOT_FOUND);
      }
      else {
         List<ProductResponse> productResponseList = productConverter.toDtoProductList(productList);
         commonResponse.setData(productResponseList);
         commonResponse.setMessage("Accessed the products successfully");
         commonResponse.setStatusCode(HttpStatus.OK);
      }
      return commonResponse;
   }

   @Override
   public CommonResponse getProductBySubCategoryId(Long subCategoryId) {
      CommonResponse commonResponse = new CommonResponse();
      List<Product> productList = productRepository.findProductBySubCategoryId(subCategoryId);

      if(productList.isEmpty()) {

         commonResponse.setData(null);
         commonResponse.setMessage("Products not found");
         commonResponse.setStatusCode(HttpStatus.NOT_FOUND);
      }
      else {
         List<ProductResponse> productResponseList = productConverter.toDtoProductList(productList);
         commonResponse.setData(productResponseList);
         commonResponse.setMessage("Accessed the products successfully");
         commonResponse.setStatusCode(HttpStatus.OK);
      }
      return commonResponse;
   }
//   @Override
//   public CommonResponse addProduct(ProductRequest request) {
//
//      if (productRepository.existsByName(request.getName())) {
//
//         return CommonResponse.builder()
//                 .data(null)
//                 .message("Product with name " + request.getName() + " already exists")
//                 .statusCode(HttpStatus.BAD_REQUEST).build();
//      }
//
//      Product product = productConverter.toEntityProduct(request);
//      productRepository.save(product);
//
//      ProductResponse productResponse = productConverter.toDtoProduct(product);
//
//      return CommonResponse.builder()
//              .data(productResponse)
//              .message("Add product successfully")
//              .statusCode(HttpStatus.CREATED)
//              .build();
//   }
}
