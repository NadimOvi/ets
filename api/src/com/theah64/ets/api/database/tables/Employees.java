package com.theah64.ets.api.database.tables;

import com.theah64.ets.api.database.Connection;
import com.theah64.ets.api.models.Employee;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by theapache64 on 18/11/16,1:31 AM.
 * id          INT         NOT NULL AUTO_INCREMENT,
 * company_id  INT         NOT NULL,
 * name        VARCHAR(20) NOT NULL,
 * imei        VARCHAR(20) NOT NULL,
 * device_hash TEXT        NOT NULL,
 * fcm_id      TEXT        NOT NULL,
 * api_key     VARCHAR(20) NOT NULL,
 * is_active   TINYINT(4)  NOT NULL DEFAULT 1,
 * created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
 * PRIMARY KEY (id),
 * UNIQUE KEY (api_key),
 * FOREIGN KEY (company_id) REFERENCES companies (id)
 * ON UPDATE CASCADE
 * ON DELETE CASCADE
 */
public class Employees extends BaseTable<Employee> {

    private static final Employees instance = new Employees();
    public static final String COLUMN_API_KEY = "api_key";
    public static final String COLUMN_IMEI = "imei";
    public static final String COLUMN_FCM_ID = "fcm_id";
    public static final String COLUMN_DEVICE_HASH = "device_hash";

    private Employees() {
        super("employee");
    }

    public static Employees getInstance() {
        return instance;
    }

    @Override
    public void add(Employee newEmp) throws InsertFailedException {
        boolean isFailed = false;
        final String query = "INSERT INTO employees (company_id, name, imei,device_hash, fcm_id, api_key) VALUES (?,?,?,?,?,?); ";

        final java.sql.Connection con = Connection.getConnection();

        try {

            final PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, newEmp.getCompanyId());
            ps.setString(2, newEmp.getName());
            ps.setString(3, newEmp.getImei());
            ps.setString(4, newEmp.getDeviceHash());
            ps.setString(5, newEmp.getFcmId());
            ps.setString(6, newEmp.getApiKey());

            isFailed = ps.executeUpdate() != 1;
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            isFailed = true;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (isFailed) {
            throw new InsertFailedException("Failed to add new employee");
        }
    }
}