package io.github.leassets.internal.batch;

import io.github.leassets.service.LeassetsFileUploadService;
import io.github.leassets.service.dto.LeassetsFileUploadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("leassetsFileUploads")
public class LeassetsFileUploadsImpl implements LeassetsFileUploads<LeassetsFileUploadDTO> {

    private final LeassetsFileUploadService leassetsFileUploadService;

    public LeassetsFileUploadsImpl(LeassetsFileUploadService leassetsFileUploadService) {
        this.leassetsFileUploadService = leassetsFileUploadService;
    }

    @Override
    public LeassetsFileUploadDTO save(LeassetsFileUploadDTO fileUploadDTO) {
        return leassetsFileUploadService.save(fileUploadDTO);
    }

    @Override
    public Optional<LeassetsFileUploadDTO> partialUpdate(LeassetsFileUploadDTO fileUploadDTO) {
        return leassetsFileUploadService.partialUpdate(fileUploadDTO);
    }

    @Override
    public Page<LeassetsFileUploadDTO> findAll(Pageable pageable) {
        return leassetsFileUploadService.findAll(pageable);
    }

    @Override
    public Optional<LeassetsFileUploadDTO> findOne(Long id) {
        return leassetsFileUploadService.findOne(id);
    }

    @Override
    public void delete(Long id) {

        leassetsFileUploadService.delete(id);
    }

    @Override
    public Page<LeassetsFileUploadDTO> search(String query, Pageable pageable) {
        return leassetsFileUploadService.search(query, pageable);
    }
}
