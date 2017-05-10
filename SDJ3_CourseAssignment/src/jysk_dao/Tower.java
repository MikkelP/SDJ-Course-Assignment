package jysk_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jysk_shared.Box;
import jysk_shared.DataCollection;
import jysk_shared.Pallet;

public class Tower implements ITower {

	private Connection sqlCon;
	private final String sqlUsername = "postgres"; 
	private final String sqlPassword = "123456"; 
	private final String sqlHost = "jdbc:postgresql://localhost:5432/"; 
	private Connection sqlResultConnection;
	private String type; 

	public Tower(String towerName, String typeOfItems) {
		try {
			sqlCon = DriverManager.getConnection(sqlHost+towerName, sqlUsername, sqlPassword);
			sqlResultConnection = DriverManager.getConnection(sqlHost+"systemresults", sqlUsername, sqlPassword);
		} catch (SQLException e) {
			if (!towerName.equals("systemresults"))
				connectToMaint(towerName);
		} 
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
			storeData(pallet.getSendFrom(), "Tower", pallet.getTimeDiff());
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

			int id = -1;
			if(rs.next()) {
				id = rs.getInt(1); 
			}

			if(id == -1) {
				System.err.println("There is no pallet with ID "+id +" in type "+type);
				return null;
			}

			Pallet p = new Pallet(type, id, "Tower"); 

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


	@Override
	public void storeData(String from, String to, long timeTaken) {
		try {
			if (sqlResultConnection == null) {
				System.out.println("connection to testresults is null");
			}
			PreparedStatement ps = sqlResultConnection.prepareStatement("INSERT INTO testresults (time_taken, sendTo, sendFrom) VALUES (?, ?, ?)");
			ps.setLong(1, timeTaken);
			ps.setString(2, to);
			if (from.equals("Tower")) {
				ps.setString(3, "Pick Station");
			} else {
				ps.setString(3, from);
			}
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	@Override
	public ArrayList<DataCollection> retrieveData(String sendFrom) {
		try {
			PreparedStatement ps = sqlResultConnection.prepareStatement("SELECT time_taken, sendTo, sendFrom FROM testresults WHERE sendFrom = ?");
			ps.setString(1, sendFrom);
			ResultSet rs = ps.executeQuery(); 
			ArrayList<DataCollection> arrayOfData = new ArrayList<DataCollection>();
			while (rs.next()) {
				long timeTaken = rs.getLong(1);
				String sendTo = rs.getString(2);
				arrayOfData.add(new DataCollection(sendFrom, sendTo, timeTaken));
			}

			return arrayOfData;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
