/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PRODUCTPACKAGES;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
/**
 *
 * @author Zainab
 */
@ApplicationScoped
public class ProductItem {
    
    private List<Product> ProductItem;

    public ProductItem() {
       ProductItem = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM Products_Table";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getInt("Pro_ID"),
                        rs.getString("Pro_name"),
                        rs.getString("Pro_Description"),
                        rs.getInt("Pro_Quantity"));
                ProductItem.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JsonArray toJSON() {
        JsonArrayBuilder json = Json.createArrayBuilder();
        for (Product p : ProductItem) {
            json.add(p.toJSON());
        }
        return json.build();
    }

    public Product get(int ProductItem) {
        Product result = null;
        for (Product p : ProductItem) {
            if (p.getPro_ID() == Pro_ID) {
                result = p;
            }
        }
        return result;
    }

    public void set(int Pro_ID, Product p) {
        int result = doUpdate(
                "update product SET Pro_name = ?, Pro_Description = ?, Pro_Quantity = ? where Pro_ID = ?",
                p.getPro_name(),
                p.getPro_Description(),
                String.valueOf(p.getPro_Quantity()),
                String.valueOf(Pro_ID));
        if (result > 0) {
            Product original = get(Pro_ID);
            original.setPro_name(p.getPro_name());
            original.setPro_Description(p.getPro_Description());
            original.setPro_Quantity(p.getPro_Quantity());
        }

    }

    public void add(Product p) throws Exception {
        int result = doUpdate(
                "INSERT into Products_Table (Pro_ID,Pro_name, Pro_Description,Pro_Quantity ) values (?, ?, ?, ?)",
                String.valueOf(p.getPro_ID()),
                p.getPro_name(),
                p.getPro_Description(),
                String.valueOf(p.getPro_Quantity()));
        if (result > 0) {
            ProductItem.add(p);
        } else {
            throw new Exception("Error Inserting");
        }
    }
    


    public void remove(Product p) throws Exception {
        remove(p.getPro_ID());
    }

    public void remove(int Pro_ID) throws Exception {
        int result = doUpdate("DELETE from Products_Table where Pro_ID = ?",
                String.valueOf(Pro_ID));
        if (result > 0) {
            Product original = get(Pro_ID);
            ProductItem.remove(original);
        } else {
            throw new Exception("Delete failed");
        }

    }

    private Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://localhost/javaproducts";
            conn = (Connection) DriverManager.getConnection(jdbc, "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private int doUpdate(String query, String... params) {
        int numChanges = 0;
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            numChanges = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numChanges;
    }

}

    

