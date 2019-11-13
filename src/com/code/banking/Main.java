package com.code.banking;

public class Main {
    public static void main(String[] args) {

        Store store = new Store();
        store.start();

        CustomerOperationThread customerOperationThread = new CustomerOperationThread();
        Thread thread = new Thread(customerOperationThread);
        thread.setName("Nguyễn Văn A");
        thread.start();

        Thread thread1 = new Thread(customerOperationThread);
        thread1.setName("Nguyễn Văn B");
        thread1.start();

        Thread thread2 = new Thread(customerOperationThread);
        thread2.setName("Nguyễn Văn C");
        thread2.start();

        Thread thread3 = new Thread(customerOperationThread);
        thread3.setName("Nguyễn Văn D");
        thread3.start();

        Thread thread4 = new Thread(customerOperationThread);
        thread4.setName("Nguyễn Văn G");
        thread4.start();

        Thread thread5 = new Thread(customerOperationThread);
        thread5.setName("Nguyễn Văn H");
        thread5.start();

        Thread thread6 = new Thread(customerOperationThread);
        thread6.setName("Nguyễn Văn K");
        thread6.start();

        Thread thread7 = new Thread(customerOperationThread);
        thread7.setName("Nguyễn Văn L");
        thread7.start();

        Thread thread8 = new Thread(customerOperationThread);
        thread8.setName("Nguyễn Văn T");
        thread8.start();

        Thread thread9 = new Thread(customerOperationThread);
        thread9.setName("Nguyễn Văn X");
        thread9.start();
    }
}