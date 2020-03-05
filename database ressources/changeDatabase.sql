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

--25-02-2020
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

--26-02-2020
--Modif module_component table
DROP TABLE public.module_component;
CREATE TABLE public.module_component(
   module_id INTEGER NOT NULL,
    component_id INTEGER NOT NULL,
  PRIMARY KEY(module_id, component_id)
);
ALTER TABLE public.module_component
    ADD FOREIGN KEY (module_id) REFERENCES public.module(id),
    ADD FOREIGN KEY (component_id) REFERENCES public.component(id);
--Creation of the angle enum
CREATE TYPE angle AS ENUM ('Sans', 'Sortant', 'Entrant');
--Creation of the angle enum
CREATE TABLE public.cctp(
   id serial PRIMARY KEY,
   name VARCHAR NOT NULL
);
INSERT INTO public.cctp(id, name)
VALUES
    (1, 'Cctp 1'),
    (2, 'Cctp 2'),
    (3, 'Cctp 3'),
    (4, 'Cctp 4'),
    (5, 'Cctp 5');
--Delete angle and cctp from module
ALTER TABLE public.module
    DROP COLUMN angle,
    DROP COLUMN cctp;
ALTER TABLE public.module
    ADD COLUMN cctp INTEGER NOT NULL;
ALTER TABLE public.module
    ADD FOREIGN KEY (cctp) REFERENCES public.cctp(id);
ALTER TABLE public.module
    DROP COLUMN specs,
    ADD COLUMN specs VARCHAR NOT NULL;

--28-2-2020
ALTER TABLE public.project
    ADD COLUMN range INTEGER;
ALTER TABLE public.project
    ADD FOREIGN KEY (range) REFERENCES public.ranges(id);

--29-02-2020
ALTER TABLE "module"
    DROP COLUMN creationDate;
ALTER TABLE "module"
    ADD COLUMN created_at timestamptz DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE "project"
    ADD COLUMN created_at timestamptz DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE "project"
    DROP CONSTRAINT project_user_id_fk,
    DROP COLUMN "user",
    ADD COLUMN commercial INTEGER,
    ADD FOREIGN KEY (commercial) REFERENCES public.user(id);

INSERT INTO module_family(id, name, specs, units)
VALUES
    (8, 'Angle', 'Longueur', 'degrés');

--01-3-2020
ALTER TABLE public.project_module
    DROP COLUMN "width",
    DROP COLUMN "length",
    DROP COLUMN "price",
    ADD COLUMN "position" INTEGER,
    ADD COLUMN "quantity" DOUBLE PRECISION;
DROP TABLE module_equipment;
DROP TABLE equipment;


--05-03-2020

INSERT INTO public.quotation_state (id, name) VALUES (1, 'Brouillon');
INSERT INTO public.quotation_state (id, name) VALUES (2, 'Accepté');
INSERT INTO public.quotation_state (id, name) VALUES (3, 'En attente');
INSERT INTO public.quotation_state (id, name) VALUES (4, 'Refusé');
INSERT INTO public.quotation_state (id, name) VALUES (5, 'En commande');
INSERT INTO public.quotation_state (id, name) VALUES (6, 'Transfert en facturation');
