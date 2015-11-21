<div class="row main-content">

    <div id="logo-home">
        <a href="<?php echo base_url() ?>" id="base-url">
            <img id="logo_image" src="<?php echo base_url() ?>assets/img/invoice-logo.png" class="img-center">
        </a>
    </div>
    <br>

    <!--Announcement area-->
    <div class="container-fluid background-blue">
        <div class="row-fluid">
            <div class="col-md-6">
                <div class="pull-right spacious text-white magin-top-md">
                    <?php echo $announce_message ?>
                </div>
            </div>
            <div class="col-md-6">
                <div class="pull-left spacious">
                    <a href="<?php echo $announce_action ?>" role="button" class="btn btn-success btn-wide"><?php echo $announce_action_text ?></a>
                </div>
            </div>
        </div>
    </div>
    <br>

    <!--Summary stats-->
    <input type="hidden" id="user_id" value="<?php echo $user_id ?>">
    
    <div class="container-fluid" id="your-stat">
        <!--Header-->
        <h3 class="text-center"><b>YOUR STATS</b></h3>
        <!--Table-->
        <table class="table" id="stat-table">
            <tbody>
                <tr>
                    <td><span class="fa fa-heart"></span></td>
                    <td>Sale Volume:</td>
                    <td id="stat-volume"></td>
                </tr>
                <tr>
                    <td><span class="fa fa-dollar"></span></td>
                    <td>Total Number of Invoices:</td>
                    <td id="stat-invoice"></td>
                </tr>
                <tr>
                    <td><span class="fa fa-refresh"></span></td>
                    <td>Recurring Plans:</td>
                    <td id="stat-recurring"></td>
                </tr>
                <tr>
                    <td><span class="fa fa-group"></span></td>
                    <td>Number of Clients:</td>
                    <td id="stat-client"></td>
                </tr>
            </tbody>
        </table>
    </div>

    <!--Your company information--> 
    <hr>
    <div class="container-fluid" id="your-information">
        <!--Header-->
        <h3 class="text-center"><b>YOUR INFORMATION</b></h3>

        <form class="form-create" role="form" id="form_company" onsubmit="return false;">
            <fieldset>
                
                <!-- File Button --> 
                <div class="form-group">
                    <label class="" >Your Logo:</label>
                    <div class="row">
                        <div class="col-sm-4">
                            <img id="company_logo" class="img-responsive" src="<?php echo base_url() ?>assets/img/invoice-logo.png" alt="Your Icon">
                        </div>
                        <div class="col-sm-7 upload" id="drop_image">
                            <a href="#" class="btn btn-success btn-sm" role="button">Add Image</a>
                            <span style="color: #888; font-size: 10px;">or Drop here</span>
                            <input id="image" name="userfile" class="form-control" type="file">
                        </div>
                    </div>

                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label for="company_name">Company's Name:</label>  
                    <input id="company_name" name="company_name" type="text" placeholder="" class="form-control input-md">
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label for="company_url">Company's URL:</label>  
                    <input id="company_url" name="company_url" type="text" placeholder="" class="form-control input-md">
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label for="company_address">Company's Street Address</label>  
                    <input id="company_address" name="company_address" type="text" placeholder="" class="form-control input-md">
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <div class="row">
                        <div class="col-sm-7">
                            <label for="company_city">City</label>  
                            <input id="company_city" name="company_city" type="text" placeholder="" class="form-control input-md">
                        </div>
                        <div class="col-sm-5">
                            <label for="company_state">State</label>  
                            <input id="company_state" name="company_state" type="text" placeholder="" class="form-control input-md">     
                        </div>
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <div class="row">
                        <div class="col-sm-5">
                            <label for="company_zip">Zip Code</label>  
                            <input id="company_zip" name="company_zip" type="text" placeholder="" class="form-control input-md">
                        </div>
                        <div class="col-sm-7">
                            <label for="company_country">Country</label>  
                            <input id="company_country" name="company_country" type="text" placeholder="" class="form-control input-md">     
                        </div>
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <div class="row">
                        <div class="col-sm-6">
                            <label for="company_phone">Phone Number</label>  
                            <input id="company_phone" name="company_phone" type="text" placeholder="" class="form-control input-md">
                        </div>
                        <div class="col-sm-6">
                            <label for="company_fax">Fax Number</label>  
                            <input id="company_fax" name="company_fax" type="text" placeholder="" class="form-control input-md">     
                        </div>
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label for="company_contact">Primary Contact:</label>  
                    <input id="company_contact" name="company_contact" type="text" placeholder="" class="form-control input-md">
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label for="company_currency">Currency:</label>  
                    <select id="company_currency" name="company_currency" class="form-control">
                        <option value="USD">U.S. Dollar</option>
                        <option value="AUD">Australian Dollar</option>
                        <option value="BRL">Brazilian Real </option>
                        <option value="CAD">Canadian Dollar</option>
                        <option value="CZK">Czech Koruna</option>
                        <option value="DKK">Danish Krone</option>
                        <option value="EUR">Euro</option>
                        <option value="HKD">Hong Kong Dollar</option>
                        <option value="HUF">Hungarian Forint </option>
                        <option value="ILS">Israeli New Sheqel</option>
                        <option value="JPY">Japanese Yen</option>
                        <option value="MYR">Malaysian Ringgit</option>
                        <option value="MXN">Mexican Peso</option>
                        <option value="NOK">Norwegian Krone</option>
                        <option value="NZD">New Zealand Dollar</option>
                        <option value="PHP">Philippine Peso</option>
                        <option value="PLN">Polish Zloty</option>
                        <option value="GBP">Pound Sterling</option>
                        <option value="SGD">Singapore Dollar</option>
                        <option value="SEK">Swedish Krona</option>
                        <option value="CHF">Swiss Franc</option>
                        <option value="TWD">Taiwan New Dollar</option>
                        <option value="THB">Thai Baht</option>
                        <option value="TRY">Turkish Lira</option>
                    </select>
                </div>

                <!-- Button -->
                <div class="form-group">
                    <button id="company_save" name="company_save" class="btn btn-success btn-block">Save</button>

                </div>

            </fieldset>
        </form>
    </div>

    <!--Your clients-->
    <!--Create new client-->
    <hr>
    <div class="container-fluid" id="clients">
        <!--Header-->
        <h3 class="text-center"><b>YOUR CLIENTS</b></h3>

        <form class="form-create" role="form" id="form_client" onsubmit="return false;">
            <fieldset>
                <input type="hidden" id="client_id">
                <!-- Text input-->
                <div class="form-group">
                    <label for="client_name">Company's Name:</label>  
                    <input id="client_name" name="client_name" type="text" placeholder="" class="form-control input-md" required="">
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label for="client_email">Company's Email:</label>  
                    <input id="client_email" name="client_email" type="text" placeholder="" class="form-control input-md" required="">
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label for="client_address">Company's Street Address</label>  
                    <input id="client_address" name="client_address" type="text" placeholder="" class="form-control input-md">
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <div class="row">
                        <div class="col-sm-7">
                            <label for="client_city">City</label>  
                            <input id="client_city" name="client_city" type="text" placeholder="" class="form-control input-md">
                        </div>
                        <div class="col-sm-5">
                            <label for="client_state">State</label>  
                            <input id="client_state" name="client_state" type="text" placeholder="" class="form-control input-md">     
                        </div>
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <div class="row">
                        <div class="col-sm-5">
                            <label for="client_zip">Zip Code</label>  
                            <input id="client_zip" name="client_zip" type="text" placeholder="" class="form-control input-md">
                        </div>
                        <div class="col-sm-7">
                            <label for="client_country">Country</label>  
                            <input id="client_country" name="client_country" type="text" placeholder="" class="form-control input-md">     
                        </div>
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <div class="row">
                        <div class="col-sm-6">
                            <label for="client_phone">Phone Number</label>  
                            <input id="client_phone" name="client_phone" type="text" placeholder="" class="form-control input-md">
                        </div>
                        <div class="col-sm-6">
                            <label for="client_fax">Fax Number</label>  
                            <input id="client_fax" name="client_fax" type="text" placeholder="" class="form-control input-md">     
                        </div>
                    </div>
                </div>

                <!-- Button -->
                <div class="form-group row-fluid">
                    <button type="submit" id="client_save" name="client_save" class="btn btn-success col-sm-5">Add Client</button>
                    <button type="reset" id="client_clear" name="client_clear" class="btn btn-default col-sm-5 col-sm-offset-2">Clear form</button>
                </div>

            </fieldset>
        </form>

        <!--Clients Table-->
        <table class="table table-striped table-hover" id="client_table">
            <thead>
                <tr>
                    <th>Company's Name</th>
                    <th>Email</th>
                    <th>Phone #</th>
                    <th>Received</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>

    <!--Your invoices listing-->
    <hr>
    <div class="container-fluid" id="your_invoice">
        <!--Header-->
        <h3 class="text-center"><b>YOUR INVOICES</b></h3>
        <!--Table-->
        <table class="table table-striped table-hover" id="invoice_table">
            <tbody>
            </tbody>
        </table>
    </div>

    <!--Create an Invoice form-->
    <hr>
    <div class="container-fluid" id="create_invoice">
        <h3 class="text-center" id="invoice-form-title"><b>CREATE AN INVOICE</b></h3>
        <p class="text-center" id="invoice-form-description">Fill in the form below to add an invoice.</p>

        
            <form class="form-large" role="form" id="form_invoice" onsubmit="return false;">
                <fieldset>
                    <!--Id for the invoice-->
                    <input type="hidden" id="invoice_id">
                    <input type="hidden" id="invoice_uid">

                    <div class="invoice-printable">
                    
                    <!--Company name-->
                    <div class="row">
                        <div class="col-md-2">
                            <img id="invoice_com_logo" class="img-responsive" src="">
                        </div>
                        <div class="col-md-4">
                            <span id="invoice_com_name"></span><br>
                            <span id="invoice_com_address"></span><br>
                            <span id="invoice_com_city"></span><br>
                            <span>Tel: </span><span id="invoice_com_phone"></span>
                        </div>
                        <div class="col-md-3 pull-right text-right">
                            <span>Invoice #</span><span id="invoice_number">0</span><br><br>
                            <span>Total Due:</span><br>
                            <span id="invoice_total_due">$0.00</span>
                        </div>
                    </div>

                    <!--Bill to-->
                    <div class="row margin-top-20">
                        <div class="col-md-2 text-right" id="invoice_bill_to">
                            BILL TO:
                        </div>
                        <div class="col-md-4">
                            <select id="client_select" name="client_select" class="form-control">
                            </select>
                        </div>

                        <!--Date-->
                        <div class="col-md-3 pull-right text-right">
                            <span>Date sent: </span><span id="invoice_date_sent"></span><br>
                            <span>Date due: </span><span id="invoice_date_due"></span>
                        </div>
                    </div>
                    
                    <!--Title-->
                    <div class="form-group margin-top-10">
                        <div class="row">
                            <label class="control-label col-md-2 text-right" for="invoice_frequency">Title</label>
                            <div class="col-md-4">
                                <input id="invoice_title" type="text" placeholder="" class="form-control input-md" required="">
                            </div>
                        </div>
                    </div>

                    <!--Frequency-->
                    <!-- Multiple Radios -->
                    <div class="form-group margin-top-10">
                        <div class="row">
                            <label class="control-label col-md-2" for="invoice_frequency">Frequency of Payment</label>
                            <div class="col-md-4">
                                <label for="frequency-0" class="radio-inline">
                                    <input type="radio" name="invoice_frequency" id="frequency-0" value="1" checked="checked">
                                    One time
                                </label>
                                <label for="frequency-1" class="radio-inline">
                                    <input type="radio" name="invoice_frequency" id="frequency-1" value="2">
                                    Recurring
                                </label>
                            </div>
                        </div>
                    </div>
                    <!-- Select Basic -->
                    <div class="form-group row" id="frequency-area" style="display: none;">
                        <label class="control-label col-sm-2" for="invoice_frequency_period">Recurring Period</label>
                        <div class="col-sm-4">
                            <select id="invoice_frequency_period" name="frequency" class="form-control">
                                <option value="0">Select the period</option>
                                <option value="7">Weekly</option>
                                <option value="30">Monthly</option>
                                <option value="3">3 months</option>
                                <option value="6">6 months</option>
                                <option value="12">Yearly</option>
                            </select>
                        </div>
                    </div>

                    <!--Items and Description Table-->
                    <table class="table table-striped table-hover" id="item_table">
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
                            <tr class="row item_record">
                                <td class="col-sm-3"><input name="invoice_item" type="text" placeholder="" class="form-control input-md" required=""></td>
                                <td class="col-sm-2"><input name="invoice_quantity" type="number" placeholder="" class="form-control input-sm" required="" onchange="calculateItemAmount(this)"></td>
                                <td class="col-sm-2"><input name="invoice_rate" type="number" placeholder="" class="form-control input-sm" required="" onchange="calculateItemAmount(this)"></td>
                                <td class="col-sm-2">
                                    <div class="input-group">
                                        <input name="invoice_discount" type="number" class="form-control" placeholder="" aria-describedby="discount_type_span" onchange="calculateItemAmount(this)">
                                        <span class="input-group-addon" id="discount_type_span">
                                            <select class="form-control" name="invoice_discount_type" onchange="calculateItemAmount(this)">
                                                <option value="1">$</option>
                                                <option value="0">%</option>
                                            </select>
                                        </span>
                                    </div>
                                </td>
                                <td name="item_amount" class="col-sm-2 text-right item_amount">0</td>
                                <td class="col-sm-1">
                                    <span class="fa fa-times remove-invoice-item"></span>
                                </td>
                            </tr>

                        </tbody>
                    </table>
                    <div class="row-fluid item_add">
                        <a href="#" id="add_another_item" >+ Add another amount</a>
                    </div>
                    <div class="row-fluid margin-top-10">
                        <div class="col-md-6 pull-right background-dark-grey" id="invoice_total">
                            <div class="col-md-6">
                                <b>TOTAL (S)</b>
                            </div>
                            <div class="col-md-6 text-right">
                                <span id="invoice_total_amount">0.00</span>
                            </div>
                        </div>
                    </div>
                    <hr>

                    <!-- Confirmation -->
                    <!-- Textarea -->
                    <div class="form-group">
                        <label class="control-label" for="announce-email">
                            Notes To Customer in the Email 
                            <button type="button" id="invoice_template" name="invoice_template" class="btn btn-default btn-sm">Use template</button>
                        </label>
                        <div class="">                     
                            <textarea class="form-control" id="announce-email" name="announce-email" rows="10"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="confirmation">After your client pays, show confirmation message (Also send by email)</label>
                        <div class="">                     
                            <textarea class="form-control" id="confirmation" name="confirmation"></textarea>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label" for="invoice-notes">Notes when payment</label>
                        <div class="">                     
                            <textarea class="form-control" id="invoice-notes" name="invoice-notes"></textarea>
                        </div>
                    </div>
                    
                    </div>

                    <div class="form-large">
                        <!-- Embedded code -->
                        <div class="form-group">
                            <label class="control-label" for="embedded">Embedded Code for your website</label>
                            <div class="">                     
                                <input id="embed" type="text" class="form-control input-md" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="pay-link">Payment link</label>
                            <div class="">                     
                                <input id="pay-link" type="text" class="form-control input-md" readonly="readonly">
                            </div>
                        </div>

                        <!-- Button  -->
                        <div class="form-group ">
                            <label class="control-label" for="invoice_save"></label>
                            <div class="center-block" id="invoice_control">
                                <button type="submit" id="invoice_save" name="save" class="btn btn-success">Publish &amp; Send</button>
                                <button type="button" id="invoice_draft" name="draft" class="btn btn-default">Save as Draft</button>
                                <!--Reset button-->
                                <button type="reset" id="invoice_reset" name="reset" class="btn btn-default">Clear form</button>

                            </div>
                        </div>
                    </div>

                </fieldset>
            </form>
        </div>

        <!--Your payment-->
        <hr>
        <div class="container-fluid" id="your_payment">
            <!--Header-->
            <h3 class="text-center"><b>YOUR PAYMENTS</b></h3>
            <!--Table-->
            <table class="table table-striped table-hover" id="payment_table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Payment #</th>
                        <th>Invoice</th>
                        <th>Email</th>
                        <th>Recurring</th>
                        <th>Total amount</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>

    </div> <!-- end of main content -->
