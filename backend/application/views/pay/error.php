<!--/* 
 * Code written by Nguyen Van Hung at @imrhung
 * Feel free to re-use or share it.
 * Happy code!!!
 */-->

<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">Some error happened on your Payment</h3>
    </div>
    <div class="panel-body">
        <?php echo $error ?>
    </div>
    <div class="panel-footer">
        <a href="<?php echo base_url(); ?>pay/index/<?php echo $invoice_uid?>" id="refresh" class="text-small">Try again!</a>
    </div>
</div>