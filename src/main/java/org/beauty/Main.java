package org.beauty;


import org.beauty.db.DatabaseInitializer;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        DatabaseInitializer.initializeDatabase();
    }


}