/* 
 * Code written by Nguyen Van Hung at @imrhung
 * Feel free to re-use or share it.
 * Happy code!!!
 */

//Global variable
var baseUrl;
var itemCount;
var userId;

// Constant
var DATE_FORMAT = 'DD MMM YYYY';
var DATE_SQL_FORMAT = 'YYYY-MM-DD';

// Function run after page is loaded
$(function () {

    // Init variable
    baseUrl = $("#base-url").attr("href");
    userId = $('#user_id').val();

    // Company form submit
    $('#form_company').submit(function () {
        saveCompany();
    });

    // Init the upload file
    // Set the button
    $('#drop_image a').click(function () {
        // Simulate a click on the file input button
        // to show the file browser dialog
        $(this).parent().find('input').click();
    });
    $('#drop_image').fileupload({
        // This element will accept file drag/drop uploading
        dropZone: $('#drop_image'),
        url: 'api/file/do_upload',
        dataType: 'json',
        // This function is called when a file is added to the queue;
        // either via the browse button, or via drag/drop:
        add: function (e, data) {
            // Show upload started (spinning or progressbar)

            // Automatically upload the file once it is added to the queue
            data.submit();
        },
        done: function (e, data) {
            $('#company_logo').attr('src', data.result.files[0].url);
            showNotification('success', "Image uploaded!");
        },
        fail: function (e, data) {
            showNotification('warning', "Upload failed!");
        }
    });
    // Prevent the default action when a file is dropped on the window
    $(document).on('drop dragover', function (e) {
        e.preventDefault();
    });

    // Client table
    // Add client
    $("#form_client").submit(function () {
        saveClient();
    });
    // Reset button
    $('#client_clear').click(function () {
        resetClientForm();
    });

    // Invoice
    /*
     * Handle dynamic field of item data.
     */
    itemCount = $('#item_table tr.item_record').size();
    $('#add_another_item').on('click', function () {
        $(".item_record:last").clone()
                .find("input").val("")
                .end()
                .insertAfter('.item_record:last');
        $(".item_record:last").find('td[name="item_amount"]').text('0.00');
        itemCount++;
        return false;
    });
    $(document).on('click', '.remove-invoice-item', function () {
        if (itemCount > 1) {
            $(this).parents('tr.item_record').remove();
            itemCount--;
        } else {
            // If the only one left, just clear it.
            $(this).parents('tr.item_record').find("input").val('');
            $(this).parents('tr.item_record').find('td[name="item_amount"]').text('0.00');
        }
        // Recalculate the total amount.
        calculateTotalAmount();
        return false;
    });

    // We will check form submit only on Publish. For draft, we save without checking form
    // On form submit.
    $('#form_invoice').submit(function () {
        console.log("Invoice submit");
        saveInvoice(1);
        updateInvoiceEmbed();
    });
    // On save as draft.
    $('#invoice_draft').on('click', function () {
        saveInvoice(0);
        updateInvoiceEmbed();
    });
    
    // Using template for announcing email
    $('#invoice_template').on('click', function () {
        setInvoiceTemplateMail();
    });
    // Reset button
    $("#invoice_reset").click(function () {
        resetInvoiceForm();
    });

    // Recurring selection
    $("input[name='invoice_frequency']").on('change', function () {
        // Recurring
        if ($(this).val() === '2') {
            $("#frequency-area").css("display", "block");
        } else {
            $("#frequency-area").css("display", "none");
        }
    });

    // Load the data
    loadStatTable($('#user_id').val());
    loadCompany($('#user_id').val());
    loadClientTable($('#user_id').val());
    loadInvoiceTable($('#user_id').val());
    initInvoiceForm($('#user_id').val());
    loadPaymentTable($('#user_id').val());
});

function loadStatTable(userId) {

    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/payment/?q=summary&user_id=" + userId,
        dataType: "json",
        success: function (data) {
            console.log(data);
            $('#stat-volume').text('$' + data.volume);
            $('#stat-invoice').text(data.invoice);
            $('#stat-recurring').text(data.recurring);
            $('#stat-client').text(data.client);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showNotification('danger', "Error on getting statistic data!");
        }
    });
}

function loadCompany(userId) {

    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/company/?user_id=" + userId,
        dataType: "json",
        success: function (data) {
            console.log(data);

            updateCompanyView(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // Notify success
            showNotification('danger', "Error on loading Company information!");
        }
    });
}

