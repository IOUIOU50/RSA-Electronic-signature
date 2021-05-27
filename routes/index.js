var express = require('express');
var router = express.Router();



/* GET home page. */

router.get('/', (req, res) => {
  console.log('post request from ', req.headers['x-forwarded-for'] || req.connection.remoteAddress)
  res.render('index.ejs', {title : "express"});
})


router.post('/', function(req, res, next) {
  console.log(req.body.encodedText);
  console.log('post request from ', req.headers['x-forwarded-for'] || req.connection.remoteAddress)
  res.send('hello world');
});



module.exports = router;
