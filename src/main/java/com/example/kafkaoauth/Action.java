// src/main/java/com/example/kafkaoauth/Action.java
package com.example.kafkaoauth;

public class Action {
    private String name;
    private boolean granted;

    public Action(String name, boolean granted) {
        this.name = name;
        this.granted = granted;
    }

    public String getName() {
        return name;
    }

    public boolean getGranted() {
        return granted;
    }
}
