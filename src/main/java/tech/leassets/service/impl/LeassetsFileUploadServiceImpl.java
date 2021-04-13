package tech.leassets.service.impl;

import tech.leassets.service.LeassetsFileUploadService;
import tech.leassets.domain.LeassetsFileUpload;
import tech.leassets.repository.LeassetsFileUploadRepository;
import tech.leassets.repository.search.LeassetsFileUploadSearchRepository;
import tech.leassets.service.dto.LeassetsFileUploadDTO;
import tech.leassets.service.mapper.LeassetsFileUploadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link LeassetsFileUpload}.
 */
@Service
@Transactional
public class LeassetsFileUploadServiceImpl implements LeassetsFileUploadService {

    private final Logger log = LoggerFactory.getLogger(LeassetsFileUploadServiceImpl.class);

    private final LeassetsFileUploadRepository leassetsFileUploadRepository;

    private final LeassetsFileUploadMapper leassetsFileUploadMapper;

    private final LeassetsFileUploadSearchRepository leassetsFileUploadSearchRepository;

    public LeassetsFileUploadServiceImpl(LeassetsFileUploadRepository leassetsFileUploadRepository, LeassetsFileUploadMapper leassetsFileUploadMapper, LeassetsFileUploadSearchRepository leassetsFileUploadSearchRepository) {
        this.leassetsFileUploadRepository = leassetsFileUploadRepository;
        this.leassetsFileUploadMapper = leassetsFileUploadMapper;
        this.leassetsFileUploadSearchRepository = leassetsFileUploadSearchRepository;
    }

    @Override
    public LeassetsFileUploadDTO save(LeassetsFileUploadDTO leassetsFileUploadDTO) {
        log.debug("Request to save LeassetsFileUpload : {}", leassetsFileUploadDTO);
        LeassetsFileUpload leassetsFileUpload = leassetsFileUploadMapper.toEntity(leassetsFileUploadDTO);
        leassetsFileUpload = leassetsFileUploadRepository.save(leassetsFileUpload);
        LeassetsFileUploadDTO result = leassetsFileUploadMapper.toDto(leassetsFileUpload);
        leassetsFileUploadSearchRepository.save(leassetsFileUpload);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeassetsFileUploadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeassetsFileUploads");
        return leassetsFileUploadRepository.findAll(pageable)
            .map(leassetsFileUploadMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LeassetsFileUploadDTO> findOne(Long id) {
        log.debug("Request to get LeassetsFileUpload : {}", id);
        return leassetsFileUploadRepository.findById(id)
            .map(leassetsFileUploadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeassetsFileUpload : {}", id);
        leassetsFileUploadRepository.deleteById(id);
        leassetsFileUploadSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeassetsFileUploadDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LeassetsFileUploads for query {}", query);
        return leassetsFileUploadSearchRepository.search(queryStringQuery(query), pageable)
            .map(leassetsFileUploadMapper::toDto);
    }
}
