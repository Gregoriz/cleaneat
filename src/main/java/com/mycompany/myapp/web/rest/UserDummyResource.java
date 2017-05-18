package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.UserDummy;

import com.mycompany.myapp.repository.UserDummyRepository;
import com.mycompany.myapp.repository.search.UserDummySearchRepository;
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
 * REST controller for managing UserDummy.
 */
@RestController
@RequestMapping("/api")
public class UserDummyResource {

    private final Logger log = LoggerFactory.getLogger(UserDummyResource.class);

    private static final String ENTITY_NAME = "userDummy";
        
    private final UserDummyRepository userDummyRepository;

    private final UserDummySearchRepository userDummySearchRepository;

    public UserDummyResource(UserDummyRepository userDummyRepository, UserDummySearchRepository userDummySearchRepository) {
        this.userDummyRepository = userDummyRepository;
        this.userDummySearchRepository = userDummySearchRepository;
    }

    /**
     * POST  /user-dummies : Create a new userDummy.
     *
     * @param userDummy the userDummy to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userDummy, or with status 400 (Bad Request) if the userDummy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-dummies")
    @Timed
    public ResponseEntity<UserDummy> createUserDummy(@Valid @RequestBody UserDummy userDummy) throws URISyntaxException {
        log.debug("REST request to save UserDummy : {}", userDummy);
        if (userDummy.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userDummy cannot already have an ID")).body(null);
        }
        UserDummy result = userDummyRepository.save(userDummy);
        userDummySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/user-dummies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-dummies : Updates an existing userDummy.
     *
     * @param userDummy the userDummy to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userDummy,
     * or with status 400 (Bad Request) if the userDummy is not valid,
     * or with status 500 (Internal Server Error) if the userDummy couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-dummies")
    @Timed
    public ResponseEntity<UserDummy> updateUserDummy(@Valid @RequestBody UserDummy userDummy) throws URISyntaxException {
        log.debug("REST request to update UserDummy : {}", userDummy);
        if (userDummy.getId() == null) {
            return createUserDummy(userDummy);
        }
        UserDummy result = userDummyRepository.save(userDummy);
        userDummySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDummy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-dummies : get all the userDummies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userDummies in body
     */
    @GetMapping("/user-dummies")
    @Timed
    public List<UserDummy> getAllUserDummies() {
        log.debug("REST request to get all UserDummies");
        List<UserDummy> userDummies = userDummyRepository.findAll();
        return userDummies;
    }

    /**
     * GET  /user-dummies/:id : get the "id" userDummy.
     *
     * @param id the id of the userDummy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userDummy, or with status 404 (Not Found)
     */
    @GetMapping("/user-dummies/{id}")
    @Timed
    public ResponseEntity<UserDummy> getUserDummy(@PathVariable Long id) {
        log.debug("REST request to get UserDummy : {}", id);
        UserDummy userDummy = userDummyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userDummy));
    }

    /**
     * DELETE  /user-dummies/:id : delete the "id" userDummy.
     *
     * @param id the id of the userDummy to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-dummies/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserDummy(@PathVariable Long id) {
        log.debug("REST request to delete UserDummy : {}", id);
        userDummyRepository.delete(id);
        userDummySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-dummies?query=:query : search for the userDummy corresponding
     * to the query.
     *
     * @param query the query of the userDummy search 
     * @return the result of the search
     */
    @GetMapping("/_search/user-dummies")
    @Timed
    public List<UserDummy> searchUserDummies(@RequestParam String query) {
        log.debug("REST request to search UserDummies for query {}", query);
        return StreamSupport
            .stream(userDummySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
