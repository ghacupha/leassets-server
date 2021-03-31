package io.github.leassets.internal.service;

import io.github.leassets.domain.FixedAssetAcquisition;
import io.github.leassets.internal.model.FixedAssetAcquisitionEntityRepository;
import io.github.leassets.repository.FixedAssetAcquisitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DefaultEntityRepository implements FixedAssetAcquisitionEntityRepository {

    @Autowired
    private FixedAssetAcquisitionRepository fixedAssetAcquisitionEntityRepository;

    @Override
    public List<FixedAssetAcquisition> findAll() {
        return null;
    }

    @Override
    public List<FixedAssetAcquisition> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<FixedAssetAcquisition> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public <S extends FixedAssetAcquisition> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends FixedAssetAcquisition> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<FixedAssetAcquisition> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public FixedAssetAcquisition getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends FixedAssetAcquisition> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends FixedAssetAcquisition> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Optional<FixedAssetAcquisition> findOne(Specification<FixedAssetAcquisition> specification) {
        return Optional.empty();
    }

    @Override
    public List<FixedAssetAcquisition> findAll(Specification<FixedAssetAcquisition> specification) {
        return null;
    }

    @Override
    public Page<FixedAssetAcquisition> findAll(Specification<FixedAssetAcquisition> specification, Pageable pageable) {
        return null;
    }

    @Override
    public List<FixedAssetAcquisition> findAll(Specification<FixedAssetAcquisition> specification, Sort sort) {
        return null;
    }

    @Override
    public long count(Specification<FixedAssetAcquisition> specification) {
        return 0;
    }

    @Override
    public Page<FixedAssetAcquisition> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends FixedAssetAcquisition> S save(S s) {
        return null;
    }

    @Override
    public Optional<FixedAssetAcquisition> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(FixedAssetAcquisition fixedAssetAcquisition) {

    }

    @Override
    public void deleteAll(Iterable<? extends FixedAssetAcquisition> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends FixedAssetAcquisition> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends FixedAssetAcquisition> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends FixedAssetAcquisition> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends FixedAssetAcquisition> boolean exists(Example<S> example) {
        return false;
    }
}
