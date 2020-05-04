package com.caremarque.appointment.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caremarque.appointment.model.Appointment;
import com.caremarque.appointment.service.AppointmentServiceImpl;

@WebServlet("/AppointmentAPI")
public class AppointmentAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AppointmentAPI() {

	}

	private static Map getParasMap(HttpServletRequest request) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		try {
		Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
		String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
		
		scanner.close();
		
		String[] params = queryString.split("&");
		for(String param : params) {
			String[] a = param.split("=");
			map.put(a[0], a[1]);
		}
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
		
		return map;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AppointmentAPI");
		Appointment appointment = new Appointment();
		appointment.setPatientId(request.getParameter("patientId"));
		appointment.setPatientName(request.getParameter("patientName"));
		appointment.setPhone(request.getParameter("phone"));
		appointment.setDoctorName(request.getParameter("doctorName"));
		appointment.setSpecialization(request.getParameter("specialization"));
		appointment.setHospitalId(request.getParameter("hospitalId"));
		appointment.setHospitalName(request.getParameter("hospitalName"));
		appointment.setAppointmentDate(request.getParameter("appointmentDate"));
		appointment.setAppointmentTime(request.getParameter("appointmentTime"));
		appointment.setAppointmentStatus(request.getParameter("appointmentStatus"));
		
		AppointmentServiceImpl appointmentServiceImpl = new AppointmentServiceImpl();
		String output = appointmentServiceImpl.createAppointment(appointment);
		System.out.println(output);
		response.getWriter().write(output);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPut(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppointmentServiceImpl appointmentServiceImpl = new AppointmentServiceImpl();
		
		Map paras = getParasMap(request);
		System.out.println("Appointment Id: " + paras.get("hidAppointmentIDSave").toString());
		System.out.println("Patient Name: " + paras.get("patientIame").toString());
		/*
		 * System.out.println("Patient Name: " + paras.get("patientName").toString());
		 * System.out.println("Phone: " + paras.get("phone").toString());
		 * System.out.println("Doctor Name: " + paras.get("doctorName").toString());
		 * System.out.println("Specialization: " +
		 * paras.get("specialization").toString()); System.out.println("Hospital Id: " +
		 * paras.get("hospitalId").toString()); System.out.println("Hospital Name: " +
		 * paras.get("hospitalName").toString());
		 * System.out.println("Appointment Date: " +
		 * paras.get("appointmentDate").toString());
		 * System.out.println("Appointment Time: " +
		 * paras.get("appointmentTime").toString());
		 * System.out.println("Appointment Status: " +
		 * paras.get("appointmentStatus").toString());
		 */
		
		String output = appointmentServiceImpl.updateAppointment(
				paras.get("hidAppointmentIDSave").toString(),
				paras.get("patientId").toString(),
				paras.get("patientName").toString(),
				paras.get("phone").toString(),
				paras.get("doctorName").toString(),
				paras.get("specialization").toString(),
				paras.get("hospitalId").toString(),
				paras.get("hospitalName").toString(),
				paras.get("appointmentDate").toString(),
				paras.get("appointmentTime").toString());
		
		response.getWriter().write(output);
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doDelete(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AppointmentServiceImpl appointmentServiceImpl = new AppointmentServiceImpl();
		
		Map paras = getParasMap(request);
		
		String output = appointmentServiceImpl.cancelAppointment(paras.get("appointmentId").toString());

		response.getWriter().write(output);
		
	}
	
	
	
}
