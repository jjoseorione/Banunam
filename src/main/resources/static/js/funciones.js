//Generales

function closeSuccess(){
    document.getElementById("success").style.display = 'none';
}

function closeDanger(){
    document.getElementById("danger").style.display = 'none';
}

//user-administration

function eliminarUsuario(idUsuario, usuario, usuarioFirmado){
        console.log(usuarioFirmado);
        if(usuario === usuarioFirmado){
            alert("¡No puedes eliminarte a ti mismo!")
            return;
        }
        let del = confirm("¿Eliminar usuario " + usuario + "?");
        console.log(del);
        if(del)
            location.href = "/user-administration/usuarios/" + idUsuario + "/delete"
    }