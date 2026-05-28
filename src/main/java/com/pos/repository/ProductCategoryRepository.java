package com.pos.repository;

import com.pos.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByParentIdOrderBySortOrder(Long parentId);
    List<ProductCategory> findByStatusOrderBySortOrder(Integer status);
}
