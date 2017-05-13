package org.jetbrains.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CallEntry {
    private final CallEntry parent;
    private final List<CallEntry> nestedCalls = new ArrayList<>();
    private final String methodName;
    private final List<Object> args;
    private long startedTimeMillis;
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
