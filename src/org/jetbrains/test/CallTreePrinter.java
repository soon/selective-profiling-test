package org.jetbrains.test;

import java.util.List;
import java.util.stream.Collectors;

public final class CallTreePrinter {
    public static final String NESTED_ENTRY_EMPTY_OFFSET = "  ";
    public static final String NESTED_ENTRY_OFFSET = "+ ";
    public static final String LAST_ENTRY_PREFIX = "\\--";
    public static final String ENTRY_PREFIX = "+--";

    private CallTreePrinter() {
    }

    public static synchronized void printTree(CallTree callTree) {
        CallEntry root = callTree.getRoot();
        List<CallEntry> nestedCalls = root.getNestedCalls();
        if (nestedCalls.isEmpty()) {
            System.out.printf("Empty call tree (thread %s)\n", callTree.getThreadName());
        } else {
            long startTime = nestedCalls.get(0).getStartedTimeMillis();
            System.out.printf("-- thread: %s, start time: %d\n", callTree.getThreadName(), startTime);
            for (int i = 0; i < nestedCalls.size(); i++) {
                CallEntry nestedCall = nestedCalls.get(i);
                printCallEntry(nestedCall, startTime, i, nestedCalls.size(), NESTED_ENTRY_EMPTY_OFFSET);
            }
        }
    }

    private static void printCallEntry(CallEntry callEntry, long startTime, int callNo,
                                       int callsNumOnSameLevel, String parentPrefix) {
        String nestedPrefix;
        String prefix;
        if (callNo == callsNumOnSameLevel - 1) {
            nestedPrefix = parentPrefix + NESTED_ENTRY_EMPTY_OFFSET;
            prefix = LAST_ENTRY_PREFIX;
        } else {
            nestedPrefix = parentPrefix + NESTED_ENTRY_OFFSET;
            prefix = ENTRY_PREFIX;
        }

        List<CallEntry> nestedCalls = callEntry.getNestedCalls();
        System.out.printf("%s%s %s(%s), start: +%s, end: +%s, duration: %s, nested calls: %d\n",
                parentPrefix, prefix,
                callEntry.getMethodName(), callEntry.getArgs().stream().map(CallTreePrinter::formatArg)
                        .collect(Collectors.joining(", ")),
                formatTimeMillis(callEntry.getStartedTimeMillis() - startTime),
                formatTimeMillis(callEntry.getEndedTimeMillis() - startTime),
                formatTimeMillis(callEntry.getEndedTimeMillis() - callEntry.getStartedTimeMillis()),
                nestedCalls.size());

        for (int i = 0; i < nestedCalls.size(); i++) {
            CallEntry nestedCall = nestedCalls.get(i);
            printCallEntry(nestedCall, startTime, i, nestedCalls.size(), nestedPrefix);
        }
    }

    private static String formatTimeMillis(long millis) {
        String suffix = "ms";
        double result = millis;

        if (result > 1000) {
            result /= 1000;
            suffix = "s";

            if (result > 60) {
                result /= 60;
                suffix = "m";
            }
        }

        return String.format("%.2f%s", result, suffix);
    }

    private static String formatArg(Object o) {
        if (o == null) {
            return "null";
        }
        if (o instanceof String) {
            return String.format("\"%s\"", o);
        }

        return o.toString();
    }
}
