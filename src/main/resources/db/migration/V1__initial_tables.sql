
CREATE TABLE customers (
  id UUID PRIMARY KEY,
  full_name VARCHAR(255) NOT NULL,
  type VARCHAR(255) NOT NULL,
  document VARCHAR(255) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE wallets (
  id UUID PRIMARY KEY,
  customer_id UUID NOT NULL,
  balance DECIMAL(10, 2) NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE transactions (
  id UUID PRIMARY KEY,
  payer_id UUID NOT NULL,
  payee_id UUID NOT NULL,
  amount DECIMAL(10, 2) NOT NULL,
  FOREIGN KEY (payer_id) REFERENCES customers(id),
  FOREIGN KEY (payee_id) REFERENCES customers(id)
);