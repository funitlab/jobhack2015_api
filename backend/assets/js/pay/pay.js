/* 
 * Code written by Nguyen Van Hung at @imrhung
 * Feel free to re-use or share it.
 * Happy code!!!
 */

// Constant
var DATE_FORMAT = 'DD MMM YYYY';
var DATE_SQL_FORMAT = 'YYYY-MM-DD';

var stripeResponseHandler = function (status, response) {
    var $form = $('#payment-form');

    if (response.error) {
        // Show the errors on the form
        $form.find('.payment-errors').text(response.error.message);
        $form.find('button').prop('disabled', false);
    } else {
        // token contains id, last4, and card type
        var token = response.id;
        // Insert the token into the form so it gets submitted to the server
        $form.append($('<input type="hidden" name="stripeToken" />').val(token));
        // and re-submit
        $form.get(0).submit();
    }
};

jQuery(function ($) {
    // This identifies your website in the createToken call below
    Stripe.setPublishableKey($('#publish-key').val());

    $('#payment-form').submit(function (e) {
        var $form = $(this);

        // Disable the submit button to prevent repeated clicks
        $form.find('button').prop('disabled', true);

        Stripe.card.createToken($form, stripeResponseHandler);

        // Prevent the form from submitting with the default action
        return false;
    });
    
    $('#credit').on('click', function(){
        $('#expand-credit').toggleClass('hide');
    });
    
    // Set the date sent and due.
    $('#invoice_date_sent').text(moment($('#invoice_date_sent').text(), DATE_SQL_FORMAT).format(DATE_FORMAT));
    $('#invoice_date_due').text(moment($('#invoice_date_due').text(), DATE_SQL_FORMAT).format(DATE_FORMAT));
});
