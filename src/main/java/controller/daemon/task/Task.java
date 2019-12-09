package controller.daemon.task;

import javax.persistence.EntityManager;

public interface Task {
    public boolean tickTask(EntityManager database);
}
