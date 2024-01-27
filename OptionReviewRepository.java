package com.optum.aog.jpa.repo;

import com.optum.aog.jpa.domain.OptionReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionReviewRepository extends JpaRepository<OptionReview, Long> {

    OptionReview findByHierarchyId(Long id);

}
