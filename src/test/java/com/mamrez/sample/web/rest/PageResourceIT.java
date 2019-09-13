package com.mamrez.sample.web.rest;

import com.mamrez.sample.SampleMvnApp;
import com.mamrez.sample.domain.Page;
import com.mamrez.sample.repository.PageRepository;
import com.mamrez.sample.service.PageService;
import com.mamrez.sample.service.dto.PageDTO;
import com.mamrez.sample.service.mapper.PageMapper;
import com.mamrez.sample.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mamrez.sample.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PageResource} REST controller.
 */
@SpringBootTest(classes = SampleMvnApp.class)
public class PageResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PageMapper pageMapper;

    @Autowired
    private PageService pageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPageMockMvc;

    private Page page;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PageResource pageResource = new PageResource(pageService);
        this.restPageMockMvc = MockMvcBuilders.standaloneSetup(pageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Page createEntity(EntityManager em) {
        Page page = new Page()
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .details(DEFAULT_DETAILS);
        return page;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Page createUpdatedEntity(EntityManager em) {
        Page page = new Page()
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .details(UPDATED_DETAILS);
        return page;
    }

    @BeforeEach
    public void initTest() {
        page = createEntity(em);
    }

    @Test
    @Transactional
    public void createPage() throws Exception {
        int databaseSizeBeforeCreate = pageRepository.findAll().size();

        // Create the Page
        PageDTO pageDTO = pageMapper.toDto(page);
        restPageMockMvc.perform(post("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageDTO)))
            .andExpect(status().isCreated());

        // Validate the Page in the database
        List<Page> pageList = pageRepository.findAll();
        assertThat(pageList).hasSize(databaseSizeBeforeCreate + 1);
        Page testPage = pageList.get(pageList.size() - 1);
        assertThat(testPage.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPage.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPage.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    public void createPageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pageRepository.findAll().size();

        // Create the Page with an existing ID
        page.setId(1L);
        PageDTO pageDTO = pageMapper.toDto(page);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageMockMvc.perform(post("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Page in the database
        List<Page> pageList = pageRepository.findAll();
        assertThat(pageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = pageRepository.findAll().size();
        // set the field null
        page.setTitle(null);

        // Create the Page, which fails.
        PageDTO pageDTO = pageMapper.toDto(page);

        restPageMockMvc.perform(post("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageDTO)))
            .andExpect(status().isBadRequest());

        List<Page> pageList = pageRepository.findAll();
        assertThat(pageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDetailsIsRequired() throws Exception {
        int databaseSizeBeforeTest = pageRepository.findAll().size();
        // set the field null
        page.setDetails(null);

        // Create the Page, which fails.
        PageDTO pageDTO = pageMapper.toDto(page);

        restPageMockMvc.perform(post("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageDTO)))
            .andExpect(status().isBadRequest());

        List<Page> pageList = pageRepository.findAll();
        assertThat(pageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPages() throws Exception {
        // Initialize the database
        pageRepository.saveAndFlush(page);

        // Get all the pageList
        restPageMockMvc.perform(get("/api/pages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(page.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())));
    }
    
    @Test
    @Transactional
    public void getPage() throws Exception {
        // Initialize the database
        pageRepository.saveAndFlush(page);

        // Get the page
        restPageMockMvc.perform(get("/api/pages/{id}", page.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(page.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPage() throws Exception {
        // Get the page
        restPageMockMvc.perform(get("/api/pages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePage() throws Exception {
        // Initialize the database
        pageRepository.saveAndFlush(page);

        int databaseSizeBeforeUpdate = pageRepository.findAll().size();

        // Update the page
        Page updatedPage = pageRepository.findById(page.getId()).get();
        // Disconnect from session so that the updates on updatedPage are not directly saved in db
        em.detach(updatedPage);
        updatedPage
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .details(UPDATED_DETAILS);
        PageDTO pageDTO = pageMapper.toDto(updatedPage);

        restPageMockMvc.perform(put("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageDTO)))
            .andExpect(status().isOk());

        // Validate the Page in the database
        List<Page> pageList = pageRepository.findAll();
        assertThat(pageList).hasSize(databaseSizeBeforeUpdate);
        Page testPage = pageList.get(pageList.size() - 1);
        assertThat(testPage.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPage.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPage.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void updateNonExistingPage() throws Exception {
        int databaseSizeBeforeUpdate = pageRepository.findAll().size();

        // Create the Page
        PageDTO pageDTO = pageMapper.toDto(page);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageMockMvc.perform(put("/api/pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Page in the database
        List<Page> pageList = pageRepository.findAll();
        assertThat(pageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePage() throws Exception {
        // Initialize the database
        pageRepository.saveAndFlush(page);

        int databaseSizeBeforeDelete = pageRepository.findAll().size();

        // Delete the page
        restPageMockMvc.perform(delete("/api/pages/{id}", page.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Page> pageList = pageRepository.findAll();
        assertThat(pageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Page.class);
        Page page1 = new Page();
        page1.setId(1L);
        Page page2 = new Page();
        page2.setId(page1.getId());
        assertThat(page1).isEqualTo(page2);
        page2.setId(2L);
        assertThat(page1).isNotEqualTo(page2);
        page1.setId(null);
        assertThat(page1).isNotEqualTo(page2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageDTO.class);
        PageDTO pageDTO1 = new PageDTO();
        pageDTO1.setId(1L);
        PageDTO pageDTO2 = new PageDTO();
        assertThat(pageDTO1).isNotEqualTo(pageDTO2);
        pageDTO2.setId(pageDTO1.getId());
        assertThat(pageDTO1).isEqualTo(pageDTO2);
        pageDTO2.setId(2L);
        assertThat(pageDTO1).isNotEqualTo(pageDTO2);
        pageDTO1.setId(null);
        assertThat(pageDTO1).isNotEqualTo(pageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pageMapper.fromId(null)).isNull();
    }
}
