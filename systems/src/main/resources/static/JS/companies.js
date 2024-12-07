// Show Add Company Form
function showAddCompanyForm() {
    document.getElementById("buttons-container").style.display = "block";
    document.getElementById("add-company-form").style.display = "block";
    toggleButtons(false); // Disable buttons
  }


// Create a New Company
async function createCompany() {
    const companyName = document.getElementById("company-name").value;


    if (!companyName) {
        alert("Please enter a company name!");
        return;
    }

    const companyData = {
        name: companyName,
    };

    try {
        const response = await fetch("http://localhost:8080/bluenile/api/v1/companies", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(companyData),
        });

        if (response.ok) {
            alert("Your company was created successfully!");
            goBack(); // Return to buttons view
        } else {
            alert("Failed to create company. Please try again.");
        }
    } catch (error) {
        alert("Error: " + error.message);
    }
}

// Show Get Company Form
function showGetCompanyForm() {
    hideAllSections();
    document.getElementById("get-company-form").style.display = "block";
    toggleButtons(false); // Disable buttons
    const paginationContainer = document.getElementById("pagination-container");
    paginationContainer.style.display = "block";

}




let companyName = null;
let currentPage = 0; // Initialize the current page

// Fetch Company Info
async function getCompany() {
    companyName = document.getElementById("search-company-name").value;
    currentPage = 0; // Reset the current page
    if (!companyName) {
        alert("من فضلك ادخل اسم الشركه");
        return;
    }

    document.getElementById("next-button").disabled = false; // Enable Next button for a new search

    try {
        const response = await fetch(`http://localhost:8080/bluenile/api/v1/companies/${companyName}`, {
            method: "GET",
        });

        if (response.ok) {
            const companyData = await response.json();
            displayCompanyInfo(companyData);
        } else {
            alert("Company not found or an error occurred.");
        }
    } catch (error) {
        console.error("Error fetching company:", error);
        alert("An error occurred while fetching the company.");
    }
}

async function fetchCompanyDetails() {
    //togglePaginationButtons(false);


    if (!companyName) {
        alert("Please enter a company name first.");
        return;
    }

    const paginationContainer = document.getElementById("pagination-container");


    try {
        const response = await fetch(
            `http://localhost:8080/bluenile/api/v1/companies/details?name=${encodeURIComponent(companyName)}&page=${currentPage}`
        );

        if (!response.ok) {
            throw new Error("Failed to fetch company details");
        }

        const data = await response.json();

        if (!data.companyDetails || data.companyDetails.length === 0) {
            if (currentPage === 0) {
                alert("No details found for this company.");
            } else {
                alert("No more company details found.");
                document.getElementById("next-button").disabled = true;
            }
            return;
        }

        if (data.companyDetails.length > 0) {
                    paginationContainer.style.display = "none";
        }


        populateTable(data.companyDetails);
        document.getElementById("company-details-container").style.display = "block";
    } catch (error) {
        console.error("Error fetching company details:", error);
        alert("An error occurred while fetching company details.");
    }
}

//function populateTable(companyDetails) {
//    const tbody = document.getElementById("company-details-body");
//    tbody.innerHTML = ""; // Clear previous data
//
//
//
//    companyDetails.forEach((detail) => {
//        const row = document.createElement("tr");
//
//        row.innerHTML = `
//            <td>${detail.date}</td>
//            <td>${detail.companyName}</td>
//            <td>${detail.category}</td>
//            <td>${detail.money.toFixed(2)}</td>
//            <td>${detail.total.toFixed(2)}</td>
//        `;
//
//       // tbody.appendChild(row);
//       tbody.insertBefore(row, tbody.firstChild);
//
//    });
//
//
//}


