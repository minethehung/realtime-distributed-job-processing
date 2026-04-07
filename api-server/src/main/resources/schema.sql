CREATE TABLE jobs (
                      id UUID PRIMARY KEY,
                      type VARCHAR(50),
                      payload JSONB,
                      status VARCHAR(20),
                      retry_count INT DEFAULT 0,
                      created_at TIMESTAMP,
                      updated_at TIMESTAMP
);