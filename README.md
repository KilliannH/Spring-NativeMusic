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
(no body needed)

remove
delete /songs/id/artists/artistId

nb: you cannot update an already created relationship.

CRUD on all tables. Relations are made after a content is created.
when post a new song, youtube_url must be present to perform a download.

related songs for artists might be handled by song controller 
// since the relationship is  bidirectional, it will be seen when querying an artist as well

