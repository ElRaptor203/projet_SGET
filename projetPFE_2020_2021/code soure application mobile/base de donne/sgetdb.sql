PGDMP                          y           sgetdb    12.7 (Debian 12.7-1.pgdg90+1)    13.2     R           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            S           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            T           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            U           1262    16449    sgetdb    DATABASE     Z   CREATE DATABASE sgetdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';
    DROP DATABASE sgetdb;
                postgres    false                        2615    16450    raptor    SCHEMA        CREATE SCHEMA raptor;
    DROP SCHEMA raptor;
                postgres    false            ?            1259    16451    planning_emploie_de_temps    TABLE     ?  CREATE TABLE raptor.planning_emploie_de_temps (
    id integer NOT NULL,
    jours text NOT NULL,
    semaine text NOT NULL,
    descrip_semaine text NOT NULL,
    matiere1 text NOT NULL,
    ens_matiere1 text NOT NULL,
    matiere2 text NOT NULL,
    ens_matiere2 text NOT NULL,
    matiere3 text NOT NULL,
    ens_matiere3 text NOT NULL,
    matiere4 text NOT NULL,
    ens_matiere4 text NOT NULL,
    option text NOT NULL,
    filiere text NOT NULL
);
 -   DROP TABLE raptor.planning_emploie_de_temps;
       raptor         heap    postgres    false    8            ?            1259    16457 '   emploie_de_temps_id_emploie_de_temp_seq    SEQUENCE     ?   ALTER TABLE raptor.planning_emploie_de_temps ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME raptor.emploie_de_temps_id_emploie_de_temp_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000
    CACHE 1
);
            raptor          postgres    false    203    8            ?            1259    16459    etudiant    TABLE     ?   CREATE TABLE raptor.etudiant (
    id_etudiant integer NOT NULL,
    nom text NOT NULL,
    prenom text NOT NULL,
    date_naissance text NOT NULL,
    matricule text NOT NULL,
    option text NOT NULL
);
    DROP TABLE raptor.etudiant;
       raptor         heap    postgres    false    8            ?            1259    16465    etudiant_id_etudiant_seq    SEQUENCE     ?   ALTER TABLE raptor.etudiant ALTER COLUMN id_etudiant ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME raptor.etudiant_id_etudiant_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000
    CACHE 1
);
            raptor          postgres    false    8    205            ?            1259    16467    user    TABLE     ?   CREATE TABLE raptor."user" (
    id_user integer NOT NULL,
    nom text NOT NULL,
    prenom text NOT NULL,
    login text NOT NULL,
    password text NOT NULL,
    satus text NOT NULL,
    photo_profile text NOT NULL,
    matricule text NOT NULL
);
    DROP TABLE raptor."user";
       raptor         heap    postgres    false    8            ?            1259    16473    user_id_user_seq    SEQUENCE     ?   ALTER TABLE raptor."user" ALTER COLUMN id_user ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME raptor.user_id_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000
    CACHE 1
);
            raptor          postgres    false    207    8            L          0    16459    etudiant 
   TABLE DATA           _   COPY raptor.etudiant (id_etudiant, nom, prenom, date_naissance, matricule, option) FROM stdin;
    raptor          postgres    false    205   ?       J          0    16451    planning_emploie_de_temps 
   TABLE DATA           ?   COPY raptor.planning_emploie_de_temps (id, jours, semaine, descrip_semaine, matiere1, ens_matiere1, matiere2, ens_matiere2, matiere3, ens_matiere3, matiere4, ens_matiere4, option, filiere) FROM stdin;
    raptor          postgres    false    203   ?       N          0    16467    user 
   TABLE DATA           h   COPY raptor."user" (id_user, nom, prenom, login, password, satus, photo_profile, matricule) FROM stdin;
    raptor          postgres    false    207          V           0    0 '   emploie_de_temps_id_emploie_de_temp_seq    SEQUENCE SET     W   SELECT pg_catalog.setval('raptor.emploie_de_temps_id_emploie_de_temp_seq', 191, true);
          raptor          postgres    false    204            W           0    0    etudiant_id_etudiant_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('raptor.etudiant_id_etudiant_seq', 4, true);
          raptor          postgres    false    206            X           0    0    user_id_user_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('raptor.user_id_user_seq', 7, true);
          raptor          postgres    false    208            ?
           2606    16476 /   planning_emploie_de_temps emploie_de_temps_pkey 
   CONSTRAINT     m   ALTER TABLE ONLY raptor.planning_emploie_de_temps
    ADD CONSTRAINT emploie_de_temps_pkey PRIMARY KEY (id);
 Y   ALTER TABLE ONLY raptor.planning_emploie_de_temps DROP CONSTRAINT emploie_de_temps_pkey;
       raptor            postgres    false    203            ?
           2606    16478    etudiant etudiant_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY raptor.etudiant
    ADD CONSTRAINT etudiant_pkey PRIMARY KEY (id_etudiant);
 @   ALTER TABLE ONLY raptor.etudiant DROP CONSTRAINT etudiant_pkey;
       raptor            postgres    false    205            ?
           2606    16480    user user_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY raptor."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id_user);
 :   ALTER TABLE ONLY raptor."user" DROP CONSTRAINT user_pkey;
       raptor            postgres    false    207            L   ?   x?U??
?0 ?绯?,?35CE?C?[/Ö??n#??$B?G¤?p?ฟ^Ə4XCO??(0Q??P4?/;e??!?	ԑ?*f18?&??`?f?YS? ?????Jw??/-???x]?-??????&?t~????ݗ?j???a?} m3_      J   ?  x???MO?0????$-mz,??,٥?VH{?&Cj??8h??qJB???.8????3???G?ݮOx.F\??-??????ӭF?3?????*?T?$d?̠@2?pG?4G??@R?????DΗ??h?L&?&T?Ly0<l??????S8
???'??z????73?s?@k??h?ĨXq?sÄ?S?*???e??? ?p?????{w???ƇZ1	&ϕ԰?m?30?1V"+???,?#T:E????3e?*?F?$_.jQ?>KaH?=?ipr#????׷?#?(????{tc?&Yf4}@?????h????????=h???g:0???.??T?9M????/_&?Hǃ??&x?ڦ{դ????q?ZN???S?>:?{{%?k,~l?wW@???m???{%?k???+?oY7???
?5??@???m?z??,@???5?4?}o????? ?o?@?1?E??wV@?????Fh`??\*?Rf???w??ƃ0?霡֋m????^?/??[]?????V??N9 v{??6?:3g2͠	A4%?.\???"AQ??H]??g?W]?<i?6??8??,????o?zdĿw????f?Y<??5_?b?????;??6۽?{??ȿv???H      N   ?   x????1???)??%v??8p?M"Mw??vfU??!???????S?TEtуW-4lKN?C,?? ??5I???J???7ǈ0n???a?Y??O??nCL??u?줣?z?g?PdP+??????TqT`?o??~d?|ݠKo~?ol޲IҲ9???K???5X6?????????N??l!6]!?*?}?     