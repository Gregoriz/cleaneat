package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Tool;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tool entity.
 */
public interface ToolSearchRepository extends ElasticsearchRepository<Tool, Long> {
}
