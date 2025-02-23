package deeper.into.you.todo_app.services;

import deeper.into.you.todo_app.notes.notifications.NoteEvent;
import deeper.into.you.todo_app.notes.notifications.NotificationRepository;
import deeper.into.you.todo_app.notes.notifications.ScheduledNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class KafkaConsumerNotificationService {

    private final NotificationRepository repository;
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerNotificationService.class);

    public KafkaConsumerNotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "notes-topic", groupId = "notification-group")
    public void handleNoteEvent(NoteEvent event) {
        switch (event.getEventType()) {
            case "CREATED" -> handleCreateEvent(event);
            case "UPDATED" -> handleUpdateEvent(event);
            case "DELETED" -> handleDeleteEvent(event);
            default -> log.warn("Unknown event type: {}", event.getEventType());
        }
    }

    private void handleCreateEvent(NoteEvent event) {
        ScheduledNotification notification = new ScheduledNotification();
        notification.setNoteId(event.getNoteId());
        notification.setUserEmail(event.getUserEmail());
        notification.setNoteTitle(event.getNoteTitle());
        notification.setNoteContent(event.getNoteContent());
        notification.setTodoDate(event.getTodoDate());

        repository.save(notification);
        log.info("Created notification for note ID: {}", event.getNoteId());
    }

    private void handleUpdateEvent(NoteEvent event) {
        repository.findByNoteId(event.getNoteId()).ifPresentOrElse(
                existing -> {
                    existing.setNoteTitle(event.getNoteTitle());
                    existing.setTodoDate(event.getTodoDate());
                    existing.setNoteContent(event.getNoteContent());
                    existing.setSent(false); // Сбрасываем статус отправки
                    repository.save(existing);
                    log.info("Updated notification for note ID: {}", event.getNoteId());
                },
                () -> log.warn("Note ID {} not found for update", event.getNoteId())
        );
    }

    private void handleDeleteEvent(NoteEvent event) {
        repository.deleteByNoteId(event.getNoteId());
        log.info("Deleted notification for note ID: {}", event.getNoteId());
    }

}
