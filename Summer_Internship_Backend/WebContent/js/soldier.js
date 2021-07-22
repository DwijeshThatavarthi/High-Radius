
var delButn = document.getElementById("del");
var editButn = document.getElementById("edit");
var addButn = document.getElementById("add");
delButn.disabled = true;
editButn.disabled = true;
var noOfCheckboxSelected = 0;
updateButtonBorder();


var state = {
  'querySet':[],
  'page': 1,
  'rows': 11,
}

	fetch('http://localhost:8080/H2HBABBA2527/dummy.do')
	  .then((response) => {
		  return response.json();
	  })
	.then((myJson) => {
	    state.querySet=myJson;
	    loadTable(state.querySet,state.page);
	  });	



document.getElementById("formadd").addEventListener("submit", (event) => {
	  
	  event.preventDefault();
	  let customer_name = document.getElementsByName("custa")[0].value;
	  let customer_number = document.getElementsByName("cnoa")[0].value;
	  let invoice_amount = document.getElementsByName("inna")[0].value;
	  let invoice_id = document.getElementsByName("inva")[0].value;
	  let due_date = document.getElementsByName("datea")[0].value;
	  let notes = document.getElementsByName("notesa")[0].value;


	  var table = document.getElementById("myTable");
	  table.innerHTML = "";


	  fetch(`http://localhost:8080/H2HBABBA2527/add.do/?Name=${customer_name}&Customer=${customer_number}&Amount=${invoice_amount}&Invoice=${invoice_id}&due=${due_date}&notes=${notes}`,
			  {method:"POST"}).then(response => {
		  return response.text();
	  }).then(x => {
		 
		  location.reload();
	  });
	  document.querySelector("form").reset();
	  document.querySelector(".popadd").style.display = "none";
	});


document.getElementById("editform").addEventListener("submit", (event) => {
	event.preventDefault();
	let invoice_amount = document.getElementsByName("ia")[0].value;
	let notes = document.getElementsByName("nd")[0].value;
	
	let check = document.getElementsByClassName("checkbox");
	let invoice_id = "";
	for(let i=0; i < check.length; i++){
		if (check[i].checked) {
			invoice_id = check[i].parentElement.parentElement.id;
			break;
		}	
	}
	
	fetch(`http://localhost:8080/H2HBABBA2527/edit.do/?Amount=${invoice_amount}&Invoice=${invoice_id}&notes=${notes}`,
			  {method:"POST"}).then(response => {
		  return response.text();
	  }).then(x => {
		  location.reload();
	  });
	  document.querySelector("form").reset();
	  document.querySelector(".popedit").style.display = "none";
	
	
	var table = document.getElementById("myTable");
	table.innerHTML = "";
	
});


document.getElementById("delsub").addEventListener("submit", (event) => {
	event.preventDefault();	
	let check = document.getElementsByClassName("checkbox");
	let parIds = [];
	
	for(let i=0; i < check.length; i++){
		if (check[i].checked) {
			parIds.push(check[i].parentElement.parentElement.id);
		
		}	
		}
	document.querySelector(".popdel").style.display = "none";
	parIds.forEach((id) => {
		fetch(`http://localhost:8080/H2HBABBA2527/delete.do/?Invoice=${id}`,
				  {method:"POST"}).then(response => response.text()
		  ).then(x => {
			 location.reload();
		  });
	});
});



function search() {
  var input = document.getElementById("search").value;
  var table = document.getElementById('myTable');
  state.page = 1;
  table.innerHTML = '';
  
  var data = filterdata(input, state.querySet);
  loadTable(data, state.page);
}

function none() {
  document.getElementById("popadd").style.display = "none";

}

function none1() {
  document.getElementById("popedit").style.display = "none";
}


function filterdata(value, data) {
  var filtered = [];
  for (var i = 0; i < data.length; i++) {
    var invoice = data[i].invoice_id.toString();
    console.log(invoice);
    
    if (invoice.includes(value)) {
      filtered.push(data[i]);
    }
  }
  return filtered;
}


function validateBtn() {
	  var count = 0;
	  document.querySelectorAll("input[type='checkbox']").forEach((box) => {
	    if (box.checked) {
	      count += 1;
	    }
	  });
	  if (count == 0) {
	    delButn.disabled = true;
	    addButn.disabled = false;
	    editButn.disabled = true;
	  }
	  if (count > 0) {
	    delButn.disabled = false;
	    addButn.disabled = false;
	    editButn.disabled = false;
	  }
	  if (count > 1) {
	    addButn.disabled = true;
	    editButn.disabled = true;
	  }
	}


