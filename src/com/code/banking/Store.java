package com.code.banking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class Store extends Thread {

    private static int balance = 2000000;
    private BufferedReader bufferedReader = null;
    private static ReentrantLock lock = new ReentrantLock();
    private static int moneyExcess;
    static LinkedBlockingDeque<Product> result = new LinkedBlockingDeque<>();

    public static int getMoneyExcess() {
        return moneyExcess;
    }

    public void run() {
        try {
            String strFile = "/home/cong/Downloads/bai4/orderlist.csv";
            bufferedReader = new BufferedReader(new FileReader(strFile));
            String line = null;
            Product product = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineSp = line.split(",");
                String name = lineSp[0];
                int price = Integer.parseInt(lineSp[1]);
                product = new Product(name, price);
                result.offer(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void payTheExcess(int amount, int price, String nameProduct) {
        lock.lock();
        try {
            moneyExcess = amount - price;
            System.out.println("Tien con du cua khach hang " + Thread.currentThread().getName() + " la: " + moneyExcess + " sau khi mua san pham: " + nameProduct);
            balance += price;
            System.out.println("Tai khoan cua ban duoc cong them : " + price + ", tai khoan cua cua hang hien co: " + balance);
        } finally {
            lock.unlock();
        }
    }

}

