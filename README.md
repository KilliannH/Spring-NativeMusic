current release is : v1.1
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

many to many bidirectional relations to all tables.

to manage relations :
create
post /songs/id/artists/artistId
post /albums/id/artists/artistId
(no body needed)

remove
delete /songs/id/artists/artistId
delete /albums/id/artists/artistId

nb: you cannot update an already created relationship. You have to remove and recreate it instead.

CRUD on all tables. Relations are made after a content is created.
when creating a new song, ytUrl property must be present to perform a download.
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

todo -- download song & store it when creating a new song with ytdownload
-- stream a song at "/stream" + song.filename ex. /stream/myMusic.mp3: done
-- verify bcrypt passwordEncoder when creating a new user / authenticate user : done
test auth with failed password : done

toString method on Models does not include relationship because app crashes when querying relations & JPA session is closed,
because relations are lazy loaded (except users <-> roles, relation is Eagger).
 
bodyRequest on "/authenticate" must be the password without encryption.
Because encrypt pwd in frontend must introduce a security failure.
(giving the hash, and the hashMethod in the source code on the client side)

-- post /songs/
{
    "ytUrl": "my.youtube.url",
    "song": {
        "title": "myTitle",
        "filename": "myFilename.mp3"
    }
}

remeber to set your app context in constants/MyLinks.java
