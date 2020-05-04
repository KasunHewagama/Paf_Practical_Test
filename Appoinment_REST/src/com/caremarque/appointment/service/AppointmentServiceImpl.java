package com.caremarque.appointment.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



//import org.apache.tomcat.util.security.Escape;

import com.caremarque.appointment.model.Appointment;
import com.caremarque.appointment.utils.CommonUtils;
import com.caremarque.appointment.utils.DBConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.caremarque.appointment.utils.Constants;
import com.mysql.cj.log.Log;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AppointmentServiceImpl implements IAppointmentService {

	//this object is for logging
	public static final Logger log = Logger.getLogger(IAppointmentService.class.getName());

	public static Connection con;
	
	public static Statement st;
	
	@Override
	public String createAppointment(Appointment appointment) {
		// return "Appointment created successfully...!";

		String output = null;
		PreparedStatement preparedStatement = null;
		
		//Here we call the generateAppointmentIDs method to auto generate a appointmentID
		//To do that we pass the existing appointmentid set as an arraylist
		String appointmentId = CommonUtils.generateAppointmentIDs(getAppointmentIDs());
		System.out.println("AppointmentID: " +  appointmentId);


		try {
			con = DBConnection.getDBConnection();

			String query = "INSERT INTO appointment(" 
					+ "appointmentId,patientId,patientName,phone,doctorName,"
					+ "specialization,hospitalId,hospitalName,appointmentDate,appointmentTime,"
					+ "lastUpdateDate,lastUpdateTime,appointmentStatus)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			preparedStatement = con.prepareStatement(query);

			appointment.setAppointmentId(appointmentId);
			preparedStatement.setString(1, appointment.getAppointmentId());
			preparedStatement.setString(2, appointment.getPatientId());
			preparedStatement.setString(3, appointment.getPatientName());
			preparedStatement.setString(4, appointment.getPhone());
			preparedStatement.setString(5, appointment.getDoctorName());
			preparedStatement.setString(6, appointment.getSpecialization());
			preparedStatement.setString(7, appointment.getHospitalId());
			preparedStatement.setString(8, appointment.getHospitalName());
			preparedStatement.setString(9, appointment.getAppointmentDate());
			preparedStatement.setString(10, appointment.getAppointmentTime());
			preparedStatement.setString(11, LocalDate.now().toString());
			preparedStatement.setString(12, LocalTime.now().toString());
			preparedStatement.setString(13, appointment.getAppointmentStatus());

			preparedStatement.executeUpdate();
			
			String newAppointment = getAppointments();

			//output = "Appointment No:" + appointmentId + " is Successfully Inserted...!";
			output = "{\"status\":\"success\", \"data\": \"" + newAppointment + "\"}";

		} catch (Exception e) {

			//output = "Error when Inserting the Appointment...!";
			output = "{\"status\" : \"error\", \"data\" : \"Error while entering to the system..!\"}";
			System.err.println(e.getMessage());
			log.log(Level.SEVERE, e.getMessage());

		} finally {

			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
			}
		}

		return output;

	}

	@Override
	public String getAppointment(String appointmentId) {
		
		String output = null;
		ArrayList<Appointment> arrayList = new ArrayList<Appointment>();
		
		try {
			con = DBConnection.getDBConnection();
			
			String query = "SELECT * FROM appointment "
					+ "WHERE appointmentId = '"+ appointmentId + "'";
			
			PreparedStatement pStatement = con.prepareStatement(query);
			ResultSet rs = pStatement.executeQuery();
			
			output = "<table class=\' table table-sm table-responsive\' style=\"font-family: 'Roboto', sans-serif\" > "
					+ "<tr>" + "<th scope=\"col\">Appointment Id</th> " + "<th scope=\"col\">Patient Id</th> "
					+ "<th scope=\"col\">Patient Name</th> " + "<th scope=\"col\">Phone</th> "
					+ "<th scope=\"col\">Doctor Name</th> " + "<th scope=\"col\">Specialization</th> "
					+ "<th scope=\"col\">Hospital Id</th> " + "<th scope=\"col\">Hospital Name</th> "
					+ "<th scope=\"col\">Appointment Date</th> " + "<th scope=\"col\">Appointment Time</th> "
					+ "<th scope=\"col\">Last Update Date</th> " + "<th scope=\"col\">Last Update Time No</th> "
					+ "<th scope=\"col\">Update</th>" + "<th scope=\"col\">Delete</th>" + "</tr>";
			
			while(rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setPatientId(rs.getString(Constants.COLUMN_INDEX_ONE));
				appointment.setPatientName(rs.getString(Constants.COLUMN_INDEX_TWO));
				appointment.setPhone(rs.getString(Constants.COLUMN_INDEX_THREE));
				appointment.setDoctorName(rs.getString(Constants.COLUMN_INDEX_FOUR));
				appointment.setSpecialization(rs.getString(Constants.COLUMN_INDEX_FIVE));
				appointment.setHospitalId(rs.getString(Constants.COLUMN_INDEX_SIX));
				appointment.setHospitalName(rs.getString(Constants.COLUMN_INDEX_SEVEN));
				appointment.setAppointmentDate(rs.getString(Constants.COLUMN_INDEX_EIGHT));
				appointment.setAppointmentTime(rs.getString(Constants.COLUMN_INDEX_NINE));
				appointment.setLastUpdateDate(rs.getString(Constants.COLUMN_INDEX_TEN));
				appointment.setLastUpdateTime(rs.getString(Constants.COLUMN_INDEX_ELEVEN));
				appointment.setAppointmentStatus(rs.getString(Constants.COLUMN_INDEX_TWELVE));
				arrayList.add(appointment);
				
				output += "<tr><td style=\"color:#008AD9;font-weight: bold;\">"
						+ "<input id='hidAppointmentIdUpdate' name = 'hidAppointmentIdUpdate' type='hidden' value='" +rs.getString(Constants.COLUMN_INDEX_ONE)+"'>"
						+ rs.getString(Constants.COLUMN_INDEX_ONE) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_TWO) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_THREE) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_FOUR) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_FIVE) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_SIX) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_SEVEN) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_EIGHT) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_NINE) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_TEN) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_ELEVEN) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_TWELVE) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_THIRTEEN) + "</td></tr>";
				
				output += "<td>"
						+ "<input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' style= \"font-size: 14px;\">"
						+ "</td>"
						+ "<td>"
						+ "<input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' style= \"font-size: 14px;\" data-appointmentid='"
						+ rs.getString(Constants.COLUMN_INDEX_ONE) + "'>" 
						+ "</td>"
						+ "</tr>";
				
				System.out.println("Data Retrieved from DB...!");
			}
			
			output += "</table>";
		
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		
		} finally {
			try {
				if(st != null) {
					st.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
		}
		
		return output;
	}
	
	//TO RETURN A APPOINTMENT LIST

	public ArrayList<Appointment> getAppointmentByID(String appointmentId) {
		
		String output = null;
		ArrayList<Appointment> arrayList = new ArrayList<Appointment>();
		
		try {
			con = DBConnection.getDBConnection();
			
			String query = "SELECT * FROM appointment "
					+ "WHERE appointmentId = '"+ appointmentId + "'";
			
			PreparedStatement pStatement = con.prepareStatement(query);
			ResultSet rs = pStatement.executeQuery();
			
			
			while(rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setAppointmentId(appointmentId);
				appointment.setPatientId(rs.getString(Constants.COLUMN_INDEX_ONE));
				appointment.setPatientName(rs.getString(Constants.COLUMN_INDEX_TWO));
				appointment.setPhone(rs.getString(Constants.COLUMN_INDEX_THREE));
				appointment.setDoctorName(rs.getString(Constants.COLUMN_INDEX_FOUR));
				appointment.setSpecialization(rs.getString(Constants.COLUMN_INDEX_FIVE));
				appointment.setHospitalId(rs.getString(Constants.COLUMN_INDEX_SIX));
				appointment.setHospitalName(rs.getString(Constants.COLUMN_INDEX_SEVEN));
				appointment.setAppointmentDate(rs.getString(Constants.COLUMN_INDEX_EIGHT));
				appointment.setAppointmentTime(rs.getString(Constants.COLUMN_INDEX_NINE));
				appointment.setLastUpdateDate(rs.getString(Constants.COLUMN_INDEX_TEN));
				appointment.setLastUpdateTime(rs.getString(Constants.COLUMN_INDEX_ELEVEN));
				appointment.setAppointmentStatus(rs.getString(Constants.COLUMN_INDEX_TWELVE));
				arrayList.add(appointment);

				
				System.out.println("Data Retrieved from DB...!");
			}
			
			
		
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		
		} finally {
			try {
				if(st != null) {
					st.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
		}
		
		return arrayList;
	}

	@Override
	public String getAppointments() {

		String output = null;
		ResultSet rs = null;
		
		ArrayList<Appointment> arrayList = new ArrayList<Appointment>();

		try {
			con = DBConnection.getDBConnection();

			String query = "SELECT * FROM appointment";

			st = con.createStatement();
			rs = st.executeQuery(query);
			
			DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
			
	
			output = "<table class=\' table table-sm table-responsive\' style=\"font-family: 'Roboto', sans-serif\" > "
					+ "<tr>" + "<th scope=\"col\">Appointment Id</th> " + "<th scope=\"col\">Patient Id</th> "
					+ "<th scope=\"col\">Patient Name</th> " + "<th scope=\"col\">Phone</th> "
					+ "<th scope=\"col\">Doctor Name</th> " + "<th scope=\"col\">Specialization</th> "
					+ "<th scope=\"col\">Hospital Id</th> " + "<th scope=\"col\">Hospital Name</th> "
					+ "<th scope=\"col\">Appointment Date</th> " + "<th scope=\"col\">Appointment Time</th> "
					+ "<th scope=\"col\">Last Update Date</th> " + "<th scope=\"col\">Last Update Time No</th> "
					+ "<th scope=\"col\">Update</th>" + "<th scope=\"col\">Delete</th>" + "</tr>";

			while (rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setPatientId(rs.getString(Constants.COLUMN_INDEX_ONE));
				appointment.setPatientName(rs.getString(Constants.COLUMN_INDEX_TWO));
				appointment.setPhone(rs.getString(Constants.COLUMN_INDEX_THREE));
				appointment.setDoctorName(rs.getString(Constants.COLUMN_INDEX_FOUR));
				appointment.setSpecialization(rs.getString(Constants.COLUMN_INDEX_FIVE));
				appointment.setHospitalId(rs.getString(Constants.COLUMN_INDEX_SIX));
				appointment.setHospitalName(rs.getString(Constants.COLUMN_INDEX_SEVEN));
				appointment.setAppointmentDate(rs.getString(Constants.COLUMN_INDEX_EIGHT));
				appointment.setAppointmentTime(rs.getString(Constants.COLUMN_INDEX_NINE));
				appointment.setLastUpdateDate(rs.getString(Constants.COLUMN_INDEX_TEN));
				appointment.setLastUpdateTime(rs.getString(Constants.COLUMN_INDEX_ELEVEN));
				appointment.setAppointmentStatus(rs.getString(Constants.COLUMN_INDEX_TWELVE));
				arrayList.add(appointment);
				

				output += "<tr><td style=\"color:#008AD9;font-weight: bold;\">"
						+ "<input id='hidAppointmentIdUpdate' name = 'hidAppointmentIdUpdate' type='hidden' value='" +rs.getString(Constants.COLUMN_INDEX_ONE)+"'>"
						+ rs.getString(Constants.COLUMN_INDEX_ONE) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_TWO) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_THREE) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_FOUR) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_FIVE) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_SIX) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_SEVEN) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_EIGHT) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_NINE) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_TEN) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_ELEVEN) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_TWELVE) + "</td>";
				output += "<td>" + rs.getString(Constants.COLUMN_INDEX_THIRTEEN) + "</td></tr>";
				
				output += "<td>"
						+ "<input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' style= \"font-size: 14px;\">"
						+ "</td>"
						+ "<td>"
						+ "<input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' style= \"font-size: 14px;\" data-appointmentid='"
						+ rs.getString(Constants.COLUMN_INDEX_ONE) + "'>" 
						+ "</td>"
						+ "</tr>";
				
				System.out.println("Data Retrieved from database...!");
			}

			output += "</table>";

		} catch (Exception e) {

			output = "Error while reading appointment details...!";
			System.err.println(e.getMessage());
			log.log(Level.SEVERE, e.getMessage());

		} finally {

			try {
				if (st != null) {
					st.close();
				}

				if (con != null) {
					con.close();
				}

				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
			}

		}
		return output;
	}

	@Override
	public String updateAppointment(String appointmnetId, String patientId, String patientName, String phone, String doctorName, String specialization, String hospitalId,String hospitalName, String appointmentDate, String appointmentTime) {
		
		String output = "";
		PreparedStatement pStatement = null;
		
		try {
			con = DBConnection.getDBConnection();
			
			String query = "UPDATE appointment SET appointmentId = ?, patientId = ?, patientName = ?, phone = ?, doctorName = ?, specialization = ?, hospitalId = ?, hospitalName = ?, appointmentDate = ?, appointmentTime = ?, lastUpdateDate = ?, lastUpdateTime = ?, appointmentStatus = ? WHERE appointmentId = ?";
			
			pStatement = con.prepareStatement(query);
			
			pStatement.setString(Constants.COLUMN_INDEX_ONE, appointmnetId);
			pStatement.setString(Constants.COLUMN_INDEX_TWO, patientId);
			pStatement.setString(Constants.COLUMN_INDEX_THREE, patientName);
			pStatement.setString(Constants.COLUMN_INDEX_FOUR, phone);
			pStatement.setString(Constants.COLUMN_INDEX_FIVE, doctorName);
			pStatement.setString(Constants.COLUMN_INDEX_SIX, specialization);
			pStatement.setString(Constants.COLUMN_INDEX_SEVEN, hospitalId);
			pStatement.setString(Constants.COLUMN_INDEX_EIGHT, hospitalName);
			pStatement.setString(Constants.COLUMN_INDEX_NINE, patientId);
			pStatement.setString(Constants.COLUMN_INDEX_TEN, patientId);
			pStatement.setString(Constants.COLUMN_INDEX_ELEVEN, LocalDate.now().toString());
			pStatement.setString(Constants.COLUMN_INDEX_TWELVE, LocalTime.now().toString());
			pStatement.setString(Constants.COLUMN_INDEX_THIRTEEN, "Pending");
			pStatement.setString(Constants.COLUMN_INDEX_FOURTEEN, appointmnetId);
			
			pStatement.execute();
			
			System.out.println("Update patintId : " + appointmnetId);
			
			String newAppointment = getAppointments();
			output = "{\"status\":\"success\", \"data\": \"" + newAppointment + "\"}"; 
			

		} catch (Exception e) {
			
			output = "{\"status\":\"error\", \"data\":\"Error while updating the Appointment details..!\"}"; 
			System.err.println(e.getMessage());
			log.log(Level.SEVERE, e.getMessage());
					
		} finally {

			try {
				if (pStatement != null) {
					pStatement.close();
				}

				if (con != null) {
					con.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.log(Level.SEVERE, e.getMessage());
			}
		}
		
		return output;
	}

	@Override
	public String cancelAppointment(String appointmnetId) {
		
		String output = "";
		PreparedStatement pStatement = null;
		
		try {
			con = DBConnection.getDBConnection();
			
			String query = "DELETE FROM appointment WHERE appointmentId = ?";
			
			pStatement = con.prepareStatement(query);
			
			pStatement.setString(Constants.COLUMN_INDEX_ONE, appointmnetId);
			pStatement.execute();
			
			String newAppointment = getAppointments(); 
			output = "{\"status\":\"success\", \"data\": \"" + newAppointment + "\"}"; 

		} catch (Exception e) {

			output = "{\"status\":\"error\", \"data\":\"Error while deleting the Appointment details..!\"}"; 
			System.err.println(e.getMessage());
			log.log(Level.SEVERE, e.getMessage());
		
		} finally {

			try {
				if (pStatement != null) {
					pStatement.close();
				}

				if (con != null) {
					con.close();
				}

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
			}
		}

		return output;
	}

	//This method get all the existing appointmentids and put them into a arraylist
	@Override
	public ArrayList<String> getAppointmentIDs() {
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		ArrayList<String> arrayList = new ArrayList<String>();
		
		try {
			con = DBConnection.getDBConnection();
			
			String query = "SELECT appointment.appointmentId FROM appointment";
			
			preparedStatement = con.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				
				arrayList.add(rs.getString(1));
				
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
			log.log(Level.SEVERE, e.getMessage());
			}

		}
		System.out.println(arrayList.size());
		return arrayList;
	}
	
	
	public String createPayment(String appointmentId) {
		
		Client client = Client.create();
		ArrayList<Appointment> appList = new ArrayList<Appointment>();
		appList = getAppointmentByID(appointmentId);
		
		WebResource webResource = client.resource("http://localhost:8088/Payment_REST/myService/Payment/fromAppointment");
		ObjectMapper mapper = new ObjectMapper();
		String jsonInput ="";
		try {
			jsonInput = mapper.writeValueAsString(appList.get(0));
			System.out.println("JSON: " + jsonInput);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
		
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, jsonInput);
		return response.getEntity(String.class);
	}
	
	
	

}
