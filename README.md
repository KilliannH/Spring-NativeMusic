current release is : v1.3
----------------
Backend structure :
3 Tables, songs, artists, albums

songs:
- title
- filename

artists:
- name
- image_url

albums:
- title
- image_url

many to many bidirectional relations on songs n <-> n artists, artists <-> albums.
one to many bidirectional relation on songs n <-> 1 albums

to manage relations :
create
post /songs/id/artists/artistId
post /albums/id/artists/artistId
post /songs/id/albums/albumId
(no req body needed)

remove
delete /songs/id/artists/artistId
delete /albums/id/artists/artistId
delete /songs/id/albums/delete // as only one album is related to n songs.

nb for manyTomany: you cannot update an already created relationship. You have to remove and recreate it instead.
    for oneToMany: a post over an already created relation will replace it.

CRUD on all tables. Relations are made ALWAYS AFTER a content is created.
when creating a new song, ytUrl property must be specified to perform a download.
cf. post songs schema bellow.

related songs for artists might be handled by song controller 
related artists for albums might be handled by album controller 

// Relationships are  bidirectional.

Querying one song will show a list of songs with a list of artists foreach songs & a list of albums foreach songs.
Querying one artist will show a list of artists with a list of songs foreach artists & a list of albums foreach artists.
Querying one album will show a list of albums with a list of songs foreach albums & a list of artists foreach albums.

All API responses are properly Serialized by Jackson.

The db has three tables on startup : users, roles & users_roles.
There must be an sql file to create those tables & feed them with some data.

user:
- username
- password
- enabled

pk (username)

user_role:
- user_role_id
- username
- role

fk (username) references users (username)

The API is properly secured by a token Based authentication :
authentication endpoint : "/authenticate"
Currently, user has to be registered with ROLE_USER privileges to query endpoints

todo -- download song & store it when creating a new song with ytdownload : done on a separate nodejs server
-- stream a song at "/stream" + song.filename ex. /stream/myMusic.mp3: done on nodejs server
test auth with failed password : done

toString method on Models does not include relationship because app crashes when querying relations & JPA session is closed,
because relations are lazy loaded (except users <-> roles, relation is Eagger).
 
-- post /songs/
{
    "title": "myTitle",
    "filename": "myFilename.mp3"
}
-- limit queries (by ids): example /songs/limit?start=0&end=10

remember to set your app context in application.properties

POST /api/songs/byAlbum {albumId: 1}
POST /api/songs/byArtists {artistsIds: [1, 2]}
POST /api/playlists/bySongAndName {songId: [1], name: "Favorite"}