function checkbox(myCheckBox) {
	console.log("inside mycheckbox");
	  if (myCheckBox.checked) {
		noOfCheckboxSelected = noOfCheckboxSelected + 1;
	    var parentID = myCheckBox.parentElement.parentElement.id;
	    document.getElementById(parentID).classList.add("highlighted");
	    delButn.disabled = false;
	    editButn.disabled = false;
	  } else {
		noOfCheckboxSelected = noOfCheckboxSelected - 1;
	    document
	      .getElementById(myCheckBox.parentElement.parentElement.id)
	      .classList.remove("highlighted");
	    delButn.disabled = true;
	    editButn.disabled = true;
	  }
	  validateBtn();
	  updateButtonBorder();
	}

function updateButtonBorder(){
	console.log("inside updateborder");
	console.log("checkbox number :", noOfCheckboxSelected);
	if(noOfCheckboxSelected == 0){
		addButn.classList.add("active-border");
		editButn.classList.remove("active-border");
		delButn.classList.remove("active-border");
	}else if(noOfCheckboxSelected == 1){
		addButn.classList.add("active-border");
		editButn.classList.add("active-border");
		delButn.classList.add("active-border");
	}else{
		addButn.classList.remove("active-border");
		editButn.classList.remove("active-border");
		delButn.classList.add("active-border");
	}
}


function allcheck(myBox) {
	  var all = document.querySelectorAll("input[type='checkbox']");
	  if (myBox.checked) {
	    for (var i = 1; i < all.length; i++) {
	      all[i].checked = true;
	      checkbox(all[i]);
	    }
	  } else {
	    for (var i = 1; i < all.length; i++) {
	      all[i].checked = false;
	      checkbox(all[i]);
	    }
	  }
	}


function pagination(querySet, page, rows) {
  var trimStart = (page - 1) * rows;
  var trimEnd = trimStart + rows;
  var trimmedData = querySet.slice(trimStart, trimEnd);
  return  trimmedData;
  
}

function loadTable(data, page) {
  var table = document.getElementById('myTable');
  if (data.length == 0) {
    empty();
  }
  

  var data = pagination(data, page, state.rows)
  let tableHTML = '';
  for (let i of data) {
    tableHTML += `<tr id=${i.invoice_id}>
         <td><input id="checkbox" class="checkbox" onClick="checkbox(this)" type="checkbox">${i.customer_name}</td>
        <td>${i.customer_number}</td>
        <td>${i.invoice_id}</td>
        <td>${i.invoice_amount}</td>
        <td>${i.due_in_date}</td>
         <td>${!i.predicted_date ? "-------" : i.predicted_date}</td>
        <td>${i.notes}</td>
        </tr>`
  }
  table.innerHTML += tableHTML;
}

function empty() {
  var table = document.getElementById("myTable");
  document.getElementById("btnl").style.display = "none";
  document.getElementById("btnr").style.display = "none";
  table.innerHTML = "";
  table.innerHTML += `<div class='error'>
<img class='error-image'src = 'images/error_outline-24px.svg'/>
<h4 style='color: #FFFFFF; opacity: 1;'>No results found</h4>
<p style='letter-spacing: 0px;color: #C0C6CA;opacity: 1;'>Try adjusting your search to find what you are looking for.</p>
<a style='color: #14aff1; cursor:pointer;' onclick='clearSearch()'>Clear Search</a>
</div>`;
}

function clearSearch() {
  document.getElementById("search").value = "";
  document.getElementById("btnl").style.display = "";
  document.getElementById("btnr").style.display = "";
  search();
}

function pageincrement() {
  state.page++;
  var table = document.getElementById('myTable');
  table.innerHTML = '';
  loadTable(state.querySet, state.page);
}

function pagedecrement() {
  if (state.page > 1) {
    state.page--;
    var table = document.getElementById('myTable');
    table.innerHTML = '';
    loadTable(state.querySet, state.page);
  }

}



document.getElementById("add").addEventListener("click", function() {
  document.querySelector(".popadd").style.display = "flex";
})

document.getElementById("cancel").addEventListener("click", function() {
  document.querySelector(".popadd").style.display = "none";
})

document.getElementById("del").addEventListener("click", function() {
  document.querySelector(".popdel").style.display = "flex";
})

document.getElementById("cancdel").addEventListener("click", function() {
  document.querySelector(".popdel").style.display = "none";
})

document.getElementById("edit").addEventListener("click", function() {
  document.querySelector(".popedit").style.display = "flex";
})

document.getElementById("cancedit").addEventListener("click", function() {
  document.querySelector(".popedit").style.display = "none";
})