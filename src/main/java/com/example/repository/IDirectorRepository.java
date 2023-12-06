package com.example.repository;

import com.example.model.Director;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDirectorRepository extends CrudRepository<Director,Long> {
}
