<?php echo form_open('login', array('class' => 'form-signin')) ?>


<img src="<?php echo base_url(); ?>assets/img/logo-home.png" class="img-responsive img-center" id="login-logo" alt="Invoice" width="200">

<div class="<?php echo (form_error('identity')) ? 'has-error' : '' ?> form-group">

    <label for="identity" class="control-label"></label>
    <div class="">
        <?php echo form_input($identity) ?>
        <?php echo form_error('identity') ?>
    </div>

</div>
<div class="<?php echo (form_error('password')) ? 'has-error' : '' ?> form-group">

    <div class="">
        <?php echo form_input($password) ?>
        <?php echo form_error('password') ?>
    </div>

</div>

<div class="">
    <button id="btn_submit" class="btn tn-larbge btn-primary btn-block" type="submit">Sign me in</button>
</div>
<hr>

<div class="checkbox row">
    <div class="col-xs-8">
    <label class="cb_rememberme">
        <?php echo form_checkbox('remember', '1', FALSE, 'id="remember"') ?> Keep me sign in
    </label>
    </div>
    <div class="col-xs-4">
        <a href="<?php echo base_url(); ?>signup">Sign up</a>
    </div>
</div>

<?php echo form_close() ?>
