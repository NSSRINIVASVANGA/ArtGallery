CREATE TABLE if not exists artist(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT,
    genre TEXT
);

CREATE TABLE if not exists art(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    title TEXT,
    theme TEXT,
    artistId INTEGER,
    FOREIGN KEY (artistId) REFERENCES artist(id)
);

CREATE TABLE if not exists gallery(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT,
    location TEXT 
);

CREATE TABLE if not exists artist_gallery(
    artistId INTEGER,
    galleryId INTEGER,
    PRIMARY KEY(artistId,galleryId),
    FOREIGN KEY (artistId) REFERENCES artist(id),
    FOREIGN KEY (galleryId) REFERENCES gallery(id)
);