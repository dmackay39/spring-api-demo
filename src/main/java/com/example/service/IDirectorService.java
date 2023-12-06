package com.example.service;

import com.example.model.Director;

import java.util.List;

public interface IDirectorService {
    List<Director> findAll();
    List<Director> findByNameContains(String filter);
}
