package net.kotek.jdbm;


import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CompactTest extends TestCaseWithTestFile {
    
    final int MAX = 1000 * 1000;

    public void testHashCompaction() throws IOException{

        String f = newTestFile();
        
        DB db0 = new DBMaker(f).disableTransactions().build();
        Map<String,String> db = db0.createHashMap("db");

        System.out.println("Adding");
        for( int i=0 ; i < MAX; i++) {
            db.put("key"+i, "value"+i);
        }

        db0.close();
        db0 = new DBMaker(f).disableTransactions().build();
        db =  db0.getHashMap("db");

        System.out.println("Deleting");
        for( int i=0 ; i < MAX; i++) {
            db.remove("key"+i);
        }

        db0.close();
        db0 = new DBMaker(f).disableTransactions().build();
        db =  db0.getHashMap("db");


        System.out.println("Adding");
        for( int i=0 ; i < MAX; i++) {
            db.put("key"+i, "value"+i);
        }

        db0.close();
    }

    public void testBTreeCompaction() throws IOException{

        String f = newTestFile();

        DB db0 = new DBMaker(f).disableTransactions().build();
        Map<String,String> db = db0.createTreeMap("db");

        System.out.println("Adding");
        for( int i=0 ; i < MAX; i++) {
            db.put("key"+i, "value"+i);
        }

        db0.close();
        db0 = new DBMaker(f).disableTransactions().build();
        db =  db0.getTreeMap("db");

        System.out.println("Deleting");
        for( int i=0 ; i < MAX; i++) {
            db.remove("key"+i);
        }

        db0.close();
        db0 = new DBMaker(f).disableTransactions().build();
        db =  db0.getTreeMap("db");



        System.out.println("Adding");
        for( int i=0 ; i < MAX; i++) {
            db.put("key"+i, "value"+i);
        }

        db0.close();
    }

}
