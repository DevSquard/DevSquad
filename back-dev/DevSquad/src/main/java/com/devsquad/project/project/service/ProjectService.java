package com.devsquad.project.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devsquad.auth.entity.User;
import com.devsquad.auth.repository.UserRepository;
import com.devsquad.project.project.domain.ProjectRequest;
import com.devsquad.project.project.domain.ProjectResponse;
import com.devsquad.project.project.entity.Project;
import com.devsquad.project.project.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<ProjectResponse> getAllProject() {
        List<Project> proList = projectRepository.findAll();
        return proList.stream().map(ProjectResponse::toDTO).toList();
    }

    public ProjectResponse addProject(ProjectRequest pro) {
    	// request에 담긴 userId로 유저 가져오기
    	User user = userRepository.findByIdAndDeletedAtIsNull(pro.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    	
    	// Request를 Project 타입으로 변환
    	Project project = Project.builder()
    		.id(pro.getId())
    		.user(user)
    		.projectName(pro.getProjectName())
    		.simpleIntro(pro.getSimpleIntro())
    		.description(pro.getDescription())
    		.devStack(pro.getDevStack())
    		.participaint(pro.getParticipaint())
    		.endedAt(pro.getEndedAt())
    		.startedAt(pro.getStartedAt())
    		.build();
    	
        Project savedPro = projectRepository.save(project);
        return ProjectResponse.toDTO(savedPro);
    }

    public ProjectResponse getProject(Long id) {
        Project pro = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없음"));
        return ProjectResponse.toDTO(pro);
    }

    public ProjectResponse deleteProject(Long id) {
        Project pro = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없음"));
        projectRepository.deleteById(pro.getId());
        return null;
    }

    public ProjectResponse updateProject(ProjectRequest pro) {
        Project project = projectRepository.findById(pro.getId()).orElseThrow(() -> new IllegalArgumentException("없음"));
        Project updatedProject = projectRepository.save(project);
        return ProjectResponse.toDTO(updatedProject);
    }
}
