<?php echo form_open('signup', array('class' => 'form-signup')) ?>


<img src="<?php echo base_url(); ?>assets/img/logo-home.png" class="img-responsive img-center" id="login-logo" alt="Donation" width="200">
<hr>
<div style="text-align: center;">
    <h2><b>Sign up</b></h2>
</div>

<div class="<?php echo (form_error('name')) ? 'has-error' : '' ?> form-group">

    <div class="">
        <?php echo form_input($name) ?>
        <?php echo form_error('name') ?>
    </div>
</div>
<div class="<?php echo (form_error('identity')) ? 'has-error' : '' ?> form-group">

    <div class="">
        <?php echo form_input($identity) ?>
        <?php echo form_error('identity') ?>
    </div>

</div>
<div class="<?php echo (form_error('email')) ? 'has-error' : '' ?> form-group">

    <div class="">
        <?php echo form_input($email) ?>
        <?php echo form_error('email') ?>
    </div>

</div>
<div class="<?php echo (form_error('password')) ? 'has-error' : '' ?> form-group">

    <div class="">
        <?php echo form_input($password) ?>
        <?php echo form_error('password') ?>
    </div>

</div>
<div class="<?php echo (form_error('passconf')) ? 'has-error' : '' ?> form-group">

    <div class="">
        <?php echo form_input($passconf) ?>
        <?php echo form_error('passconf') ?>
    </div>

</div>

<div class="">
    <button id="btn_submit" class="btn tn-larbge btn-primary btn-block" type="submit">Sign me up</button>
</div>
<hr>

<div>
    Already signed up, 
    <a href="<?php echo base_url(); ?>login">Sign in</a>
</div>

<?php echo form_close() ?>
