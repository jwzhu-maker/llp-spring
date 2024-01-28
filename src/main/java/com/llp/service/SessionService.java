package com.llp.service;

import com.llp.model.Sessions;
import com.llp.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    @Autowired
    SessionRepository sessionRepository;

    public void createSession(Sessions session) {
        sessionRepository.save(session);
    }

    public Sessions findById(int sessionId) {
        return sessionRepository.findById(sessionId).orElse(null);
    }

    public void updateSession(Sessions session) {
        sessionRepository.save(session);
    }

    public List<Sessions> findAll() {
        return sessionRepository.findAll();
    }
}

