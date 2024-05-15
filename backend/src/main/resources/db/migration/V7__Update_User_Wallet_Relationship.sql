-- Add wallet_id column to user table if it does not exist
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'user'
        AND column_name = 'wallet_id'
    ) THEN
ALTER TABLE public."user"
    ADD COLUMN wallet_id BIGINT;
END IF;
END $$;

-- Add foreign key constraint if it does not exist
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fk_wallet_on_user'
    ) THEN
ALTER TABLE wallet
    ADD CONSTRAINT FK_WALLET_ON_USER
        FOREIGN KEY (user_id) REFERENCES public."user" (id)
            ON DELETE CASCADE;
END IF;
END $$;

-- Add unique constraint if it does not exist
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'uc_user_wallet'
    ) THEN
ALTER TABLE public."user"
    ADD CONSTRAINT UC_USER_WALLET
        UNIQUE (wallet_id);
END IF;
END $$;
