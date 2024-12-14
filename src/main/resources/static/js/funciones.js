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

//customer-care-center
var colonias = new Map();
async function buscarColonias(){
    let cp = document.getElementById("cp").value;
    let endpoint = "/customer-care-center/colonias/" + cp;
    let select = document.getElementById("colonia");
    select.innerHTML = '<option value="">Selecciona la colonia...</option>'
    console.log(endpoint);
    const response = await fetch(endpoint, {
        method: "GET"
    });
    response.json().then((data) => {
        data.forEach(element => {
            let option = document.createElement("option");
            option.textContent = element.nombre + ", " + element.municipio + ", " + element.estado;
            option.value = option.textContent;
            select.appendChild(option);
            colonias.set(option.textContent, element.id_colonia);
        })
    });
    console.log(colonias);

}

function obtieneIdColonia(){
    let hidden = document.getElementById("idColonia");
    let select = document.getElementById("colonia");
    hidden.value = colonias.get(select.value);
}