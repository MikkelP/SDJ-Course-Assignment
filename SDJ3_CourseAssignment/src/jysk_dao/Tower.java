package jysk_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jysk_shared.Box;
import jysk_shared.Pallet;

public class Tower implements ITower {

	private Connection sqlCon; 
	private String towerName; 
	private final String sqlUsername = "postgres"; 
	private final String sqlPassword = "123456"; 
	private final String sqlHost = "jdbc:postgresql://localhost:5432/postgres"; 

	public Tower(String towerName) {
		try {
			sqlCon = DriverManager.getConnection(sqlHost, sqlUsername, sqlPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		this.towerName = towerName; 
	}

	@Override
	public boolean storePallet(Pallet pallet, String whatTower) {
		try {
			PreparedStatement ps = sqlCon.prepareStatement("INSERT INTO ? (palletID, amountOfBoxes) VALUES (?, ?, ?)"); 
			ps.setString(1, whatTower);
			ps.setString(2, pallet.getID());
			ps.setInt(3, pallet.amountOfBoxes());		
			return ps.execute(); 
		} catch (SQLException e) {
			e.printStackTrace();
			return false; 
		}
	}

	//This method is NOT done yet. 
	@Override
	public Pallet retrievePallet() {
		try {
			PreparedStatement ps = sqlCon.prepareStatement("SELECT * FROM ? LIMIT 1");
			ps.setString(1, towerName);

			ResultSet rs = ps.executeQuery();
			String palletID = rs.getString(1);
			String palletType = rs.getString(2); 

			ps = sqlCon.prepareStatement("SELECT * FROM ? WHERE belongsTo = ?"); 
			String boxesTable = towerName+"BOX"; 
			ps.setString(1, boxesTable);
			ps.setString(2, palletID);
			rs = ps.executeQuery(); 

			Pallet p = new Pallet(palletType, palletID); 
			while (rs.isAfterLast()) 
			{
				String type = rs.getString(1); 
				String item = rs.getString(2); 
				int amount = rs.getInt(3); 
				p.addBox(new Box(type, item, amount)); 
			}
			return p; 
		} catch (SQLException e) {
			e.printStackTrace();
			return null; 
		}
	}
}
