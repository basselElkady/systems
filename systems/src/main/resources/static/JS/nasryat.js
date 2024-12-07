// Show the New Nasryat Form
function showNewNasryatForm() {
    document.getElementById("buttons-container").style.display = "block";
    document.getElementById("new-nasryat-form").style.display = "block";
    toggleButtons(false);
}

// Create Nasryat
async function createNasryat() {
    const name = document.getElementById("nasryat-name").value;
    const category = document.getElementById("nasryat-category").value;
    const price = document.getElementById("nasryat-price").value;

    // Validate the input
    if (!name || !category || isNaN(price) || price <= 0) {
        alert("Please provide valid values for all fields.");
        return;
    }

    const nasryatDto = {    name: name,
                            category: category,
                            price:parseFloat(price)
                            };
    try {
        // Call the backend API
        const response = await fetch("http://localhost:8080/bluenile/api/v1/nasryat", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(nasryatDto)
        });

        if (response.ok) {
            const result = await response.json();
            if (result) {
                alert("Nasryat created successfully!");
                goBack();
            } else {
                alert("Failed to create Nasryat.");
            }
        } else {
            alert("Error occurred while creating Nasryat.");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("An unexpected error occurred.");
    }

}

// Cancel and Hide the Form
function cancelNasryat() {
    goBack();
}


function showGetInvoiceForm() {

    document.getElementById("buttons-container").style.display = "block";
    document.getElementById("get-nasryat-form").style.display = "block";
    toggleButtons(false);
}








let currentPage = 0;
let specification = null;

async function getNasryat() {
    const id = document.getElementById("specification-id").value;
    const fromDate = document.getElementById("specification-from-date").value;
    const toDate = document.getElementById("specification-to-date").value;
    const name = document.getElementById("specification-name").value;
    const category = document.getElementById("specification-category").value;
    const price = document.getElementById("specification-price").value;

    specification = {
        id: id ? parseInt(id) : null,
        fromDate: fromDate || null,
        toDate: toDate || null,
        name: name || null,
        category: category || null,
        price: price ? parseFloat(price) : null,
    };

    currentPage = 0;
    await fetchNasryat(currentPage, specification);
}

async function fetchNasryat(page, specification) {
    try {
        const response = await fetch(`http://localhost:8080/bluenile/api/v1/nasryat/search?page=${page}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(specification),
        });

        if (!response.ok) throw new Error("Failed to fetch Nasryat");

        const data = await response.json();

        if (!data.nasryat || data.nasryat.length === 0) {
//            const resultsBody = document.getElementById("nasryat-results-body");
//            resultsBody.innerHTML = `<tr><td colspan="5">No results found.</td></tr>`;
            alert('No more invoices found.');
            document.getElementById("next-button").disabled = true;
            document.getElementById("next-button").style.backgroundColor = "gray";

            return;
        }

        displayNasryatResults(data.nasryat);
        document.getElementById("next-button").disabled = false; // Enable Next button
    } catch (error) {
        console.error("Error:", error);
        alert(`Failed to retrieve Nasryat. Error: ${error.message}`);
    }
}

function displayNasryatResults(nasryatList) {
    const resultsBody = document.getElementById("nasryat-results-body");
    resultsBody.innerHTML = "";

    const nasryatt = nasryatList.slice().reverse();

    nasryatt.forEach((nasryat) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${nasryat.date}</td>
            <td>${nasryat.name}</td>
            <td>${nasryat.category}</td>
            <td>${nasryat.price !== null && nasryat.price !== undefined ? nasryat.price.toFixed(2) : "N/A"}</td>
            <td>
                <button class="danger-button" onclick="confirmDeletion(${nasryat.id})">Delete</button>
                <button class="danger-button" onclick="showUpdateForm(${nasryat.id}, '${nasryat.name}', '${nasryat.category}', ${nasryat.price})">Update</button>
            </td>
        `;
        resultsBody.appendChild(row);
    });

    document.getElementById("get-nasryat-form").style.display = "none";
    document.getElementById("nasryat-results").style.display = "block";
    document.getElementById("next-button").style.display = "inline-block";
    document.getElementById("next-button").style.backgroundColor = "#4CAF50";

}

