package jysk_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jysk_shared.Pallet;

public class RemoteTower implements ITower {

	private Connection sqlCon; 
	private final String sqlUsername = "postgres"; 
	private final String sqlPassword = "123456"; 
	private final String sqlHost = "jdbc:postgresql://localhost:5432/postgres"; 
	
	public RemoteTower() {
		try {
			sqlCon = DriverManager.getConnection(sqlHost, sqlUsername, sqlPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
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
	public Pallet retrievePallet(String whatTower) {
//		try {
//		PreparedStatement ps = sqlCon.prepareStatement("SELECT * FROM ? WHERE ROWNUM = 1"); 
//		ps.setString(1, whatTower);	
//		ResultSet rs = ps.executeQuery();
//	    String palletID = rs.getString(1); 
//	    int amountOfBoxes = rs.getInt(2); 
//	
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		return null;
	}
}
