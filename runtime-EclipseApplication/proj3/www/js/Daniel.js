function deleteDaniel(id, pageBack) {
	window.location = "/Daniel/delete.php?id=" + id + "&pageBack=" + pageBack;
}

function updateDaniel(id) {
	$("#id").val(id);
	$("#cpf").val($("#Daniel_" + id + "_cpf").val());
	$("#endereco").val($("#Daniel_" + id + "_endereco").val());
	
	
	$("#formAddDaniel").modal();

	return true;
}

function addDaniel() {
	$("#id").val("");
	$("#cpf").val("");
	$("#endereco").val("");
	
	
	$("#formAddDaniel").modal();

	return true;
}