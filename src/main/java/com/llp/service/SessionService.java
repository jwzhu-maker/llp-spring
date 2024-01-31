package com.llp.service;

import com.llp.model.Sessions;
import com.llp.repository.SessionRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void createSession(@NonNull Sessions session) {
        sessionRepository.save(session);
    }

    public Sessions findById(int sessionId) {
        return sessionRepository.findById(sessionId).orElse(null);
    }

    public void updateSession(@NonNull Sessions session) {
        sessionRepository.save(session);
    }

    public List<Sessions> findAll() {
        return sessionRepository.findAll();
    }
}

