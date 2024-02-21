const express = require('express');
const mysql = require('mysql2');

const app = express();

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    database: 'Majdi_db',
    password: "" ,
});

db.connect(err => {
    if (err) {
        console.error('Databasanslutningsfel: ' + err.message);
        return;
    }
    console.log('Framgångsrikt ansluten till databasen');
});

app.get('/', (req, res) => {
    res.send('Hej från Node.js på AWS!');
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Servern kör på port ${PORT}`);
});
