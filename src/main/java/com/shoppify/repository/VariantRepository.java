package com.shoppify.repository;

import com.shoppify.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantRepository extends JpaRepository<Variant,Long> {
}
