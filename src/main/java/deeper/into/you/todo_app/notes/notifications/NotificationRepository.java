package deeper.into.you.todo_app.notes.notifications;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<ScheduledNotification, Long> {

    List<ScheduledNotification> findByTodoDateAndSentFalse(LocalDate date);

    Optional<ScheduledNotification> findByNoteId(Long noteId);

    void deleteByNoteId(Long noteId);
}
