package com.caremarque.appointment.utils;

import java.util.regex.Pattern;

import com.caremarque.appointment.model.Appointment;

public class AppointmentValidation {

	public static boolean isValid(Appointment appointment) {
		
		Pattern patientIdPattern = Pattern.compile("/^[a-zA-Z0-9]+$");
		Pattern patientNamePattern = Pattern.compile("/^[a-zA-Z]+$");
		Pattern phonePattern = Pattern.compile("/^[0-9]{10}$/");
		Pattern doctorNamePattern = Pattern.compile("/^[a-zA-Z]+$");
		Pattern specializationPattern = Pattern.compile("/^[a-zA-Z]+$");
		Pattern hospitalIdPattern = Pattern.compile("/^[a-zA-Z0-9]+$");
		Pattern hospitalNamePattern = Pattern.compile("/^[a-zA-Z]+$");
		Pattern appointmentDatePattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
		Pattern appointmentTimePattern = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
		Pattern appointmentStatusPattern = Pattern.compile("/^[a-zA-Z]+$");
		
//		if(appointment != null) {
		if((appointment.getPatientId() != null) && (!appointment.getPatientId().isEmpty()) && patientIdPattern.matcher(appointment.getPatientId()).matches()) {
			if((appointment.getPatientName() != null) && (!appointment.getPatientName().isEmpty()) && patientNamePattern.matcher(appointment.getPatientName()).matches()) {
				if((appointment.getPhone() != null) && (!appointment.getPhone().isEmpty()) && phonePattern.matcher(appointment.getPhone()).matches()) {
					if((appointment.getDoctorName() != null) && (!appointment.getDoctorName().isEmpty()) && doctorNamePattern.matcher(appointment.getDoctorName()).matches()) {
						if((appointment.getSpecialization() != null) && (!appointment.getSpecialization().isEmpty()) && specializationPattern.matcher(appointment.getSpecialization()).matches()) {
							if((appointment.getHospitalId() != null) && (!appointment.getHospitalId().isEmpty()) && hospitalIdPattern.matcher(appointment.getHospitalId()).matches()) {
								if((appointment.getHospitalName() != null) && (!appointment.getHospitalName().isEmpty()) && hospitalNamePattern.matcher(appointment.getHospitalName()).matches()) {
									if((appointment.getAppointmentDate() != null) && (!appointment.getAppointmentDate().isEmpty()) && appointmentDatePattern.matcher(appointment.getAppointmentDate()).matches()) {
										if((appointment.getAppointmentTime() != null) && (!appointment.getAppointmentTime().isEmpty()) && appointmentTimePattern.matcher(appointment.getAppointmentTime()).matches()) {
											if((appointment.getAppointmentStatus() != null) && (!appointment.getAppointmentStatus().isEmpty()) && appointmentStatusPattern.matcher(appointment.getAppointmentStatus()).matches()) {
												
												return true;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return false;
		
	}
}