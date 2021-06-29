package spring.authentication.web.models;

import java.time.Instant;

public class ErrorResponse {

    private Instant time;
    private String message;

    public ErrorResponse(String message) {
        this.time = Instant.now();
        this.message = message;
    }

    public Instant getTime() {
        return time;
    }

    public ErrorResponse setTime(Instant time) {
        this.time = time;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorResponse{");
        sb.append("time=").append(time);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
