package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Tool;

import com.mycompany.myapp.repository.ToolRepository;
import com.mycompany.myapp.repository.search.ToolSearchRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Tool.
 */
@RestController
@RequestMapping("/api")
public class ToolResource {

    private final Logger log = LoggerFactory.getLogger(ToolResource.class);

    private static final String ENTITY_NAME = "tool";
        
    private final ToolRepository toolRepository;

    private final ToolSearchRepository toolSearchRepository;

    public ToolResource(ToolRepository toolRepository, ToolSearchRepository toolSearchRepository) {
        this.toolRepository = toolRepository;
        this.toolSearchRepository = toolSearchRepository;
    }

    /**
     * POST  /tools : Create a new tool.
     *
     * @param tool the tool to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tool, or with status 400 (Bad Request) if the tool has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tools")
    @Timed
    public ResponseEntity<Tool> createTool(@Valid @RequestBody Tool tool) throws URISyntaxException {
        log.debug("REST request to save Tool : {}", tool);
        if (tool.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tool cannot already have an ID")).body(null);
        }
        Tool result = toolRepository.save(tool);
        toolSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tools : Updates an existing tool.
     *
     * @param tool the tool to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tool,
     * or with status 400 (Bad Request) if the tool is not valid,
     * or with status 500 (Internal Server Error) if the tool couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tools")
    @Timed
    public ResponseEntity<Tool> updateTool(@Valid @RequestBody Tool tool) throws URISyntaxException {
        log.debug("REST request to update Tool : {}", tool);
        if (tool.getId() == null) {
            return createTool(tool);
        }
        Tool result = toolRepository.save(tool);
        toolSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tool.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tools : get all the tools.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tools in body
     */
    @GetMapping("/tools")
    @Timed
    public List<Tool> getAllTools() {
        log.debug("REST request to get all Tools");
        List<Tool> tools = toolRepository.findAll();
        return tools;
    }

    /**
     * GET  /tools/:id : get the "id" tool.
     *
     * @param id the id of the tool to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tool, or with status 404 (Not Found)
     */
    @GetMapping("/tools/{id}")
    @Timed
    public ResponseEntity<Tool> getTool(@PathVariable Long id) {
        log.debug("REST request to get Tool : {}", id);
        Tool tool = toolRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tool));
    }

    /**
     * DELETE  /tools/:id : delete the "id" tool.
     *
     * @param id the id of the tool to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tools/{id}")
    @Timed
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        log.debug("REST request to delete Tool : {}", id);
        toolRepository.delete(id);
        toolSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tools?query=:query : search for the tool corresponding
     * to the query.
     *
     * @param query the query of the tool search 
     * @return the result of the search
     */
    @GetMapping("/_search/tools")
    @Timed
    public List<Tool> searchTools(@RequestParam String query) {
        log.debug("REST request to search Tools for query {}", query);
        return StreamSupport
            .stream(toolSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
