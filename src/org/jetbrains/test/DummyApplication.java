package org.jetbrains.test;

import java.util.List;
import java.util.Random;

/**
 * Nikolay.Tropin
 * 18-Apr-17
 */
public class DummyApplication {
    private final List<String> args;
    private final Random random = new Random(System.nanoTime());
    private final CallTree callTree = new CallTree();

    public DummyApplication(List<String> args) {
        this.args = args;
    }

    private boolean nextBoolean() {
        return random.nextBoolean();
    }

    private boolean stop() {
        return random.nextDouble() < 0.05;
    }

    private String nextArg() {
        int idx = random.nextInt(args.size());
        return args.get(idx);
    }

    private void sleep() {
        try {
            Thread.sleep(random.nextInt(20));
        } catch (InterruptedException ignored) {

        }
    }

    private void abc(String s) {
        callTree.startCall("abc", s);

        sleep();
        if (stop()) {
            //do nothing
        } else if (nextBoolean()) {
            def(nextArg());
            foo(nextArg(), nextBoolean());
        } else {
            xyz(nextArg());
            bar(nextArg(), nextBoolean());
        }

        callTree.endCall();
    }

    private void def(String s) {
        callTree.startCall("def", s);

        sleep();
        if (stop()) {
            //do nothing
        } else if (nextBoolean()) {
            abc(nextArg());
        } else {
            xyz(nextArg());
        }

        callTree.endCall();
    }

    private void xyz(String s) {
        callTree.startCall("xyz", s);

        sleep();
        if (stop()) {
            //do nothing
        } else if (nextBoolean()) {
            abc(nextArg());
        } else {
            def(nextArg());
        }

        callTree.endCall();
    }

    private void foo(String s, boolean b) {
        callTree.startCall("foo", s, b);

        sleep();
        if (stop()) {
            //do nothing
        } else if (nextBoolean()) {
            bar(nextArg(), nextBoolean());
        } else {
            baz(nextArg(), nextBoolean());
        }

        callTree.endCall();
    }

    private void baz(String s, boolean b) {
        callTree.startCall("baz", s, b);

        sleep();
        if (stop()) {
            //do nothing
        } else if (nextBoolean()) {
            foo(nextArg(), nextBoolean());
        } else {
            bar(nextArg(), nextBoolean());
        }

        callTree.endCall();
    }

    private void bar(String s, boolean b) {
        callTree.startCall("bar", s, b);

        sleep();
        if (stop()) {
            //do nothing
        } else if (nextBoolean()) {
            foo(nextArg(), nextBoolean());
        } else {
            baz(nextArg(), nextBoolean());
        }

        callTree.endCall();
    }

    public void start() {
        Thread currentThread = Thread.currentThread();
        String threadName = currentThread.getName();
        callTree.init(threadName);
        abc(nextArg());
    }

    public CallTree getCallTree() {
        return callTree;
    }
}
