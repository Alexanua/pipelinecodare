// Ladda in miljövariabler från en specifik .env-fil
require('dotenv').config({ path: '/Users/macbook/Downloads/pipelinecodare/my-node-app/majdi.env' });

const express = require('express');
const mysql = require('mysql2');

// Skapar en instans av express-appen
const app = express();

// Skapar databasanslutningen med konfiguration från .env-filen
const db = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    database: process.env.DB_DATABASE,
    password: process.env.DB_PASSWORD,
});

// Försöker ansluta till databasen
db.connect(err => {
    if (err) {
        console.error('Databasanslutningsfel: ' + err.message);
        return;
    }
    console.log('Framgångsrikt ansluten till databasen');
});

// Definierar en grundläggande rutt
app.get('/', (req, res) => {
    res.send('Hej från Node.js på AWS!');
});

// Startar servern på den port som specificeras i .env, eller 3000 som fallback
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Servern kör på port ${PORT}`);
});
