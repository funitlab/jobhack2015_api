<div class="row main-content">

    <div id="logo-home">
        <a href="<?php echo base_url() ?>" id="base-url">
            <img src="<?php echo base_url() ?>assets/img/logo-home.png" class="img-center">
        </a>
    </div>
    <br>

    <!--Announcement area-->
    <div class="container-fluid background-orange">
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
    <input type="hidden" id="user-id" value="<?php echo $user_id ?>">
    <div class="container-fluid" id="your-stat">
        <!--Header-->
        <h3 class="text-center"><b>YOUR STATS</b></h3>
        <!--Table-->
        <table class="table" id="stat-table">
            <tbody>
                <tr>
                    <td><span class="fa fa-heart"></span></td>
                    <td>Donation Volume:</td>
                    <td id="stat-volume"></td>
                </tr>
                <tr>
                    <td><span class="fa fa-dollar"></span></td>
                    <td>Total Number of Payments:</td>
                    <td id="stat-payment"></td>
                </tr>
                <tr>
                    <td><span class="fa fa-refresh"></span></td>
                    <td>Recurring Plans:</td>
                    <td id="stat-recurring"></td>
                </tr>
                <tr>
                    <td><span class="fa fa-group"></span></td>
                    <td>Number of Donors:</td>
                    <td id="stat-donor"></td>
                </tr>
            </tbody>
        </table>
    </div>


    <!--Your donation listing-->
    <hr>
    <div class="container-fluid" id="your-donation">
        <!--Header-->
        <h3 class="text-center"><b>YOUR DONATION</b></h3>
        <!--Table-->
        <table class="table table-striped table-hover" id="donation-table">
            <tbody>
            </tbody>
        </table>
    </div>

    <!--Create a Donation form-->
    <hr>
    <div class="container-fluid" id="create-donation">

        <form class="form-create" role="form" id="form-donation" onsubmit="return false;">

            <h3 id="donation-form-title"><b>CREATE DONATION</b></h3>
            <p id="donation-form-description">Fill in the form below to add a donation.</p>


            <fieldset>

                <!--Reset button-->
                <div class="text-right" >
                    <button type="reset" id="reset" name="reset" class="btn btn-default btn-xs">Reset</button>
                </div>

                <!--Id for the donation-->
                <input type="hidden" id="donation-id">

                <!-- Text input-->
                <div class="form-group">
                    <label class="control-label" for="title">Add Title:</label>  

                    <input id="title" name="title" type="text" placeholder="" class="form-control input-md" required="">

                </div>

                <!-- Multiple Radios (inline) -->
                <div class="form-group">
                    <label class=" control-label" for="radio-description">Add Description</label>
                    <div class="">  
                        <label class="radio-inline" for="radio-description-1">
                            <input type="radio" name="radio-description" id="radio-description-1" value="0" checked="checked">
                            No
                        </label>
                        <label class="radio-inline" for="radio-description-0">
                            <input type="radio" name="radio-description" id="radio-description-0" value="1">
                            Yes
                        </label>
                    </div>
                </div>

                <!-- Text area -->
                <div class="form-group" id="description-area" style="display: none;">
                    <label class=" control-label" for="description">Type a Description</label>
                    <div class="">                     
                        <textarea class="form-control" id="description" name="description"></textarea>
                    </div>
                </div>

                <!-- Multiple Radios -->
                <div class="form-group">
                    <label class="control-label" for="radio-amount">How is the amount decided?</label>
                    <div class="">
                        <div class="radio">
                            <label for="radio-amount-1">
                                <input type="radio" name="radio-amount" id="radio-amount-1" value="0" checked="checked">
                                Decided at checkout
                            </label>
                        </div>
                        <div class="radio">
                            <label for="radio-amount-0">
                                <input type="radio" name="radio-amount" id="radio-amount-0" value="1">
                                Set a specific amount(s)
                            </label>
                        </div>
                    </div>
                </div>

                <!-- Amounts and Description fields-->
                <div class="form-group" id="amounts-fields" style="display: none;">
                    <div class="row">
                        <div class="col-sm-4">
                            <label class="control-label" for="amount">Amounts</label>
                        </div>
                        <div class="col-sm-8">
                            <label class="control-label" for="amount-description">Description (optional)</label>
                        </div>
                    </div>

                    <div class="row amount-record">
                        <div class="col-sm-4">
                            <div class="">
                                <div class="input-group">
                                    <span class="input-group-addon">$</span>
                                    <input id="amount" name="amount[]" class="form-control" placeholder="" type="number">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-7">
                            <div class="">
                                <input id="description-amount" name="description-amount[]" type="text" placeholder="" class="form-control input-md">

                            </div>
                        </div>
                        <div class="col-sm-1">
                            <span class="fa fa-times removeNew"></span>
                        </div>
                    </div>
                    <a href="#" id="addNew">+ Add new Amount</a>
                </div>

                <!-- Multiple Radios -->
                <div class="form-group">
                    <label class="control-label" for="frequency">Frequency of Payment</label>
                    <div class="">
                        <div class="radio">
                            <label for="frequency-0">
                                <input type="radio" name="frequency" id="frequency-0" value="1" checked="checked">
                                One time
                            </label>
                        </div>
                        <div class="radio">
                            <label for="frequency-2">
                                <input type="radio" name="frequency" id="frequency-2" value="0">
                                Decided at checkout
                            </label>
                        </div>
                        <div class="radio">
                            <label for="frequency-1">
                                <input type="radio" name="frequency" id="frequency-1" value="2">
                                Recurring
                            </label>
                        </div>
                    </div>
                </div>

                <!-- Select Basic -->
                <div class="form-group" id="frequency-area" style="display: none;">
                    <label class="control-label" for="frequency">Recurring Period</label>
                    <div class="">
                        <select id="frequency" name="frequency" class="form-control">
                            <option value="0">Select the period</option>
                            <option value="7">Weekly</option>
                            <option value="30">Monthly</option>
                            <option value="3">3 months</option>
                            <option value="6">6 months</option>
                            <option value="12">Yearly</option>
                        </select>
                    </div>
                </div>

                <!-- Confirmation -->
                <div class="form-group">
                    <label class="control-label" for="confirmation">After the donor checked out, <br>Show confirmation message</label>
                    <div class="">                     
                        <textarea class="form-control" id="confirmation" name="confirmation"></textarea>
                    </div>
                </div>
                <!-- Textarea -->
                <div class="form-group">
                    <label class="control-label" for="confirmation-email">And Email confirmation message</label>
                    <div class="">                     
                        <textarea class="form-control" id="confirmation-email" name="confirmation-email"></textarea>
                    </div>
                </div>
                
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

                <!-- Button (Double) -->
                <div class="form-group">
                    <label class="control-label" for="save"></label>
                    <div class="">
                        <button type="submit" id="save" name="save" class="btn btn-success">Save &amp; Publish</button>
                        <button type="button" id="draft" name="draft" class="btn btn-default">Save as Draft</button>
                    </div>
                </div>

            </fieldset>
        </form>


    </div>

    <!--Your orders-->
    <hr>
    <div class="container-fluid" id="your-order">
        <!--Header-->
        <h3 class="text-center"><b>YOUR ORDERS</b></h3>
        <!--Table-->
        <table class="table table-striped table-hover" id="order-table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Order #</th>
                    <th>Donated to</th>
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