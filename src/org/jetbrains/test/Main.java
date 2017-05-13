package org.jetbrains.test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            int start = 100 * i;
            List<String> arguments = IntStream.range(start, start + 10)
                    .mapToObj(Integer::toString)
                    .collect(Collectors.toList());
            service.submit(() -> {
                DummyApplication dummyApplication = new DummyApplication(arguments);
                dummyApplication.start();
                CallTree callTree = dummyApplication.getCallTree();
                writeCallTreeToFile(callTree);
            });
        }
        service.shutdown();
    }

    private static void writeCallTreeToFile(CallTree callTree) {
        try {
            String fileName = callTree.getThreadName() + "-" + UUID.randomUUID() + ".json";
            CallTreeSerializer.writeToFile(callTree, fileName);
            System.out.println("Written call tree for thread " + callTree.getThreadName() + " to file " + fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
