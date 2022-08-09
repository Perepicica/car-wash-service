ALTER TABLE appointment ADD ends_at TIME;
ALTER TABLE appointment RENAME COLUMN time TO starts_at;
ALTER TABLE appointment RENAME COLUMN date TO app_date;
ALTER TABLE appointment DROP COLUMN duration;