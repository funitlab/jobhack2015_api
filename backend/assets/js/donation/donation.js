/* 
 * Code written by Nguyen Van Hung at @imrhung
 * Feel free to re-use or share it.
 * Happy code!!!
 */

// Global variable
var amountCount;

// Run after document ready.
$(function () {
    
    // We will check form submit only on Publish. For draft, we save without checking form
    // On form submit.
    $('#form-donation').submit(function () {
        submitDonation(1);
        updateEmbed();
    });
    // On save as draft.
    $('#draft').on('click', function(){
        submitDonation(0);
        updateEmbed();
    });
    
    /*
     * Handle dynamic field of amount data.
     */
    amountCount = $('#amounts-fields > div.amount-record').size();
    $('#addNew').on('click', function () {
        $(".amount-record:last").clone()
                          .find("input").val("").end()
                          .insertAfter('.amount-record:last');
        amountCount++;
        return false;
    });
    $(document).on('click', '.removeNew', function () {
        
        if (amountCount > 1) {
            $(this).parents('div.amount-record').remove();
            amountCount--;
        } else {
            // If the only one left, just clear it.
            $(this).parents('div.amount-record').find("input").val('');
        }
        return false;
    });
    
    // Reset button
    $("#reset").click(function () {
        resetDonationForm();
    });
    
    // Has decription radio button handle.
    $("input[name='radio-description']").on('change', function(){
        if ($(this).val() == 0){
            $("#description-area").css("display", "none");
        } else {
            $("#description-area").css("display", "block");
        }
    });
    // Has amount radio button handle.
    $("input[name='radio-amount']").on('change', function(){
        if ($(this).val() == 0){
            $("#amounts-fields").css("display", "none");
            $(".amount-record").find("input[name='amount[]']").removeAttr('required');
        } else {
            $("#amounts-fields").css("display", "block");
            $(".amount-record").find("input[name='amount[]']").attr('required', true);
        }
    });
    // Recurring selection
    $("input[name='frequency']").on('change', function(){
        // Recurring
        if ($(this).val() == 2){
            $("#frequency-area").css("display", "block");
        } else {
            $("#frequency-area").css("display", "none");
        }
    });
    
    loadStatTable();
    loadDonationTable();
    loadOrderTable();
});

function submitDonation(publish){
    var baseUrl = $("#base-url").attr("href");
    
    // Loading button
    var button = $("#save");
    button.button('Loading...');
    
    // Get data
    var userId = $('#user-id').val();
    var title = $('#title').val();
    var hasDescription = $("input[name='radio-description']:checked", '#form-donation').val();
    var description = $('#description').val();
    var decideAmount = $("input[name='radio-amount']:checked", '#form-donation').val();
    // Determin the frequency
    var frequency = $("input[name='frequency']:checked", '#form-donation').val();
    var period = $('#frequency').val();
    if (frequency == 2){
        frequency = period;
    }
    
    var confirmation = $('#confirmation').val();
    var confirmationEmail = $('#confirmation-email').val();
    
    // Get amount data
    var amounts = [];
    var description_amounts = [];
    $('input[name^=description-amount]').each(function(){
        description_amounts.push($(this).val());
    });
    $('input[name^=amount]').each(function(){
        amounts.push($(this).val());
    });
    var amountList = [];
    for (var i=0; i<amounts.length; i++){
        var amount = new Object();
        amount.amount = amounts[i];
        amount.description = description_amounts[i];
        amountList.push(amount);
    }
    var amountsJSON = (JSON.stringify(amountList));
    
    // TODO : validate data: has description...
    
    // Determine POST or PUT
    var type, url;
    if ($('#donation-id').val()) {
        // Edit
        type = 'PUT';
        url = baseUrl + 'api/donation/id/' + $('#donation-id').val();
    } else {
        // Create a new
        type = 'POST';
        url = baseUrl + 'api/donation';
    }
    
    // Post to api
    $.ajax({
        type: type,
        url: url,
        dataType: "json",
        data: {
            user_id: userId,
            title: title,
            has_description: hasDescription,
            description: description,
            amount_decide: decideAmount,
            frequency: frequency,
            confirmation_message: confirmation,
            confirmation_email: confirmationEmail,
            public_state: publish,
            amounts: amountsJSON
        },
        success: function (data, textStatus, jqXHR) {
            console.log(data);
            
            // Add donation to table or make change to the table. Maybe just need to re-draw the table.
            // TODO
            loadDonationTable();

            // Notify success
            showNotification('success', "Successful!");

            button.button('reset');
            
            // Change the view to "Edit" mode
            $('#donation-id').val(data.id);
            $('#save').html("Update & Publish");
            $('#donation-form-title').text("UPDATE THIS DONATION");
            $('#donation-form-description').text("Change this as you want");
            
            // Update the embed code:
            updateEmbed();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showNotification('danger', "Error on saving data!");
            // Reset button
            button.button('reset');
        }
    });
}

