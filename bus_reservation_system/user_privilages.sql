CREATE USER admin INDETIFIED BY "Admin123$";
GRANT ALL PRIVILAGES *.* admin@localhost;

CREATE USER customer INDETIFIED BY "Customer123$";
GRANT ALL PRIVILAGES *.* customer@localhost;
/* we will change the permissions later*/