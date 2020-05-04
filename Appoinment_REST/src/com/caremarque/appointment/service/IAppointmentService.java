package com.caremarque.appointment.service;

import java.util.ArrayList;

import com.caremarque.appointment.model.Appointment;

public interface IAppointmentService {

	public String createAppointment(Appointment appointment);
	
	public String getAppointment(String appointmentId);
	
	public String getAppointments();	
	
	public String updateAppointment(String appointmnetId, String patientId, String patientName, String phone, String doctorName, String specialization, String hospitalId,String hospitalName, String appointmentDate, String appointmentTime);

	public String cancelAppointment(String appointmentId);
	
	public ArrayList<String> getAppointmentIDs();
	
}
