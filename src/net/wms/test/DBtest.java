package net.wms.test;

import net.wms.util.LocalDataStore;

public class DBtest {

    public static void main(String[] args) {

       try {
           LocalDataStore.initialize();
           System.out.println("Local data store is ready.");
       }catch (Exception e){
           e.printStackTrace();
       }



    }


}
