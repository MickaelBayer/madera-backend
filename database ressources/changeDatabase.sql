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

-- Modif id component 23-2-2020
CREATE SEQUENCE component_id_seq MINVALUE 10;
ALTER TABLE public.component
  ALTER id SET DEFAULT nextval('component_id_seq');

-- Insert ranges 24-2-2020
INSERT INTO public.ranges(id, name, type, percentage_final_price)
VALUES
    (1, 'Premium', 'Deutsch qualitat !', 1.15),
    (2, 'Classic', 'We doing our best ...', 1.07),
    (3, 'Basic', 'You get what you get ...', 1.00);

-- Modif component 24-2-2020
ALTER TABLE public.component
    ADD COLUMN provider INTEGER,
    ADD COLUMN ranges INTEGER;
ALTER TABLE public.component
    ADD FOREIGN KEY (provider) REFERENCES public.provider(id);
ALTER TABLE public.component
    ADD FOREIGN KEY (ranges) REFERENCES public.ranges(id);

-- ADD Module_Family / nature 25-2-2020
CREATE TABLE public.module_family(
    id serial PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    specs VARCHAR NOT NULL,
    units VARCHAR NOT NULL
    );
ALTER TABLE public.module
    DROP COLUMN nature;
ALTER TABLE public.module
    ADD COLUMN family INTEGER;
ALTER TABLE public.module
    ADD FOREIGN KEY (family) REFERENCES public.module_family(id);
INSERT INTO public.module_family(id, name, specs, units)
VALUES
    (1, 'Mur extérieur', 'Hauteur-Longueur', 'm linéaire'),
    (2, 'Mur intérieur (porteur)', 'Hauteur-Longueur', 'm linéaire'),
    (3, 'Cloison intérieure', 'Hauteur-Longueur', 'm linéaire'),
    (4, 'Plancher sur dalle', 'Hauteur-Longueur', 'm²'),
    (5, 'Plancher porteur', 'Hauteur-Longueur', 'm²'),
    (6, 'Ferme de charpente', 'Longueur', 'unité'),
    (7, 'Couverture (toit)', 'Hauteur-Longueur', 'm²');