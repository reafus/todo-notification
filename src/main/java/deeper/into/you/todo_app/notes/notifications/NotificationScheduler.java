package deeper.into.you.todo_app.notes.notifications;

import deeper.into.you.todo_app.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationScheduler {
    private final NotificationRepository repository;
    private final EmailService emailService;
    private static final Logger log = LoggerFactory.getLogger(NotificationScheduler.class);

    public NotificationScheduler(NotificationRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "${scheduler.cron:0 35 19 * * *}")
    public void checkScheduledNotifications() {
        LocalDate today = LocalDate.now();
        List<ScheduledNotification> notifications =
                repository.findByTodoDateAndSentFalse(today);

        notifications.forEach(notification -> {
            try {
                emailService.sendEmail(
                        notification.getUserEmail(),
                        "Напоминание: " + notification.getNoteTitle(),
                        "Описание: " + notification.getNoteContent()
                );
                notification.setSent(true);
                repository.save(notification);
            } catch (Exception e) {
                log.error("Failed to send notification for note ID {}: {}",
                        notification.getNoteId(), e.getMessage());
            }
        });
    }
}
