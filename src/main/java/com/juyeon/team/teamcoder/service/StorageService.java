package com.juyeon.team.teamcoder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public interface StorageService {

    void init();
    void Store(MultipartFile file);
    Stream<Path> loadAll();
    Path load(String fileName);
    Resource loadAsResource(String filename);
    void deleteAll();
}
