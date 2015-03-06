package com.cti.vpx.view;


import java.util.ArrayList;

class Pinger {

    private PingerThread pingers;
    public String neighbours;
    private String fromIP, toIP;
    String fromIpClass[], toIpClass[];
    ArrayList list = new ArrayList();
    ScanWindow parent;
    StatusWindow message;

    Pinger(String fromIP, String toIP, ScanWindow parent, StatusWindow message) throws Exception {
        fromIpClass = fromIP.split("\\.");
        toIpClass = toIP.split("\\.");
        this.fromIP = fromIpClass[0] + "." + fromIpClass[1] + "." + fromIpClass[2] + ".";
        this.toIP = toIpClass[0] + "." + toIpClass[1] + "." + toIpClass[2] + ".";
        list.add("IP Address");
        this.parent = parent;
        this.message = message;
        findNeighbours();
    }

    private void findNeighbours() throws Exception {
        pingers = new PingerThread(Integer.parseInt(fromIpClass[3]), Integer.parseInt(toIpClass[3]));
        pingers.start();
    }

    public void getNeighbours(String neighbours, int i) throws Exception {
        this.neighbours = neighbours;
        list.add(neighbours);
    }

    class PingerThread extends Thread {

        private int lower, higher;

        private PingerThread(int argLow, int argHigh) throws Exception {
            lower = argLow;
            higher = argHigh;

        }
        int inc = 1;

        @Override
        public void run() {
            try {

                for (int i = lower; i <= higher; i++) {

                    Process pr = Runtime.getRuntime().exec("ping -w 50 -n 1 " + fromIP + i);
                    int res = pr.waitFor();
                    //System.out.println("res: " + res);
                    if (res == 0) {
                        getNodes(fromIP + i);
                        getNeighbours(fromIP + i, inc);
                        System.out.println("Pinging..." + fromIP + i);
                        inc++;
                    }

                }
                commandComplete();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void commandComplete() {
            message.close();
            ScanWindow.treeButton.setEnabled(true);
            ScanWindow.addButton.setEnabled(true);
        }

        public void getNodes(String ips) {
            ScanWindow.addingNodes(ips);
        }
    }
}
