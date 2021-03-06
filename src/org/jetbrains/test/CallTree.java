package org.jetbrains.test;

import com.google.gson.annotations.Expose;

public class CallTree {
    @Expose
    private CallEntry root;
    private CallEntry context;
    @Expose
    private String threadName;

    public void init(String threadName) {
        root = new CallEntry(null);
        context = root;
        this.threadName = threadName;
    }

    public void startCall(String methodName, Object... args) {
        Assert.notNull(context, "context must not be null");
        context = context.createNestedCallEntry(methodName, args);
        context.startCall();
    }

    public void endCall() {
        Assert.notNull(context, "context must not be null");
        context.endCall();
        context = context.getParent();
    }

    public CallEntry getRoot() {
        return root;
    }

    public String getThreadName() {
        return threadName;
    }
}
