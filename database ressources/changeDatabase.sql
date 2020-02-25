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
INSERT INTO public.component_family(id, name, units, specs)
VALUES
    (1, 'Montant lisse', 'cm (longueur)', 'cm (section)'),
    (2, 'Contrefort', 'cm (longueur)', 'cm (section)'),
    (3, 'Sabot métallique','pièce', 'cm (section)'),
    (4, 'Boulon','pièce', 'cm (section)'),
    (5, 'Gougeon','pièce', 'cm (section)'),
    (6, 'Elément de montage','pièce', 'cm (section)'),
    (7, 'Panneau d''isolation','m²', 'cm (épaisseur)'),
    (8, 'Pare-pluie','m²', 'cm (épaisseur)'),
    (9, 'Pare-vapeur','m²', 'cm (épaisseur)'),
    (10, 'Panneau intermédiaire','m²', 'mm (épaisseur)'),
    (11, 'Panneau de couverture extérieur','m²', 'mm (épaisseur)'),
    (12, 'Panneau de couverture intérieur','m²', 'mm (épaisseur)'),
    (13, 'Couverture', NULL, 'mm (longueur et largeur)');

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

--Maybe useless ?
--Add column range_percent : computed percent value of the module depending on his components
--The range of a module should be computed on range_percent.
ALTER TABLE public.module
    ADD COLUMN range_percent DOUBLE PRECISION;
-- Drop unit_use (in the module.family) + Add specs
ALTER TABLE public.module
    DROP COLUMN unit_use,
    ADD COLUMN specs DOUBLE PRECISION;

-- Pour chaque Module_family, assoscier des familles de composants qui doivent le composer
CREATE TABLE public.modfam_compofam(
   module_family_id INTEGER NOT NULL,
   component_family_id INTEGER NOT NULL,
   PRIMARY KEY(module_family_id, component_family_id)
);
ALTER TABLE public.modfam_compofam
    ADD FOREIGN KEY (module_family_id) REFERENCES public.module_family(id),
    ADD FOREIGN KEY (component_family_id) REFERENCES public.component_family(id);
INSERT INTO public.modfam_compofam(module_family_id, component_family_id)
VALUES
    (1, 11),
    (1, 8),
    (1, 2),
    (1, 9),
    (1, 10),
    (1, 7),
    (1, 1),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 12),
    (2, 12),
    (2, 3),
    (2, 1),
    (2, 7),
    (2, 4),
    (3, 5),
    (3, 3),
    (3, 12),
    (3, 1),
    (3, 7),
    (3, 10),
    (3, 4),
    (4, 12),
    (4, 1),
    (4, 7),
    (4, 6),
    (4, 4),
    (5, 12),
    (5, 7),
    (5, 1),
    (5, 10),
    (5, 6),
    (5, 3),
    (5, 2),
    (6, 2),
    (6, 1),
    (6, 7),
    (6, 10),
    (7, 13),
    (7, 8),
    (7, 1),
    (7, 5),
    (7, 3),
    (7, 9);