function updateEmbed(){
    
    var baseUrl = $("#base-url").attr("href");
    
    // Update the embed part
    var code = '<iframe width="560" height="1000" src="'+baseUrl+'pay/'+$('#donation-id').val()+'" frameborder="0" allowstransperency="true"></iframe>';
    $('#embed').val(code);
    
    // Update the link
    var link = baseUrl + 'pay/' + $('#donation-id').val();
    $('#pay-link').val(link);
}

function loadDonation(id) {
    var baseUrl = $("#base-url").attr("href");

    // Clear all old data before load to the view:
    resetDonationForm();
    
    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/donation/id/" + id+"/?user_id="+$('#user-id').val(),
        dataType: "json",
        success: function (data) {
            console.log(data);
            
            $('#donation-id').val(data.id);
            $('#title').val(data.title);
            
            // Set the description state:
            var radioDes = $('input:radio[name=radio-description]');
            switch (parseInt(data.has_description)) {
                case 0:
                    radioDes.filter('[value=0]').prop('checked', true);
                    break;
                case 1:
                    radioDes.filter('[value=1]').prop('checked', true);
                    
                    // Description area:
                    $("#description-area").css("display", "block");
                    $('#description').val(data.description);
                    break;
                default:
                    radioDes.filter('[value=0]').prop('checked', true);
            }
            
            
            
            // Set the Amount decide state:
            var radioAmount = $('input:radio[name=radio-amount]');
            switch (parseInt(data.amount_decide)) {
                case 0:
                    radioAmount.filter('[value=0]').prop('checked', true);
                    break;
                case 1:
                    radioAmount.filter('[value=1]').prop('checked', true);
                    
                    // Amount area:
                    $("#amounts-fields").css("display", "block");
                    $(".amount-record").find("input[name='amount[]']").attr('required', true);
                    // Set the amounts
                    for (var i=0; i< data.amounts.length; i++){
                        if (i === 0){
                            // First amount
                            $('.amount-record:first').find('input[name^=amount]').val(data.amounts[0].amount);
                            $('.amount-record:first').find('input:text[name^=description-amount]').val(data.amounts[0].description);
                        } else {
                            // Add more amount
                            $(".amount-record:last").clone()
                                  .find("input").val("").end()
                                  .insertAfter('.amount-record:last');
                            amountCount++;
                            $('.amount-record:last').find('input[name^=amount]').val(data.amounts[i].amount);
                            $('.amount-record:last').find('input:text[name^=description-amount]').val(data.amounts[i].description);
                        }
                    }
                    break;
                default:
                    radioAmount.filter('[value=0]').prop('checked', true);
            }
            
            
            // Set the Frequency of payment
            var radioFrequency = $('input:radio[name=frequency]');
            var frequency = parseInt(data.frequency);
            if (frequency > 2){
                // Recurring area:
                $("#frequency-area").css("display", "block");
                // Recurring payment:
                radioFrequency.filter('[value=2]').prop('checked', true);
                $('#frequency').val(frequency);
            } else {
                // One time or decide later.
                if (frequency === 1){
                    radioFrequency.filter('[value=1]').prop('checked', true);
                } else {
                    radioFrequency.filter('[value=0]').prop('checked', true);
                }
                // Disable Recurring period.
                // $('#frequency').attr('disabled', 'disabled');
            }
            
            // Set the confirmation message
            $('#confirmation').val(data.confirmation_message);
            $('#confirmation-email').val(data.confirmation_email);

            // Change the view to edit mode
            $('#save').html("Update & Publish");
            $('#donation-form-title').text("UPDATE THIS DONATION");
            $('#donation-form-description').text("Change this as you want");
            
            // Update the embed code:
            updateEmbed();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showAlert('danger', "Error on getting data!");
        }
    });
}

