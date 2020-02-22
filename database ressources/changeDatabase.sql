alter table "user"
    add firstconnection boolean default true;


INSERT INTO public.role (id, name) VALUES (1, 'Admin');
INSERT INTO public.role (id, name) VALUES (2, 'Commercial');
INSERT INTO public.role (id, name) VALUES (3, 'Bureau d''étude');
