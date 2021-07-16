package com.jb.db;

import com.jb.utils.DBUtils;
import java.sql.SQLException;

public class DatabaseManager {

    public static final String URL = "jdbc:mysql://localhost:3306" +
            "?createDatabaseIfNotExist=FALSE" +
            "&useTimezone=TRUE" +
            "&serverTimezone=UTC";
    public  static final String USER = "root";
    public  static final String PASS = "Shaked3211";

    private static final String DROP_SCHEMA = "drop schema coupons;";
    private static final String CREATE_SCHEMA = "CREATE SCHEMA coupons;";

    private static final String CREATE_TABLE_COMPANIES = " "+
            "CREATE TABLE `coupons`.`companies` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `name` VARCHAR(45) NOT NULL," +
            "  `email` VARCHAR(45) NOT NULL," +
            "  `password` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`));";

    private static final String CREATE_TABLE_CUSTOMERS = "" +
            "CREATE TABLE `coupons`.`customers` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `first_name` VARCHAR(45) NOT NULL," +
            "  `last_name` VARCHAR(45) NOT NULL," +
            "  `email` VARCHAR(45) NOT NULL," +
            "  `password` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`));";

    private static final String CREATE_TABLE_CATEGORIES = "" +
            "    CREATE TABLE `coupons`.`categories` (" +
            "            `id` INT NOT NULL AUTO_INCREMENT," +
            "  `name` VARCHAR(45) NOT NULL," +
            "    PRIMARY KEY (`id`));";

    private static final String INSERT_TABLE_CATEGORIES =
            "INSERT INTO `coupons`.`categories` (`name`) VALUES ('Food'),('Electricity'),('Restaurant'),('Vacation');";


    private static final String CREATE_TABLE_COUPONS = "" +
            "CREATE TABLE `coupons`.`coupons` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `company_id` INT NOT NULL," +
            "  `category_id` INT NOT NULL," +
            "  `title` VARCHAR(45) NOT NULL," +
            "  `description` VARCHAR(45) NOT NULL," +
            "  `start_date` DATE NOT NULL," +
            "  `end_date` DATE NOT NULL," +
            "  `amount` INT NOT NULL," +
            "  `price` DOUBLE NOT NULL," +
            "  `image` VARCHAR(45) NOT NULL," +
            "  PRIMARY KEY (`id`)," +
            "  INDEX `id_idx` (`company_id` ASC) VISIBLE," +
            "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE," +
            "  CONSTRAINT `company_id`" +
            "    FOREIGN KEY (`company_id`)" +
            "    REFERENCES `coupons`.`companies` (`id`)" +
            "    ON DELETE NO ACTION" +
            "    ON UPDATE NO ACTION," +
            "  CONSTRAINT `category_id`" +
            "    FOREIGN KEY (`category_id`)" +
            "    REFERENCES `coupons`.`categories` (`id`)" +
            "    ON DELETE NO ACTION" +
            "    ON UPDATE NO ACTION);";

    private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "" +
            "CREATE TABLE `coupons`.`customers_vs_coupons` (" +
            "            `customer_id` INT NOT NULL," +
            "            `coupon_id` INT NOT NULL," +
            "    PRIMARY KEY (`customer_id`, `coupon_id`)," +
            "    INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE," +
            "    CONSTRAINT `customer_id`" +
            "    FOREIGN KEY (`customer_id`)" +
            "    REFERENCES `coupons`.`customers` (`id`)" +
            "    ON DELETE NO ACTION" +
            "    ON UPDATE NO ACTION," +
            "    CONSTRAINT `coupon_id`" +
            "    FOREIGN KEY (`coupon_id`)" +
            "    REFERENCES `coupons`.`coupons` (`id`)" +
            "    ON DELETE NO ACTION" +
            "    ON UPDATE NO ACTION);";

    public static void dropAndCreate() throws SQLException {

        DBUtils.runQuery(DROP_SCHEMA);
        DBUtils.runQuery(CREATE_SCHEMA);
        DBUtils.runQuery(CREATE_TABLE_COMPANIES);
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS);
        DBUtils.runQuery(CREATE_TABLE_CATEGORIES);
        DBUtils.runQuery(INSERT_TABLE_CATEGORIES);
        DBUtils.runQuery(CREATE_TABLE_COUPONS);
        DBUtils.runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
    }




}
