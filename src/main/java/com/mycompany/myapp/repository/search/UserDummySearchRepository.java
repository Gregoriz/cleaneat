package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.UserDummy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserDummy entity.
 */
public interface UserDummySearchRepository extends ElasticsearchRepository<UserDummy, Long> {
}
