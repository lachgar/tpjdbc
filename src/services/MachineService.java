package services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import dao.IDao;
import entities.Machine;
import entities.Salle;

public class MachineService implements IDao<Machine> {

	private SalleService ss;

	public MachineService() {
		ss = new SalleService();
	}

	@Override
	public boolean create(Machine o) {

		try {
			String req = "insert into machine values (null, ?, ? , ? , ?, ?)";
			PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
			ps.setString(1, o.getReference());
			ps.setString(2, o.getMarque());
			ps.setDouble(3, o.getPrix());
			ps.setDate(4, new Date(o.getDateAchat().getTime()));
			ps.setInt(5, o.getSalle().getId());
			if (ps.executeUpdate() == 1)
				return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean delete(Machine o) {
		try {
			String req = "delete from machine where id  = ?";
			PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
			ps.setInt(1, o.getId());
			if (ps.executeUpdate() == 1)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Machine o) {
		try {
			String req = "update machine set reference = ? , marque = ?, prix = ?, dateAchat = ?, salle = ? where id = ?";
			PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
			ps.setString(1, o.getReference());
			ps.setString(2, o.getMarque());
			ps.setDouble(3, o.getPrix());
			ps.setDate(4, new Date(o.getDateAchat().getTime()));
			ps.setInt(5, o.getSalle().getId());
			ps.setInt(6, o.getId());
			if (ps.executeUpdate() == 1)
				return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Machine findById(int id) {
		
		return null;
	}

	@Override
	public List<Machine> findAll() {
		List<Machine> machines = new ArrayList<Machine>();
		try {
			String sql = "select * from machine";
			Statement st = Connexion.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next())
				machines.add(new Machine(rs.getInt("id"), rs.getString("reference"), rs.getString("marque"),
						rs.getDouble("prix"), rs.getDate("dateAchat"), ss.findById(rs.getInt("salle"))));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return machines;
	}
	
	
	public List<Machine> findMachinesBySalle(Salle s) {
		List<Machine> machines = new ArrayList<Machine>();
		try {
			String sql = "select * from machine where salle = "+ s.getId();
			Statement st = Connexion.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next())
				machines.add(new Machine(rs.getInt("id"), rs.getString("reference"), rs.getString("marque"),
						rs.getDouble("prix"), rs.getDate("dateAchat"), ss.findById(rs.getInt("salle"))));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return machines;		
	}
	
	
	public List<Machine> findBetweenDate(java.util.Date d1, java.util.Date d2) {
		List<Machine> machines = new ArrayList<Machine>();
		try {
			String sql = "select * from machine where dateAchat between ? and ?";
			PreparedStatement st = Connexion.getConnection().prepareStatement(sql);
			st.setDate(1, new Date(d1.getTime()));
			st.setDate(2, new Date(d2.getTime()));
			ResultSet rs = st.executeQuery();
			while (rs.next())
				machines.add(new Machine(rs.getInt("id"), rs.getString("reference"), rs.getString("marque"),
						rs.getDouble("prix"), rs.getDate("dateAchat"), ss.findById(rs.getInt("salle"))));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return machines;
	}

}
