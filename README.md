Backend structure :
3 Tables, song artist, album

song:
- title
- filename

artist:
- name
- image_url

album:
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

nb: you cannot update an already created relationship. Instead you have to remove and recreate the relation.

CRUD on all tables. Relations are made after a content is created.
when post a new song, youtube_url must be present to perform a download.

related songs for artists might be handled by song controller 
related artists for albums might be handled by album controller 


// since the relationships are  bidirectional, it will be seen when querying an artist as well
