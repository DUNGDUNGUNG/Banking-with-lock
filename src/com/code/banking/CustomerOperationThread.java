package com.code.banking;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class CustomerOperationThread implements Runnable {
    private static int balance = 100000000;
    private static ReentrantLock lock = new ReentrantLock();
    private static int moneyToPay;
    private static BufferedWriter bw;
    private Date date = new Date();

    static {
        try {
            bw = new BufferedWriter(new FileWriter("transB.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    lock.lock();

                    Product lineByPrice = Store.result.take();
                    System.out.println(Thread.currentThread().getName() + ": " +
                            "Ten san pham: " + lineByPrice.getName() + " | " + lineByPrice.getPrice());
                    checkTheAmountToPay(lineByPrice.getPrice());
                    Store.payTheExcess(moneyToPay, lineByPrice.getPrice(), lineByPrice.getName());
                    takeMoneyRedundant(Store.getMoneyExcess(), lineByPrice.getName());
                    bw.write("Giao dich mua | Ngay mua: " + date.toString() + ", nhan vien: " + Thread.currentThread().getName() +
                            ", ten san pham: " + lineByPrice.getName() + ", so tien " + moneyToPay);
                    bw.newLine();

                    bw.write("Giao dich nhan tien thua | Ngay mua: " + date.toString() + ", ten san pham: " + lineByPrice.getName() + ", so tien " + Store.getMoneyExcess());
                    bw.newLine();
                    System.out.println();
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void payMoney(int amount) {
        lock.lock();
        try {
            System.out.println("So tien dung de mua hang la : " + amount);
            if (balance < amount) {
                System.out.println("Khong du tien mua hang");
            }
            balance -= amount;
            System.out.println("So tien dung de mua hang la: " + amount + ", Tien con trong tai khoan la: " + balance);
        } finally {
            lock.unlock();
        }
    }

    private void checkTheAmountToPay(int priceProduct) {
        int[] denominations = {100000, 50000, 20000};
        int i = 0;
        int amount = 0;
        for (int denomination : denominations) {
            i++;
            int integer = priceProduct / denomination;
            int money = 0;
            priceProduct = priceProduct % denomination;
            if (integer > 0) {
                System.out.println("Menh gia: " + denomination + " | so dong: " + integer);
                money = (denomination * integer);
                amount += money;
            }
        }
        if (priceProduct != 0) {
            System.out.println("Menh gia: " + denominations[2] + " | so dong " + 1);
            moneyToPay = amount + denominations[2];
        } else {
            moneyToPay = amount;
        }
        System.out.println("Tien mua hang can dua: " + (moneyToPay));
        payMoney(moneyToPay);
    }

    private void takeMoneyRedundant(int moneyExcess, String nameProduct) {
        lock.lock();
        try {
            balance += moneyExcess;
            System.out.println("Tien du cua san pham " + nameProduct + " la: " + moneyExcess + " | Tai khoan Cong ty hien co: " + balance);
        } finally {
            lock.unlock();
        }
    }
}
