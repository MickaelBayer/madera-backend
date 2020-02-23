alter table "user"
    add firstconnection boolean default true;


INSERT INTO public.role (id, name) VALUES (1, 'Admin');
INSERT INTO public.role (id, name) VALUES (2, 'Commercial');
INSERT INTO public.role (id, name) VALUES (3, 'Bureau d''étude');


alter table "user"
    add isActiv boolean default true;

-- Modifications module 22-2-2020
ALTER TABLE "module"
    ADD COLUMN isActive boolean DEFAULT TRUE;
ALTER TABLE "module"
    ADD COLUMN creationDate timestamptz DEFAULT CURRENT_TIMESTAMP;
SET timezone = 'Europe/Paris';

-- Modifications components 23-2-2020
ALTER TABLE "component"
    DROP COLUMN nature,
    DROP COLUMN default_quantity,
    DROP COLUMN unit_use;
ALTER TABLE "component"
    ADD COLUMN specs VARCHAR,
    ADD COLUMN comment VARCHAR;

-- Modifications / Inserts into component_family 23-2-2020
-- Represents the nature of a component
ALTER TABLE public.component_family
    ADD COLUMN units VARCHAR,
    ADD COLUMN specs VARCHAR;
INSERT INTO public.component_family(name, units, specs)
VALUES
    ('Montant lisse', 'cm (longueur)', 'cm (section)'),
    ('Contrefort', 'cm (longueur)', 'cm (section)'),
    ('Sabot métallique','pièce', 'cm (section)'),
    ('Boulon','pièce', 'cm (section)'),
    ('Gougeon','pièce', 'cm (section)'),
    ('Elément de montage','pièce', 'cm (section)'),
    ('Panneau d''isolation','m²', 'cm (épaisseur)'),
    ('Pare-pluie','m²', 'cm (épaisseur)'),
    ('Panneau intermédiaire','m²', 'mm (épaisseur)'),
    ('Panneau de couverture extérieur','m²', 'mm (épaisseur)'),
    ('Panneau de couverture intérieur','m²', 'mm (épaisseur)'),
    ('Couverture tuiles', NULL, 'mm (longueur et largeur)'),
    ('Couverture ardoises', NULL, 'mm (longueur et largeur)');
