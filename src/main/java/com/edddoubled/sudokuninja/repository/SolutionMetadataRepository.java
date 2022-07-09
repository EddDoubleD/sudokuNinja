package com.edddoubled.sudokuninja.repository;

import com.edddoubled.sudokuninja.model.SolutionMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SolutionMetadataRepository extends MongoRepository<SolutionMetadata, String> {

}
