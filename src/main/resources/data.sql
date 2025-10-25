-- Initialize sample data

-- Insert sample agencies
INSERT INTO agencies (id, code, name, description) VALUES (1, 'AGN001', 'Department of Education', 'State education department');
INSERT INTO agencies (id, code, name, description) VALUES (2, 'AGN002', 'Department of Health', 'State health department');
INSERT INTO agencies (id, code, name, description) VALUES (3, 'AGN003', 'Department of Transportation', 'State transportation department');

-- Insert sample budget codes
INSERT INTO budget_codes (id, code, description, agency_id) VALUES (1, 'BC001', 'Operating Expenses', 1);
INSERT INTO budget_codes (id, code, description, agency_id) VALUES (2, 'BC002', 'Capital Projects', 1);
INSERT INTO budget_codes (id, code, description, agency_id) VALUES (3, 'BC003', 'Personnel Costs', 1);
INSERT INTO budget_codes (id, code, description, agency_id) VALUES (4, 'BC004', 'Operating Expenses', 2);
INSERT INTO budget_codes (id, code, description, agency_id) VALUES (5, 'BC005', 'Medical Supplies', 2);

-- Insert active biennium (2023-2025, currently in first year)
INSERT INTO biennium (id, start_year, end_year, current_fiscal_year, active) VALUES (1, 2023, 2024, 2023, true);

-- Insert sample categories
INSERT INTO categories (id, title, description, agency_id, created_by, updated_by, created_at, updated_at) 
VALUES (1, 'Administration', 'Administrative costs and overhead', 1, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO categories (id, title, description, agency_id, created_by, updated_by, created_at, updated_at) 
VALUES (2, 'Programs', 'Program-specific expenses', 1, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO categories (id, title, description, agency_id, created_by, updated_by, created_at, updated_at) 
VALUES (3, 'Infrastructure', 'Infrastructure and facilities', 1, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
