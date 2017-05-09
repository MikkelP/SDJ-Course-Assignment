package jysk_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jysk_shared.Box;
import jysk_shared.Pallet;

public class Tower implements ITower {

	private Connection sqlCon; 
	private String type; 
	private String towerName; 
	private final String sqlUsername = "postgres"; 
	private final String sqlPassword = "123456"; 
	private final String sqlHost = "jdbc:postgresql://localhost:5432/"; 

	public Tower(String towerName, String typeOfItems) {
		try {
			sqlCon = DriverManager.getConnection(sqlHost+towerName, sqlUsername, sqlPassword);
		} catch (SQLException e) {
			connectToMaint(towerName);
		} 
		this.towerName = towerName; 
		type = typeOfItems; 
	}

	private void connectToMaint(String towerName) {
		try {
			sqlCon = DriverManager.getConnection(sqlHost+"postgres", sqlUsername, sqlPassword);
			PreparedStatement ps = sqlCon.prepareStatement("CREATE DATABASE "+towerName);
			ps.execute();
			sqlCon.close(); 

			sqlCon = DriverManager.getConnection(sqlHost+towerName, sqlUsername, sqlPassword);
			ps = sqlCon.prepareStatement("CREATE TABLE pallets ("
					+ "id int PRIMARY KEY); CREATE TABLE boxes (id serial PRIMARY KEY, "
					+ "palletID int REFERENCES pallets (id), item varchar(40), amount int);");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} 	
	}

	@Override
	public boolean storePallet(Pallet pallet) {
		try {
			PreparedStatement ps = sqlCon.prepareStatement("INSERT INTO pallets (id) VALUES (?)"); 
			ps.setInt(1, pallet.getID());
			ps.execute();

			for (Box b : pallet.getBoxes()) {
				ps = sqlCon.prepareStatement("INSERT INTO boxes (palletID, item, amount) VALUES (?, ?, ?)");
				ps.setInt(1, pallet.getID());
				ps.setString(2, b.getItem());
				ps.setInt(3, b.getAmount());
				ps.execute();
			}
			return true; 
		} catch (SQLException e) {
			e.printStackTrace();
			return false; 
		}
	}

	@Override
	public Pallet retrievePallet() {
		try {
			PreparedStatement ps = sqlCon.prepareStatement("SELECT * FROM pallets LIMIT 1");

			ResultSet rs = ps.executeQuery();

			rs.next();
		    int id = rs.getInt(1); 


			Pallet p = new Pallet(type, id); 

			ps = sqlCon.prepareStatement("SELECT item, amount FROM boxes WHERE palletID = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				String item = rs.getString(1);
				int amount = rs.getInt(2); 
				p.addBox(new Box(type, item, amount));
			}
			removePallet(id); 
			return p; 

		} catch (SQLException e) {
			e.printStackTrace();
			return null; 
		}
	}

	private void removePallet(int id) throws SQLException {
		PreparedStatement ps = sqlCon.prepareStatement("DELETE FROM boxes WHERE palletid = ?");
		ps.setInt(1, id);
		ps.executeUpdate();

		ps = sqlCon.prepareStatement("DELETE FROM pallets WHERE id = ?");
		ps.setInt(1, id);
		ps.executeUpdate();
	}
}
