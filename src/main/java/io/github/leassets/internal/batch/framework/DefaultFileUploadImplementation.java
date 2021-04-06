package io.github.leassets.internal.batch.framework;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * This is an abstract implementation for the data-file-upload-service. The implementation
 * does not do much as most of the implementation detail is actually handled by the client. The
 * class provides uniformity in persistence procedures
 *
 * @param <F> File Upload Class
 */
public abstract class DefaultFileUploadImplementation<F> implements DataFileUploadService<F> {

    @Override
    public F save(F fileUploadDTO) {
        return getFileUploadService().save(fileUploadDTO);
    }

    @Override
    public Optional<F> partialUpdate(F fileUploadDTO) {
        return getFileUploadService().partialUpdate(fileUploadDTO);
    }

    @Override
    public Page<? extends HasDataFile> findAll(Pageable pageable) {
        return getFileUploadService().findAll(pageable);
    }

    @Override
    public Optional<? extends HasDataFile> findOne(Long id) {
        return getFileUploadService().findOne(id);
    }

    @Override
    public void delete(Long id) {
        getFileUploadService().delete(id);
    }

    @Override
    public Page<? extends HasDataFile> search(String query, Pageable pageable) {
        return getFileUploadService().search(query, pageable);
    }

    protected abstract DataFileUploadService<F> getFileUploadService();
}
