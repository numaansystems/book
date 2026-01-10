/**
 * Categories Page JavaScript
 * Handles category management with DataTables and Bootstrap spinners
 */

let currentAgencyId = null;
let dataTable = null;

$(document).ready(function() {
    // Initialize DataTable
    dataTable = $('#categoriesTable').DataTable({
        data: [],
        columns: [
            { data: 'id' },
            { data: 'title' },
            { data: 'description' },
            { data: 'createdBy' },
            { 
                data: 'createdAt',
                render: function(data) {
                    return data ? new Date(data).toLocaleString() : '';
                }
            }
        ],
        order: [[0, "desc"]],
        pageLength: 10,
        language: {
            emptyTable: "Please select an agency to view categories"
        }
    });

    // Load agencies on page load
    loadAgencies();

    // Agency selection handler
    $('#agencySelect').on('change', function() {
        currentAgencyId = $(this).val();
        if (currentAgencyId) {
            loadCategories();
        } else {
            dataTable.clear().draw();
        }
    });

    // Add category form submission
    $('#addCategoryForm').on('submit', function(e) {
        e.preventDefault();
        if (!currentAgencyId) {
            showMessage('Please select an agency first', 'warning');
            return;
        }
        addCategory();
    });

    // Refresh button handler
    $('#refreshBtn').on('click', function() {
        if (currentAgencyId) {
            loadCategories();
        } else {
            showMessage('Please select an agency first', 'warning');
        }
    });
});

/**
 * Load all agencies from the API
 */
function loadAgencies() {
    SpinnerHelper.executeWithSpinner(
        $('#agencySelect'),
        function() {
            return $.ajax({
                url: '/api/agencies',
                method: 'GET',
                dataType: 'json'
            });
        },
        function(data) {
            // Success callback
            const select = $('#agencySelect');
            select.empty();
            select.append('<option value="">-- Select an Agency --</option>');
            data.forEach(function(agency) {
                select.append('<option value="' + agency.id + '">' + agency.name + '</option>');
            });
        },
        function(error) {
            // Error callback
            console.error('Error loading agencies:', error);
            showMessage('Failed to load agencies', 'danger');
        }
    );
}

/**
 * Load categories for the selected agency
 */
function loadCategories() {
    SpinnerHelper.executeWithSpinner(
        $('#refreshBtn'),
        function() {
            return $.ajax({
                url: '/api/categories/by-agency/' + currentAgencyId,
                method: 'GET',
                dataType: 'json'
            });
        },
        function(data) {
            // Success callback
            dataTable.clear();
            dataTable.rows.add(data);
            dataTable.draw();
            showMessage('Categories loaded successfully', 'success');
        },
        function(error) {
            // Error callback
            console.error('Error loading categories:', error);
            showMessage('Failed to load categories', 'danger');
        }
    );
}

/**
 * Add a new category
 */
function addCategory() {
    const formData = {
        title: $('#categoryTitle').val(),
        description: $('#categoryDescription').val()
    };

    SpinnerHelper.executeWithSpinner(
        $('#addCategoryBtn'),
        function() {
            return $.ajax({
                url: '/api/categories?agencyId=' + currentAgencyId,
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(formData)
            });
        },
        function(data) {
            // Success callback
            $('#addCategoryForm')[0].reset();
            showMessage('Category added successfully', 'success');
            loadCategories();
        },
        function(error) {
            // Error callback
            console.error('Error adding category:', error);
            if (error.status === 400) {
                showMessage('Category with this title already exists for this agency', 'danger');
            } else {
                showMessage('Failed to add category', 'danger');
            }
        }
    );
}

/**
 * Show a message to the user
 */
function showMessage(message, type) {
    const messageDiv = $('#formMessage');
    messageDiv.removeClass('d-none alert-success alert-danger alert-warning alert-info');
    messageDiv.addClass('alert-' + type);
    messageDiv.text(message);
    
    // Auto-hide after 5 seconds
    setTimeout(function() {
        messageDiv.addClass('d-none');
    }, 5000);
}
