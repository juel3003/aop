package com.optum.aog.jpa.repo;

import com.optum.aog.jpa.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mmacpher on 12/3/18.
 */
@Repository
public interface AttachmentsRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findByHierarchyId(Long hierarchyId);

}
