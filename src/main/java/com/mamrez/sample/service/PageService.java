package com.mamrez.sample.service;

import com.mamrez.sample.domain.Page;
import com.mamrez.sample.repository.PageRepository;
import com.mamrez.sample.service.dto.PageDTO;
import com.mamrez.sample.service.mapper.PageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Page}.
 */
@Service
@Transactional
public class PageService {

    private final Logger log = LoggerFactory.getLogger(PageService.class);

    private final PageRepository pageRepository;

    private final PageMapper pageMapper;

    public PageService(PageRepository pageRepository, PageMapper pageMapper) {
        this.pageRepository = pageRepository;
        this.pageMapper = pageMapper;
    }

    /**
     * Save a page.
     *
     * @param pageDTO the entity to save.
     * @return the persisted entity.
     */
    public PageDTO save(PageDTO pageDTO) {
        log.debug("Request to save Page : {}", pageDTO);
        Page page = pageMapper.toEntity(pageDTO);
        page = pageRepository.save(page);
        return pageMapper.toDto(page);
    }

    /**
     * Get all the pages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<PageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pages");
        return pageRepository.findAll(pageable)
            .map(pageMapper::toDto);
    }


    /**
     * Get one page by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PageDTO> findOne(Long id) {
        log.debug("Request to get Page : {}", id);
        return pageRepository.findById(id)
            .map(pageMapper::toDto);
    }

    /**
     * Delete the page by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Page : {}", id);
        pageRepository.deleteById(id);
    }
}
