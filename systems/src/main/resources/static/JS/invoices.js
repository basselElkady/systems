

function showInvoicesButtons() {
    hideAll();
    document.getElementById("invoices-buttons-container").style.display = "block";
}

function showCreateInvoiceForm() {
    document.getElementById("buttons-container").style.display = "block";
    document.getElementById("create-invoice-form").style.display = "block";
    toggleButtons(false);

}

// create invoice
async function createInvoice() {
    const price = document.getElementById("invoice-price").value;
    const quantity = document.getElementById("invoice-quantity").value;
    const taxes = document.getElementById("invoice-taxes").value;
    const productName = document.getElementById("invoice-product-name").value;
    const category = document.getElementById("invoice-category").value;
    const companyNameFrom = document.getElementById("invoice-company-from").value;
    const companyNameTo = document.getElementById("invoice-company-to").value;

    // Validate inputs
    if (!price || !quantity || !taxes || !productName || !category || !companyNameFrom || !companyNameTo) {
        alert("Please fill in all fields.");
        return;
    }

    const invoiceDto = {
        price: parseFloat(price),
        quantity: parseFloat(quantity),
        taxes: parseFloat(taxes),
        productName: productName,
        category: category,
        companyNameFrom: companyNameFrom,
        companyNameTo: companyNameTo
    };

    // Send data to backend
    fetch("http://localhost:8080/bluenile/api/v1/invoice", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(invoiceDto)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Failed to create invoice.");
            }
        })
        .then(result => {
            if (result) {
                alert("تم انشاء الفاتوره بنجاح");
                goBack(); // Return to the Invoices menu
            } else {
                alert("Failed to create invoice.");
            }
        })
        .catch(error => alert(error.message));
}



// Show Get Invoice Form
function showGetInvoiceForm() {

    document.getElementById("buttons-container").style.display = "block";
    document.getElementById("get-invoice-form").style.display = "block";
    toggleButtons(false);
}

// Search Invoices
//async function searchInvoices() {
//
//console.log("Search button clickeddddddddddddddd");
//
//    // Gather form data
//    const specificationInvoice = {
//        fromDate: document.getElementById("from-date").value || null,
//        toDate: document.getElementById("to-date").value || null,
//        price: parseFloat(document.getElementById("invoicePrice").value) || null,
//        quantity: parseFloat(document.getElementById("invoiceQuantity").value) || null,
//        taxes: parseFloat(document.getElementById("invoiceTaxes").value) || null,
//        total: parseFloat(document.getElementById("invoiceTotal").value) || null,
//        productName: document.getElementById("product-name").value || null,
//        category: document.getElementById("category").value || null,
//        companyToName: document.getElementById("company-to-name").value.trim() || null,
//        companyFromName: document.getElementById("company-from-name").value.trim() || null,
//    };
//
//    console.log(JSON.stringify(specificationInvoice));
//
////    const queryParams = new URLSearchParams();
////        Object.entries(specificationInvoice).forEach(([key, value]) => {
////            if (value) queryParams.append(key, value); // Only append non-null values
////        });
//
//    try {
//        // Make POST request with the specificationInvoice as request body
//        const response = await fetch("http://localhost:8080/bluenile/api/v1/invoice/search", {
//          method: 'POST',
//                      headers: {
//                          'Content-Type': 'application/json',
//                      },
//          body: JSON.stringify(specificationInvoice),
//        });
//
//
//
//        if (!response.ok) throw new Error('Failed to fetch invoices');
//
//        const data = await response.json();
//
//        // Check if there are invoices
//        if (!data.invoices || data.invoices.length === 0) {
//            alert('No invoices found.');
//            return;
//        }
//
//        // Populate Results Table
//        const tbody = document.getElementById("invoice-results-body");
//        tbody.innerHTML = ""; // Clear previous results
//
//        // Loop over the invoices from the data
//        data.invoices.forEach(invoice => {
//            const row = document.createElement("tr");
//
//            row.innerHTML = `
//                <td>${invoice.id}</td>
//                <td>${invoice.date}</td>
//                <td>${invoice.productName}</td>
//                <td>${invoice.category}</td>
//                <td>${invoice.companyNameFrom}</td>
//                <td>${invoice.companyNameTo}</td>
//                <td>${invoice.price.toFixed(2)}</td>
//                <td>${invoice.quantity}</td>
//                <td>${invoice.taxes.toFixed(2)}</td>
//                <td>${invoice.total.toFixed(2)}</td>
//                <td>
//                     <button class="danger-button" onclick="showUpdateForm(${invoice.id})">Update</button>
//                     <button class="danger-button" onclick="showDeleteConfirmation(${invoice.id})">Delete</button>
//                </td>
//            `;
//            tbody.appendChild(row);
//        });
//
//        // Show results
//        document.getElementById("get-invoice-form").style.display = "none";
//        document.getElementById("invoice-results").style.display = "block";
//
//    } catch (error) {
//        alert(`Error: ${error.message}`);
//    }
//}













