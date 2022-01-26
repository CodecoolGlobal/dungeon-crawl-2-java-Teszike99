DROP TABLE IF EXISTS public.game_state;
CREATE TABLE public.game_state (
    id serial NOT NULL PRIMARY KEY,
    current_map text NOT NULL,
    map_id integer NOT NULL,
    saved_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    player_id integer NOT NULL
);

DROP TABLE IF EXISTS public.player;
CREATE TABLE public.player (
    id serial NOT NULL PRIMARY KEY,
    player_name text NOT NULL,
    hp integer NOT NULL,
    strength integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL
);

DROP TABLE IF EXISTS public.enemy;
CREATE TABLE public.enemy (
    id serial NOT NULL PRIMARY KEY,
    enemy_name text NOT NULL,
    strength integer NOT NULL,
    hp integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL
);

DROP TABLE IF EXISTS public.item;
CREATE TABLE public.item (
    id serial NOT NULL PRIMARY KEY,
    item_name text NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL
);

DROP TABLE IF EXISTS public.map;
CREATE TABLE public.map (
    id serial NOT NULL PRIMARY KEY,
    map_name char varying(10)
);

ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);

ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_map_id FOREIGN KEY (map_id) REFERENCES public.map(id);

