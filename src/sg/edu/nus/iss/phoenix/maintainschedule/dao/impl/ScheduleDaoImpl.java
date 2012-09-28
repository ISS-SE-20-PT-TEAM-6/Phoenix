package sg.edu.nus.iss.phoenix.maintainschedule.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import sg.edu.nus.iss.phoenix.core.dao.DBConstants;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.maintainschedule.dao.ScheduleDao;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.Schedule;

/**
 * Schedule Data Access Object (DAO). This class contains all database handling that
 * is needed to permanently store and retrieve Schedule object instances.
 */
public class ScheduleDaoImpl implements ScheduleDao {

	Connection connection;

	public ScheduleDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
		connection = openConnection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#createValueObject()
	 */
	@Override
	public Schedule createValueObject() {
		return new Schedule();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#getObject(java.sql
	 * .Connection, java.lang.String)
	 */
	@Override
	public Schedule getObject(String scheduleIDIn) throws NotFoundException, SQLException {

		Schedule valueObject = createValueObject();
		valueObject.setScheduleID(scheduleIDIn);
		load(valueObject);
		return valueObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#load(java.sql.Connection
	 * , sg.edu.nus.iss.phoenix.authenticate.entity.Role)
	 */
	@Override
	public void load(Schedule valueObject) throws NotFoundException, SQLException {

		if (valueObject.getScheduleID() == null || valueObject.getScheduleID()=="") {
			// System.out.println("Can not select without Primary-Key!");
			throw new NotFoundException("Can not select without Primary-Key!");
		}

		String sql = "SELECT * FROM Schedule WHERE ( scheduleID = ? ) ";
		PreparedStatement stmt = null;
		connection = openConnection();
		try {
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, valueObject.getScheduleID());

			singleQuery(stmt, valueObject);

		} finally {
			if (stmt != null)
				stmt.close();
			closeConnection();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#loadAll(java.sql
	 * .Connection)
	 */
	@Override
	public List<Schedule> loadAll() throws SQLException {
		connection = openConnection();
		String sql = "SELECT * FROM SCHEDULE ORDER BY programDate, startTime ASC ";
		PreparedStatement stmt = null;

		stmt = this.connection.prepareStatement(sql);
		
		List<Schedule> searchResults = listQuery(this.connection
				.prepareStatement(sql));

		return searchResults;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#loadAll(java.sql
	 * .Connection)
	 */
	@Override
	public List<Schedule> loadAnnual(int year) throws SQLException {
		connection = openConnection();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year,0,1);
		Date fromDate = new Date(calendar.getTime().getTime());
		calendar.set(year,11,31);
		Date toDate = new Date(calendar.getTime().getTime());
		String sql = "SELECT * FROM SCHEDULE WHERE programDate between ? and ? ORDER BY programDate, startTime ASC ";
		PreparedStatement stmt = null;

		stmt = this.connection.prepareStatement(sql);
		stmt.setDate(1,fromDate);
		stmt.setDate(2,toDate);
		
		List<Schedule> searchResults = listQuery(stmt);

