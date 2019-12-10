package controller.reporter;

import model.TimestampState;

import javax.persistence.EntityManager;

public interface Reporter {
    public boolean tickTask(EntityManager database, TimestampState timestamp);
}
