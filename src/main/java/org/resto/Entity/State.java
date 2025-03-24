package org.resto.Entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class State {
    Status status_name;
    LocalDateTime statusModificationDate;

    public State(Status status_name, LocalDateTime statusModificationDate) {
        this.status_name = status_name;
        this.statusModificationDate = statusModificationDate;
    }

    public Status getStatus_name() {
        return status_name;
    }

    public void setStatus_name(Status status_name) {
        this.status_name = status_name;
    }

    public LocalDateTime getStatusModificationDate() {
        return statusModificationDate;
    }

    public void setStatusModificationDate(LocalDateTime statusModificationDate) {
        this.statusModificationDate = statusModificationDate;
    }

    @Override
    public boolean equals(Object o) {


        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return status_name == state.status_name && Objects.equals(statusModificationDate, state.statusModificationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status_name, statusModificationDate);
    }

    @Override
    public String toString() {
        return "State{" +
                "status_name=" + status_name +
                ", statusModificationDate=" + statusModificationDate +
                '}';
    }
}