async function fetchNextPage() {
    currentPage++;
    await fetchNasryat(currentPage, specification);
}

























function confirmDeletion(id) {
    const confirmation = confirm("Are you sure you want to delete nasryat with ID " + id + "?");
    if (confirmation) {
        deleteNasryat(id);
    }
}


// Function to delete a nasryat
async function deleteNasryat(id) {
    try {
        const response = await fetch(`http://localhost:8080/bluenile/api/v1/nasryat?id=${id}`, {
            method: "DELETE",
        });

        if (response.ok) {
            alert("Invoice with deleted successfully.");
            // Refresh the results or remove the row
            //document.querySelector(`tr td:first-child:contains("${id}")`).parentElement.remove();
            getNasryat();
        } else {
            alert("Failed to delete nasryat. Please try again.");
        }
    } catch (error) {
        alert("An error occurred while deleting the nasryat. Please try again.");
        console.error(error);
    }
}



// Function to show the update form with pre-filled values
function showUpdateForm(id, name, category, price) {
    const updateForm = document.getElementById("update-nasryat-form");

    // Pre-fill the form with existing data
    document.getElementById("update-name").value = name;
    document.getElementById("update-category").value = category;
    document.getElementById("update-price").value = price;

    // Store the ID for update
    document.getElementById("update-id").value = id;

    // Show the update form
    updateForm.style.display = "block";
    document.getElementById("nasryat-results").style.display = "none"; // Hide results
}

// Function to cancel the update
function cancelUpdate() {
    const updateForm = document.getElementById("update-nasryat-form");
    updateForm.style.display = "none";
    document.getElementById("nasryat-results").style.display = "block"; // Show results
}

// Function to update the nasryat
async function updateNasryat() {
    const id = document.getElementById("update-id").value;
    const name = document.getElementById("update-name").value;
    const category = document.getElementById("update-category").value;
    const price = parseFloat(document.getElementById("update-price").value);

    const nasryatDto = {
        name,
        category,
        price
    };

    try {
        const response = await fetch(`http://localhost:8080/bluenile/api/v1/nasryat?id=${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(nasryatDto),
        });

        if (response.ok) {
            alert("Nasryat updated successfully.");
            //cancelUpdate();
            hideAll();// Hide update form
            getNasryat(); // Refresh results
        } else {
            alert("Failed to update nasryat. Please try again.");
        }
    } catch (error) {
        alert("An error occurred while updating the nasryat. Please try again.");
        console.error(error);
    }
}




async function getSumOfMonth() {
    const sumContainer = document.getElementById("sum-container");
    const sumResultElement = document.getElementById("sum-result");
    const getSumButton = document.getElementById("get-sum-button");

    // Show loading state
    sumResultElement.textContent = "Loading...";
    sumContainer.style.display = "block";
    toggleButtons(false);


    //getSumButton.style.display = "none"; // Hide the "Get Sum" button

    try {
        const response = await fetch("http://localhost:8080/bluenile/api/v1/nasryat", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const sum = await response.json();
            sumResultElement.textContent = `حساب النثريات فى هذا الشهر: ${sum}`;
        } else {
            sumResultElement.textContent = "Failed to fetch the sum.";
        }
    } catch (error) {
        console.error("Error:", error);
        sumResultElement.textContent = "An unexpected error occurred.";
    }
}








function goToDashboard() {
    window.location.href = "dashboard.html";
}



function goBack() {
    hideAll();
    // Hide all forms
     document.getElementById("buttons-container").style.display = "block";
     toggleButtons(true);
}


function toggleButtons(enable) {
    const buttons = document.querySelectorAll(".buttons-container button");
    buttons.forEach(button => {
        button.disabled = !enable; // Enable or disable buttons
        button.style.opacity = enable ? "1" : "0.5"; // Adjust button appearance
        button.style.cursor = enable ? "pointer" : "not-allowed"; // Change cursor
    });
}

function hideAll() {

    document.getElementById("new-nasryat-form").style.display = "none";
    document.getElementById("get-nasryat-form").style.display = "none";
    document.getElementById("nasryat-results").style.display = "none";
    document.getElementById("update-nasryat-form").style.display = "none";
    document.getElementById("sum-container").style.display = "none";


}