let currentPage = 0; // To track the current page
let currentSpecification = null; // To store the current search filters

async function searchInvoices() {
    console.log("Search button clicked");

    // Gather form data for the initial search
    currentSpecification = {
        fromDate: document.getElementById("from-date").value || null,
        toDate: document.getElementById("to-date").value || null,
        price: parseFloat(document.getElementById("invoicePrice").value) || null,
        quantity: parseFloat(document.getElementById("invoiceQuantity").value) || null,
        taxes: parseFloat(document.getElementById("invoiceTaxes").value) || null,
        total: parseFloat(document.getElementById("invoiceTotal").value) || null,
        productName: document.getElementById("product-name").value || null,
        category: document.getElementById("category").value || null,
        companyToName: document.getElementById("company-to-name").value.trim() || null,
        companyFromName: document.getElementById("company-from-name").value.trim() || null,
    };

    //console.log("Specification:", JSON.stringify(currentSpecification));

    // Reset the page count
    currentPage = 0;

     const tbody = document.getElementById("invoice-results-body");
     tbody.innerHTML = "";
      const nextButton = document.getElementById("next-button");
      nextButton.disabled = false;
      nextButton.style.backgroundColor = "#4CAF50";

    // Fetch the first page with the current specification
    await fetchInvoices(currentPage, currentSpecification);
}

async function fetchInvoices(page, specificationInvoice) {
    try {
        // Make POST request with the specificationInvoice and page parameter
        const response = await fetch(`http://localhost:8080/bluenile/api/v1/invoice/search?page=${page}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(specificationInvoice),
        });

        if (!response.ok) throw new Error('Failed to fetch invoices');

        const data = await response.json();

        // Check if there are invoices
        if (!data.invoices || data.invoices.length === 0) {
            alert(' لا يوجد فواتير بهذه البيانات');
            document.getElementById("next-button").disabled = true;
            document.getElementById("next-button").style.backgroundColor = "gray";


            return;
        }

        // Populate Results Table
        const tbody = document.getElementById("invoice-results-body");
        //tbody.innerHTML = ""; // Clear previous results

        const reversedInvoices = data.invoices.slice().reverse();
        // Loop over the invoices from the data
        reversedInvoices.forEach(invoice => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${invoice.date}</td>
                <td>${invoice.productName}</td>
                <td>${invoice.category}</td>
                <td>${invoice.companyNameFrom}</td>
                <td>${invoice.companyNameTo}</td>
                <td>${invoice.price.toFixed(2)}</td>
                <td>${invoice.quantity}</td>
                <td>${invoice.taxes.toFixed(2)}</td>
                <td>${invoice.total.toFixed(2)}</td>
                <td>
                     <button class="danger-button" onclick="showUpdateForm(${invoice.id})">Update</button>
                     <button class="danger-button" onclick="showDeleteConfirmation(${invoice.id})">Delete</button>
                </td>
            `;
            tbody.appendChild(row);
        });

        // Show results and Next button
        document.getElementById("get-invoice-form").style.display = "none";
        document.getElementById("invoice-results").style.display = "block";
        document.getElementById("next-button").style.display = "inline-block"; // Show Next button
        document.getElementById("next-button").style.backgroundColor = "#4CAF50";

    } catch (error) {
        alert(`Error: ${error.message}`);
    }
}

// Function to fetch the next page of invoices using the same specification
async function fetchNextPage() {
    currentPage++; // Increment the page
    console.log("Fetching next page:", currentPage);

    // Ensure the current specification is not null
//    if (!currentSpecification) {
//        alert("Please search first to load invoices.");
//        return;
//    }

    // Fetch the next page with the same specification
    await fetchInvoices(currentPage, currentSpecification);
}







