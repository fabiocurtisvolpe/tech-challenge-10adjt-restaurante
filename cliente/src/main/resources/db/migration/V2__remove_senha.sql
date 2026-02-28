ALTER TABLE public.tb_cliente
    DROP COLUMN IF EXISTS senha;

ALTER TABLE public.tb_cliente_aud
    DROP COLUMN IF EXISTS senha;