		return searchResults;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#loadAll(java.sql
	 * .Connection)
	 */
	@Override
	public List<Schedule> loadWeekly(java.util.Date firstDateOfWeek) throws SQLException {
		connection = openConnection();
		String sql = "SELECT * FROM SCHEDULE WHERE programDate between ? and ? ORDER BY programDate, startTime ASC";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstDateOfWeek);
		Date fromDate = new Date(calendar.getTime().getTime());
		calendar.add(Calendar.DATE,6);
		Date toDate = new Date(calendar.getTime().getTime());
		PreparedStatement stmt = null;

		stmt = this.connection.prepareStatement(sql);

		stmt.setDate(1,fromDate);
		stmt.setDate(2,toDate);
		

		List<Schedule> searchResults = listQuery(stmt);

		return searchResults;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#loadAll(java.sql
	 * .Connection)
	 */
	@Override
	public List<Schedule> loadDaily(java.util.Date scheduleDate) throws SQLException {
		connection = openConnection();
		String sql = "SELECT * FROM SCHEDULE WHERE programDate >= ? and programDate< ? ORDER BY programDate, startTime ASC";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(scheduleDate);

		java.sql.Date fromDate = new Date(calendar.getTime().getTime());
		calendar.add(Calendar.DATE,1);
		Date toDate = new Date(calendar.getTime().getTime());
		PreparedStatement stmt = null;

		stmt = this.connection.prepareStatement(sql);

		stmt.setDate(1,fromDate);
		stmt.setDate(2,toDate);
		

		List<Schedule> searchResults = listQuery(stmt);

		return searchResults;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#create(java.sql.
	 * Connection, sg.edu.nus.iss.phoenix.authenticate.entity.Role)
	 */
	@Override
	public synchronized void create(Schedule valueObject) throws SQLException {

		String sql = "";
		PreparedStatement stmt = null;
		connection = openConnection();
		try {
			sql = "INSERT INTO schedule (scheduleID, programName, programDate, presenter, producer, startTime, endTime ) VALUES (?, ?, ?, ?, ?, ?, ?) ";
			stmt = this.connection.prepareStatement(sql);
			
			//check if the schedule id in YYYYMMDDHHMM format
			String scheduleIDIn = convertScheduleID(valueObject);
			valueObject.setScheduleID(scheduleIDIn);
			stmt.setString(1, scheduleIDIn);
			stmt.setString(2, valueObject.getProgramName());
			java.sql.Date sDate = new java.sql.Date(valueObject.getProgramDate().getTime());
			stmt.setDate(3, sDate);
			stmt.setString(4, valueObject.getPresenter());
			stmt.setString(5, valueObject.getProducer());
			stmt.setTime(6, valueObject.getStartTime());
			stmt.setTime(7,valueObject.getEndTime());

			int rowcount = databaseUpdate(stmt);
			if (rowcount != 1) {
				// System.out.println("PrimaryKey Error when updating DB!");
				throw new SQLException("PrimaryKey Error when updating DB!");
			}

		} finally {
			if (stmt != null)
				stmt.close();
			closeConnection();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#save(java.sql.Connection
	 * , sg.edu.nus.iss.phoenix.authenticate.entity.Role)
	 */
	@Override
	public void save(Schedule valueObject) throws NotFoundException, SQLException {

		if (valueObject.getScheduleID() == null || valueObject.getScheduleID()=="") {
			// System.out.println("Can not save without Primary-Key!");
			throw new NotFoundException("Can not save without Primary-Key!");
		}

		String sql = "UPDATE schedule set scheduleID=?, programName=?, programDate=?, presenter=?, producer=?, startTime=?, endTime =? WHERE (scheduleID = ? ) ";
		PreparedStatement stmt = null;
		connection = openConnection();
		try {
			stmt = this.connection.prepareStatement(sql);
			//check if the schedule id in YYYYMMDDHHMM format
			String scheduleIDIn = convertScheduleID(valueObject);
			
			stmt.setString(1, scheduleIDIn);
			stmt.setString(2, valueObject.getProgramName());
			stmt.setDate(3, new Date(valueObject.getProgramDate().getTime()));
			stmt.setString(4, valueObject.getPresenter());
			stmt.setString(5, valueObject.getProducer());
			stmt.setTime(6, valueObject.getStartTime());
			stmt.setTime(7,valueObject.getEndTime());
			stmt.setString(8,valueObject.getScheduleID());

			int rowcount = databaseUpdate(stmt);
			if (rowcount == 0) {
				// System.out.println("Object could not be saved! (PrimaryKey not found)");
				throw new NotFoundException(
						"Object could not be saved! (PrimaryKey not found)");
			}
			if (rowcount > 1) {
				// System.out.println("PrimaryKey Error when updating DB! (Many objects were affected!)");
				throw new SQLException(
						"PrimaryKey Error when updating DB! (Many objects were affected!)");
			}
			if(!valueObject.getScheduleID().equals(scheduleIDIn))
				valueObject.setScheduleID(scheduleIDIn);

		} finally {
			if (stmt != null)
				stmt.close();
			closeConnection();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#delete(java.sql.
	 * Connection, sg.edu.nus.iss.phoenix.authenticate.entity.Role)
	 */
	@Override
	public void delete(Schedule valueObject) throws NotFoundException, SQLException {

		if (valueObject.getScheduleID() == null || valueObject.getScheduleID()=="") {
			// System.out.println("Can not delete without Primary-Key!");
			throw new NotFoundException("Can not delete without Primary-Key!");
		}

		String sql = "DELETE FROM schedule WHERE (scheduleID = ? ) ";
		PreparedStatement stmt = null;
		connection = openConnection();
		try {
			stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, valueObject.getScheduleID());

			int rowcount = databaseUpdate(stmt);
			if (rowcount == 0) {
				// System.out.println("Object could not be deleted (PrimaryKey not found)");
				throw new NotFoundException(
						"Object could not be deleted! (PrimaryKey not found)");
			}
			if (rowcount > 1) {
				// System.out.println("PrimaryKey Error when updating DB! (Many objects were deleted!)");
				throw new SQLException(
						"PrimaryKey Error when updating DB! (Many objects were deleted!)");
			}
		} finally {
			if (stmt != null)
				stmt.close();
			closeConnection();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#deleteAll(java.sql
	 * .Connection)
	 */
	@Override
	public void deleteAll() throws SQLException {

		String sql = "DELETE FROM schedule";
		PreparedStatement stmt = null;
		connection = openConnection();
		try {
			stmt = this.connection.prepareStatement(sql);
			int rowcount = databaseUpdate(stmt);
			System.out.println("deleted rows" + rowcount);
		} finally {
			if (stmt != null)
				stmt.close();
			closeConnection();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#countAll(java.sql
	 * .Connection)
	 */
	@Override
	public int countAll() throws SQLException {

		String sql = "SELECT count(*) FROM schedule";
		PreparedStatement stmt = null;
		ResultSet result = null;
		int allRows = 0;
		connection = openConnection();
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
			closeConnection();
		}
		return allRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDao#searchMatching(java
	 * .sql.Connection, sg.edu.nus.iss.phoenix.authenticate.entity.Role)
	 */
	@Override
	public List<Schedule> searchMatching(Schedule valueObject) throws SQLException {

		List<Schedule> searchResults;
		connection = openConnection();
		boolean first = true;
		StringBuffer sql = new StringBuffer("SELECT * FROM schedule WHERE 1=1 ");

		if (valueObject.getScheduleID() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND scheduleID LIKE '").append(valueObject.getScheduleID())
					.append("%' ");
		}

		if (valueObject.getProgramName() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND programName LIKE '")
					.append(valueObject.getProgramName()).append("%' ");
		}

		if (valueObject.getPresenter() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND presenter LIKE '")
					.append(valueObject.getPresenter()).append("%' ");
		}

		if (valueObject.getProducer() != null) {
			if (first) {
				first = false;
			}
			sql.append("AND producer LIKE '")
					.append(valueObject.getProducer()).append("%' ");
		}
	
		sql.append("ORDER BY scheduleID ASC ");

		// Prevent accidential full table results.
		// Use loadAll if all rows must be returned.
		if (first)
			searchResults = new ArrayList<Schedule>();
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
	protected void singleQuery(PreparedStatement stmt, Schedule valueObject)
			throws NotFoundException, SQLException {

		ResultSet result = null;

		try {
			result = stmt.executeQuery();

			if (result.next()) {
				valueObject.setScheduleID(result.getString("scheduleID"));
				valueObject.setProgramName(result.getString("programName"));
				valueObject.setPresenter(result.getString("presenter"));
				valueObject.setProducer(result.getString("producer"));
				valueObject.setProgramDate(result.getDate("programDate"));
				valueObject.setStartTime(result.getTime("startTime"));
				valueObject.setEndTime(result.getTime("endTime"));
			} else {
				// System.out.println("Role Object Not Found!");
				throw new NotFoundException("Schedule Object Not Found!");
			}
		} finally {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
			closeConnection();
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
	protected List<Schedule> listQuery(PreparedStatement stmt) throws SQLException {

		ArrayList<Schedule> searchResults = new ArrayList<Schedule>();
		ResultSet result = null;
		//connection = openConnection();
		try {
			result = stmt.executeQuery();

			while (result.next()) {
				Schedule temp = createValueObject();

				temp.setScheduleID(result.getString("scheduleID"));
				temp.setProgramName(result.getString("programName"));
				temp.setPresenter(result.getString("presenter"));
				temp.setProducer(result.getString("producer"));
				temp.setProgramDate(result.getDate("programDate"));
				temp.setStartTime(result.getTime("startTime"));
				temp.setEndTime(result.getTime("endTime"));

				searchResults.add(temp);
			}

		} finally {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
			closeConnection();
		}

		return (List<Schedule>) searchResults;
	}

	private Connection openConnection() {
		Connection conn = null;

		try {
			Class.forName(DBConstants.COM_MYSQL_JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(DBConstants.dbUrl,
					DBConstants.dbUserName, DBConstants.dbPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	private void closeConnection() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String convertScheduleID(Schedule valueObject){
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMdd");
		Time dt=valueObject.getStartTime();
		String strTemp=smf.format(valueObject.getProgramDate());
		return String.format("%s%02d%02d", strTemp, dt.getHours(), dt.getMinutes());
	}
	public String testConvertScheduleID(Schedule valueObject){
		return convertScheduleID(valueObject);
	}
	
}