function loadClientTable(userId) {

    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/client/?user_id=" + userId,
        dataType: "json",
        success: function (data) {
            console.log(data);

            updateClientTable(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // Notify error
            showNotification('danger', "Error on loading client table!");
        }
    });
}

function loadClient(clientId) {
    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/client/?id=" + clientId,
        dataType: "json",
        success: function (data) {
            console.log(data);

            updateClientForm(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // Notify error
            showNotification('danger', "Error on loading client infomation!");
        }
    });
}

function loadInvoice(invoiceId) {
    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/invoice/?id=" + invoiceId,
        dataType: "json",
        success: function (data) {
            console.log(data);

            updateInvoiceForm(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // Notify error
            showNotification('danger', "Error on loading invoice infomation!");
        }
    });
}

function loadInvoiceTable(userId) {

    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/invoice/?user_id=" + userId,
        dataType: "json",
        success: function (data) {
            console.log(data);

            updateInvoiceTable(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // Notify error
            showNotification('danger', "Error on loading invoices infomation!");
        }
    });
}

function updateInvoiceForm(data) {

    // Clear all old data before load to the view:
    resetInvoiceForm();

    $('#invoice_id').val(data.id);
    $('#invoice_uid').val(data.uid);
    $('#client_select').val(data.client_id);
    $('#invoice_title').val(data.title);

    // Set the Frequency of payment
    var radioFrequency = $('input:radio[name=invoice_frequency]');
    var frequency = parseInt(data.frequency);
    if (frequency > 2) {
        // Recurring area:
        $("#frequency-area").css("display", "block");
        // Recurring payment:
        radioFrequency.filter('[value=2]').prop('checked', true);
        $('#invoice_frequency_period').val(frequency);
    } else {
        // One time 
        radioFrequency.filter('[value=1]').prop('checked', true);
        // Recurring area:
        $("#frequency-area").css("display", "none");
    }

    $('#invoice_number').text(data.id);

    // Date
    $('#invoice_date_sent').text(moment(data.date_send, DATE_SQL_FORMAT).format(DATE_FORMAT));
    $('#invoice_date_due').text(moment(data.date_due, DATE_SQL_FORMAT).format(DATE_FORMAT));

    // Items area:
    // $(".amount-record").find("input[name='amount[]']").attr('required', true);
    // Set the amounts
    var $row;
    for (var i = 0; i < data.items.length; i++) {
        if (i === 0) {
            // First amount
            $row = $('.item_record:first');
            $row.find('input[name=invoice_item]').val(data.items[i].description);
            $row.find('input[name=invoice_quantity]').val(data.items[i].quantity);
            $row.find('input[name=invoice_rate]').val(data.items[i].rate);
            $row.find('input[name=invoice_discount]').val(data.items[i].discount);
            $row.find('select[name=invoice_discount_type]').val(data.items[i].discount_type);

            // Calculate
            calculateItemAmount($row.find('input[name=invoice_item]'));
        } else {
            // Copy a new item
            $(".item_record:last").clone()
                    .find("input").val("")
                    .end()
                    .insertAfter('.item_record:last');
            $(".item_record:last").find('td[name="item_amount"]').text('0.00');
            itemCount++;

            // Set data
            $row = $('.item_record:last');
            $row.find('input[name=invoice_item]').val(data.items[i].description);
            $row.find('input[name=invoice_quantity]').val(data.items[i].quantity);
            $row.find('input[name=invoice_rate]').val(data.items[i].rate);
            $row.find('input[name=invoice_discount]').val(data.items[i].discount);
            $row.find('select[name=invoice_discount_type]').val(data.items[i].discount_type);

            // Calculate
            calculateItemAmount($row.find('input[name=invoice_item]'));
        }
    }

    // Confirmation areas
    $('#confirmation').val(data.confirmation_message);
    $('#confirmation-email').val(data.confirmation_email);
    $('#invoice-notes').val(data.notes);
    $('#announce-email').val(data.announce_email);

    // Embeded code areas
    updateInvoiceEmbed();

    setModeInvoiceEdit(data);
}

