//Page refresh moment
$(document).ready(function(){
	if($("#alertSuccess").text().trim() == ""){
		
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

//Save
$(document).on("click", "#btnSave", function(event){
	
	//Clear alerts
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	//Form validation
	var status = validateForm();
	if(status != true){
		
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	//If valid
	var type = ($("#hidAppointmentIdSave").val() == "") ? "POST" : "PUT";
	
	$.ajax({
		
		url : "AppointmentAPI",
		type : type,
		data : $("#appointmentCreation").serialize(),
		dataType : "text",
		complete : function(response, status){
			
			onAppointmentComplete(response.responseText, status);
		}
	});
	
});

function onAppointmentComplete(response, status){
	
	if(status == "success"){
		
		var resultSet = JSON.parse(response);
		
		if(resultSet.status.trim() == "success"){
			
			$("#alertSuccess").text("Successfully saved..!");
			$("#alertSuccess").show();
			
			$("#divAppointmentsGrid").html(resultSet.data);	
			
		}else if(resultSet.status.trim() == "error"){
			
			$("#alertError").text(resultSet.data);
			$("#alertError").show(); 
		}
		
	}else if(status == "error"){
		$("#alertError").text("Error while saving.");
		$("#alertError").show(); 
	
	}else{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show(); 
	}
	
	$("#hidAppointmentIdSave").val("");
	$("#appointmentCreation")[0].reset(); 
	
}

//Update
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidAppointmentIDSave").val($(this).closest("tr").find('#hidAppointmentIDSave').val());
	$("#patientId").val($(this).closest("tr").find('td:eq(1)').text());
	$("#patientName").val($(this).closest("tr").find('td:eq(2)').text());
	$("#phone").val($(this).closest("tr").find('td:eq(3)').text());
	$("#doctorName").val($(this).closest("tr").find('td:eq(4)').text());
	$("#specialization").val($(this).closest("tr").find('td:eq(5)').text());
	$("#hospitalId").val($(this).closest("tr").find('td:eq(6)').text());
	$("#hospitalName").val($(this).closest("tr").find('td:eq(7)').text());
	$("#appointmentDate").val($(this).closest("tr").find('td:eq(8)').text());
	$("#appointmentTime").val($(this).closest("tr").find('td:eq(9)').text());
	
	
});

//Delete
$(document).on("click", ".btnRemove", function(event){
		
	$.ajax({
		url : "AppointmentAPI",
		type : "DELETE",
		data : "appointmentId=" + $(this).data("appointmentId"),
		dataType : "text",
		complete : function(response, status){
			 
			onAppointmentDeleteComplete(response.responseText, status);
		 }
	});
});

function onAppointmentDeleteComplete(response, status){
	
	if (status == "success")
	 {
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success"){
			
			 $("#alertSuccess").text("Successfully deleted..!");
			 $("#alertSuccess").show();
			 
			 $("#divPatientsGrid").html(resultSet.data);
			 
		} else if (resultSet.status.trim() == "error"){
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
		}
		
	 } else if (status == "error")
	 {
		 $("#alertError").text("Error while deleting..!");
		 $("#alertError").show();
	 } else
	 {
		 $("#alertError").text("Unknown error while deleting..!");
		 $("#alertError").show();
	 } 
}

//Client-Model
function validateForm(){
	
	if($("#patientId").val().trim() == ""){
		return "Insert Patient Id...!";
	}
	if($("#patientName").val().trim() == ""){
		return "Insert Patient Name...!";
	}
	if($("#phone").val().trim() == ""){
		return "Insert Mobile Number...!";
	}
	if($("#doctorName").val().trim() == ""){
		return "Insert Doctor Name...!";
	}
	if($("#specialization").val().trim() == ""){
		return "Insert Specialization...!";
	}
	if($("#hospitalId").val().trim() == ""){
		return "Insert Hospital Id...!";
	}
	if($("#hospitalName").val().trim() == ""){
		return "Insert Hospital Name...!";
	}
	if($("#appointmentDate").val().trim() == ""){
		return "Insert Appointment Date...!";
	}
	if($("#appointmentTime").val().trim() == ""){
		return "Insert Appointment Time...!";
	}
	
	return true;
}