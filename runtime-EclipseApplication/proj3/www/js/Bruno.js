function deleteBruno(id, pageBack) {
	window.location = "/Bruno/delete.php?id=" + id + "&pageBack=" + pageBack;
}

function updateBruno(id) {
	$("#id").val(id);
	$("#cpf").val($("#Bruno_" + id + "_cpf").val());
	$("#endereco").val($("#Bruno_" + id + "_endereco").val());
	$("#nascimento").val($("#Bruno_" + id + "_nascimento").val());
	$("#mensalidade").val($("#Bruno_" + id + "_mensalidade").val());
	
		$("#tableDanielformAdd").bootstrapTable('uncheckAll');
		$("#tableDanielformAdd").bootstrapTable('checkBy', {field:'id', values:[$("#Bruno_" + id + "_orientador").val()]});
		$("#txtDanielformAdd").val(JSON.stringify($("#tableDanielformAdd").bootstrapTable('getSelections')));
	
	
	$("#formAddBruno").modal();

	return true;
}

function addBruno() {
	$("#id").val("");
	$("#cpf").val("");
	$("#endereco").val("");
	$("#nascimento").val("");
	$("#mensalidade").val("");
	$("#tableDanielformAdd").bootstrapTable('uncheckAll');
		$("#txtDanielformAdd").val("");
	
	
	$("#formAddBruno").modal();

	return true;
}