function populateTable(companyDetails) {
    const tbody = document.getElementById("company-details-body");

    // Reverse the list to arrange it from last to first
    const reversedDetails = companyDetails.slice().reverse();

    // Append rows to the existing table
    reversedDetails.forEach((detail) => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${detail.date}</td>
            <td>${detail.companyName}</td>
            <td>${detail.category}</td>
            <td>${detail.money.toFixed(2)}</td>
            <td>${detail.total.toFixed(2)}</td>
        `;

        // Append the new row to the end of the table body
        tbody.appendChild(row);
    });
}


function fetchNextPage() {
    currentPage += 1; // Increment the page number
    fetchCompanyDetails(); // Fetch the next page
}















// Display Company Info in a Table
function displayCompanyInfo(company) {
    const tableBody = document.getElementById("company-info-body");
    tableBody.innerHTML = `
        <tr>
            <td>${company.name}</td>
            <td>${company.moneyLena}</td>
        </tr>
    `;

    hideAllSections();
    document.getElementById("company-info").style.display = "block";
}

function toggleButtons(enable) {
    const buttons = document.querySelectorAll(".buttons-container button");
    buttons.forEach(button => {
        button.disabled = !enable; // Enable or disable buttons
        button.style.opacity = enable ? "1" : "0.5"; // Adjust button appearance
        button.style.cursor = enable ? "pointer" : "not-allowed"; // Change cursor
    });
}




// Show Delete Company Form
function showDeleteCompanyForm() {
    hideAllSections();
    document.getElementById("delete-company-form").style.display = "block";
    toggleButtons(false); // Disable buttons
}

// Delete a Company
async function deleteCompany() {
    const companyName = document.getElementById("delete-company-name").value;

    if (!companyName) {
        alert("من فضلك ادخل اسم الشركه");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/bluenile/api/v1/companies?name=${encodeURIComponent(companyName)}`, {
            method: "DELETE",
        });

        if (response.ok) {
            const isDeleted = await response.json();
            if (isDeleted) {
                alert(`المسح تم بنجاح`);
            } else {
                alert(`لم يتم المسح "${companyName}".`);
            }
        } else {
            alert("An error occurred while deleting the company.");
        }
    } catch (error) {
        console.error("Error deleting company:", error);
        alert("An error occurred while deleting the company.");
    }

    goBack(); // Return to the main menu
}

// Show Update Company Form
function showUpdateCompanyForm() {
    hideAllSections();
    document.getElementById("update-company-form").style.display = "block";
    toggleButtons(false); // Disable other buttons
}

// Update a Company
async function updateCompany() {
    const companyName = document.getElementById("update-company-name").value;
    const moneyLena = document.getElementById("update-company-moneyLena").value;

    const category = document.getElementById("update-company-category").value;



    if (!companyName || !moneyLena || !category) {
        alert("من فضلك ادخل كل البيانات!");
        return;
    }

    const companyData = {
        name: companyName,
        moneyLena: parseFloat(moneyLena),
    };

    try {
        const response = await fetch(`http://localhost:8080/bluenile/api/v1/companies?name=${encodeURIComponent(companyName)}&category=${encodeURIComponent(category)}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(companyData),
        });

        if (response.ok) {
            const isUpdated = await response.json();
            if (isUpdated) {
                alert(`تم "${companyName}" التحديث بنجاح.`);
            } else {
                alert(`Failed to update company "${companyName}".`);
            }
        } else {
            alert("An error occurred while updating the company.");
        }
    } catch (error) {
        console.error("Error updating company:", error);
        alert("An error occurred while updating the company.");
    }

    goBack(); // Return to the main menu
}

function goToDashboard() {
    window.location.href = "dashboard.html";
}


function togglePaginationButtons(enable) {
    const buttons = document.querySelectorAll(".pagination-container");
    buttons.forEach(button => {
        button.disabled = !enable; // Enable or disable buttons
        button.style.opacity = enable ? "1" : "0.5"; // Adjust button appearance
        button.style.cursor = enable ? "pointer" : "not-allowed"; // Change cursor
    });
}



function goBack() {
    hideAllSections();
    document.getElementById("buttons-container").style.display = "block";
    toggleButtons(true);
}

function hideAllSections() {

    document.getElementById("add-company-form").style.display = "none";
    document.getElementById("get-company-form").style.display = "none";
    document.getElementById("company-info").style.display = "none";
    document.getElementById("delete-company-form").style.display = "none";
    document.getElementById("update-company-form").style.display = "none";
    document.getElementById("company-details-container").style.display = "none";

}




