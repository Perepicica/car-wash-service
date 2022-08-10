CREATE INDEX idx_box_operator
    ON box(operator_id);
CREATE INDEX idx_appointment_box
    ON appointment(box_id);
CREATE INDEX idx_appointment_customer
    ON appointment(customer_id);
CREATE INDEX idx_appointment_date
    ON appointment(app_date);