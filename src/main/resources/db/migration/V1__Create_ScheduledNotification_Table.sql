CREATE TABLE scheduled_notification (
    id BIGSERIAL PRIMARY KEY,
    note_id BIGINT UNIQUE NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    note_title VARCHAR(255) NOT NULL,
    note_content TEXT,
    todo_date DATE NOT NULL,
    sent BOOLEAN DEFAULT FALSE
);

CREATE INDEX idx_note_id ON scheduled_notification(note_id);
CREATE INDEX idx_todo_date ON scheduled_notification(todo_date);