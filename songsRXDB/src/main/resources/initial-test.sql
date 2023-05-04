insert into test."user" (userid, lastname, firstname) values ('mmuster', 'Muster', 'Maxime');
insert into test."user" (userid, lastname, firstname) values ('eschuler', 'Schuler', 'Elena');
insert into test."song" (title, artist, album, released) values ('Can''t Stop the Feeling', 'Justin Timberlake', 'Trolls', 2016);
insert into test."song" (title, artist, album, released) values ('Mom', 'Meghan Trainor, Kelli Trainor', 'Thank You', 2016);
insert into test."song" (title, artist, album, released) values ('Team', 'Iggy Azalea', '', 2016);
insert into test."song" (title, artist, album, released) values ('Ghostbusters (I''m not a fraid)', 'Fall Out Boy, Missy Elliott', 'Ghostbusters', 2016);
insert into test."song" (title, artist, album, released) values ('Bad Things', 'Camila Cabello, Machine Gun Kelly', 'Bloom', 2017);
insert into test."song" (title, artist, album, released) values ('I Took a Pill in Ibiza', 'Mike Posner', 'At Night, Alone.', 2016);
insert into test."song" (title, artist, album, released) values ('i hate u, i love u', 'Gnash', 'Top Hits 2017', 2017);
insert into test."song" (title, artist, album, released) values ('No', 'Meghan Trainor', 'Thank You', 2016);
insert into test."song" (title, artist, album, released) values ('Private Show', 'Britney Spears', 'Glory', 2016);
insert into test."song" (title, artist, album, released) values ('7 Years', 'Lukas Graham', 'Lukas Graham (Blue Album)', 2015);
insert into test."songlist" (name, owner, personal, playlist) values ('mix', 1, FALSE, '{3,6,9}')
insert into test."songlist" (name, owner, personal, playlist) values ('maxmix', 1, FALSE, '{1,3,4,6,7,8}')
insert into test."songlist" (name, owner, personal, playlist) values ('lame', 1, TRUE, '{5,1,3}')
insert into test."songlist" (name, owner, personal, playlist) values ('superdupermix', 2, TRUE, '{1,2,3,4,5,6,7,8,9,10}')
insert into test."songlist" (name, owner, personal, playlist) values ('DIEliste', 2, FALSE, '{4,5,1,2,8,10,1,3}')
insert into test."songlist" (name, owner, personal, playlist) values ('masterlist', 2, TRUE, '{4,8,10,1,3}')
commit