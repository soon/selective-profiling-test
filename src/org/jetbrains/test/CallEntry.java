package org.jetbrains.test;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CallEntry {
    private final CallEntry parent;
    @Expose
    private final List<CallEntry> nestedCalls = new ArrayList<>();
    @Expose
    private final String methodName;
    @Expose
    private final List<Object> args;
    @Expose
    private long startedTimeMillis;
    @Expose
    private long endedTimeMillis;

    public CallEntry(CallEntry parent) {
        this(parent, null, null);
    }

    public CallEntry(CallEntry parent, String methodName, Object[] args) {
        this.parent = parent;
        this.methodName = methodName;
        if (args == null) {
            this.args = Collections.emptyList();
        } else {
            this.args = Arrays.asList(args);
        }
    }

    public CallEntry getParent() {
        return parent;
    }

    public List<CallEntry> getNestedCalls() {
        return Collections.unmodifiableList(nestedCalls);
    }

    public CallEntry createNestedCallEntry(String methodName, Object[] args) {
        CallEntry nestedCallEntry = new CallEntry(this, methodName, args);
        nestedCalls.add(nestedCallEntry);
        return nestedCallEntry;
    }

    public void startCall() {
        startedTimeMillis = System.currentTimeMillis();
    }

    public void endCall() {
        endedTimeMillis = System.currentTimeMillis();
    }

    public String getMethodName() {
        return methodName;
    }

    public List<Object> getArgs() {
        return Collections.unmodifiableList(args);
    }

    public long getStartedTimeMillis() {
        return startedTimeMillis;
    }

    public long getEndedTimeMillis() {
        return endedTimeMillis;
    }
}
