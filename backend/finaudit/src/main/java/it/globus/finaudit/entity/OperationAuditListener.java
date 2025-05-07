package it.globus.finaudit.listener;

import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.entity.OperationLogHistory;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OperationAuditListener {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @PostPersist
    public void postPersist(Operation operation) {
        saveHistory(operation, OperationLogHistory.Action.CREATED);
    }

    @PostUpdate
    public void postUpdate(Operation operation) {
        saveHistory(operation, OperationLogHistory.Action.UPDATED);
    }

    @PostRemove
    public void postRemove(Operation operation) {
        saveHistory(operation, OperationLogHistory.Action.DELETED);
    }

    private void saveHistory(Operation operation, OperationLogHistory.Action action) {
        try {
            OperationLogHistory history = new OperationLogHistory();
            history.setOperationId(operation.getId());
            history.setOperationSnapshot(objectMapper.writeValueAsString(operation));
            history.setModifiedById(getCurrentUserId());
            history.setAction(action);

            BeanUtil.getBean(OperationLogHistoryRepository.class).save(history);
        } catch (Exception e) {
            throw new RuntimeException("Audit logging failed", e);
        }
    }

    private Long getCurrentUserId() {
        // Реализация через Spring Security
        return SecurityContextUtil.getCurrentUserId();
    }
}
