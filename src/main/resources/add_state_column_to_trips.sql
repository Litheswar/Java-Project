-- Add state column to trips table to store the selected state/province
ALTER TABLE trips ADD COLUMN state VARCHAR(100);

-- Add a comment to describe the purpose of the column
COMMENT ON COLUMN trips.state IS 'Selected state or province for the trip';