async function generatePDF() {
    const { jsPDF } = window.jspdf;

    const doc = new jsPDF();

    // Title
    //doc.text("Invoice Table", 14, 10);

    // Fetch the table rows
    const table = document.getElementById("invoice-results-body");
    const rows = [];
    const headers = [
        "Date",
        "Product Name",
        "From Company",
        "To Company",
        "Price",
        "Quantity",
        "Taxes",
        "Total",
    ];

    // Extract rows and exclude the "Category" column (index 2)
    for (const row of table.rows) {
        const rowData = [];
        const cells = Array.from(row.cells);

        cells.forEach((cell, index) => {
            if (index === 2) return; // Skip the "Category" column
            if (!cell.querySelector("button")) {
                rowData.push(cell.innerText.trim()); // Add cell content except buttons
            }
        });

        rows.push(rowData);
    }

    // Use autoTable to format the PDF table with grid theme and alignment
    doc.autoTable({
        head: [headers],
        body: rows,
        startY: 20,
        theme: "grid", // Use 'grid' for visible borders
        styles: {
            fontSize: 8,
            cellPadding: 5,
        },
        headStyles: {
            fillColor: [41, 128, 185], // Blue header background
            textColor: [255, 255, 255], // White header text
            fontSize: 8,
            halign: "center", // Center-align header text
        },
        bodyStyles: {
            valign: "middle", // Vertically align text to middle
        },
        columnStyles: {
            0: { halign: "left" },   // Align "Date" column to the left
            1: { halign: "left" },   // Align "Product Name" column to the left
            4: { halign: "right" },  // Align "Price" column to the right
            5: { halign: "right" },  // Align "Quantity" column to the right
            6: { halign: "right" },  // Align "Taxes" column to the right
            7: { halign: "right" },  // Align "Total" column to the right
        },
    });

    // Save the PDF with a custom name
    doc.save("فواتير.pdf");
}










// show delete input
// JavaScript function to show delete confirmation
function showDeleteConfirmation(invoiceId) {
    const userConfirmed = confirm(`Are هل you sure you want to delete the invoice with ID ${invoiceId}?`);
    if (userConfirmed) {
        deleteInvoice(invoiceId); // Call the deletion function
    }
}

// Function to delete an invoice
async function deleteInvoice(invoiceId) {
    try {
        const response = await fetch(`http://localhost:8080/bluenile/api/v1/invoice?id=${invoiceId}`, {
            method: "DELETE",
        });

        if (response.ok) {
            alert(`تم حذف الفاتوره بنجاح "${invoiceId}" `);
            // Optionally, refresh the invoice table to reflect the deletion
            searchInvoices();
        } else {
            throw new Error("Failed to delete invoice");
        }
    } catch (error) {
        alert(`Error: ${error.message}`);
    }
}




let invoiceToUpdateId = null; // Store the ID of the invoice being updated

// Function to show the update form
function showUpdateForm(invoiceid) {
    invoiceToUpdateId = invoiceid; // Store the ID for updating
//
//    // Pre-fill form fields with current invoice data
//    document.getElementById("update-product-name").value = invoice.productName;
//    document.getElementById("update-category").value = invoice.category;
//    document.getElementById("update-company-from").value = invoice.companyNameFrom;
//    document.getElementById("update-company-to").value = invoice.companyNameTo;
//    document.getElementById("update-price").value = invoice.price;
//    document.getElementById("update-quantity").value = invoice.quantity;
//    document.getElementById("update-taxes").value = invoice.taxes;

    // Show the update form
    document.getElementById("update-invoice-form").style.display = "block";
    document.getElementById("invoice-results").style.display = "none";
}

// Function to submit the updated invoice
async function submitInvoiceUpdate() {
    const updatedInvoice = {
        productName: document.getElementById("update-product-name").value,
        category: document.getElementById("update-category").value,
        companyNameFrom: document.getElementById("update-company-from").value,
        companyNameTo: document.getElementById("update-company-to").value,
        price: parseFloat(document.getElementById("update-price").value),
        quantity: parseFloat(document.getElementById("update-quantity").value),
        taxes: parseFloat(document.getElementById("update-taxes").value),
    };

    try {
        const response = await fetch(`http://localhost:8080/bluenile/api/v1/invoice?id=${invoiceToUpdateId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(updatedInvoice),
        });

        if (response.ok) {
            alert(`تم تحديث الفاتوره بنجاح `);
            // Refresh the invoice list
            searchInvoices();
        } else {
            throw new Error("Failed to update invoice");
        }
    } catch (error) {
        alert(`Error: ${error.message}`);
    } finally {
        cancelUpdate(); // Close the update form
    }
}

// Function to cancel the update
function cancelUpdate() {
    document.getElementById("update-invoice-form").style.display = "none";
    document.getElementById("invoice-results").style.display = "block";
    invoiceToUpdateId = null; // Reset the ID
}
















function goToDashboard() {
    window.location.href = "dashboard.html";
}






function showUpdateInvoiceForm() {
    hideAll();
    document.getElementById("update-invoice-form").style.display = "block";
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
    document.querySelectorAll(".buttons-container, .create-invoice-form, .get-invoices-form, .update-invoice-form").forEach(el => el.style.display = "none");
    document.getElementById("get-invoice-form").style.display = "none";
    document.getElementById("invoice-results").style.display = "none";


}











































