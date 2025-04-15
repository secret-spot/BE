package com.example.SecretSpot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

@SpringBootTest
public class TableExistenceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void checkTableExistence() throws Exception {
        String[] tableNames = {
                "users", "keywords", "guides", "places", "regions",
                "reviews", "scraps", "guide_images", "guide_keywords",
                "guide_places", "guide_regions", "region_keywords",
                "user_keywords", "rankings", "qnas"
        };

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            System.out.println("=== Table Existence Check ===");
            for (String tableName : tableNames) {
                try (ResultSet tables = metaData.getTables(null, null, tableName, null)) {
                    if (tables.next()) {
                        System.out.println("Table exists: " + tableName);
                    } else {
                        System.out.println("Table missing: " + tableName);
                    }
                }
            }
            System.out.println("=============================");
        }
    }
}