function saveClient() {
    // Loading button
    var button = $("#client_save");
    button.button('Saving...');

    // Get data
    var userId = $('#user_id').val();
    var clientId = $('#client_id').val();
    // Bind data
    var name = $('#client_name').val();
    var email = $('#client_email').val();
    var address = $('#client_address').val();
    var city = $('#client_city').val();
    var state = $('#client_state').val();
    var zip = $('#client_zip').val();
    var country = $('#client_country').val();
    var phone = $('#client_phone').val();
    var fax = $('#client_fax').val();

    // TODO : validate data: has description...

    // Determine POST or PUT
    var type, url;
    if (clientId) {
        // Edit
        type = 'PUT';
    } else {
        // Create a new
        type = 'POST';
    }

    // Post to api
    $.ajax({
        type: type,
        url: baseUrl + 'api/client',
        dataType: "json",
        data: {
            id: clientId,
            user_id: userId,
            name: name,
            email: email,
            street_address: address,
            city: city,
            state: state,
            zip_code: zip,
            country: country,
            phone_number: phone,
            fax_number: fax
        },
        success: function (data, textStatus, jqXHR) {
            console.log(data);

            // Reload the client table
            loadClientTable(userId);

            // Notify success
            showNotification('success', "Successful!");

            button.button('reset');

            // Change the view to "Edit" mode
            changeModeClientForm('edit');

            // Set the id for this client
            $('#client_id').val(data.id);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showNotification('danger', "Error on saving data!");
            // Reset button
            button.button('reset');
        }
    });
}

function resetClientForm() {
    // Change the view to edit mode
    changeModeClientForm('add');

    // Clear the id in hidden input
    $('#client_id').val('');
}

function changeModeClientForm(mode) {
    if (mode === 'edit') {
        $('#client_save').html("Update Client");
    } else {
        // Mode add new
        $('#client_id').val('');
        $('#client_save').html("Add Client");
    }
}

function saveCompany() {
    var baseUrl = $("#base-url").attr("href");
    var userId = $('#user_id').val();

    var button = $('#company_save');
    button.button('loading');

    // Bind data
    var logo = $('#company_logo').attr('src');
    var name = $('#company_name').val();
    var url = $('#company_url').val();
    var address = $('#company_address').val();
    var city = $('#company_city').val();
    var state = $('#company_state').val();
    var zip = $('#company_zip').val();
    var country = $('#company_country').val();
    var phone = $('#company_phone').val();
    var fax = $('#company_fax').val();
    var contact = $('#company_contact').val();
    var currency = $('#company_currency').val();

    // PUT to api
    $.ajax({
        type: 'PUT',
        url: baseUrl + "api/company",
        dataType: "json",
        data: {
            user_id: userId,
            name: name,
            logo: logo,
            url: url,
            street_address: address,
            city: city,
            state: state,
            zip_code: zip,
            country: country,
            phone_number: phone,
            fax_number: fax,
            primary_contact: contact,
            currency: currency
        },
        success: function (data, textStatus, jqXHR) {
            console.log(data);

            // Notify success
            showNotification('success', "Successful!");

            // Reset button
            button.button('reset');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // Notify success
            showNotification('danger', "Error on saving company!");
            // Reset button
            button.button('reset');
        }
    });
}

function updateCompanyView(data) {
    $('#company_logo').attr('src', data.logo);
    $('#company_name').val(data.name);
    $('#company_url').val(data.url);
    $('#company_address').val(data.street_address);
    $('#company_city').val(data.city);
    $('#company_state').val(data.state);
    $('#company_zip').val(data.zip_code);
    $('#company_country').val(data.country);
    $('#company_phone').val(data.phone_number);
    $('#company_fax').val(data.fax_number);
    $('#company_contact').val(data.primary_contact);
    $('#company_currency').val(data.currency);
}

function updateClientTable(data) {
    var clientTable = $('#client_table');

    // Clear the table body first.
    $('#client_table tbody tr').remove();

    for (var i = 0; i < data.length; i++) {
        var row = $('<tr></tr>');
        var name = $('<td></td>').text(data[i].name);
        var email = $('<td></td>').text(data[i].email);
        var phone = $('<td></td>').text(data[i].phone_number);
        var received = $('<td></td>').text('$' + data[i].amount);
        var action = '<td class="cell-action"><a href="#clients" onclick="loadClient(' + data[i].id + ');"><span class="label label-success">EDIT</span></a><a href="#" onclick="callDeleteClient(' + data[i].id + ', this);return false;"><p class="text-small">DELETE</p></a></td>';
        row.append(name).append(email).append(phone).append(received).append(action);
        clientTable.append(row);
    }
}

