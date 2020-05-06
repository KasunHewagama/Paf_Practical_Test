<%@page
	import="com.caremarque.appointment.service.AppointmentServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Appointment Service...!</title>

<!-- CSS -->
<link rel="stylesheet" href="Views/bootstrap.min.css">
<link rel="stylesheet" href="Views/appointment.css">
<!-- <link rel="stylesheet" href="Views/bootstrap-datepicker3.css" /> -->

<!-- JS -->
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/appointment.js" type="text/javascript"></script>
<!-- <script src="Components/popper.min.js" type="text/javascript"></script> -->
<!-- <script src="Components/bootstrap-datepicker.min.js" type="text/javascript"></script> -->


</head>
<body>

	<div class="container">

		<form name="appointmentCreation" id="appointmentCreation"
			method="POST" action="Appointment.jsp">

			<div class="form-title">Add An Appointment</div>
			<br /> <br />
			<h4>Appointment Information</h4>

			<div class="row">
				<div class="col">
					<label>Patient Id <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Your Id...!" name="patientId"
						id="patientId" class="form-control form-control-sm"><br />
				</div>

				<div class="col">
					<label>Patient Name <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Your Name...!" name="patientName"
						id="patientName" class="form-control form-control-sm"><br />
				</div>
			</div>

			<div class="row">
				<div class="col">
					<label>Mobile Number <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Your Number...!" name="phone"
						id="phone" class="form-control form-control-sm"><br />
				</div>
			</div>

			<div class="row">
				<div class="col">
					<label>Doctor Name <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Doctor Name...!" name="doctorName"
						id="doctorName" class="form-control form-control-sm"><br />
				</div>

				<div class="col">
					<label>Specialization <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Any Specialization...!"
						name="specialization" id="specialization"
						class="form-control form-control-sm"><br />
				</div>
			</div>

			<div class="row">
				<div class="col">
					<label>Hospital Id <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Hospital Id...!" name="hospitalId"
						id="hospitalId" class="form-control form-control-sm"><br />
				</div>

				<div class="col">
					<label>Hospital Name <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Hospital Name...!"
						name="hospitalName" id="hospitalName"
						class="form-control form-control-sm"><br />
				</div>
			</div>

			<div class="row">
				<div class="col">
					<label>Appointment Date <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Appointment Date...!"
						name="appointmentDate" id="appointmentDate"
						class="form-control form-control-sm"><br />
				</div>

				<div class="col">
					<label>Appointment Time <label_1>*</label_1></label><br /> <input
						type="text" placeholder="Enter Appointment Time...!"
						name="appointmentTime" id="appointmentTime"
						class="form-control form-control-sm"><br />
				</div>
			</div>


			<input id="btnSave" name="btnSave" type="button"
				value="Add Appointment" class="btn btn-primary"> <input
				type="hidden" id="hidAppointmentIdSave" name="hidAppointmentIdSave"
				value=""> <br> <br>
		
		</form>

		<div id="alertSuccess" class="alert alert-success"></div>
		<div id="alertError" class="alert alert-danger"></div>



		<div id="divAppointmentsGrid">
			<%
				AppointmentServiceImpl appointmentObj = new AppointmentServiceImpl();
				out.print(appointmentObj.getAppointments());
			%>
		</div>

	</div>


</body>
</html>