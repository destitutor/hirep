package net.hexabrain.hireo.service;

import lombok.RequiredArgsConstructor;
import net.hexabrain.hireo.domain.Job;
import net.hexabrain.hireo.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    public Job findOne(Long id) {
        return jobRepository.findById(id).get();
    }

    public List<Job> findAll() {
        return jobRepository.findAll();
    }
}
