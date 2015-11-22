<div class="content" id="js-content">
    <!--Company name-->
    <div class="invoice-pay-printable">
        <h1 class="text-center">Invoice | <?php echo $invoice['title'] ?></h1><br>
        <div class="row">
            <div class="col-md-2">
                <img id="invoice_com_logo" class="img-responsive" 
                     src="<?php echo $company['logo']; ?>">
            </div>
            <div class="col-md-5">
                <span id="invoice_com_name"><?php echo $company['name']; ?></span><br>
                <span id="invoice_com_address"><?php echo $company['street_address']; ?></span><br>
                <span id="invoice_com_city"><?php echo $company['city'] . ', ' . $company['state'] . ', ' . $company['country']; ?></span><br>
                <span>Tel: </span><span id="invoice_com_phone"><?php echo $company['phone_number']; ?></span>
            </div>
            <div class="col-md-4 pull-right text-right">
                <span>Invoice #</span><span id="invoice_number"><?php echo $invoice['id']; ?></span><br>
                <span>Total Due:</span><br>
                <span id="invoice_total_due"><b><?php echo '$' . $total; ?></b></span>
                <br><br>
                <span>Date sent: </span><span id="invoice_date_sent"><?php echo $invoice['date_send']; ?></span><br>
                <span>Date due: </span><span id="invoice_date_due"><?php echo $invoice['date_due']; ?></span>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-sm-4">
                BILL TO: 
            </div>
            <div class="col-sm-8">
                <b><?php echo $client['name']; ?></b>
            </div>
        </div>
        <div class="brand-logo"></div>

        <h3><?php echo $invoice['title'] ?></h3>

        <table class="table table-striped table-hover" id="item_pay_table">
            <thead>
                <tr class="row">
                    <th class="col-sm-4">Item and Description</th>
                    <th class="col-sm-2">Quantity($)</th>
                    <th class="col-sm-2">Rate</th>
                    <th class="col-sm-2">Discount</th>
                    <th class="col-sm-2">Amount</th>
                </tr>
            </thead>
            <tbody>
                <?php
                foreach ($item as $row) {
                    echo "<tr>";
                    echo "<td></td>";
                    echo "<td>" . $row['description'] . "</td>";
                    echo "<td>" . $row['quantity'] . "</td>";
                    echo "<td>" . $row['rate'] . "</td>";
                    echo "<td>" . ($row['quantity'] * $row['rate'] - $row['total']) . "</td>";
                    echo "<td>$" . $row['total'] . "</td>";
                    echo '</tr>';
                }
                ?>
            </tbody>
        </table>
        <div class="row-fluid margin-top-10">
            <div class="col-md-6 pull-right background-dark-grey" id="invoice_total">
                <div class="col-md-6">
                    <b>TOTAL (S)</b>
                </div>
                <div class="col-md-6 text-right">
                    <span id="invoice_total_amount">$ <?php echo $total; ?></span>
                </div>
            </div>
        </div>
        <hr>
        <div class="row-fluid margin-top-10">
            Notes: <?php echo $invoice['notes']; ?>
        </div>
    </div>

    <!--        <form action="" method="POST" id="payment-form">-->
    <form method="POST" id="payment-form" onsubmit="return false;" class="margin-top-20">
        <!-- Form Name -->
        <legend>Your payment information here:</legend>

        <input id="publish-key" type="hidden" value="<?php echo $publish_key ?>">

        <span class="payment-errors"></span>

        <!--Name-->
        <div class="form-group">
            <label for="name">Your name</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="">
        </div>

        <!--Email-->
        <div class="form-group">
            <label for="email">Your email address</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="" required="required">
        </div>

        <!--Frequency-->
        <?php if ($invoice['frequency'] == 1) : ?>
            <!--One time payment. Show nothing-->

        <?php elseif ($invoice['frequency'] == 0) : ?>
            <!--Client choose the frequency-->
            <div class="form-group">
                <label for="frequency">Frequency</label>
                <select id="frequency" name="frequency" class="form-control">
                    <option value="1">Once</option>
                    <option value="7">Weekly</option>
                    <option value="30">Monthly</option>
                    <option value="3">3 months</option>
                    <option value="6">6 months</option>
                    <option value="12">Yearly</option>
                </select>
            </div>

        <?php else : ?>
            <!--Frequency is set. Just show to user.-->
            <div class="form-group">
                <label for="frequency">Frequency</label>
                <select id="frequency" name="frequency" class="form-control">
                    <option value="1">Once</option>
                    <option value="7">Weekly</option>
                    <option value="30">Monthly</option>
                    <option value="3">3 months</option>
                    <option value="6">6 months</option>
                    <option value="12">Yearly</option>
                </select>
            </div>
            <script>
                $('#frequency').val(<?php echo $invoice['frequency']; ?>)
                        .prop('disabled', 'disabled');
            </script>
        <?php endif; ?>



        <div class="form-group">
            <label>
                <span>Card Number</span>
            </label>
            <input type="text" size="20" data-stripe="number" class="form-control input-sm"/>

        </div>

        <div class="form-group">
            <div class="row">
                <div class="col-xs-4">
                    <label>
                        <span>CVC</span>
                    </label>
                    <input type="text" size="4" data-stripe="cvc" class="form-control input-sm"/>
                </div>
                <div class="col-xs-2"></div>
                <div class="col-xs-6">
                    <label>
                        <span>Expiration (MM/YYYY)</span>
                    </label>
                    <div class="row">
                        <div class="col-xs-6">
                            <input type="text" size="2" data-stripe="exp-month" class="form-control input-sm"/>
                        </div>
                        <div class="col-xs-6">
                            <input type="text" size="4" data-stripe="exp-year" class="form-control input-sm"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-6 col-xs-offset-3">
                <button type="submit" class="btn btn-primary btn-block">Submit Payment</button>
            </div>
        </div>
    </form>
    <div class="well well-sm text-center" id="credit">
        <span class="text-center text-header"><span class="fa fa-lock text-20">  </span> This page is securely encrypted</span>
        <div class="help-tooltip hide text-left text-small" id="expand-credit">
            <hr>
            <p>This page is hosted by <a href="https://alignlab.com/">Align Lab, LLC</a>. We using <a href="https://stripe.com/">Stripe</a> to safely process card transaction and Global Sign Alpha SSL to encrypt your connection.</p>
            <p>Your connection is encrypted with 128-bit encryption. The connection uses TLS 1.2. The connection is encrypted and authenticated using AES_128_GCM and uses ECDHE_RSA as the key exchange mechanism.</p>
            <p>The identity of this website has been verified by AlphaSSL CA - SHA256 - G2.</p>
            <p>Invoice by <a href="https://alignlab.com/">Align Lab, LLC</a> and <a href="https://stripe.com/">Stripe</a> provide full SSL encryption and all PCI compliance. All card numbers are fully encrypted on disk with AES-256.</p>
            <p>PCI compliance is certified to PCI Service Provider Level 1, the most stringent level of certification available. All browsers interact with AlignLab and Stripe over HTTPS.</p>

            <div class="container-fluid">
                <div class="row-fluid margin-bottom-5">
                    <div class="col-xs-3" id="powered-by">Powered by: </div>
                    <div class="col-xs-3"><a href="https://alignlab.com/"><img class="img-responsive" src="<?php echo base_url(); ?>/assets/img/alignlab-logo.png" style="width: 70%"></a></div>
                    <div class="col-xs-3"><a href="https://stripe.com/"><img class="img-responsive" src="<?php echo base_url(); ?>/assets/img/stripe-logo.png"></a></div>
                    <!--<div class="col-xs-3"><a href="http://alphassl.com/"><img class="img-responsive" src="<?php echo base_url(); ?>/assets/img/alphaSSL.png"></a></div>-->
                    <div class="col-xs-3 padding-left-0">
                        <!--- Secure Site Seal - DO NOT EDIT --->
                        <span id="ss_img_wrapper_115-55_image_en"><a href="http://www.alphassl.com/ssl-certificates/wildcard-ssl.html" target="_blank" title="SSL Certificates"><img alt="Wildcard SSL Certificates" border=0 id="ss_img" src="//seal.alphassl.com/SiteSeal/images/alpha_noscript_115-55_en.gif" title="SSL Certificate"></a></span><script type="text/javascript" src="//seal.alphassl.com/SiteSeal/alpha_image_115-55_en.js"></script>
                        <!--- Secure Site Seal - DO NOT EDIT --->
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="col-xs-3" id="card-accept">This site accepts: </div>
                    <div class="col-xs-9"><img class="img-responsive" src="<?php echo base_url(); ?>/assets/img/credit-card.png"></div>
                </div>
            </div>
        </div>
    </div>
</div>
