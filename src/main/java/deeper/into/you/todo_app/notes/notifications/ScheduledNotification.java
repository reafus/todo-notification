package deeper.into.you.todo_app.notes.notifications;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "scheduled_notification")
public class ScheduledNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long noteId;
    private String userEmail;
    private String noteTitle;
    private String noteContent;
    private LocalDate todoDate;
    private boolean sent = false;

    public ScheduledNotification() {
    }

    public ScheduledNotification(Long id, Long noteId, String noteTitle, boolean sent, LocalDate todoDate, String userEmail, String noteContent) {

        this.id = id;
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.sent = sent;
        this.todoDate = todoDate;
        this.userEmail = userEmail;
        this.noteContent = noteContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public LocalDate getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(LocalDate todoDate) {
        this.todoDate = todoDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }
}
