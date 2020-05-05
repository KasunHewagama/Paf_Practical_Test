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
			preparedStatement.setString(Constants.COLUMN_INDEX_ONE, appointment.getAppointmentId());
			preparedStatement.setString(Constants.COLUMN_INDEX_TWO, appointment.getPatientId());
			System.out.println(appointment.getPatientId());
			preparedStatement.setString(Constants.COLUMN_INDEX_THREE, appointment.getPatientName());
			preparedStatement.setString(Constants.COLUMN_INDEX_FOUR, appointment.getPhone());
			preparedStatement.setString(Constants.COLUMN_INDEX_FIVE, appointment.getDoctorName());
			preparedStatement.setString(Constants.COLUMN_INDEX_SIX, appointment.getSpecialization());
			preparedStatement.setString(Constants.COLUMN_INDEX_SEVEN, appointment.getHospitalId());
			preparedStatement.setString(Constants.COLUMN_INDEX_EIGHT, appointment.getHospitalName());
			preparedStatement.setString(Constants.COLUMN_INDEX_NINE, appointment.getAppointmentDate());
			preparedStatement.setString(Constants.COLUMN_INDEX_TEN, appointment.getAppointmentTime());
			preparedStatement.setString(Constants.COLUMN_INDEX_ELEVEN, LocalDate.now().toString());
			preparedStatement.setString(Constants.COLUMN_INDEX_TWELVE, LocalTime.now().toString());
			preparedStatement.setString(Constants.COLUMN_INDEX_THIRTEEN, "Pending");

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
			
			output ="<table class = \"table table-striped table-responsive\" style=\"width:120%; margin-left: -40px\">" +
					 "<tr style=\"background-color:#000099; color:#ffffff;\"><th>Appointment Id</th>" + "<th>Patient Id</th>" + "<th>Patient_Name</th>" +
					 "<th>Phone</th>" + "<th>Doctor_Name</th>" + "<th>Specialization</th>" + "<th>Hospital_Id</th>" +
					 "<th>Hospital_Name</th>" + "<th>Appointment_Date</th>" + "<th>Appointment_Time</th>" + "<th>Last_Update_Date</th>" + "<th>Last_Update_Time</th>" + "<th>Appointment_Status</th>" + "<th>Update</th>" + "<th>Remove</th></tr>";
			
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

				
				
			}
			
			System.out.println("Data Retrieved from DB...!");
			
		
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

		String output = "";
		ResultSet rs = null;
		
		ArrayList<Appointment> arrayList = new ArrayList<Appointment>();

		try {
			con = DBConnection.getDBConnection();

			String query = "SELECT * FROM appointment";

			st = con.createStatement();
			rs = st.executeQuery(query);
			
			DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
			
	
			output ="<table class = \"table table-striped table-responsive\" style=\"width:120%; margin-left: -40px\">" +
					 "<tr style=\"background-color:#000099; color:#ffffff;\"><th>Appointment Id</th>" + "<th>Patient Id</th>" + "<th>Patient_Name</th>" +
					 "<th>Phone</th>" + "<th>Doctor_Name</th>" + "<th>Specialization</th>" + "<th>Hospital_Id</th>" +
					 "<th>Hospital_Name</th>" + "<th>Appointment_Date</th>" + "<th>Appointment_Time</th>" + "<th>Last_Update_Date</th>" + "<th>Last_Update_Time</th>" + "<th>Appointment_Status</th>" + "<th>Update</th>" + "<th>Remove</th></tr>";

			while (rs.next()) {
				String appointmentId = rs.getString("appointmentId");
				String patientId = rs.getString("patientId");
				String patientName = rs.getString("patientName");
				String phone = rs.getString("phone");
				String doctorName = rs.getString("doctorName");
				String specialization = rs.getString("specialization");
				String hospitalId = rs.getString("hospitalId");
				String hospitalName = rs.getString("hospitalName");
				String appointmentDate = rs.getString("appointmentDate");	
				String appointmentTime = rs.getString("appointmentTime");
				String lastUpdateDate = rs.getString("lastUpdateDate");
				String lastUpdateTime = rs.getString("lastUpdateTime");
				String appointmentStatus = rs.getString("appointmentStatus");
				

				System.out.println("GetAllAppointments : AppointmentId : " + appointmentId);

				output += "<tr><td><input id = \"hidAppointmentIdUpdate\" name = \"hidAppointmentIdUpdate\" type=\"hidden\" value = '" + appointmentId + "'>" + appointmentId + "</td>";
				output += "<td>" + patientId + "</td>";
				output += "<td>" + patientName + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + doctorName + "</td>";
				output += "<td>" + specialization + "</td>";
				output += "<td>" + hospitalId + "</td>";
				output += "<td>" + hospitalName + "</td>";
				output += "<td>" + appointmentDate + "</td>";
				output += "<td>" + appointmentTime + "</td>";
				output += "<td>" + lastUpdateDate + "</td>";
				output += "<td>" + lastUpdateTime + "</td>";
				output += "<td>" + appointmentStatus + "</td>";
				output += "<td><input name = \"btnUpdate\" type = \"button\" value = \"Update\" class = \"btnUpdate btn btn-success btn-sm\"></td>"
						+ "<td><input name = 'btnRemove' type = 'button' value = 'Remove' class = 'btnRemove btn btn-danger btn-sm' data-appointmentId = '"+ appointmentId +"'>" 
						+ "</td></tr>";
								
				
			}
			
			System.out.println("Data Retrieved from database...!");

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
	public String updateAppointment(String appointmentId, String patientId, String patientName, String phone, String doctorName, String specialization, String hospitalId,String hospitalName, String appointmentDate, String appointmentTime) {
		
		String output = "";
		PreparedStatement pStatement = null;
		
		try {
			con = DBConnection.getDBConnection();
			
			String query = "UPDATE appointment SET patientId = ?, patientName = ?, phone = ?, doctorName = ?, specialization = ?, hospitalId = ?, hospitalName = ?, appointmentDate = ?, appointmentTime = ?, lastUpdateDate = ?, lastUpdateTime = ?, appointmentStatus = ? WHERE appointmentId = ?";
			
			pStatement = con.prepareStatement(query);
			
			//pStatement.setString(Constants.COLUMN_INDEX_ONE, appointmnetId);
			pStatement.setString(Constants.COLUMN_INDEX_ONE, patientId);
			pStatement.setString(Constants.COLUMN_INDEX_TWO, patientName);
			pStatement.setString(Constants.COLUMN_INDEX_THREE, phone);
			pStatement.setString(Constants.COLUMN_INDEX_FOUR, doctorName);
			pStatement.setString(Constants.COLUMN_INDEX_FIVE, specialization);
			pStatement.setString(Constants.COLUMN_INDEX_SIX, hospitalId);
			pStatement.setString(Constants.COLUMN_INDEX_SEVEN, hospitalName);
			pStatement.setString(Constants.COLUMN_INDEX_EIGHT, patientId);
			pStatement.setString(Constants.COLUMN_INDEX_NINE, patientId);
			pStatement.setString(Constants.COLUMN_INDEX_TEN, LocalDate.now().toString());
			pStatement.setString(Constants.COLUMN_INDEX_ELEVEN, LocalTime.now().toString());
			pStatement.setString(Constants.COLUMN_INDEX_TWELVE, "Pending");
			pStatement.setString(Constants.COLUMN_INDEX_THIRTEEN, appointmentId);
			
			pStatement.execute();
			
			System.out.println("Update appointmentId : " + appointmentId);
			
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
	public String cancelAppointment(String appointmentId) {
		
		String output = "";
		PreparedStatement pStatement = null;
		
		try {
			con = DBConnection.getDBConnection();
			
			String query = "DELETE FROM appointment WHERE appointmentId = ?";
			
			pStatement = con.prepareStatement(query);
			
			pStatement.setString(Constants.COLUMN_INDEX_ONE, appointmentId);
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
