package com.mapmory.services.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapmory.common.domain.Search;
import com.mapmory.services.product.dao.ProductDao;
import com.mapmory.services.product.dao.ProductImageDao;
import com.mapmory.services.product.domain.Product;
import com.mapmory.services.product.domain.ProductImage;
import com.mapmory.services.product.service.ProductService;
import com.mapmory.common.util.ImageFileUtil;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImageDao productImageDao;

    @Override
    @Transactional
    public void addProduct(Product product, List<String> imageFiles) throws Exception {
        productDao.addProduct(product); // 상품 정보를 먼저 추가

        // 이미지 파일의 UUID 값을 생성하고 ProductImage 객체에 설정하여 데이터베이스에 삽입
        for (String imageFile : imageFiles) {
            String uuid = ImageFileUtil.getProductImageUUIDFileName(imageFile);
            ProductImage productImage = new ProductImage();
            productImage.setProductNo(product.getProductNo());
            productImage.setImageFile(imageFile);
            productImage.setUuid(uuid); // UUID 설정
            productImageDao.addProductImage(productImage);
        }
    }

    @Override
    public Product getDetailProduct(int productNo) throws Exception {
        Product product = productDao.getDetailProduct(productNo); // 상품 정보 가져오기
        List<String> imageUuidList = productImageDao.getProductImageList(productNo); // 이미지 정보 가져오기
        product.setUuid(imageUuidList); // 이미지 정보를 상품 객체에 설정
        return product; // 이미지 정보를 포함한 상품 정보 반환
    }

    @Override
    public Map<String,Object> getProductList(Search search) throws Exception {
    	
    	List<Product> productList = productDao.getProductList(search);
    	int totalCount = productDao.getProductTotalCount(search);
    	
    	
    	for (Product product : productList) {
            List<String> imageUuidList = productImageDao.getProductImageList(product.getProductNo());
            product.setUuid(imageUuidList);
        }
    	
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("productList",productList);
    	map.put("productTotalCount", new Integer(totalCount));
        
        return map;
    }

    @Transactional
    @Override
    public void updateProduct(Product product, List<String> imageFiles) throws Exception {
    	productDao.updateProduct(product); 
    	
    	// 이미지 파일 업데이트
        for (String imageFile : imageFiles) {
            String uuid = ImageFileUtil.getProductImageUUIDFileName(imageFile);
            ProductImage productImage = new ProductImage();
            productImage.setProductNo(product.getProductNo());
            productImage.setImageFile(imageFile);
            productImage.setUuid(uuid); // UUID 설정
            productImageDao.addProductImage(productImage);
        }
        
    }

    @Override
    public void deleteProduct(int productNo) throws Exception {
    	
    	productImageDao.deleteProductImage(productNo);
    	productDao.deleteProduct(productNo);
        
    }
    
    @Override
    public void deleteImage(String uuid) throws Exception {
    	productImageDao.deleteImage(uuid);
    	
    }

    @Override
    public Product getProductByName(String productTitle) throws Exception {
        return productDao.getProductByName(productTitle);
    }

    @Override
    public int getProductTotalCount(Search search) throws Exception {
        // getProductTotalCount 메서드 구현
        return 0;
    }

    @Override
    public Map<String,Object> getProductImages(int productNo) throws Exception {
        return (Map<String, Object>) productImageDao.getProductImageList(productNo);
    }
}
