package dev.c20.workflow.commons.wrapper.responses;

import java.util.ArrayList;
import java.util.List;

public class Activity {

    String path;
    String name;
    Long count = 0l;
    List<Activity> activities = new ArrayList<>();

    public List<Activity> getActivities() {
        return activities;
    }

    public Activity setActivities(List<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Activity setPath(String path) {
        this.path = path;
        return this;
    }

    public String getName() {
        return name;
    }

    public Activity setName(String name) {
        this.name = name;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public Activity setCount(Long count) {
        this.count = count;
        return this;
    }

}