function updateInvoiceTable(data) {
    var invoiceTable = $('#invoice_table');

    // Clear the table first.
    $('#invoice_table tr').remove();

    for (var i = 0; i < data.length; i++) {
        var row = $('<tr></tr>');
        var state;
        if (data[i].public_state == 1) {
            state = $('<td><span class="label label-success">LIVE</span></td>');
        } else {
            state = $('<td><span class="label label-default">DRAFT</span></td>')
        }
        var paid
        if (data[i].payment_state == 1) {
            paid = $('<td><span class="label label-danger">PAID</span></td>');
        } else {
            paid = $('<td></td>')
        }
        var title = $('<td></td>').text(data[i].title);
        var timestamp = moment(data[i].created_date);
        var date = $('<td class="text-small"></td>').text(moment().calendar(timestamp));
        var action = '<td class="cell-action"><a href="#create_invoice" onclick="loadInvoice(' + data[i].id + ');"><span class="label label-success">EDIT</span></a><a href="#" onclick="callDeleteInvoice(' + data[i].id + ', this);return false;"><p class="text-small">DELETE</p></a></td>';
        row.append(state).append(paid).append(title).append(date).append(action);
        invoiceTable.append(row);
    }
}

function updateClientForm(data) {
    $('#client_id').val(data.id);

    $('#client_name').val(data.name);
    $('#client_email').val(data.email);
    $('#client_address').val(data.street_address);
    $('#client_city').val(data.city);
    $('#client_state').val(data.state);
    $('#client_zip').val(data.zip_code);
    $('#client_country').val(data.country);
    $('#client_phone').val(data.phone_number);
    $('#client_fax').val(data.fax_number);

    // Set form to edit mode
    changeModeClientForm('edit');
}

//Open a dialog to confirm delete the member.
function callDeleteClient(clientId, element) {
    bootbox.confirm(
            "Are you sure you want to delete? The action cannot be undone!",
            function (result) {
                if (result) {
                    deleteClient(clientId, element);
                }
            }
    );
}

// And here we actually delete the member. :)
function deleteClient(clientId, element) {
    console.log("Deleting");

    // Delete process
    $.ajax({
        type: 'DELETE',
        url: baseUrl + "api/client/" + clientId,
        success: function (data, textStatus, jqXHR) {
            // Refresh table
            console.log(data);

            // Delete data from table
            var tr = $(element).closest('tr');
            tr.css("background-color", "#FF3700");
            tr.fadeOut(1000, function () {
                tr.remove();
            });
            return false;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            // Notify success
            showNotification('danger', "Error on deleting client!");
        }
    });
}

/*
 * Invoice stuff
 */

function initInvoiceForm(userId) {
    // Load the company information
    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/company/?user_id=" + userId,
        dataType: "json",
        success: function (data) {
            console.log(data);

            initInvoiceCompanyInfo(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // Notify success
            showNotification('danger', "Error on loading Company information!");
        }
    });
    // Load the client list
    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/client/?user_id=" + userId + "&order=1",
        dataType: "json",
        success: function (data) {
            console.log(data);

            initClientSelect(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // Notify error
            showNotification('danger', "Error on loading client data!");
        }
    });

    // Init the date
    var today = moment();
    var dateDue = moment().add(30, 'days');
    // Set to the view
    $('#invoice_date_sent').text(today.format(DATE_FORMAT));
    $('#invoice_date_due').text(dateDue.format(DATE_FORMAT));
}

function initClientSelect(data) {
    var select = $('#client_select');
    for (var i = 0; i < data.length; i++) {
        select.append($('<option>').attr('value', data[i].id).text(data[i].name));
    }
}

function initInvoiceCompanyInfo(data) {

    $('#invoice_com_logo').attr('src', data.logo);
    $('#invoice_com_name').text(data.name);
    $('#invoice_com_address').text(data.street_address);
    $('#invoice_com_city').text(data.city + ', ' + data.state + ', ' + data.country);
    $('#invoice_com_phone').text(data.phone_number);
}

