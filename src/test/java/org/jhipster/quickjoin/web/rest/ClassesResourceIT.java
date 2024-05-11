package org.jhipster.quickjoin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.jhipster.quickjoin.domain.ClassesAsserts.*;
import static org.jhipster.quickjoin.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.quickjoin.IntegrationTest;
import org.jhipster.quickjoin.domain.Classes;
import org.jhipster.quickjoin.repository.ClassesRepository;
import org.jhipster.quickjoin.repository.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ClassesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ClassesResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TECHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TECHER_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final String ENTITY_API_URL = "/api/classes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Classes classes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classes createEntity(EntityManager em) {
        Classes classes = new Classes()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .techer_name(DEFAULT_TECHER_NAME)
            .price(DEFAULT_PRICE)
            .location(DEFAULT_LOCATION)
            .duration(DEFAULT_DURATION);
        return classes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classes createUpdatedEntity(EntityManager em) {
        Classes classes = new Classes()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .techer_name(UPDATED_TECHER_NAME)
            .price(UPDATED_PRICE)
            .location(UPDATED_LOCATION)
            .duration(UPDATED_DURATION);
        return classes;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Classes.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        classes = createEntity(em);
    }

    @Test
    void createClasses() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Classes
        var returnedClasses = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(Classes.class)
            .returnResult()
            .getResponseBody();

        // Validate the Classes in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertClassesUpdatableFieldsEquals(returnedClasses, getPersistedClasses(returnedClasses));
    }

    @Test
    void createClassesWithExistingId() throws Exception {
        // Create the Classes with an existing ID
        classes.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        classes.setTitle(null);

        // Create the Classes, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        classes.setDescription(null);

        // Create the Classes, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTecher_nameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        classes.setTecher_name(null);

        // Create the Classes, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        classes.setPrice(null);

        // Create the Classes, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkLocationIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        classes.setLocation(null);

        // Create the Classes, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDurationIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        classes.setDuration(null);

        // Create the Classes, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllClassesAsStream() {
        // Initialize the database
        classesRepository.save(classes).block();

        List<Classes> classesList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Classes.class)
            .getResponseBody()
            .filter(classes::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(classesList).isNotNull();
        assertThat(classesList).hasSize(1);
        Classes testClasses = classesList.get(0);

        // Test fails because reactive api returns an empty object instead of null
        // assertClassesAllPropertiesEquals(classes, testClasses);
        assertClassesUpdatableFieldsEquals(classes, testClasses);
    }

    @Test
    void getAllClasses() {
        // Initialize the database
        classesRepository.save(classes).block();

        // Get all the classesList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(classes.getId().intValue()))
            .jsonPath("$.[*].title")
            .value(hasItem(DEFAULT_TITLE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].techer_name")
            .value(hasItem(DEFAULT_TECHER_NAME))
            .jsonPath("$.[*].price")
            .value(hasItem(DEFAULT_PRICE))
            .jsonPath("$.[*].location")
            .value(hasItem(DEFAULT_LOCATION))
            .jsonPath("$.[*].duration")
            .value(hasItem(DEFAULT_DURATION));
    }

    @Test
    void getClasses() {
        // Initialize the database
        classesRepository.save(classes).block();

        // Get the classes
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, classes.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(classes.getId().intValue()))
            .jsonPath("$.title")
            .value(is(DEFAULT_TITLE))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.techer_name")
            .value(is(DEFAULT_TECHER_NAME))
            .jsonPath("$.price")
            .value(is(DEFAULT_PRICE))
            .jsonPath("$.location")
            .value(is(DEFAULT_LOCATION))
            .jsonPath("$.duration")
            .value(is(DEFAULT_DURATION));
    }

    @Test
    void getNonExistingClasses() {
        // Get the classes
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingClasses() throws Exception {
        // Initialize the database
        classesRepository.save(classes).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classes
        Classes updatedClasses = classesRepository.findById(classes.getId()).block();
        updatedClasses
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .techer_name(UPDATED_TECHER_NAME)
            .price(UPDATED_PRICE)
            .location(UPDATED_LOCATION)
            .duration(UPDATED_DURATION);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedClasses.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(updatedClasses))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClassesToMatchAllProperties(updatedClasses);
    }

    @Test
    void putNonExistingClasses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classes.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, classes.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchClasses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamClasses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateClassesWithPatch() throws Exception {
        // Initialize the database
        classesRepository.save(classes).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classes using partial update
        Classes partialUpdatedClasses = new Classes();
        partialUpdatedClasses.setId(classes.getId());

        partialUpdatedClasses.description(UPDATED_DESCRIPTION).price(UPDATED_PRICE).location(UPDATED_LOCATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedClasses.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedClasses))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Classes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassesUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedClasses, classes), getPersistedClasses(classes));
    }

    @Test
    void fullUpdateClassesWithPatch() throws Exception {
        // Initialize the database
        classesRepository.save(classes).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classes using partial update
        Classes partialUpdatedClasses = new Classes();
        partialUpdatedClasses.setId(classes.getId());

        partialUpdatedClasses
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .techer_name(UPDATED_TECHER_NAME)
            .price(UPDATED_PRICE)
            .location(UPDATED_LOCATION)
            .duration(UPDATED_DURATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedClasses.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedClasses))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Classes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassesUpdatableFieldsEquals(partialUpdatedClasses, getPersistedClasses(partialUpdatedClasses));
    }

    @Test
    void patchNonExistingClasses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classes.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, classes.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchClasses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamClasses() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(classes))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Classes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteClasses() {
        // Initialize the database
        classesRepository.save(classes).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classes
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, classes.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classesRepository.count().block();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Classes getPersistedClasses(Classes classes) {
        return classesRepository.findById(classes.getId()).block();
    }

    protected void assertPersistedClassesToMatchAllProperties(Classes expectedClasses) {
        // Test fails because reactive api returns an empty object instead of null
        // assertClassesAllPropertiesEquals(expectedClasses, getPersistedClasses(expectedClasses));
        assertClassesUpdatableFieldsEquals(expectedClasses, getPersistedClasses(expectedClasses));
    }

    protected void assertPersistedClassesToMatchUpdatableProperties(Classes expectedClasses) {
        // Test fails because reactive api returns an empty object instead of null
        // assertClassesAllUpdatablePropertiesEquals(expectedClasses, getPersistedClasses(expectedClasses));
        assertClassesUpdatableFieldsEquals(expectedClasses, getPersistedClasses(expectedClasses));
    }
}
