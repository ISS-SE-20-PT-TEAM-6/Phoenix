package sg.edu.nus.iss.phoenix.user.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.user.dao.UserDao;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * User Data Access Object (DAO). This class contains all database handling that
 * is needed to permanently store and retrieve User object instances.
 */
public class UserDaoImpl implements UserDao {
	Connection connection;

	public UserDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
		connection = openConnection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDao#createValueObject()
	 */
	@Override
	public User createValueObject() {
		return new User();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDao#getObject(java.sql
	 * .Connection, int)
	 */
	@Override
	public User getObject(String id) throws NotFoundException, SQLException {

		User valueObject = createValueObject();
		valueObject.setId(id);
		load(valueObject);
		return valueObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDao#load(java.sql.Connection
	 * , sg.edu.nus.iss.phoenix.authenticate.entity.User)
	 */
	@Override
	public void load(User valueObject) throws NotFoundException, SQLException {

		String sql = "SELECT a.*, b.role as userRole, c.accessPrivilege  FROM user as a left join `user-roles` as b on a.id=b.userId left join role c on coalesce(b.role, a.role) = c.role WHERE (id = ? ) order by b.role";
		PreparedStatement stmt = null;

		try {
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, valueObject.getId());

			singleQuery(stmt, valueObject);

		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDao#loadAll(java.sql
	 * .Connection)
	 */
	@Override
	public List<User> loadAll() throws SQLException {

		String sql = "SELECT a.*, b.role as userRole, c.accessPrivilege  FROM user as a left join `user-roles` as b on a.id=b.userId left join role c on coalesce(b.role, a.role) = c.role order by id asc";
		List<User> searchResults = listQuery(this.connection
				.prepareStatement(sql));

		return searchResults;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDao#create(java.sql.
	 * Connection, sg.edu.nus.iss.phoenix.authenticate.entity.User)
	 */
	@Override
	public synchronized void create(User valueObject) throws SQLException {
		
		String sql = "";
		PreparedStatement stmt = null;
		String sqlRoles = "";
		PreparedStatement stmtRoles = null;

		Connection conn = null;
		try {
			conn = openConnection();
			conn.setAutoCommit(false);  

			sql = "INSERT INTO user ( id, password, name, role, address1, address2, address3, city, state, homephone, mobile, email, country ) "
					+ "VALUES (?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, valueObject.getId());
			stmt.setString(2, valueObject.getPassword());
			stmt.setString(3, valueObject.getName());
			stmt.setString(4, valueObject.getRoles().get(0).getRole());
			stmt.setString(5, valueObject.getAddress1());
			stmt.setString(6, valueObject.getAddress2());
			stmt.setString(7, valueObject.getAddress3());
			stmt.setString(8, valueObject.getCity());
			stmt.setString(9, valueObject.getState());
			stmt.setString(10, valueObject.getHomePhone());
			stmt.setString(11, valueObject.getMobile());
			stmt.setString(12, valueObject.getEmailAddress());
			stmt.setString(13, valueObject.getCountry());

			int rowcount = stmt.executeUpdate();
			if (rowcount != 1) {				
				// System.out.println("PrimaryKey Error when updating DB!");
				throw new SQLException("PrimaryKey Error when updating DB!");
			}
			
			sqlRoles = "INSERT INTO `user-roles` (userID, role) VALUES (?, ?)";
			
			stmtRoles = conn.prepareStatement(sqlRoles);
			for(Role role : valueObject.getRoles())
			{
				stmtRoles.setString(1, valueObject.getId());
				stmtRoles.setString(2, role.getRole());
				if(stmtRoles.executeUpdate() !=1) {
					// System.out.println("PrimaryKey Error when updating DB!");
					throw new SQLException("PrimaryKey Error when updating DB!");
				}
			}
			conn.commit();

		}
		catch(SQLException ex){
			conn.rollback();
			throw ex;
		}
		finally {
			if (stmt != null)
				stmt.close();
			if(stmtRoles !=null)
				stmtRoles.close();
			if(conn !=null)
				conn.close();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDao#save(java.sql.Connection
	 * , sg.edu.nus.iss.phoenix.authenticate.entity.User)
	 */
	@Override
	public void save(User valueObject) throws NotFoundException, SQLException {
	
		String sqlRoles = "";
		PreparedStatement stmtRoles = null;
		String sql = "UPDATE user SET password = ?, name = ?, role = ?, address1=?, address2=?, address3=?, city = ?, state=?, homephone=?, mobile=?, email=?, country=? WHERE (id = ? ) ";
		PreparedStatement stmt = null;
		String sqlDelRoles =" delete from `user-roles` where userId = ? ";
		PreparedStatement stmtDelRoles=null;
		Connection conn = null;
		try {
			conn = openConnection();
			conn.setAutoCommit(false);  
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(13, valueObject.getId());
			stmt.setString(1, valueObject.getPassword());
			stmt.setString(2, valueObject.getName());
			stmt.setString(3, valueObject.getRoles().get(0).getRole());
			stmt.setString(4, valueObject.getAddress1());
			stmt.setString(5, valueObject.getAddress2());
			stmt.setString(6, valueObject.getAddress3());
			stmt.setString(7, valueObject.getCity());
			stmt.setString(8, valueObject.getState());
			stmt.setString(9, valueObject.getHomePhone());
			stmt.setString(10, valueObject.getMobile());
			stmt.setString(11, valueObject.getEmailAddress());
			stmt.setString(12, valueObject.getCountry());

			int rowcount = stmt.executeUpdate();
			if (rowcount == 0) {
				conn.rollback();
				// System.out.println("Object could not be saved! (PrimaryKey not found)");
				throw new NotFoundException(
						"Object could not be saved! (PrimaryKey not found)");
			}
			if (rowcount > 1) {
				// System.out.println("PrimaryKey Error when updating DB! (Many objects were affected!)");
				throw new SQLException(
						"PrimaryKey Error when updating DB! (Many objects were affected!)");
			}

			//delete role then insert new role
			stmtDelRoles = conn.prepareStatement(sqlDelRoles);
			stmtDelRoles.setString(1, valueObject.getId());
			stmtDelRoles.executeUpdate();
			
			sqlRoles = "INSERT INTO `user-roles` (userID, role) VALUES (?, ?)";
			
			stmtRoles = conn.prepareStatement(sqlRoles);
			for(Role role : valueObject.getRoles())
			{
				stmtRoles.setString(1, valueObject.getId());
				stmtRoles.setString(2, role.getRole());
				if(stmtRoles.executeUpdate() !=1) {
					// System.out.println("PrimaryKey Error when updating DB!");
					throw new SQLException("PrimaryKey Error when updating DB!");
				}
			}
			conn.commit();			
		} 
		catch(SQLException ex){
			conn.rollback();
			throw ex;
		}
		finally {
			if (stmt != null)
				stmt.close();
			if(stmtDelRoles !=null)
				stmtDelRoles.close();
			if(stmtRoles !=null)
				stmtRoles.close();
			if(conn !=null)
				conn.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDao#delete(java.sql.
	 * Connection, sg.edu.nus.iss.phoenix.authenticate.entity.User)
	 */
	@Override
	public void delete(User valueObject) throws NotFoundException, SQLException {

		String sql = "DELETE FROM user WHERE (id = ? ) ";
		PreparedStatement stmt = null;
		String sqlDelRoles =" delete from `user-roles` where userId = ? ";
		PreparedStatement stmtDelRoles=null;
		Connection conn = null;

		try {
			conn = openConnection();
			conn.setAutoCommit(false);  

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, valueObject.getId());

			int rowcount = stmt.executeUpdate();
			if (rowcount == 0) {
				conn.rollback();
				// System.out.println("Object could not be deleted (PrimaryKey not found)");
				throw new NotFoundException(
						"Object could not be deleted! (PrimaryKey not found)");
			}
			if (rowcount > 1) {
				// System.out.println("PrimaryKey Error when updating DB! (Many objects were deleted!)");
				throw new SQLException(
						"PrimaryKey Error when updating DB! (Many objects were deleted!)");
			}
			//delete role
			stmtDelRoles = conn.prepareStatement(sqlDelRoles);
			stmtDelRoles.setString(1, valueObject.getId());
			stmtDelRoles.executeUpdate();
			conn.commit();
		} 
		catch(SQLException ex){
			conn.rollback();
			throw ex;
		}
		finally {
			if (stmt != null)
				stmt.close();
			if(stmtDelRoles != null)
				stmtDelRoles.close();
			if(conn != null)
				conn.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDao#deleteAll(java.sql
	 * .Connection)
	 */
	@Override
	public void deleteAll() throws SQLException {

		String sql = "DELETE FROM user";
		PreparedStatement stmt = null;
		String sqlDelRoles =" delete from `user-roles` ";
		PreparedStatement stmtDelRoles=null;
		Connection conn = null;
		try {
			conn = openConnection();
			conn.setAutoCommit(false); 			
			stmt = conn.prepareStatement(sql);
			int rowcount = databaseUpdate(stmt);
			System.out.println("Deleted rows :" + rowcount);
			//delete role
			stmtDelRoles = conn.prepareStatement(sqlDelRoles);
			stmtDelRoles.executeUpdate();
			conn.commit();
		} 
		catch(SQLException ex){
			conn.rollback();
			throw ex;
		}
		finally {
			if (stmt != null)
				stmt.close();
			if(stmtDelRoles != null)
				stmtDelRoles.close();
			if(conn!=null)
				conn.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDao#countAll(java.sql
	 * .Connection)
	 */
	@Override
	public int countAll() throws SQLException {

		String sql = "SELECT count(*) FROM user";
		PreparedStatement stmt = null;
		ResultSet result = null;
		int allRows = 0;

		try {
			stmt = this.connection.prepareStatement(sql);
			result = stmt.executeQuery();

			if (result.next())
				allRows = result.getInt(1);
		} finally {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
		}
		return allRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDao#searchMatching(java
	 * .sql.Connection, sg.edu.nus.iss.phoenix.authenticate.entity.User)
	 */
	@Override
	public List<User> searchMatching(User valueObject) throws SQLException {

		List<User> searchResults;

		boolean first = true;
		StringBuffer sql = new StringBuffer("SELECT a.*, b.role as userRole, c.accessPrivilege  FROM user as a left join `user-roles` as b on a.id=b.userId left join role c on coalesce(b.role, a.role) = c.role WHERE 1=1 ");

		if (valueObject.getId() != "") {
			if (first) {
				first = false;
			}
			sql.append("AND a.id = ").append(valueObject.getId()).append(" ");
		}

		if (valueObject.getPassword() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND a.password LIKE '").append(valueObject.getPassword())
					.append("%' ");
		}

		if (valueObject.getName() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND a.name LIKE '").append(valueObject.getName())
					.append("%' ");
		}

		if (valueObject.getRoles().get(0).getRole() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND coalesce(b.role, a.role) LIKE '")
					.append(valueObject.getRoles().get(0).getRole())
					.append("%' ");
		}

		sql.append("ORDER BY a.id ASC ");

		// Prevent accidential full table results.
		// Use loadAll if all rows must be returned.
		if (first)
			searchResults = new ArrayList<User>();
		else
			searchResults = listQuery(this.connection.prepareStatement(sql
					.toString()));

		return searchResults;
	}

	/**
	 * databaseUpdate-method. This method is a helper method for internal use.
	 * It will execute all database handling that will change the information in
	 * tables. SELECT queries will not be executed here however. The return
	 * value indicates how many rows were affected. This method will also make
	 * sure that if cache is used, it will reset when data changes.
	 * 
	 * @param conn
	 *            This method requires working database connection.
	 * @param stmt
	 *            This parameter contains the SQL statement to be excuted.
	 */
	protected int databaseUpdate(PreparedStatement stmt) throws SQLException {

		int result = stmt.executeUpdate();

		return result;
	}

	/**
	 * databaseQuery-method. This method is a helper method for internal use. It
	 * will execute all database queries that will return only one row. The
	 * resultset will be converted to valueObject. If no rows were found,
	 * NotFoundException will be thrown.
	 * 
	 * @param conn
	 *            This method requires working database connection.
	 * @param stmt
	 *            This parameter contains the SQL statement to be excuted.
	 * @param valueObject
	 *            Class-instance where resulting data will be stored.
	 */
	protected void singleQuery(PreparedStatement stmt, User valueObject)
			throws NotFoundException, SQLException {

		ResultSet result = null;
		ArrayList<Role> roles = new ArrayList<Role>();
		try {
			result = stmt.executeQuery();
			int rows = 0;
			while (result.next()) {
				rows++;
				if(rows==1){
				valueObject.setId(result.getString("id"));
				valueObject.setPassword(result.getString("password"));
				valueObject.setName(result.getString("name"));
				valueObject.setAddress1(result.getString("address1"));
				valueObject.setAddress2(result.getString("address2"));
				valueObject.setAddress3(result.getString("address3"));
				valueObject.setCity(result.getString("city"));
				valueObject.setCountry(result.getString("country"));
				valueObject.setState(result.getString("state"));
				valueObject.setHomePhone(result.getString("homephone"));
				valueObject.setMobile(result.getString("mobile"));
				valueObject.setEmailAddress(result.getString("email"));
				}
				String userRole = result.getString("userRole");
				if(userRole==null)
					userRole = result.getString("role");
				Role e = new Role();
				e.setAll(userRole,result.getString("accessPrivilege"));

				roles.add(e);			

			}
			if(rows>0){
				valueObject.setRoles(roles);
			}
			else {
				// System.out.println("User Object Not Found!");
				throw new NotFoundException("User Object Not Found!");
			}
		} finally {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * databaseQuery-method. This method is a helper method for internal use. It
	 * will execute all database queries that will return multiple rows. The
	 * resultset will be converted to the List of valueObjects. If no rows were
	 * found, an empty List will be returned.
	 * 
	 * @param conn
	 *            This method requires working database connection.
	 * @param stmt
	 *            This parameter contains the SQL statement to be excuted.
	 */
	protected List<User> listQuery(PreparedStatement stmt) throws SQLException {

		ArrayList<User> searchResults = new ArrayList<User>();
		ResultSet result = null;

		try {
			result = stmt.executeQuery();
			
			String currentUserID="";
			User temp=null;
			ArrayList<Role> roles=null;
			while (result.next()) {
				if(!currentUserID.equals(result.getString("id"))) {
					if(!currentUserID.equals(""))
					{
						//save previous and start new one.
						temp.setRoles(roles);
						searchResults.add(temp);
					}
					currentUserID = result.getString("id");
					
					temp = createValueObject();
					roles = new ArrayList<Role>();

					temp.setId(result.getString("id"));
					temp.setPassword(result.getString("password"));
					temp.setName(result.getString("name"));
					temp.setAddress1(result.getString("address1"));
					temp.setAddress2(result.getString("address2"));
					temp.setAddress3(result.getString("address3"));
					temp.setCity(result.getString("city"));
					temp.setState(result.getString("state"));
					temp.setCountry(result.getString("country"));
					temp.setHomePhone(result.getString("homephone"));
					temp.setMobile(result.getString("mobile"));
					temp.setEmailAddress(result.getString("email"));
				}
				String strRole= result.getString("userRole");
				if(strRole==null)
					strRole = result.getString("role");
						
				Role e = new Role();
				e.setAll(strRole,  result.getString("accessPrivilege"));

				roles.add(e);
			}
			//Last user
			if(!currentUserID.equals(""))
			{
				temp.setRoles(roles);
				searchResults.add(temp);
			}

		} finally {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
		}

		return (List<User>) searchResults;
	}

	private Connection openConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/phoenix", "phoenix",
					"password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