function saveInvoice(publish) {

    // Loading button
    $("#invoice_save").button('Loading...');
    $('#invoice_draft').button('Loading...');

    // Get data
    var invoiceId = $('#invoice_id').val();
    var invoiceUid = ($('#invoice_uid')).val();
    var userId = $('#user_id').val();
    var clientId = $('#client_select').val();
    var title = $('#invoice_title').val();

    // Determin the frequency
    var frequency = $("input[name='invoice_frequency']:checked", '#form_invoice').val();
    var period = $('#invoice_frequency_period').val();
    if (frequency === '2') {
        frequency = period;
    }

    var dateSent = moment($('#invoice_date_sent').text(), DATE_FORMAT);
    var dateDue = moment($('#invoice_date_due').text(), DATE_FORMAT);

    var confirmation = $('#confirmation').val();
    var confirmationEmail = $('#confirmation-email').val();
    var note = $('#invoice-notes').val();
    var announce = $('#announce-email').val();

    // Get item data
    var description = [];
    var quantity = [];
    var rate = [];
    var discount = [];
    var discountType = [];
    $('input[name^=invoice_item]').each(function () {
        description.push($(this).val());
    });
    $('input[name^=invoice_quantity]').each(function () {
        quantity.push($(this).val());
    });
    $('input[name^=invoice_rate]').each(function () {
        rate.push($(this).val());
    });
    $('input[name^=invoice_discount]').each(function () {
        discount.push($(this).val());
    });
    $('select[name^=invoice_discount_type]').each(function () {
        discountType.push($(this).val());
    });

    var amountList = [];
    for (var i = 0; i < description.length; i++) {

        var amount = new Object();
        amount.description = description[i];
        amount.quantity = quantity[i];
        amount.rate = rate[i];
        amount.discount = discount[i];
        amount.discount_type = discountType[i];
        amountList.push(amount);
    }
    var amountsJSON = (JSON.stringify(amountList));

    // TODO : validate data: has description...

    // Determine POST or PUT
    var type;
    if (invoiceId) {
        // Edit
        type = 'PUT';
    } else {
        // Create a new
        type = 'POST';
    }

    // Post to api
    $.ajax({
        type: type,
        url: baseUrl + 'api/invoice',
        dataType: "json",
        data: {
            id: invoiceId,
            uid: invoiceUid,
            user_id: userId,
            client_id: clientId,
            title: title,
            frequency: frequency,
            date_send: dateSent.format(DATE_SQL_FORMAT),
            date_due: dateDue.format(DATE_SQL_FORMAT),
            confirmation_message: confirmation,
            confirmation_email: confirmationEmail,
            public_state: publish,
            announce_email: announce,
            notes: note,
            items: amountsJSON
        },
        success: function (data, textStatus, jqXHR) {
            console.log(data);

            // Refresh the table
            loadInvoiceTable($('#user_id').val());

            // Notify success
            showNotification('success', "Saving Invoice Successful!");

            // Reset button
            $("#invoice_save").button('reset');
            $('#invoice_draft').button('reset');

            setModeInvoiceEdit(data);

            updateInvoiceEmbed();
            
            // Send announce email if published
            if (publish){
                sendAnnounceEmail(data.uid);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showNotification('danger', "Error on saving data!");
            // Reset button
            $("#invoice_save").button('reset');
            $('#invoice_draft').button('reset');
        }
    });
}

function sendAnnounceEmail(uid) {
    // Loading button
    var button = $("#invoice_save");
    button.button('Sending...');

    // Post to api
    $.ajax({
        type: 'POST',
        url: baseUrl + 'api/invoice/'+uid+"/sendmail",
        dataType: "json",
        data: {
        },
        success: function (data, textStatus, jqXHR) {
            console.log(data);

            // Notify success
            showNotification('success', "Send email successful");

            button.button('reset');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showNotification('danger', "Error sending announcement email!");
            // Reset button
            button.button('reset');
        }
    });
}

function setInvoiceTemplateMail(){
    var mailString = '';
    mailString += "Dear "+ $("#client_select option:selected").text()+",\n\n";
    mailString += 'Thanks for your business! '+    $('#invoice_com_name').text()
            +' enjoyed working with you and look forward to more future work.\n\n';
    mailString += 'The invoice INV-#'+$('#invoice_number').text()
            +' is attached with this email. You can pay online for this invoice.\n\n';
    mailString += $('#pay-link').val()+"\n\n";
    mailString += 'Here\'s an overview of the invoice for your reference.\nInvoice Overview:\n';
    mailString += 'Invoice # : #'+$('#invoice_number').text()+'\n';
    mailString += 'Date : '+ $('#invoice_date_sent').text()+"\n";
    mailString += 'Amount : $'+ $('#invoice_total_amount').text()+'\n\n\n';
    mailString += 'Regards,\n'+$('#invoice_com_name').text();
    
    $('#announce-email').val(mailString);
}

function resetInvoiceForm() {
    // Change the view to edit mode
    $('#invoice_id').val('');
    $('#invoice-form-title').text("CREATE AN INVOICE");
    $('#invoice-form-description').text("Fill in the form below to add an invoice.");

    // Remove all the amount-record but 1.
    var count = itemCount - 1;
    for (var i = 0; i < count; i++) {
        $(".item_record:last").remove();
        itemCount--;
    }
    $('td[name="item_amount"]').text('0.00');
    $('#invoice_total_amount').text('0.00');

    // Clear embed code
    $('#embed').val("");
    $('#pay-link').val('');
}

function setModeInvoiceEdit(data) {
    $('#invoice_id').val(data.id);
    $('#invoice_uid').val(data.uid);
    $('#invoice-form-title').text("EDIT THIS INVOICE");
    $('#invoice-form-description').text("You can now fill in the form below to edit this invoice.");

}

function updateInvoiceEmbed() {
    // Update the embed part
    var code = '<iframe width="560" height="1000" src="' + baseUrl + 'pay/index/' + $('#invoice_uid').val() + '" frameborder="0" allowstransperency="true"></iframe>';
    $('#embed').val(code);

    // Update the link
    var link = baseUrl + 'pay/index/' + $('#invoice_uid').val();
    $('#pay-link').val(link);
}

function calculateItemAmount(e) {

    var $row = $(e.closest('tr'));
    var $amountCell = $row.find('td[name="item_amount"]');
    var amount;

    var quantity = parseInt($row.find('input[name="invoice_quantity"]').val()) || 0;
    var rate = parseInt($row.find('input[name="invoice_rate"]').val()) || 0;
    var discount = parseInt($row.find('input[name="invoice_discount"]').val()) || 0;
    var discountType = $row.find('select[name="invoice_discount_type"]').val();

    if (discountType === '0') {
        discount = quantity * rate * discount / 100;
    }

    // Calculate the amount. Keep them with 2 decimal number
    amount = (Math.round((quantity * rate - discount) * 100) / 100).toFixed(2);

    // Set to view
    $amountCell.text(amount);

    // Recaculate the total amount:
    calculateTotalAmount();
}

function calculateTotalAmount() {
    var totalAmount = 0;
    $('td[name^=item_amount]').each(function () {
        totalAmount += parseFloat($(this).text());
    });
    totalAmount = totalAmount.toFixed(2);
    $('#invoice_total_amount').text(totalAmount);
    $('#invoice_total_due').text('$' + totalAmount);
}

//Open a dialog to confirm delete the member.
function callDeleteInvoice(invoiceId, element) {
    bootbox.confirm(
            "Are you sure you want to delete? The action cannot be undone!",
            function (result) {
                if (result) {
                    deleteInvoice(invoiceId, element);
                }
            }
    );
}

// And here we actually delete the member. :)
function deleteInvoice(invoiceId, element) {
    console.log("Deleting");

    // Delete process
    $.ajax({
        type: 'DELETE',
        url: baseUrl + "api/invoice/" + invoiceId,
        success: function (data, textStatus, jqXHR) {
            // Refresh table
            console.log(data);

            // Delete data from table
            var tr = $(element).closest('tr');
            tr.css("background-color", "#FF3700");
            tr.fadeOut(1000, function () {
                tr.remove();
            });
            return false;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            // Notify success
            showNotification('danger', "Error on deleting invoice!");
        }
    });
}

/*
 * Payment table
 */
function loadPaymentTable(userId) {

    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/payment/?user_id=" + userId,
        dataType: "json",
        success: function (data) {
            console.log(data);

            var orderTable = $('#payment_table');
            for (var i = 0; i < data.length; i++) {
                var row = $('<tr></tr>');
                var name = $('<td></td>').text(data[i].payer_name);
                var order = $('<td></td>').text(data[i].id);
                var invoice = $('<td></td>').text(data[i].title);
                var email = $('<td></td>').text(data[i].payer_email);

                var recurring;
                var period = parseInt(data[i].frequency);
                switch (period) {
                    case 1:
                        recurring = $('<td></td>').text("Once");
                        break;
                    case 7:
                        recurring = $('<td></td>').text("Weekly");
                        break;
                    case 30:
                        recurring = $('<td></td>').text("Monthly");
                        break;
                    case 3:
                        recurring = $('<td></td>').text("Every 3 months");
                        break;
                    case 6:
                        recurring = $('<td></td>').text("Every 6 months");
                        break;
                    case 12:
                        recurring = $('<td></td>').text("Yearly");
                        break;
                    default:
                        recurring = $('<td></td>').text("Once");
                        break;
                }

                var amount = $('<td></td>').text('$' + data[i].amount);

                row.append(name).append(order).append(invoice).append(email).append(recurring).append(amount);
                orderTable.append(row);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showNotification('danger', "Error on getting data!");
        }
    });
}
