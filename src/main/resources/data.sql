INSERT INTO employees (user_name, password, role, create_at, valid_until, is_valid)
SELECT 'manager123', '$2a$13$QE.IGCEOUIREmnCFEBPEueq808yDNBObwKC9CVfBbS5kDPN6n5QPq', 'MANAGER', EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM (NOW() + INTERVAL '7 days')) * 1000, true
WHERE NOT EXISTS (SELECT 1 FROM employees);