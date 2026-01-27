DELETE FROM subscription;
INSERT INTO subscription (name, description, price, image_url, category) VALUES
                                                                             ('Katastrophen-Kochbox', 'Rezepte die scheitern + Rettung', 34.99, '/assets/images/kochbox.jpg', 'food'),
                                                                             ('Resteparty-Challenge', 'Oliven + Marshmallows Challenge', 59.99, '/assets/images/resteparty.jpeg', 'food'),
                                                                             ('Faulen-Frühstück', 'Direkt aus Packung frühstücken', 9.99, '/assets/images/faulfruehstueck.jpg', 'food'),
                                                                             ('Montags-Rettungspaket', 'Anti-Montagsfrust Survival', 49.99, '/assets/images/montag.jpg', 'office'),
                                                                             ('Peinliche-Meeting-Notizen', 'Ich tu so als schreibe ich', 8.99, '/assets/images/meeting.jpeg', 'office'),
                                                                             ('Pyjama-Business', 'Oben seriös, unten gemütlich', 19.99, '/assets/images/pyjama.jpg', 'office'),
                                                                             ('Bug-Fix-Goodies', 'NullPointer-Gummibärchen + Duck', 16.99, '/assets/images/bugfix.jpeg', 'nerd'),
                                                                             ('Mini-Apokalypse-Box', 'WLAN-Ausfall + Eltern-Notfall', 69.99, '/assets/images/apokalypse.jpg', 'nerd'),
                                                                             ('Random-Fandom-Mix', 'Neues Universum monatlich', 24.99, '/assets/images/fandom.jpeg', 'nerd');
-- NEU: Users
INSERT INTO users (username, password) VALUES ('student', 'password123');
INSERT INTO users (username, password) VALUES ('admin', 'admin');

-- NEU: Cart für student mit Abos
INSERT INTO cart (user_id) VALUES (1);
INSERT INTO cart_subscriptions (cart_id, subscriptions_id) VALUES (1, 1);  -- Katastrophen-Kochbox
INSERT INTO cart_subscriptions (cart_id, subscriptions_id) VALUES (1, 7);  -- Bug-Fix-Goodies