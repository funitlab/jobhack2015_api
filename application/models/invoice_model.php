<?php

class Invoice_Model extends CI_Model {

    public function __construct() {
        parent::__construct();
    }

    public function getInvoice($id) {
        if ($id) {
            $query = $this->db->get_where('invoice', array('id' => $id));
            $result = $query->row_array();

            // Query for the item description
            $itemQuery = $this->db->get_where('invoice_item', array('invoice_id' => $id));
            $items = $itemQuery->result_array();
            $result['items'] = $items;

            return $result;
        } else {
            $query = $this->db->get('invoice');
            $result = $query->result_array();

            return $result;
        }
    }

    public function getInvoiceByUid($uid) {
        if ($uid) {
            $query = $this->db->get_where('invoice', array('uid' => $uid));
            $result = $query->row_array();

            // Query for the item description
            $itemQuery = $this->db->get_where('invoice_item', array('invoice_id' => $result['id']));
            $items = $itemQuery->result_array();
            $result['items'] = $items;

            return $result;
        } else {
            return false;
        }
    }

    public function getInvoiceByUser($userId) {
        if ($userId) {

            $query = $this->db->get_where('invoice', array('user_id' => $userId));
            $result = $query->result_array();
            return $result;
        } else {
            return false;
        }
    }

    public function getInvoiceByClient($clientId) {
        if ($clientId) {
            // TODO: Combine with the payment of this invoice

            $query = $this->db->get_where('invoice', array('client_id' => $clientId));
            $result = $query->result_array();
            return $result;
        } else {
            return false;
        }
    }

    public function getInvoiceItem($invoiceId) {
        $query = $this->db->get_where('invoice_item', array('invoice_id' => $invoiceId));
        $itemArray = $query->result_array();
        if ($itemArray) {
            foreach ($itemArray as $key => $item) {
                $total = $item['quantity'] * $item['rate'];

                // Discount stuff
                if ($item['discount_type'] == '0') {
                    $total -= round($total * ($item['discount']) / 100, 2);
                } else {
                    $total -= $item['discount'];
                }

                $itemArray[$key]['total'] = $total;
            }
            return $itemArray;
        } else {
            return FALSE;
        }
    }

    public function getTotalAmount($invoiceId) {
        $items = $this->getInvoiceItem($invoiceId);
        $total = 0;
        foreach ($items as $item) {
            $total += $item['total'];
        }
        return $total;
    }

    // Return a object of the invoice
    public function createInvoice($data) {

        // Extract the invoice and the item data
        $invoiceData = $this->getInvoiceData($data);

        // Add Unique ID
        $invoiceData['uid'] = uniqid();

        $result = $this->db->insert('invoice', $invoiceData);


        if ($result) {
            $invoiceId = $this->db->insert_id();

            // Add invoice successful. Now we add Items data
            $itemData = $this->getItemData($data);

            $this->putItems($invoiceId, $itemData);

            $invoiceData['id'] = $invoiceId;

            return $invoiceData;
        } else {
            return FALSE;
        }
    }

    public function updateInvoice($id, $data) {

        $query = $this->db->get_where('invoice', array('id' => $id));

        if ($query->row_array()) {

            $invoiceData = $this->getInvoiceData($data);
            $itemData = $this->getItemData($data);

            $this->putItems($id, $itemData);

            $this->db->where('id', $id);
            return $this->db->update('invoice', $invoiceData);
        } else {
            return $this->putInvoice($id, $data);
        }
    }
    
    public function updateInvoiceTable($id, $data) {

        $query = $this->db->get_where('invoice', array('id' => $id));

        if ($query->row_array()) {

            $this->db->where('id', $id);
            return $this->db->update('invoice', $data);
        } else {
            $data['id'] = $id;
            return $this->db->insert('invoice', $data);
        }
    }

    public function putInvoice($id, $data) {

        // Extract data
        $invoiceData = $this->getInvoiceData($data);
        $itemData = $this->getItemData($data);

        $insertData = array_merge(array('id' => $id), $invoiceData);

        // Add Unique ID
        $insertData['uid'] = uniqid();

        if ($this->db->insert('invoice', $insertData)) {
            return $this->putItems($id, $itemData);
        } else {
            return false;
        }
    }

    // TODO : Catch delete constrain with foreign key.
    public function deleteInvoice($id) {
        try {

            // Delete item first
            $this->db->delete('invoice_item', array('invoice_id' => $id));

            // Delete in Invoice table.
            $result = $this->db->delete('invoice', array('id' => $id));
            if ($this->db->affected_rows() === 0) {
                return FALSE;
            }
            return $result;
        } catch (Exception $e) {
            return FALSE;
        }
    }

    //    To handle the 'amounts' table
    function putItems($invoiceId, $items) {

        // Check if records whether existed or not.
        $query = $this->db->get_where('invoice_item', array('invoice_id' => $invoiceId));

        // If records existed
        if ($query->row()) {
            // Delete the old one
            $this->db->delete('invoice_item', array('invoice_id' => $invoiceId));
        } else {
            // Do nothing
        }
        // Insert new records
        // Generate the data array to insert
        for ($i = 0; $i < count($items); $i++) {
            $items[$i]['invoice_id'] = $invoiceId;
        }
        // Just insert batch:
        return $this->db->insert_batch('invoice_item', $items);
    }

    // Extract the data for donation table from post data.
    function getInvoiceData($data) {
        $invoiceData = $data;
        unset($invoiceData['items']);
        return $invoiceData;
    }

    // Extract the data for Amounts table from post data.
    public function getItemData($data) {
        $amountData = json_decode($data['items'], true);
        return $amountData;
    }

}