function loadDonationTable(){
    var baseUrl = $("#base-url").attr("href");

    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/donation/?user_id=" + $('#user-id').val(),
        dataType: "json",
        success: function (data) {
            console.log(data);
            
            var donationTable = $('#donation-table');
            
            // Clear the table first.
            $('#donation-table tr').remove();
            
            for (var i = 0; i < data.length; i++) {
                var row = $('<tr></tr>');
                var state ;
                if (data[i].public_state == 1){
                    state = $('<td><span class="label label-success">LIVE</span></td>');
                } else {
                    state = $('<td><span class="label label-default">DRAFT</span></td>')
                }
                var title = $('<td></td>').text(data[i].title);
                var timestamp = moment(data[i].created_date);
                var date = $('<td class="text-small"></td>').text(moment().calendar(timestamp));
                var action = '<td class="cell-action"><a href="#create-donation" onclick="loadDonation('+data[i].id+');"><span class="label label-success">EDIT</span></a><a href="#" onclick="callDelete('+data[i].id+', this);return false;"><p class="text-small">DELETE</p></a></td>';
                row.append(state).append(title).append(date).append(action);
                donationTable.append(row);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showAlert('danger', "Error on getting data!");
        }
    });
}

function loadOrderTable(){
    var baseUrl = $("#base-url").attr("href");

    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/order/?user_id=" + $('#user-id').val(),
        dataType: "json",
        success: function (data) {
            console.log(data);
            
            var orderTable = $('#order-table');
            for (var i = 0; i < data.length; i++) {
                var row = $('<tr></tr>');
                var name = $('<td></td>').text(data[i].client_name);
                var order = $('<td></td>').text(data[i].id);
                var donate = $('<td></td>').text(data[i].title);
                var email = $('<td></td>').text(data[i].client_email);
                
                var recurring;
                var period = parseInt(data[i].frequency);
                switch (period){
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
                
                var amount = $('<td></td>').text(data[i].amount);
                
                row.append(name).append(order).append(donate).append(email).append(recurring).append(amount);
                orderTable.append(row);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showAlert('danger', "Error on getting data!");
        }
    });
}

function loadStatTable(){
    var baseUrl = $("#base-url").attr("href");

    // Get data
    $.ajax({
        type: 'GET',
        url: baseUrl + "api/order/?q=summary&user_id=" + $('#user-id').val(),
        dataType: "json",
        success: function (data) {
            console.log(data);
            $('#stat-volume').text('$'+data.volume);
            $('#stat-payment').text(data.payment);
            $('#stat-recurring').text(data.recurring);
            $('#stat-donor').text(data.donor);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showAlert('danger', "Error on getting data!");
        }
    });
}

function resetDonationForm(){
    // Change the view to edit mode
    $('#donation-id').val('');
    $('#save').html("Save & Publish");
    $('#donation-form-title').text("CREATE DONATION");
    $('#donation-form-description').text("Fill in the form below to add a donation.");
    
    // Remove all the amount-record but 1.
    var count= amountCount-1;
    for (var i=0; i<count; i++){
        $(".amount-record:last").remove();
        amountCount--;
    }
    
    // Clear embed code
    $('#embed').val("");
}

//Open a dialog to confirm delete the member.
function callDelete(donationId, element) {
    bootbox.confirm(
            "Are you sure you want to delete? The action cannot be undone!",
            function (result) {
                if (result) {
                    deleteDonation(donationId, element);
                }
            }
    );
}

// And here we actually delete the member. :)
function deleteDonation(donationId, element) {
    var baseUrl = $("#base-url").attr("href");
    console.log("Deleting");

    // Delete process
    $.ajax({
        type: 'DELETE',
        url: baseUrl + "api/donation/id/" + donationId,
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
            showAlert('danger', "Error on deleting data!");
        }
    });
}