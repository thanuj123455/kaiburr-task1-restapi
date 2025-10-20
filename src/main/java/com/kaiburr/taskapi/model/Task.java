package com.kaiburr.taskapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String name;
    private String owner;
    private String command;
    private List<Execution> executions;

    // No-arg constructor
    public Task() {}

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }

    public List<Execution> getExecutions() {
        return executions;
    }
    public void setExecutions(List<Execution> executions) {
        this.executions = executions;
    }

    // Nested static Execution class
    public static class Execution {
        private String startTime;
        private String endTime;
        private String output;

        // No-arg constructor
        public Execution() {}

        // Getters and Setters
        public String getStartTime() {
            return startTime;
        }
        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }
        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getOutput() {
            return output;
        }
        public void setOutput(String output) {
            this.output = output;
        }
    }
}
